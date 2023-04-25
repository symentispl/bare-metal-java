package pl.symentis.alge.runtime;

import static pl.symentis.alge.runtime.SDLRuntime.nullCheck;

import java.lang.foreign.FunctionDescriptor;
import java.lang.foreign.Linker;
import java.lang.foreign.MemorySegment;
import java.lang.foreign.ValueLayout;
import java.lang.invoke.MethodHandle;

public class SDL_Window {

    private static final MethodHandle SDL_UpdateWindowSurface_mh = Linker.nativeLinker()
            .downcallHandle(
                    SDLRuntime.lookupSymbol("SDL_UpdateWindowSurface"),
                    FunctionDescriptor.of(ValueLayout.JAVA_INT, ValueLayout.ADDRESS));
    private static final MethodHandle sdl_getWindowSurface_mh = Linker.nativeLinker()
            .downcallHandle(
                    SDLRuntime.lookupSymbol("SDL_GetWindowSurface"),
                    FunctionDescriptor.of(ValueLayout.ADDRESS, ValueLayout.ADDRESS));

    final MemorySegment wrapped;
    private final MethodHandle SDL_UpdateWindowSurface_mh_$;

    SDL_Window(MemorySegment wrapped) {

        this.wrapped = wrapped;
        this.SDL_UpdateWindowSurface_mh_$ = SDL_UpdateWindowSurface_mh.bindTo(wrapped);
    }

    public void updateWindowSurface() throws Throwable {
        int err = (int) SDL_UpdateWindowSurface_mh_$.invoke();
        if (err != 0) {}
    }

    public SDL_Surface surface() {
        var sdlWindowSurface = nullCheck(() -> (MemorySegment) sdl_getWindowSurface_mh.invoke(wrapped));
        var memorySegment = MemorySegment.ofAddress(sdlWindowSurface.address(), SDL_Surface.layout.byteSize());
        return new SDL_Surface(memorySegment);
    }
}
