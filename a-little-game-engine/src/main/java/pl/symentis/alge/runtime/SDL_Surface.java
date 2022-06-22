package pl.symentis.alge.runtime;

import jdk.incubator.foreign.*;

import java.lang.invoke.MethodHandle;
import java.lang.invoke.MethodType;
import java.lang.invoke.VarHandle;

public class SDL_Surface {

    static final GroupLayout layout = MemoryLayout.structLayout(
                    CLinker.C_INT.withName("flags"),
                    MemoryLayout.paddingLayout(32),
                    CLinker.C_POINTER.withName("format"),
                    CLinker.C_INT.withName("w"),
                    CLinker.C_INT.withName("h"),
                    CLinker.C_INT.withName("pitch"),
                    MemoryLayout.paddingLayout(32),
                    CLinker.C_POINTER.withName("pixels"),
                    CLinker.C_POINTER.withName("userdata"),
                    CLinker.C_INT.withName("locked"),
                    MemoryLayout.paddingLayout(32),
                    CLinker.C_POINTER.withName("list_blitmap"),
                    MemoryLayout.structLayout(
                                    CLinker.C_INT.withName("x"),
                                    CLinker.C_INT.withName("y"),
                                    CLinker.C_INT.withName("w"),
                                    CLinker.C_INT.withName("h"))
                            .withName("clip_rect"),
                    CLinker.C_POINTER.withName("map"),
                    CLinker.C_INT.withName("refcount"),
                    MemoryLayout.paddingLayout(32))
            .withName("SDL_Surface");
    private static final VarHandle w_vh = layout.varHandle(int.class, MemoryLayout.PathElement.groupElement("w"));

    static int w(MemorySegment memorySegment) {
        return (Integer) w_vh.get(memorySegment);
    }

    private static final VarHandle h_vh = layout.varHandle(int.class, MemoryLayout.PathElement.groupElement("h"));

    public static int h(MemorySegment memorySegment) {
        return (Integer) h_vh.get(memorySegment);
    }

    private static final VarHandle format_vh = MemoryHandles.asAddressVarHandle(
            layout.varHandle(long.class, MemoryLayout.PathElement.groupElement("format")));

    public static MemoryAddress format(MemorySegment memorySegment) {
        return (MemoryAddress) format_vh.get(memorySegment);
    }

    private static final MethodHandle SDL_FillRect_mh = CLinker.getInstance()
            .downcallHandle(
                    SDLRuntime.lookupSymbol("SDL_FillRect"),
                    MethodType.methodType(int.class, MemoryAddress.class, MemoryAddress.class, int.class),
                    FunctionDescriptor.of(CLinker.C_INT, CLinker.C_POINTER, CLinker.C_POINTER, CLinker.C_INT));

    private static final MethodHandle SDL_BlitSurface_mh = CLinker.getInstance()
            .downcallHandle(
                    SDLRuntime.lookupSymbol("SDL_UpperBlit"),
                    MethodType.methodType(
                            int.class,
                            MemoryAddress.class,
                            MemoryAddress.class,
                            MemoryAddress.class,
                            MemoryAddress.class),
                    FunctionDescriptor.of(
                            CLinker.C_INT, CLinker.C_POINTER, CLinker.C_POINTER, CLinker.C_POINTER, CLinker.C_POINTER));

    private static final MethodHandle SDL_UpperBlitScaled_mh = CLinker.getInstance()
            .downcallHandle(
                    SDLRuntime.lookupSymbol("SDL_UpperBlitScaled"),
                    MethodType.methodType(
                            int.class,
                            MemoryAddress.class,
                            MemoryAddress.class,
                            MemoryAddress.class,
                            MemoryAddress.class),
                    FunctionDescriptor.of(
                            CLinker.C_INT, CLinker.C_POINTER, CLinker.C_POINTER, CLinker.C_POINTER, CLinker.C_POINTER));

    private static final MethodHandle SDL_FreeSurface_mh = CLinker.getInstance()
            .downcallHandle(
                    SDLRuntime.lookupSymbol("SDL_FreeSurface"),
                    MethodType.methodType(void.class, MemoryAddress.class),
                    FunctionDescriptor.ofVoid(CLinker.C_POINTER));

    private final MemoryAddress wrapped;
    private final MethodHandle SDL_FillRect_mh_$;

    SDL_Surface(MemoryAddress wrapped) {
        this.wrapped = wrapped;
        SDL_FillRect_mh_$ = SDL_FillRect_mh.bindTo(wrapped.address());
    }

    public void fillRect(SDL_Rect rect, int color) throws Throwable {
        int err = (int) SDL_FillRect_mh_$.invoke(rect == null ? MemoryAddress.NULL : rect, color);
        if (err != 0) {}
    }

    public void blitSurface(SDL_Surface src, SDL_Rect srcrect, SDL_Rect destrec) throws Throwable {
        SDL_BlitSurface_mh.invoke(
                src.wrapped.address(),
                srcrect == null ? MemoryAddress.NULL : srcrect.wrapped.address(),
                wrapped.address(),
                destrec == null ? MemoryAddress.NULL : destrec.wrapped.address());
    }

    public void blitScaled(SDL_Surface src, SDL_Rect srcrect, SDL_Rect destrec)  {
        try {
            SDL_UpperBlitScaled_mh.invoke(
                    src.wrapped.address(),
                    srcrect == null ? MemoryAddress.NULL : srcrect.wrapped.address(),
                    wrapped.address(),
                    destrec == null ? MemoryAddress.NULL : destrec.wrapped.address());
        } catch (Throwable e) {
            throw new RuntimeException(e);
        }
    }

    public SDL_PixelFormat pixelFormat() {
        var memorySegment = wrapped.asSegment(layout.byteSize(), ResourceScope.globalScope());
        var sdl_PixelFormat = SDL_Surface.format(memorySegment)
                .asSegment(SDL_PixelFormat.layout.byteSize(), ResourceScope.globalScope());
        return new SDL_PixelFormat(sdl_PixelFormat);
    }

    public static void freeSurface(MemoryAddress memoryAddress) {
        try {
            SDL_FreeSurface_mh.invoke(memoryAddress);
        } catch (Throwable e) {
            e.printStackTrace();
        }
    }
}
