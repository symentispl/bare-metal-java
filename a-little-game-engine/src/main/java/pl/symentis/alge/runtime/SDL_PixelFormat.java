package pl.symentis.alge.runtime;

import jdk.incubator.foreign.*;

import java.lang.invoke.MethodHandle;
import java.lang.invoke.MethodHandles;
import java.lang.invoke.MethodType;
import java.lang.invoke.VarHandle;

public class SDL_PixelFormat {

    static final GroupLayout layout = MemoryLayout.structLayout(
            CLinker.C_INT.withName("format"),
            MemoryLayout.paddingLayout(32),
            CLinker.C_POINTER.withName("palette"),
            CLinker.C_CHAR.withName("BitsPerPixel"),
            CLinker.C_CHAR.withName("BytesPerPixel"),
            MemoryLayout.sequenceLayout(2, CLinker.C_CHAR).withName("padding"),
            CLinker.C_INT.withName("Rmask"),
            CLinker.C_INT.withName("Gmask"),
            CLinker.C_INT.withName("Bmask"),
            CLinker.C_INT.withName("Amask"),
            CLinker.C_CHAR.withName("Rloss"),
            CLinker.C_CHAR.withName("Gloss"),
            CLinker.C_CHAR.withName("Bloss"),
            CLinker.C_CHAR.withName("Aloss"),
            CLinker.C_CHAR.withName("Rshift"),
            CLinker.C_CHAR.withName("Gshift"),
            CLinker.C_CHAR.withName("Bshift"),
            CLinker.C_CHAR.withName("Ashift"),
            CLinker.C_INT.withName("refcount"),
            CLinker.C_POINTER.withName("next")
    ).withName("SDL_PixelFormat");
    private static VarHandle bytesPerPixel_vh = layout.varHandle(byte.class, MemoryLayout.PathElement.groupElement("BytesPerPixel"));
    private final MethodHandle sdl_mapRGB_mh_adapted;


    static byte bytesPerPixel(MemorySegment memorySegment) {
        return (byte) bytesPerPixel_vh.get(memorySegment);
    }

    private final MemorySegment wrapped;

    public SDL_PixelFormat(MemorySegment wrapped) {
        this.wrapped = wrapped;
        this.sdl_mapRGB_mh_adapted = MethodHandles.insertArguments(SDL_MapRGB_mh, 0, wrapped.address());
    }

    private static MethodType SDL_MapRGB_mt = MethodType.methodType(int.class, MemoryAddress.class, byte.class, byte.class, byte.class);
    private static FunctionDescriptor SDL_MapRGB_fg = FunctionDescriptor.of(CLinker.C_INT, CLinker.C_POINTER, CLinker.C_CHAR, CLinker.C_CHAR, CLinker.C_CHAR);
    MemoryAddress SDL_MapRGB_symbol = SymbolLookup.loaderLookup().lookup("SDL_MapRGB").orElseThrow();
    MethodHandle SDL_MapRGB_mh = CLinker.getInstance().downcallHandle(SDL_MapRGB_symbol, SDL_MapRGB_mt, SDL_MapRGB_fg);

    public int SDL_MapRGB(int r, int g, int b) throws Throwable {
        return (int) sdl_mapRGB_mh_adapted.invoke(Integer.valueOf(r).byteValue(), Integer.valueOf(g).byteValue(), Integer.valueOf(b).byteValue());
    }


}
