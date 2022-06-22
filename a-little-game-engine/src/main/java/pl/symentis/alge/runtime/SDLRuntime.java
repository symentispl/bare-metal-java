package pl.symentis.alge.runtime;

import jdk.incubator.foreign.*;

import java.lang.invoke.MethodHandle;
import java.lang.invoke.MethodType;
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

    private static final MethodHandle SDL_createWindow_mh = CLinker.getInstance()
            .downcallHandle(
                    SDLRuntime.lookupSymbol("SDL_CreateWindow"),
                    MethodType.methodType(
                            MemoryAddress.class,
                            MemoryAddress.class,
                            int.class,
                            int.class,
                            int.class,
                            int.class,
                            int.class),
                    FunctionDescriptor.of(
                            CLinker.C_POINTER,
                            CLinker.C_POINTER,
                            CLinker.C_INT,
                            CLinker.C_INT,
                            CLinker.C_INT,
                            CLinker.C_INT,
                            CLinker.C_INT));

    public SDL_Window createWindow(String title, int x, int y, int w, int h, int flags) {
        var window = nullCheck(() -> (MemoryAddress) SDL_createWindow_mh.invoke(
                CLinker.toCString(title, ResourceScope.globalScope()).address(), x, y, w, h, flags));
        return new SDL_Window(window);
    }

    private static final MethodHandle IMG_Load_mh = CLinker.getInstance()
            .downcallHandle(
                    SDLRuntime.lookupSymbol("IMG_Load"),
                    MethodType.methodType(MemoryAddress.class, MemoryAddress.class),
                    FunctionDescriptor.of(CLinker.C_POINTER, CLinker.C_POINTER));

    public SDL_Surface loadImage(Path path) {
        try (var resourceScope = ResourceScope.newConfinedScope()) {
            var memoryAddress = (MemoryAddress) IMG_Load_mh.invoke(
                    CLinker.toCString(path.toString(), resourceScope).address());
            if (MemoryAddress.NULL.equals(memoryAddress)) {
                throw new NullPointerException(SDL_error.getError());
            }
            var surface = new SDL_Surface(memoryAddress);
            cleaner.register(surface, () -> SDL_Surface.freeSurface(memoryAddress));
            return surface;
        } catch (Throwable e) {
            throw new RuntimeException(e);
        }
    }

    static MemoryAddress lookupSymbol(String name) throws SDLException {
        return SymbolLookup.loaderLookup()
                .lookup(name)
                .orElseThrow(() -> new SDLException(String.format("failed to load symbol '%s'", name)));
    }

    static MemoryAddress nullCheck(CheckedSupplier<MemoryAddress> supplier) {
        try {
            var memoryAddress = supplier.get();
            if (MemoryAddress.NULL.equals(memoryAddress)) {
                throw new NullPointerException(SDL_error.getError());
            }
            return memoryAddress;
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
