/*
 * Copyright LWJGL. All rights reserved.
 * License terms: http://lwjgl.org/license.php
 */
package org.lwjgl.generator

import java.io.PrintWriter
import java.lang.Math.*
import java.util.*
import java.util.regex.Pattern
import kotlin.properties.Delegates

val INSTANCE = "__instance"
val EXT_FLAG = ""

val NULL = "{@code NULL}"

abstract class FunctionProvider(
	packageName: String,
	className: String
): CustomClass(packageName, className) {

	{
		javaImport(
			"org.lwjgl.system.*",
			"java.util.Set"
		)
	}

	private val _classes: MutableList<NativeClass> = ArrayList()

	protected fun getClasses(
		comparator: (NativeClass, NativeClass) -> Int = {(o1, o2) -> o1.templateName compareTo o2.templateName }
	): List<NativeClass> {
		val classes = ArrayList(_classes)
		Collections.sort(classes, object: Comparator<NativeClass> { // TODO: Kotlin bug: Can't use SAM conversion on JDK 8
			suppress("PARAMETER_NAME_CHANGED_ON_OVERRIDE")
			override fun compare(o1: NativeClass, o2: NativeClass) = comparator(o1, o2)
		})
		return classes
	}

	override fun getLastModified(root: String): Long {
		return max(
			super.getLastModified(root),
			getDirectoryLastModified("$root/templates")
		)
	}

	fun addCapabilities(clazz: NativeClass) {
		_classes add clazz
	}

	/** If true, different platforms/devices/contexts return different function addresses. */
	open val isLocal: Boolean = false // GL & CL are global, AL is local

	/** If false, a capabilities instance is not available in the current thread or process. A parameter must provide the instance. */
	open val hasCurrentCapabilities: Boolean = true // GL has thread-local capabilities, AL has process-wide capabilities (unless ALC_EXT_thread_local_context is used), CL has the ICD.

	open fun generateFunctionAddress(writer: PrintWriter, function: NativeClassFunction) {
		val instanceParameter = if ( hasCurrentCapabilities )
			""
		else if ( function has Capabilities ) {
			val caps = function[Capabilities]
			if ( caps.statement != null )
				writer.println("\t\t${caps.statement};")
			caps.expression
		} else {
			try {
				// Use the first ObjectType parameters
				function.getParams() { it.nativeType is ObjectType }.first().name
			} catch (e: Exception) {
				throw IllegalStateException("Neither a Capabilities modifier nor an object parameter were found on function ${function.name}")
			}
		}

		if ( function has Capabilities && function[Capabilities].override ) {
			if ( !instanceParameter.equals(FUNCTION_ADDRESS) ) // Skip if we have an explicit FUNCTION_ADDRESS parameter.
				writer.println("\t\tlong $FUNCTION_ADDRESS = $instanceParameter;")
		} else if ( function.hasParam { it has Callback && it[Callback].storeInFunctions } ) {
			writer.println("\t\t${function.nativeClass.className} $INSTANCE = getInstance($instanceParameter);")
			writer.println("\t\tlong $FUNCTION_ADDRESS = $INSTANCE.${function.addressName};")
		} else
			writer.println("\t\tlong $FUNCTION_ADDRESS = getInstance($instanceParameter).${function.addressName};")
	}

	open fun printFunctionsParams(writer: PrintWriter, nativeClass: NativeClass) {
	}
	open fun getFunctionAddressCall(function: NativeClassFunction) = "provider.getFunctionAddress(\"${function.name}\")"

	abstract fun PrintWriter.generateFunctionGetters(nativeClass: NativeClass)

}
// TODO: Remove if KT-457 or KT-1183 are fixed.
private fun FunctionProvider.generateFunctionGetters(writer: PrintWriter, nativeClass: NativeClass) = writer.generateFunctionGetters(nativeClass)

class NativeClass(
	packageName: String,
	className: String,
	nativeSubPath: String,
	val templateName: String = className,
	val prefix: String,
	val prefixMethod: String,
	val prefixConstant: String,
	val prefixTemplate: String,
	val postfix: String,
	val functionProvider: FunctionProvider?
): GeneratorTargetNative(packageName, className, nativeSubPath) {

	private val constantBlocks = ArrayList<ConstantBlock<out Any>>()

	private val _functions = LinkedHashMap<String, NativeClassFunction>()
	val functions: Iterable<NativeClassFunction>
		get() = _functions.values()

	val hasBody: Boolean
		get() = !constantBlocks.isEmpty() || hasNativeFunctions

	val hasNativeFunctions: Boolean
		get() = !_functions.isEmpty()

	public val link: String get() = "{@link ${this.className} ${this.templateName}}"

	override fun processDocumentation(documentation: String): String = processDocumentation(documentation, prefixConstant, prefixMethod)

	override fun PrintWriter.generateJava() {
		print(HEADER)
		println("package $packageName;\n")

		if ( !_functions.isEmpty() ) {
			println("import org.lwjgl.*;")
			println("import org.lwjgl.system.*;\n")

			val hasNIO = functions.any { it.returns.isBufferPointer || it.hasParam { it.isBufferPointer } }

			if ( hasNIO )
				println("import java.nio.*;\n")

			println("import static org.lwjgl.system.Checks.*;")
			if ( hasNIO ) {
				var needsPointer = false
				var needsAPIUtil = false
				functions forEach {
					if ( it.hasParam { it.nativeType.mapping == PointerMapping.DATA_POINTER } )
						needsPointer = true

					if ( it.hasParam { it has Return || it has SingleValue || it.isAutoSizeResultOut || it has PointerArray } )
						needsAPIUtil = true
				}

				if ( needsPointer )
					println("import static org.lwjgl.Pointer.*;")
				println("import static org.lwjgl.system.MemoryUtil.*;")
				if ( needsAPIUtil )
					println("import static org.lwjgl.system.APIUtil.*;")
			}
			println()
		}

		preamble.printJava(this)

		val documentation = this@NativeClass.documentation
		if ( documentation != null )
			println(processDocumentation(documentation).toJavaDoc(indentation = ""))
		println("${access.modifier}final class $className {\n")

		constantBlocks.forEach {
			it.generate(this)
		}

		if ( functionProvider != null && !_functions.isEmpty() ) {
			generateFunctionAddresses(functionProvider)
			functionProvider.generateFunctionGetters(this, this@NativeClass)
		} else {
			if ( !_functions.isEmpty() )
				println("\tstatic { Sys.touch(); }\n")

			println("\tprivate $className() {}\n")
		}

		functions.forEach {
			println("\t// --- [ ${it.name} ] ---\n")
			try {
				it.generateMethods(this)
			} catch (e: Exception) {
				throw RuntimeException("Uncaught exception while generating method: $className.${it.name}", e)
			}
		}

		print("}")
	}

	private fun PrintWriter.generateFunctionAddresses(functionProvider: FunctionProvider) {
		println("\t/** Function address. */")
		println("\t@JavadocExclude")
		print("\tpublic final long")
		if ( _functions.size == 1 ) {
			println(" ${_functions.values().first().addressName};")
		} else {
			println()
			_functions.values().forEachWithMore {(func, more) ->
				if ( more )
					println(",")
				print("\t\t${func.addressName}")
			}
			println(";")
		}

		for ( func in functions ) {
			func.getParams { it has Callback }.forEach {
				val cb = it[Callback]
				if ( cb.storeInFunctions )
					println("\n\tlong ref${cb.procClass};")
			}
		}

		println("\n\t@JavadocExclude")
		print("\t${access.modifier}$className(FunctionProvider${if ( functionProvider.isLocal ) "Local" else ""} provider")
		functionProvider.printFunctionsParams(this, this@NativeClass)
		println(") {")
		functions.forEach {
			println("\t\t${it.addressName} = ${functionProvider.getFunctionAddressCall(it)};")
		}
		println("\t}\n")
	}

	override fun PrintWriter.generateNative() {
		print(HEADER)
		println("#include \"common_tools.h\"")

		preamble.printNative(this)

		if ( functionProvider != null ) {
			// Generate typedefs for casting the function pointers
			println()
			functions.stream().filter { !it.has(Reuse) }.forEach {
				it.generateFunctionDefinition(this)
			}
		}

		println("\nEXTERN_C_ENTER")

		functions.stream().filter { !it.has(Reuse) }.forEach {
			println()
			it.generateFunction(this)
		}

		println("\nEXTERN_C_EXIT")
	}

	fun nativeImportsWarning() {
		if ( preamble.hasNativeImports )
			println("\tUnnecessary native imports in: $packageName.$templateName")
	}

	fun printPointers(
		out: PrintWriter,
		printPointer: (func: NativeClassFunction) -> String = { "funcs.${it.simpleName}" },
		filter: ((NativeClassFunction) -> Boolean)? = null
	) {
		out.print("\n\t\t\t")


		val functions = if ( filter == null )
			_functions.values()
		else
			_functions.values().filter(filter)

		var lineSize = 12
		functions.forEachWithMore {(func, more) ->
			if ( more ) {
				out.print(", ")
				lineSize += 2
			}

			val pointer = printPointer(func)

			lineSize += pointer.size
			if ( 160 <= lineSize ) {
				out.print("\n\t\t\t")
				lineSize = 12 + pointer.size
			}

			out.print(pointer)
		}

		out.print("\n\t\t")
	}

	// DSL extensions

	fun <T> ConstantType<T>.block(documentation: String, vararg constants: Constant<T>): ConstantBlock<T> {
		val block = ConstantBlock(this@NativeClass, this, processDocumentation(documentation).toJavaDoc(), *constants)
		constantBlocks add block
		return block
	}

	fun NativeType.func(name: String, documentation: String, vararg parameters: Parameter, returnDoc: String = "", since: String = "") =
		ReturnValue(this).func(name, documentation, *parameters, returnDoc = returnDoc, since = since)
	fun ReturnValue.func(name: String, documentation: String, vararg parameters: Parameter, returnDoc: String = "", since: String = ""): NativeClassFunction {
		val func = NativeClassFunction(
			returns = this,
			simpleName = name,
			documentation = this@NativeClass.toJavaDoc(processDocumentation(documentation), parameters.stream(), returnDoc, since),
			nativeClass = this@NativeClass,
			parameters = *parameters
		)

		_functions.put(name, func)
		return func
	}

	fun NativeClass.get(functionName: String): NativeClassFunction {
		val func = _functions[functionName]
		if ( func == null )
			throw IllegalArgumentException("Referenced function does not exist: ${templateName}.$functionName")
		return func
	}

	fun NativeClass.reuse(functionName: String): NativeClassFunction {
		val reference = this[functionName]

		val func = Reuse(this.className) _ NativeClassFunction(
			returns = reference.returns,
			simpleName = functionName,
			documentation = convertDocumentation(reference.documentation),
			nativeClass = this@NativeClass,
			parameters = *reference.parameters
		).copyModifiers(reference)

		this@NativeClass._functions.put(functionName, func)
		return func
	}

	private val reusePattern: Pattern by Delegates.lazy {
		Pattern.compile("""${this.className}#(\p{javaJavaIdentifierStart}\p{javaJavaIdentifierPart}*)""")
	}

	private fun convertDocumentation(documentation: String): String {
		val matcher = reusePattern.matcher(documentation)
		if ( !matcher.find() )
			return documentation

		val buffer = StringBuilder(documentation.size)
		var lastEnd = 0
		do {
			buffer.append(documentation, lastEnd, matcher.start())

			val element = matcher.group(1)
			if ( this.constantBlocks.any { block -> block.constants.any { it -> it.name == element } }) {
				buffer.append('#')
				buffer.append(matcher.group(1))
			} else {
				buffer.append(matcher.group(0))
			}

			lastEnd = matcher.end()
		} while ( matcher.find() )
		buffer.append(documentation, lastEnd, documentation.length())

		return buffer.toString()
	}

}

// DSL extensions

fun String.nativeClass(
	packageName: String,
	templateName: String = this,
	nativeSubPath: String = "",
	prefix: String = "",
	prefixMethod: String = prefix.toLowerCase(),
	prefixConstant: String = if ( prefix.isEmpty() || prefix.endsWith('_') ) prefix else "${prefix}_",
	prefixTemplate: String = prefix,
	postfix: String = "",
	functionProvider: FunctionProvider? = null,
	init: (NativeClass.() -> Unit)? = null
): NativeClass {
	val ext = NativeClass(packageName, this, nativeSubPath, templateName, prefix, prefixMethod, prefixConstant, prefixTemplate, postfix, functionProvider)
	if ( init != null )
		ext.init()

	functionProvider?.addCapabilities(ext)

	return ext
}