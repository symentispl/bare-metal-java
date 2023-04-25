package pl.symentis.alge.runtime;

import java.lang.foreign.FunctionDescriptor;
import java.lang.foreign.Linker;
import java.lang.foreign.ValueLayout;
import java.lang.invoke.MethodHandle;

public class SDL_events {

    public static int SDL_QUIT = 0x100;
    public static int SDL_KEYDOWN = 0x300;
    public static int SDL_KEYUP = 0x301;

    public static int SDLK_RIGHT = 1073741903;
    public static final int SDLK_SPACE = 32;
    private static final MethodHandle SDL_PollEvents_mh = Linker.nativeLinker()
            .downcallHandle(
                    SDLRuntime.lookupSymbol("SDL_PollEvent"),
                    FunctionDescriptor.of(ValueLayout.JAVA_INT, ValueLayout.ADDRESS));

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
