package pl.symentis.alge.runtime;

import jdk.incubator.foreign.CLinker;
import jdk.incubator.foreign.FunctionDescriptor;
import jdk.incubator.foreign.MemoryAddress;

import java.lang.invoke.MethodHandle;
import java.lang.invoke.MethodType;

public class SDL_events {

    public static int SDL_QUIT = 0x100;
    public static int SDL_KEYDOWN = 0x300;
    public static int SDL_KEYUP = 0x301;

    public static int SDLK_RIGHT = 1073741903;
    public static final int SDLK_SPACE = 32;
    private static final MethodHandle SDL_PollEvents_mh = CLinker.getInstance()
            .downcallHandle(
                    SDLRuntime.lookupSymbol("SDL_PollEvent"),
                    MethodType.methodType(int.class, MemoryAddress.class),
                    FunctionDescriptor.of(CLinker.C_INT, CLinker.C_POINTER));

    public static boolean pollEvent(SDL_Event event) {
        int resp = 0;
        try {
            resp = (Integer) SDL_PollEvents_mh.invoke(event.wrapped);
        } catch (Throwable e) {
            throw new RuntimeException(e);
        }
        if (resp == 0) {
            return false;
        }
        if (resp == 1) {
            return true;
        }
        throw new UnsupportedOperationException();
    }
}
