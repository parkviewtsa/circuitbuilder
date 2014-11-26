***I copied this over directly from last year's project; ignore things that are
irrelevant.***

Style Guide
===========

We are using [Google's style guide](http://code.google.com/p/google-styleguide/),
with a couple modifications.
The most unusual/annoying difference from the way we usually code is: **USE SPACES
FOR INDENT**. 2 of them, to be precise. It's easy enough to configure any text editor
I know of to automatically emit spaces when you press Tab, and if you use Shift+Tab
(Opt+Shift+Tab on OS X), the editor will remove them too. Other rules that I KNOW some
of you violate are:

 * C++ extension is `.cc`, not `.cpp`, `.cxx` or anything like that.
 * Objective-C extension is `.m`.
 * All headers (regardless of language) use `.h`.
 * Function declarations look like this as far as spacing: `type functionName (type arg1, type arg2) {`
 THE BRACE GOES ON **THE SAME LINE** BUT **WITH A SPACE**!
 * The only exception to this is `int main`, which puts its brace on a separate line.
 * A block of code only ends with a semicolon if there is an assignment `=` in the line that begins the
 block of code. This is only an issue in JavaScript. In other languages, NO SEMICOLONS AFTER BRACES.
 *Example:* `this.method = function () { ... };` but `function foo () { ... }`
 * **NEVER EVER EVER USE HUNGARIAN NOTATION** (Modifying the name of a variable based on its type, e.g.
 `int iFoo` or `float fBar`)! **EVER!!**
 
Example naming conventions
----

 * `ClassName` (including JS constructors)
 * `methodName`
 * `variable_name` (C and C++ only)
 * `variableName` (Java and JavaScript)
 * *(Objective-C can use either of the two above)*
 * `CONSTANT_NAME` (all languages)

Use American spelling (e.g. color, center, realize, program; not colour, centre, realise, programme) for all
names within your code.
In comments, use whatever you want. (Personally, I prefer British/International, which sometimes leads to
awkward situations such as `float bColor; //Background colour`, but that's the rule.)

 * The one exception to this is if the American spelling is a reserved word (for example, in Processing 
 `color` is a type name, so I will often use `colour` as a variable name.

Comments
----

Always begin files with the licence boilerplate which looks begins with `/*` and ends with `*/`, with asteriks
beginning every line in between. In HTML, licence boilerplates should begin with `<!--` and end with `-->` on 
separate lines, with every line of text beginning with a hyphen. Make it look good.

In languages with "Java" in their name, beginning-of-file documentation comments
should use `/** ... */`. In Objective-C, these comments should have `//` at the beginning of every line. C and
C++ should use either `/* ... */` or `/** ... */`. These comments should come AFTER header guards, but before all
other preprocessor or import directives.

Single-line comments within the code should always use `//`.

In comments or documentation, even if a name from code is used to begin a sentence, preserve its original
capitalisation.
