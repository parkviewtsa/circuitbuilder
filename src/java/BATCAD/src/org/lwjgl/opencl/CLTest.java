/*
 * Copyright LWJGL. All rights reserved.
 * License terms: http://lwjgl.org/license.php
 */
package org.lwjgl.opencl;

import org.lwjgl.BufferUtils;
import org.lwjgl.PointerBuffer;
import org.lwjgl.opencl.CLPlatform.Filter;
import org.testng.SkipException;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

import java.nio.ByteBuffer;
import java.nio.ByteOrder;
import java.nio.IntBuffer;
import java.util.List;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.TimeUnit;

import static org.lwjgl.Pointer.*;
import static org.lwjgl.opencl.CL10.*;
import static org.lwjgl.opencl.CL11.*;
import static org.lwjgl.opencl.CLUtil.*;
import static org.lwjgl.opencl.Info.*;
import static org.lwjgl.system.MemoryUtil.*;
import static org.testng.Assert.*;

@Test
public class CLTest {

	private static final Filter<CLPlatform> CL11_FILTER = new Filter<CLPlatform>() {
		@Override
		public boolean accept(CLPlatform platform) {
			return platform.getCapabilities().OpenCL11;

		}
	};

	private static final Filter<CLPlatform> CL12_FILTER = new Filter<CLPlatform>() {
		@Override
		public boolean accept(CLPlatform platform) {
			return platform.getCapabilities().OpenCL12;

		}
	};

	@BeforeClass
	private void createCL() {
		try {
			CL.getFunctionProvider();
		} catch (Throwable t) {
			throw new SkipException("Skipped because OpenCL initialization failed [" + t.getMessage() + "]");
		} finally {
			CL.destroy();
		}
	}

	public void testLifecycle() {
		try {
			CL.create();
			List<CLPlatform> platforms = CLPlatform.getPlatforms();
			assertFalse(platforms.isEmpty());

			for ( CLPlatform platform : platforms ) {
				List<CLDevice> devices = platform.getDevices(CL_DEVICE_TYPE_ALL);
				assertFalse(devices.isEmpty());
			}
		} finally {
			CL.destroy();
		}
	}

	private interface ContextTest {

		void test(CLPlatform platform, PointerBuffer ctxProps, CLDevice device);
	}

	private static void contextTest(ContextTest test) {
		contextTest(null, test);
	}

	private static void contextTest(Filter<CLPlatform> platformFilter, ContextTest test) {
		try {
			CL.create();
			List<CLPlatform> platforms = CLPlatform.getPlatforms(platformFilter);
			if ( platforms.isEmpty() ) {
				assert platformFilter != null;
				throw new SkipException("Skipped because the platform filter did not match with any cl_platform.");
			} else {
				for ( CLPlatform platform : platforms ) {
					PointerBuffer ctxProps = BufferUtils.createPointerBuffer(3);
					ctxProps.put(CL_CONTEXT_PLATFORM).put(platform).put(0).flip();

					List<CLDevice> devices = platform.getDevices(CL_DEVICE_TYPE_ALL);
					for ( CLDevice device : devices )
						test.test(platform, ctxProps, device);
				}
			}
		} finally {
			CL.destroy();
		}
	}

	public void testContext() {
		contextTest(new ContextTest() {
			@Override
			public void test(CLPlatform platform, PointerBuffer ctxProps, CLDevice device) {
				IntBuffer errcode_ret = BufferUtils.createIntBuffer(1);

				long context = clCreateContext(ctxProps, device.getPointer(), CLContextCallback.Util.getDefault(), errcode_ret);
				checkCLError(errcode_ret);
				assertNotNull(context);

				long queue = clCreateCommandQueue(context, device.getPointer(), 0, errcode_ret);
				checkCLError(errcode_ret);
				assertNotNull(queue);

				long buffer = clCreateBuffer(context, CL_MEM_READ_ONLY, 128, errcode_ret);
				checkCLError(errcode_ret);
				assertNotNull(buffer);

				int errcode = clReleaseCommandQueue(queue);
				checkCLError(errcode);

				errcode = clReleaseMemObject(buffer);
				checkCLError(errcode);

				errcode = clReleaseContext(context);
				checkCLError(errcode);
			}
		});
	}

	public void testNativeKernel() {
		contextTest(new ContextTest() {
			@Override
			public void test(CLPlatform platform, PointerBuffer ctxProps, CLDevice device) {
				if ( (clGetDeviceInfoLong(device.getPointer(), CL_DEVICE_EXECUTION_CAPABILITIES) & CL_EXEC_NATIVE_KERNEL) == 0 )
					return;

				IntBuffer errcode_ret = BufferUtils.createIntBuffer(1);

				long context = clCreateContext(ctxProps, device.getPointer(), CLContextCallback.Util.getDefault(), errcode_ret);
				checkCLError(errcode_ret);

				long queue = clCreateCommandQueue(context, device.getPointer(), 0, errcode_ret);
				checkCLError(errcode_ret);

				// Create a buffer
				final long buffer = clCreateBuffer(context, CL_MEM_READ_ONLY, 128, errcode_ret);
				checkCLError(errcode_ret);

				final int BUFFER_SIZE = (int)clGetMemObjectInfoPointer(buffer, CL_MEM_SIZE);
				assertEquals(BUFFER_SIZE, 128);

				// Fill with non-random data
				ByteBuffer bufferContents = BufferUtils.createByteBuffer(BUFFER_SIZE);
				for ( int i = 0; i < bufferContents.capacity(); i++ )
					bufferContents.put(i, (byte)i);

				int errcode = clEnqueueWriteBuffer(queue, buffer, CL_TRUE, 0, bufferContents, null, null);
				checkCLError(errcode);

				// Prepare enqueue arguments

				ByteBuffer enqueueArgs = BufferUtils.createByteBuffer(POINTER_SIZE * 2 + POINTER_SIZE + 4);

				// Slice to skip the pointers (reserved by LWJGL)
				enqueueArgs.position(POINTER_SIZE * 2);
				ByteBuffer args = enqueueArgs.slice().order(ByteOrder.nativeOrder());
				enqueueArgs.clear();

				// a cl_mem object
				PointerBuffer mem_list = BufferUtils.createPointerBuffer(1);
				mem_list.put(0, buffer);

				PointerBuffer args_mem_loc = BufferUtils.createPointerBuffer(1);
				args_mem_loc.put(0, args); // offset to where the cl_mem object id is stored

				// a non-random number
				args.putInt(POINTER_SIZE, 1337);

				// A cl_event will be stored here that will let us know when the native kernel has been called.
				PointerBuffer eventOut = BufferUtils.createPointerBuffer(1);

				errcode = clEnqueueNativeKernel(queue, new CLNativeKernel.BufAdapter() {
					@Override
					public void invoke(ByteBuffer args) {
						assertEquals(args.remaining(), POINTER_SIZE + 4);

						long memAddress = PointerBuffer.get(args, 0);
						assertTrue(memAddress != NULL);
						assertTrue(memAddress != buffer);

						ByteBuffer kernelBuffer = memByteBuffer(memAddress, BUFFER_SIZE);
						for ( int i = 0; i < kernelBuffer.capacity(); i++ )
							assertEquals(kernelBuffer.get(i), i);

						assertEquals(args.getInt(POINTER_SIZE), 1337);
					}
				}, enqueueArgs, mem_list, args_mem_loc, null, eventOut);
				checkCLError(errcode);

				long e = eventOut.get(0);
				assertNotNull(e);

				final CountDownLatch latch = new CountDownLatch(1);
				errcode = clSetEventCallback(e, CL_COMPLETE, new CLEventCallback() {
					@Override
					public void invoke(long event, int command_exec_status) {
						latch.countDown();
					}
				});
				checkCLError(errcode);

				try {
					latch.await();
				} catch (InterruptedException exc) {
					exc.printStackTrace();
				}

				errcode = clReleaseEvent(e);
				checkCLError(errcode);

				errcode = clReleaseCommandQueue(queue);
				checkCLError(errcode);

				errcode = clReleaseContext(context);
				checkCLError(errcode);
			}
		});
	}

	public void testMemObjectDestructor() {
		contextTest(CL11_FILTER, new ContextTest() {
			@Override
			public void test(CLPlatform platform, PointerBuffer ctxProps, CLDevice device) {
				IntBuffer errcode_ret = BufferUtils.createIntBuffer(1);

				long context = clCreateContext(ctxProps, device.getPointer(), CLContextCallback.Util.getDefault(), errcode_ret);
				checkCLError(errcode_ret);

				long buffer = clCreateBuffer(context, CL_MEM_READ_ONLY, 128, errcode_ret);
				checkCLError(errcode_ret);
				assertNotNull(buffer);

				final CountDownLatch eventLatch = new CountDownLatch(1);

				clSetMemObjectDestructorCallback(buffer, new CLMemObjectDestructorCallback() {
					@Override
					public void invoke(long cl_mem) {
						eventLatch.countDown();
					}
				});

				assertEquals(clGetMemObjectInfoInt(buffer, CL_MEM_REFERENCE_COUNT), 1);

				int errcode = clRetainMemObject(buffer);
				checkCLError(errcode);

				assertEquals(clGetMemObjectInfoInt(buffer, CL_MEM_REFERENCE_COUNT), 2);

				errcode = clReleaseMemObject(buffer);
				checkCLError(errcode);

				assertEquals(clGetMemObjectInfoInt(buffer, CL_MEM_REFERENCE_COUNT), 1);
				assertEquals(eventLatch.getCount(), 1L);

				errcode = clReleaseMemObject(buffer);
				checkCLError(errcode);

				try {
					if ( !eventLatch.await(100, TimeUnit.MILLISECONDS) )
						throw new IllegalStateException("MemObjectDestructor callback failed.");
				} catch (InterruptedException exc) {
					exc.printStackTrace();
				}

				errcode = clReleaseContext(context);
				checkCLError(errcode);
			}
		});
	}

	public void testEventCallback() {
		contextTest(CL11_FILTER, new ContextTest() {
			@Override
			public void test(CLPlatform platform, PointerBuffer ctxProps, CLDevice device) {
				IntBuffer errcode_ret = BufferUtils.createIntBuffer(1);

				long context = clCreateContext(ctxProps, device.getPointer(), CLContextCallback.Util.getDefault(), errcode_ret);
				checkCLError(errcode_ret);
				assertNotNull(context);

				final long e = clCreateUserEvent(context, errcode_ret);
				checkCLError(errcode_ret);
				assertNotNull(e);

				final CountDownLatch eventLatch = new CountDownLatch(1);

				int errcode = clSetEventCallback(e, CL_COMPLETE, new CLEventCallback() {
					@Override
					public void invoke(long event, int command_exec_status) {
						assertEquals(event, e);
						assertEquals(command_exec_status, CL_COMPLETE);

						eventLatch.countDown();
					}
				});
				checkCLError(errcode);

				errcode = clSetUserEventStatus(e, CL_COMPLETE);
				checkCLError(errcode);

				try {
					if ( !eventLatch.await(100, TimeUnit.MILLISECONDS) )
						throw new IllegalStateException("Event callback failed.");
				} catch (InterruptedException exc) {
					exc.printStackTrace();
				}

				errcode = clReleaseEvent(e);
				checkCLError(errcode);

				errcode = clReleaseContext(context);
				checkCLError(errcode);
			}
		});
	}

	public void testSubBuffer() {
		contextTest(CL11_FILTER, new ContextTest() {
			@Override
			public void test(CLPlatform platform, PointerBuffer ctxProps, CLDevice device) {
				// TODO: Intel has broken reference counting atm
				boolean doContextCountChecks = !"Intel(R) OpenCL".equals(clGetPlatformInfoStringUTF8(platform.getPointer(), CL_PLATFORM_NAME));

				IntBuffer errcode_ret = BufferUtils.createIntBuffer(1);

				long context = clCreateContext(ctxProps, device.getPointer(), CLContextCallback.Util.getDefault(), errcode_ret);
				checkCLError(errcode_ret);
				assertNotNull(context);

				if ( doContextCountChecks )
					assertEquals(clGetContextInfoInt(context, CL_CONTEXT_REFERENCE_COUNT), 1);

				long buffer = clCreateBuffer(context, CL_MEM_READ_ONLY, 128, errcode_ret);
				checkCLError(errcode_ret);
				assertNotNull(buffer);

				assertEquals(clGetMemObjectInfoInt(buffer, CL_MEM_REFERENCE_COUNT), 1);
				if ( doContextCountChecks )
					assertEquals(clGetContextInfoInt(context, CL_CONTEXT_REFERENCE_COUNT), 2);

				ByteBuffer bufferRegion = cl_buffer_region.malloc(0, 64);

				long subbuffer = clCreateSubBuffer(buffer, CL_MEM_READ_ONLY, CL_BUFFER_CREATE_TYPE_REGION, bufferRegion, errcode_ret);
				checkCLError(errcode_ret);
				assertNotNull(subbuffer);

				if ( doContextCountChecks ) {
					assertEquals(
						clGetMemObjectInfoInt(buffer, CL_MEM_REFERENCE_COUNT),
						2,
						clGetPlatformInfoStringUTF8(platform.getPointer(), CL_PLATFORM_NAME)
					);
					assertEquals(clGetContextInfoInt(context, CL_CONTEXT_REFERENCE_COUNT), 3);
				}

				int errcode = clReleaseMemObject(buffer);
				checkCLError(errcode);

				if ( doContextCountChecks ) {
					assertEquals(clGetMemObjectInfoInt(buffer, CL_MEM_REFERENCE_COUNT), 1);
					assertEquals(clGetContextInfoInt(context, CL_CONTEXT_REFERENCE_COUNT), 3);
				}

				errcode = clReleaseMemObject(subbuffer);
				checkCLError(errcode);

				assertEquals(clGetContextInfoInt(context, CL_CONTEXT_REFERENCE_COUNT), 1);

				errcode = clReleaseContext(context);
				checkCLError(errcode);
			}
		});
	}

}