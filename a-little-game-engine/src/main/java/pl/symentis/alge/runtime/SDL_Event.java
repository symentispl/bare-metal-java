package pl.symentis.alge.runtime;

import jdk.incubator.foreign.*;

import java.lang.invoke.VarHandle;

import static jdk.incubator.foreign.MemoryLayout.PathElement.*;

public class SDL_Event {

    static final GroupLayout layout = MemoryLayout.unionLayout(
                    CLinker.C_INT.withName("type"),
                    MemoryLayout.structLayout(CLinker.C_INT.withName("type"), CLinker.C_INT.withName("timestamp"))
                            .withName("common"),
                    MemoryLayout.structLayout(
                                    CLinker.C_INT.withName("type"),
                                    CLinker.C_INT.withName("timestamp"),
                                    CLinker.C_INT.withName("display"),
                                    CLinker.C_CHAR.withName("event"),
                                    CLinker.C_CHAR.withName("padding1"),
                                    CLinker.C_CHAR.withName("padding2"),
                                    CLinker.C_CHAR.withName("padding3"),
                                    CLinker.C_INT.withName("data1"))
                            .withName("display"),
                    MemoryLayout.structLayout(
                                    CLinker.C_INT.withName("type"),
                                    CLinker.C_INT.withName("timestamp"),
                                    CLinker.C_INT.withName("windowID"),
                                    CLinker.C_CHAR.withName("event"),
                                    CLinker.C_CHAR.withName("padding1"),
                                    CLinker.C_CHAR.withName("padding2"),
                                    CLinker.C_CHAR.withName("padding3"),
                                    CLinker.C_INT.withName("data1"),
                                    CLinker.C_INT.withName("data2"))
                            .withName("window"),
                    MemoryLayout.structLayout(
                                    CLinker.C_INT.withName("type"),
                                    CLinker.C_INT.withName("timestamp"),
                                    CLinker.C_INT.withName("windowID"),
                                    CLinker.C_CHAR.withName("state"),
                                    CLinker.C_CHAR.withName("repeat"),
                                    CLinker.C_CHAR.withName("padding2"),
                                    CLinker.C_CHAR.withName("padding3"),
                                    MemoryLayout.structLayout(
                                                    CLinker.C_INT.withName("scancode"),
                                                    CLinker.C_INT.withName("sym"),
                                                    CLinker.C_SHORT.withName("mod"),
                                                    MemoryLayout.paddingLayout(16),
                                                    CLinker.C_INT.withName("unused"))
                                            .withName("keysym"))
                            .withName("key"),
                    MemoryLayout.structLayout(
                                    CLinker.C_INT.withName("type"),
                                    CLinker.C_INT.withName("timestamp"),
                                    CLinker.C_INT.withName("windowID"),
                                    MemoryLayout.sequenceLayout(32, CLinker.C_CHAR)
                                            .withName("text"),
                                    CLinker.C_INT.withName("start"),
                                    CLinker.C_INT.withName("length"))
                            .withName("edit"),
                    MemoryLayout.structLayout(
                                    CLinker.C_INT.withName("type"),
                                    CLinker.C_INT.withName("timestamp"),
                                    CLinker.C_INT.withName("windowID"),
                                    MemoryLayout.sequenceLayout(32, CLinker.C_CHAR)
                                            .withName("text"))
                            .withName("text"),
                    MemoryLayout.structLayout(
                                    CLinker.C_INT.withName("type"),
                                    CLinker.C_INT.withName("timestamp"),
                                    CLinker.C_INT.withName("windowID"),
                                    CLinker.C_INT.withName("which"),
                                    CLinker.C_INT.withName("state"),
                                    CLinker.C_INT.withName("x"),
                                    CLinker.C_INT.withName("y"),
                                    CLinker.C_INT.withName("xrel"),
                                    CLinker.C_INT.withName("yrel"))
                            .withName("motion"),
                    MemoryLayout.structLayout(
                                    CLinker.C_INT.withName("type"),
                                    CLinker.C_INT.withName("timestamp"),
                                    CLinker.C_INT.withName("windowID"),
                                    CLinker.C_INT.withName("which"),
                                    CLinker.C_CHAR.withName("button"),
                                    CLinker.C_CHAR.withName("state"),
                                    CLinker.C_CHAR.withName("clicks"),
                                    CLinker.C_CHAR.withName("padding1"),
                                    CLinker.C_INT.withName("x"),
                                    CLinker.C_INT.withName("y"))
                            .withName("button"),
                    MemoryLayout.structLayout(
                                    CLinker.C_INT.withName("type"),
                                    CLinker.C_INT.withName("timestamp"),
                                    CLinker.C_INT.withName("windowID"),
                                    CLinker.C_INT.withName("which"),
                                    CLinker.C_INT.withName("x"),
                                    CLinker.C_INT.withName("y"),
                                    CLinker.C_INT.withName("direction"),
                                    CLinker.C_FLOAT.withName("preciseX"),
                                    CLinker.C_FLOAT.withName("preciseY"))
                            .withName("wheel"),
                    MemoryLayout.structLayout(
                                    CLinker.C_INT.withName("type"),
                                    CLinker.C_INT.withName("timestamp"),
                                    CLinker.C_INT.withName("which"),
                                    CLinker.C_CHAR.withName("axis"),
                                    CLinker.C_CHAR.withName("padding1"),
                                    CLinker.C_CHAR.withName("padding2"),
                                    CLinker.C_CHAR.withName("padding3"),
                                    CLinker.C_SHORT.withName("value"),
                                    CLinker.C_SHORT.withName("padding4"))
                            .withName("jaxis"),
                    MemoryLayout.structLayout(
                                    CLinker.C_INT.withName("type"),
                                    CLinker.C_INT.withName("timestamp"),
                                    CLinker.C_INT.withName("which"),
                                    CLinker.C_CHAR.withName("ball"),
                                    CLinker.C_CHAR.withName("padding1"),
                                    CLinker.C_CHAR.withName("padding2"),
                                    CLinker.C_CHAR.withName("padding3"),
                                    CLinker.C_SHORT.withName("xrel"),
                                    CLinker.C_SHORT.withName("yrel"))
                            .withName("jball"),
                    MemoryLayout.structLayout(
                                    CLinker.C_INT.withName("type"),
                                    CLinker.C_INT.withName("timestamp"),
                                    CLinker.C_INT.withName("which"),
                                    CLinker.C_CHAR.withName("hat"),
                                    CLinker.C_CHAR.withName("value"),
                                    CLinker.C_CHAR.withName("padding1"),
                                    CLinker.C_CHAR.withName("padding2"))
                            .withName("jhat"),
                    MemoryLayout.structLayout(
                                    CLinker.C_INT.withName("type"),
                                    CLinker.C_INT.withName("timestamp"),
                                    CLinker.C_INT.withName("which"),
                                    CLinker.C_CHAR.withName("button"),
                                    CLinker.C_CHAR.withName("state"),
                                    CLinker.C_CHAR.withName("padding1"),
                                    CLinker.C_CHAR.withName("padding2"))
                            .withName("jbutton"),
                    MemoryLayout.structLayout(
                                    CLinker.C_INT.withName("type"),
                                    CLinker.C_INT.withName("timestamp"),
                                    CLinker.C_INT.withName("which"))
                            .withName("jdevice"),
                    MemoryLayout.structLayout(
                                    CLinker.C_INT.withName("type"),
                                    CLinker.C_INT.withName("timestamp"),
                                    CLinker.C_INT.withName("which"),
                                    CLinker.C_CHAR.withName("axis"),
                                    CLinker.C_CHAR.withName("padding1"),
                                    CLinker.C_CHAR.withName("padding2"),
                                    CLinker.C_CHAR.withName("padding3"),
                                    CLinker.C_SHORT.withName("value"),
                                    CLinker.C_SHORT.withName("padding4"))
                            .withName("caxis"),
                    MemoryLayout.structLayout(
                                    CLinker.C_INT.withName("type"),
                                    CLinker.C_INT.withName("timestamp"),
                                    CLinker.C_INT.withName("which"),
                                    CLinker.C_CHAR.withName("button"),
                                    CLinker.C_CHAR.withName("state"),
                                    CLinker.C_CHAR.withName("padding1"),
                                    CLinker.C_CHAR.withName("padding2"))
                            .withName("cbutton"),
                    MemoryLayout.structLayout(
                                    CLinker.C_INT.withName("type"),
                                    CLinker.C_INT.withName("timestamp"),
                                    CLinker.C_INT.withName("which"))
                            .withName("cdevice"),
                    MemoryLayout.structLayout(
                                    CLinker.C_INT.withName("type"),
                                    CLinker.C_INT.withName("timestamp"),
                                    CLinker.C_INT.withName("which"),
                                    CLinker.C_INT.withName("touchpad"),
                                    CLinker.C_INT.withName("finger"),
                                    CLinker.C_FLOAT.withName("x"),
                                    CLinker.C_FLOAT.withName("y"),
                                    CLinker.C_FLOAT.withName("pressure"))
                            .withName("ctouchpad"),
                    MemoryLayout.structLayout(
                                    CLinker.C_INT.withName("type"),
                                    CLinker.C_INT.withName("timestamp"),
                                    CLinker.C_INT.withName("which"),
                                    CLinker.C_INT.withName("sensor"),
                                    MemoryLayout.sequenceLayout(3, CLinker.C_FLOAT)
                                            .withName("data"))
                            .withName("csensor"),
                    MemoryLayout.structLayout(
                                    CLinker.C_INT.withName("type"),
                                    CLinker.C_INT.withName("timestamp"),
                                    CLinker.C_INT.withName("which"),
                                    CLinker.C_CHAR.withName("iscapture"),
                                    CLinker.C_CHAR.withName("padding1"),
                                    CLinker.C_CHAR.withName("padding2"),
                                    CLinker.C_CHAR.withName("padding3"))
                            .withName("adevice"),
                    MemoryLayout.structLayout(
                                    CLinker.C_INT.withName("type"),
                                    CLinker.C_INT.withName("timestamp"),
                                    CLinker.C_INT.withName("which"),
                                    MemoryLayout.sequenceLayout(6, CLinker.C_FLOAT)
                                            .withName("data"))
                            .withName("sensor"),
                    MemoryLayout.structLayout(CLinker.C_INT.withName("type"), CLinker.C_INT.withName("timestamp"))
                            .withName("quit"),
                    MemoryLayout.structLayout(
                                    CLinker.C_INT.withName("type"),
                                    CLinker.C_INT.withName("timestamp"),
                                    CLinker.C_INT.withName("windowID"),
                                    CLinker.C_INT.withName("code"),
                                    CLinker.C_POINTER.withName("data1"),
                                    CLinker.C_POINTER.withName("data2"))
                            .withName("user"),
                    MemoryLayout.structLayout(
                                    CLinker.C_INT.withName("type"),
                                    CLinker.C_INT.withName("timestamp"),
                                    CLinker.C_POINTER.withName("msg"))
                            .withName("syswm"),
                    MemoryLayout.structLayout(
                                    CLinker.C_INT.withName("type"),
                                    CLinker.C_INT.withName("timestamp"),
                                    CLinker.C_LONG.withName("touchId"),
                                    CLinker.C_LONG.withName("fingerId"),
                                    CLinker.C_FLOAT.withName("x"),
                                    CLinker.C_FLOAT.withName("y"),
                                    CLinker.C_FLOAT.withName("dx"),
                                    CLinker.C_FLOAT.withName("dy"),
                                    CLinker.C_FLOAT.withName("pressure"),
                                    CLinker.C_INT.withName("windowID"))
                            .withName("tfinger"),
                    MemoryLayout.structLayout(
                                    CLinker.C_INT.withName("type"),
                                    CLinker.C_INT.withName("timestamp"),
                                    CLinker.C_LONG.withName("touchId"),
                                    CLinker.C_FLOAT.withName("dTheta"),
                                    CLinker.C_FLOAT.withName("dDist"),
                                    CLinker.C_FLOAT.withName("x"),
                                    CLinker.C_FLOAT.withName("y"),
                                    CLinker.C_SHORT.withName("numFingers"),
                                    CLinker.C_SHORT.withName("padding"),
                                    MemoryLayout.paddingLayout(32))
                            .withName("mgesture"),
                    MemoryLayout.structLayout(
                                    CLinker.C_INT.withName("type"),
                                    CLinker.C_INT.withName("timestamp"),
                                    CLinker.C_LONG.withName("touchId"),
                                    CLinker.C_LONG.withName("gestureId"),
                                    CLinker.C_INT.withName("numFingers"),
                                    CLinker.C_FLOAT.withName("error"),
                                    CLinker.C_FLOAT.withName("x"),
                                    CLinker.C_FLOAT.withName("y"))
                            .withName("dgesture"),
                    MemoryLayout.structLayout(
                                    CLinker.C_INT.withName("type"),
                                    CLinker.C_INT.withName("timestamp"),
                                    CLinker.C_POINTER.withName("file"),
                                    CLinker.C_INT.withName("windowID"),
                                    MemoryLayout.paddingLayout(32))
                            .withName("drop"),
                    MemoryLayout.sequenceLayout(56, CLinker.C_CHAR).withName("padding"))
            .withName("SDL_Event");
    private final VarHandle SDL_Event_type_vh = layout.varHandle(int.class, groupElement("type"));

    private final VarHandle SDL_Event_keysym_sym_vh =
            layout.varHandle(int.class, groupElement("key"),groupElement("keysym"), groupElement("sym"));

    public static SDL_Event empty() {
        return new SDL_Event(MemorySegment.allocateNative(layout.byteSize(), ResourceScope.globalScope()));
    }

    final MemoryAddress wrapped;
    final MemorySegment segment;

    public SDL_Event(MemorySegment segment) {
        this.segment = segment;
        this.wrapped = segment.address();
    }

    public int type() {
        return (int) SDL_Event_type_vh.get(segment);
    }

    public int keySymbolCode() {
        return (int) SDL_Event_keysym_sym_vh.get(segment);
    }
}
