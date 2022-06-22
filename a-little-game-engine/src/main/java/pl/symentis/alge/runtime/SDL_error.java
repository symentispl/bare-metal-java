package pl.symentis.alge.runtime;

import jdk.incubator.foreign.*;

import java.lang.invoke.MethodHandle;
import java.lang.invoke.MethodType;

public class SDL_error {

    private static final MethodHandle SDL_GetError_mh = CLinker.getInstance()
            .downcallHandle(
                    SDLRuntime.lookupSymbol("SDL_GetError"),
                    MethodType.methodType(MemoryAddress.class),
                    FunctionDescriptor.of(CLinker.C_POINTER));

    static String getError() {
        try {
            return CLinker.toJavaString((MemoryAddress) SDL_GetError_mh.invoke());
        } catch (Throwable e) {
            throw new SDLException(e);
        }
    }
}
