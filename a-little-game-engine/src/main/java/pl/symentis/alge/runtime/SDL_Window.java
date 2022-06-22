package pl.symentis.alge.runtime;

import jdk.incubator.foreign.*;

import java.lang.invoke.MethodHandle;
import java.lang.invoke.MethodType;
import java.lang.ref.Cleaner;

public class SDL_Window {

    private static final MethodHandle SDL_UpdateWindowSurface_mh = CLinker.getInstance()
            .downcallHandle(
                    SDLRuntime.lookupSymbol("SDL_UpdateWindowSurface"),
                    MethodType.methodType(int.class, MemoryAddress.class),
                    FunctionDescriptor.of(CLinker.C_INT, CLinker.C_POINTER));
    final MemoryAddress wrapped;
    private final MethodHandle SDL_UpdateWindowSurface_mh_$;

    SDL_Window(MemoryAddress wrapped) {

        this.wrapped = wrapped;
        this.SDL_UpdateWindowSurface_mh_$ = SDL_UpdateWindowSurface_mh.bindTo(wrapped);
    }

    private static final MethodHandle sdl_getWindowSurface_mh = CLinker.getInstance()
            .downcallHandle(
                    SDLRuntime.lookupSymbol("SDL_GetWindowSurface"),
                    MethodType.methodType(MemoryAddress.class, MemoryAddress.class),
                    FunctionDescriptor.of(CLinker.C_POINTER, CLinker.C_POINTER));

    public void updateWindowSurface() throws Throwable {
        int err = (int) SDL_UpdateWindowSurface_mh_$.invoke();
        if (err != 0) {}
    }

    public SDL_Surface surface() {
        var memoryAddress =
                SDLRuntime.nullCheck(() -> (MemoryAddress) sdl_getWindowSurface_mh.invoke(wrapped.address()));
        return new SDL_Surface(memoryAddress);
    }
}
