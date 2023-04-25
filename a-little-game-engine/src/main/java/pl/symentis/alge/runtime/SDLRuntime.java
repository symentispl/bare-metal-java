package pl.symentis.alge.runtime;

import java.lang.foreign.Arena;
import java.lang.foreign.FunctionDescriptor;
import java.lang.foreign.Linker;
import java.lang.foreign.MemorySegment;
import java.lang.foreign.SymbolLookup;
import java.lang.foreign.ValueLayout;
import java.lang.invoke.MethodHandle;
import java.lang.ref.Cleaner;
import java.nio.file.Path;

public final class SDLRuntime {

    static {
        System.loadLibrary("SDL2");
        System.loadLibrary("SDL2_image");
    }

    public static SDLRuntime getInstance() {
        return Singleton.INSTANCE;
    }

    private final SDL_image SDL_image;
    private final Cleaner cleaner;

    private SDLRuntime() {
        SDL.init();
        this.SDL_image = new SDL_image();
        this.cleaner = Cleaner.create();
    }

    private static final MethodHandle SDL_createWindow_mh = Linker.nativeLinker()
            .downcallHandle(
                    SDLRuntime.lookupSymbol("SDL_CreateWindow"),
                    FunctionDescriptor.of(
                            ValueLayout.ADDRESS,
                            ValueLayout.ADDRESS,
                            ValueLayout.JAVA_INT,
                            ValueLayout.JAVA_INT,
                            ValueLayout.JAVA_INT,
                            ValueLayout.JAVA_INT,
                            ValueLayout.JAVA_INT));

    public SDL_Window createWindow(String title, int x, int y, int w, int h, int flags) {
        try (var arena = Arena.openConfined()) {
            var window = nullCheck(() ->
                    (MemorySegment) SDL_createWindow_mh.invoke(arena.allocateUtf8String(title), x, y, w, h, flags));
            return new SDL_Window(window);
        }
    }

    private static final MethodHandle IMG_Load_mh = Linker.nativeLinker()
            .downcallHandle(
                    SDLRuntime.lookupSymbol("IMG_Load"),
                    FunctionDescriptor.of(ValueLayout.ADDRESS, ValueLayout.ADDRESS));

    public SDL_Surface loadImage(Path path) {
        try (var arena = Arena.openConfined()) {
            var MemorySegment = (MemorySegment) IMG_Load_mh.invoke(arena.allocateUtf8String(path.toString()));
            if (MemorySegment.NULL.equals(MemorySegment)) {
                throw new NullPointerException(SDL_error.getError());
            }
            var surface = new SDL_Surface(MemorySegment);
            cleaner.register(surface, () -> SDL_Surface.freeSurface(MemorySegment));
            return surface;
        } catch (Throwable e) {
            throw new RuntimeException(e);
        }
    }

    static MemorySegment lookupSymbol(String name) throws SDLException {
        return SymbolLookup.loaderLookup()
                .find(name)
                .orElseThrow(() -> new SDLException(String.format("failed to load symbol '%s'", name)));
    }

    static MemorySegment nullCheck(CheckedSupplier<MemorySegment> supplier) {
        try {
            var MemorySegment = supplier.get();
            if (MemorySegment.NULL.equals(MemorySegment)) {
                throw new NullPointerException(SDL_error.getError());
            }
            return MemorySegment;
        } catch (Throwable e) {
            throw new SDLException(e);
        }
    }

    static Integer errorCheck(CheckedSupplier<Integer> supplier) {
        try {
            var error = supplier.get();
            if (error != 0) {
                throw new SDLError(SDL_error.getError());
            }
            return error;
        } catch (Throwable e) {
            throw new SDLException(e);
        }
    }

    private static class Singleton {
        private static final SDLRuntime INSTANCE = new SDLRuntime();
    }

    @FunctionalInterface
    interface CheckedSupplier<R> {
        R get() throws Throwable;
    }
}
