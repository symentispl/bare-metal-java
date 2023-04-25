package pl.symentis.alge.runtime;

import static java.lang.foreign.MemoryLayout.PathElement.*;

import java.lang.foreign.FunctionDescriptor;
import java.lang.foreign.GroupLayout;
import java.lang.foreign.Linker;
import java.lang.foreign.MemoryLayout;
import java.lang.foreign.MemorySegment;
import java.lang.foreign.ValueLayout;
import java.lang.invoke.MethodHandle;
import java.lang.invoke.VarHandle;

public class SDL_Surface {

    static final GroupLayout layout = MemoryLayout.structLayout(
                    ValueLayout.JAVA_INT.withName("flags"),
                    MemoryLayout.paddingLayout(32),
                    ValueLayout.ADDRESS.withName("format"),
                    ValueLayout.JAVA_INT.withName("w"),
                    ValueLayout.JAVA_INT.withName("h"),
                    ValueLayout.JAVA_INT.withName("pitch"),
                    MemoryLayout.paddingLayout(32),
                    ValueLayout.ADDRESS.withName("pixels"),
                    ValueLayout.ADDRESS.withName("userdata"),
                    ValueLayout.JAVA_INT.withName("locked"),
                    MemoryLayout.paddingLayout(32),
                    ValueLayout.ADDRESS.withName("list_blitmap"),
                    MemoryLayout.structLayout(
                                    ValueLayout.JAVA_INT.withName("x"),
                                    ValueLayout.JAVA_INT.withName("y"),
                                    ValueLayout.JAVA_INT.withName("w"),
                                    ValueLayout.JAVA_INT.withName("h"))
                            .withName("clip_rect"),
                    ValueLayout.ADDRESS.withName("map"),
                    ValueLayout.JAVA_INT.withName("refcount"),
                    MemoryLayout.paddingLayout(32))
            .withName("SDL_Surface");
    private static final VarHandle w_vh = layout.varHandle(groupElement("w"));

    static int w(MemorySegment memorySegment) {
        return (Integer) w_vh.get(memorySegment);
    }

    private static final VarHandle h_vh = layout.varHandle(groupElement("h"));

    public static int h(MemorySegment memorySegment) {
        return (Integer) h_vh.get(memorySegment);
    }

    private static final VarHandle format_vh = layout.varHandle(groupElement("format"));

    public static MemorySegment format(MemorySegment memorySegment) {
        return (MemorySegment) format_vh.get(memorySegment);
    }

    private static final MethodHandle SDL_FillRect_mh = Linker.nativeLinker()
            .downcallHandle(
                    SDLRuntime.lookupSymbol("SDL_FillRect"),
                    FunctionDescriptor.of(
                            ValueLayout.JAVA_INT, ValueLayout.ADDRESS, ValueLayout.ADDRESS, ValueLayout.JAVA_INT));

    private static final MethodHandle SDL_BlitSurface_mh = Linker.nativeLinker()
            .downcallHandle(
                    SDLRuntime.lookupSymbol("SDL_UpperBlit"),
                    FunctionDescriptor.of(
                            ValueLayout.JAVA_INT,
                            ValueLayout.ADDRESS,
                            ValueLayout.ADDRESS,
                            ValueLayout.ADDRESS,
                            ValueLayout.ADDRESS));

    private static final MethodHandle SDL_UpperBlitScaled_mh = Linker.nativeLinker()
            .downcallHandle(
                    SDLRuntime.lookupSymbol("SDL_UpperBlitScaled"),
                    FunctionDescriptor.of(
                            ValueLayout.JAVA_INT,
                            ValueLayout.ADDRESS,
                            ValueLayout.ADDRESS,
                            ValueLayout.ADDRESS,
                            ValueLayout.ADDRESS));

    private static final MethodHandle SDL_FreeSurface_mh = Linker.nativeLinker()
            .downcallHandle(SDLRuntime.lookupSymbol("SDL_FreeSurface"), FunctionDescriptor.ofVoid(ValueLayout.ADDRESS));

    private final MemorySegment wrapped;
    private final MethodHandle SDL_FillRect_mh_$;

    SDL_Surface(MemorySegment wrapped) {
        this.wrapped = wrapped;
        SDL_FillRect_mh_$ = SDL_FillRect_mh.bindTo(wrapped);
    }

    public void fillRect(SDL_Rect rect, int color) throws Throwable {
        int err = (int) SDL_FillRect_mh_$.invoke(rect == null ? MemorySegment.NULL : rect, color);
        if (err != 0) {}
    }

    public void blitSurface(SDL_Surface src, SDL_Rect srcrect, SDL_Rect destrec) throws Throwable {
        SDL_BlitSurface_mh.invoke(
                src.wrapped.address(),
                srcrect == null ? MemorySegment.NULL : srcrect.wrapped,
                wrapped.address(),
                destrec == null ? MemorySegment.NULL : destrec.wrapped);
    }

    public void blitScaled(SDL_Surface src, SDL_Rect srcrect, SDL_Rect destrec) {
        try {
            SDL_UpperBlitScaled_mh.invoke(
                    src.wrapped,
                    srcrect == null ? MemorySegment.NULL : srcrect.wrapped,
                    wrapped,
                    destrec == null ? MemorySegment.NULL : destrec.wrapped);
        } catch (Throwable e) {
            throw new RuntimeException(e);
        }
    }

    public SDL_PixelFormat pixelFormat() {
        var sdl_PixelFormat = SDL_Surface.format(wrapped);
        return new SDL_PixelFormat(sdl_PixelFormat);
    }

    public static void freeSurface(MemorySegment MemorySegment) {
        try {
            SDL_FreeSurface_mh.invoke(MemorySegment);
        } catch (Throwable e) {
            e.printStackTrace();
        }
    }
}
