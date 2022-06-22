package pl.symentis.alge.runtime;

import jdk.incubator.foreign.CLinker;
import jdk.incubator.foreign.FunctionDescriptor;

import java.lang.invoke.MethodHandle;
import java.lang.invoke.MethodType;

public class SDL {

    private static final int SDL_INIT_VIDEO = 0x00000020;

    private static final MethodHandle SDL_Init_mh = CLinker.getInstance()
            .downcallHandle(
                    SDLRuntime.lookupSymbol("SDL_Init"),
                    MethodType.methodType(int.class, int.class),
                    FunctionDescriptor.of(CLinker.C_INT, CLinker.C_INT));

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
