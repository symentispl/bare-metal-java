package pl.symentis.alge.runtime;

import jdk.incubator.foreign.*;

import java.lang.invoke.VarHandle;

import static jdk.incubator.foreign.CLinker.C_INT;

public class SDL_Rect {

    static final MemoryLayout layout = MemoryLayout.structLayout(
            C_INT.withName("x"), C_INT.withName("y"), C_INT.withName("w"), C_INT.withName("h"));
    private static final VarHandle x_vh = layout.varHandle(int.class, MemoryLayout.PathElement.groupElement("x"));
    private static final VarHandle y_vh = layout.varHandle(int.class, MemoryLayout.PathElement.groupElement("y"));
    private static final VarHandle w_vh = layout.varHandle(int.class, MemoryLayout.PathElement.groupElement("w"));
    private static final VarHandle h_vh = layout.varHandle(int.class, MemoryLayout.PathElement.groupElement("h"));

    public static SDL_Rect create(int x, int y, int w, int h) {
        var memorySegment = MemorySegment.allocateNative(layout, ResourceScope.globalScope());
        x(memorySegment, x);
        y(memorySegment, y);
        w(memorySegment, w);
        h(memorySegment, h);
        return new SDL_Rect(memorySegment);
    }

    private static void h(MemorySegment memorySegment, int h) {
        h_vh.set(memorySegment, h);
    }

    private static void w(MemorySegment memorySegment, int w) {
        w_vh.set(memorySegment, w);
    }

    private static void y(MemorySegment memorySegment, int y) {
        y_vh.set(memorySegment, y);
    }

    private static void x(MemorySegment memorySegment, int x) {
        x_vh.set(memorySegment, x);
    }

    final MemorySegment wrapped;

    SDL_Rect(MemorySegment wrapped) {
        this.wrapped = wrapped;
    }

    public void moveX() {
        x(wrapped,x(wrapped)+1);
    }

    private static int x(MemorySegment wrapped) {
        return (int) x_vh.get(wrapped);
    }
}
