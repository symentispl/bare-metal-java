package pl.symentis.alge.runtime;

import java.lang.foreign.FunctionDescriptor;
import java.lang.foreign.GroupLayout;
import java.lang.foreign.Linker;
import java.lang.foreign.MemoryLayout;
import java.lang.foreign.MemorySegment;
import java.lang.foreign.SymbolLookup;
import java.lang.foreign.ValueLayout;
import java.lang.invoke.MethodHandle;
import java.lang.invoke.MethodHandles;
import java.lang.invoke.MethodType;
import java.lang.invoke.VarHandle;

public class SDL_PixelFormat {

    static final GroupLayout layout = MemoryLayout.structLayout(
                    ValueLayout.JAVA_INT.withName("format"),
                    MemoryLayout.paddingLayout(32),
                    ValueLayout.ADDRESS.withName("palette"),
                    ValueLayout.JAVA_BYTE.withName("BitsPerPixel"),
                    ValueLayout.JAVA_BYTE.withName("BytesPerPixel"),
                    MemoryLayout.sequenceLayout(2, ValueLayout.JAVA_BYTE).withName("padding"),
                    ValueLayout.JAVA_INT.withName("Rmask"),
                    ValueLayout.JAVA_INT.withName("Gmask"),
                    ValueLayout.JAVA_INT.withName("Bmask"),
                    ValueLayout.JAVA_INT.withName("Amask"),
                    ValueLayout.JAVA_BYTE.withName("Rloss"),
                    ValueLayout.JAVA_BYTE.withName("Gloss"),
                    ValueLayout.JAVA_BYTE.withName("Bloss"),
                    ValueLayout.JAVA_BYTE.withName("Aloss"),
                    ValueLayout.JAVA_BYTE.withName("Rshift"),
                    ValueLayout.JAVA_BYTE.withName("Gshift"),
                    ValueLayout.JAVA_BYTE.withName("Bshift"),
                    ValueLayout.JAVA_BYTE.withName("Ashift"),
                    ValueLayout.JAVA_INT.withName("refcount"),
                    ValueLayout.ADDRESS.withName("next"))
            .withName("SDL_PixelFormat");
    private static VarHandle bytesPerPixel_vh =
            layout.varHandle(MemoryLayout.PathElement.groupElement("BytesPerPixel"));
    private final MethodHandle sdl_mapRGB_mh_adapted;

    static byte bytesPerPixel(MemorySegment memorySegment) {
        return (byte) bytesPerPixel_vh.get(memorySegment);
    }

    private final MemorySegment wrapped;

    public SDL_PixelFormat(MemorySegment wrapped) {
        this.wrapped = wrapped;
        this.sdl_mapRGB_mh_adapted = MethodHandles.insertArguments(SDL_MapRGB_mh, 0, wrapped);
    }

    private static MethodType SDL_MapRGB_mt =
            MethodType.methodType(int.class, MemorySegment.class, byte.class, byte.class, byte.class);
    private static FunctionDescriptor SDL_MapRGB_fg = FunctionDescriptor.of(
            ValueLayout.JAVA_INT,
            ValueLayout.ADDRESS,
            ValueLayout.JAVA_BYTE,
            ValueLayout.JAVA_BYTE,
            ValueLayout.JAVA_BYTE);
    MemorySegment SDL_MapRGB_symbol =
            SymbolLookup.loaderLookup().find("SDL_MapRGB").orElseThrow();
    MethodHandle SDL_MapRGB_mh = Linker.nativeLinker().downcallHandle(SDL_MapRGB_symbol, SDL_MapRGB_fg);

    public int SDL_MapRGB(int r, int g, int b) throws Throwable {
        return (int) sdl_mapRGB_mh_adapted.invoke(
                Integer.valueOf(r).byteValue(),
                Integer.valueOf(g).byteValue(),
                Integer.valueOf(b).byteValue());
    }
}
