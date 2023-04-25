package pl.symentis.alge.runtime;

import java.lang.foreign.FunctionDescriptor;
import java.lang.foreign.Linker;
import java.lang.foreign.MemorySegment;
import java.lang.foreign.ValueLayout;
import java.lang.invoke.MethodHandle;

public class SDL_error {

    private static final MethodHandle SDL_GetError_mh = Linker.nativeLinker()
            .downcallHandle(SDLRuntime.lookupSymbol("SDL_GetError"), FunctionDescriptor.of(ValueLayout.ADDRESS));

    static String getError() {
        try {
            return ((MemorySegment) SDL_GetError_mh.invoke()).getUtf8String(0);
        } catch (Throwable e) {
            throw new SDLException(e);
        }
    }
}
