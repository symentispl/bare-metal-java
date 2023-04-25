package pl.symentis.alge.runtime;

import java.lang.foreign.FunctionDescriptor;
import java.lang.foreign.Linker;
import java.lang.foreign.ValueLayout;
import java.lang.invoke.MethodHandle;

public class SDL {

    private static final int SDL_INIT_VIDEO = 0x00000020;

    private static final MethodHandle SDL_Init_mh = Linker.nativeLinker()
            .downcallHandle(
                    SDLRuntime.lookupSymbol("SDL_Init"),
                    FunctionDescriptor.of(ValueLayout.JAVA_INT, ValueLayout.JAVA_INT));

    static void init() {
        try {
            int err = (int) SDL_Init_mh.invoke(SDL_INIT_VIDEO);
            if (err != 0) {
                throw new SDLException(SDL_error.getError());
            }
        } catch (Throwable e) {
            throw new SDLException(SDL_error.getError(), e);
        }
    }
}
