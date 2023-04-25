package pl.symentis.alge.runtime;

import static java.lang.foreign.MemoryLayout.PathElement.groupElement;

import java.lang.foreign.GroupLayout;
import java.lang.foreign.MemoryLayout;
import java.lang.foreign.MemorySegment;
import java.lang.foreign.SegmentScope;
import java.lang.foreign.ValueLayout;
import java.lang.invoke.VarHandle;

public class SDL_Event {

    static final GroupLayout layout = MemoryLayout.unionLayout(
                    ValueLayout.JAVA_INT.withName("type"),
                    MemoryLayout.structLayout(
                                    ValueLayout.JAVA_INT.withName("type"), ValueLayout.JAVA_INT.withName("timestamp"))
                            .withName("common"),
                    MemoryLayout.structLayout(
                                    ValueLayout.JAVA_INT.withName("type"),
                                    ValueLayout.JAVA_INT.withName("timestamp"),
                                    ValueLayout.JAVA_INT.withName("display"),
                                    ValueLayout.JAVA_BYTE.withName("event"),
                                    ValueLayout.JAVA_BYTE.withName("padding1"),
                                    ValueLayout.JAVA_BYTE.withName("padding2"),
                                    ValueLayout.JAVA_BYTE.withName("padding3"),
                                    ValueLayout.JAVA_INT.withName("data1"))
                            .withName("display"),
                    MemoryLayout.structLayout(
                                    ValueLayout.JAVA_INT.withName("type"),
                                    ValueLayout.JAVA_INT.withName("timestamp"),
                                    ValueLayout.JAVA_INT.withName("windowID"),
                                    ValueLayout.JAVA_BYTE.withName("event"),
                                    ValueLayout.JAVA_BYTE.withName("padding1"),
                                    ValueLayout.JAVA_BYTE.withName("padding2"),
                                    ValueLayout.JAVA_BYTE.withName("padding3"),
                                    ValueLayout.JAVA_INT.withName("data1"),
                                    ValueLayout.JAVA_INT.withName("data2"))
                            .withName("window"),
                    MemoryLayout.structLayout(
                                    ValueLayout.JAVA_INT.withName("type"),
                                    ValueLayout.JAVA_INT.withName("timestamp"),
                                    ValueLayout.JAVA_INT.withName("windowID"),
                                    ValueLayout.JAVA_BYTE.withName("state"),
                                    ValueLayout.JAVA_BYTE.withName("repeat"),
                                    ValueLayout.JAVA_BYTE.withName("padding2"),
                                    ValueLayout.JAVA_BYTE.withName("padding3"),
                                    MemoryLayout.structLayout(
                                                    ValueLayout.JAVA_INT.withName("scancode"),
                                                    ValueLayout.JAVA_INT.withName("sym"),
                                                    ValueLayout.JAVA_SHORT.withName("mod"),
                                                    MemoryLayout.paddingLayout(16),
                                                    ValueLayout.JAVA_INT.withName("unused"))
                                            .withName("keysym"))
                            .withName("key"),
                    MemoryLayout.structLayout(
                                    ValueLayout.JAVA_INT.withName("type"),
                                    ValueLayout.JAVA_INT.withName("timestamp"),
                                    ValueLayout.JAVA_INT.withName("windowID"),
                                    MemoryLayout.sequenceLayout(32, ValueLayout.JAVA_BYTE)
                                            .withName("text"),
                                    ValueLayout.JAVA_INT.withName("start"),
                                    ValueLayout.JAVA_INT.withName("length"))
                            .withName("edit"),
                    MemoryLayout.structLayout(
                                    ValueLayout.JAVA_INT.withName("type"),
                                    ValueLayout.JAVA_INT.withName("timestamp"),
                                    ValueLayout.JAVA_INT.withName("windowID"),
                                    MemoryLayout.sequenceLayout(32, ValueLayout.JAVA_BYTE)
                                            .withName("text"))
                            .withName("text"),
                    MemoryLayout.structLayout(
                                    ValueLayout.JAVA_INT.withName("type"),
                                    ValueLayout.JAVA_INT.withName("timestamp"),
                                    ValueLayout.JAVA_INT.withName("windowID"),
                                    ValueLayout.JAVA_INT.withName("which"),
                                    ValueLayout.JAVA_INT.withName("state"),
                                    ValueLayout.JAVA_INT.withName("x"),
                                    ValueLayout.JAVA_INT.withName("y"),
                                    ValueLayout.JAVA_INT.withName("xrel"),
                                    ValueLayout.JAVA_INT.withName("yrel"))
                            .withName("motion"),
                    MemoryLayout.structLayout(
                                    ValueLayout.JAVA_INT.withName("type"),
                                    ValueLayout.JAVA_INT.withName("timestamp"),
                                    ValueLayout.JAVA_INT.withName("windowID"),
                                    ValueLayout.JAVA_INT.withName("which"),
                                    ValueLayout.JAVA_BYTE.withName("button"),
                                    ValueLayout.JAVA_BYTE.withName("state"),
                                    ValueLayout.JAVA_BYTE.withName("clicks"),
                                    ValueLayout.JAVA_BYTE.withName("padding1"),
                                    ValueLayout.JAVA_INT.withName("x"),
                                    ValueLayout.JAVA_INT.withName("y"))
                            .withName("button"),
                    MemoryLayout.structLayout(
                                    ValueLayout.JAVA_INT.withName("type"),
                                    ValueLayout.JAVA_INT.withName("timestamp"),
                                    ValueLayout.JAVA_INT.withName("windowID"),
                                    ValueLayout.JAVA_INT.withName("which"),
                                    ValueLayout.JAVA_INT.withName("x"),
                                    ValueLayout.JAVA_INT.withName("y"),
                                    ValueLayout.JAVA_INT.withName("direction"),
                                    ValueLayout.JAVA_FLOAT.withName("preciseX"),
                                    ValueLayout.JAVA_FLOAT.withName("preciseY"))
                            .withName("wheel"),
                    MemoryLayout.structLayout(
                                    ValueLayout.JAVA_INT.withName("type"),
                                    ValueLayout.JAVA_INT.withName("timestamp"),
                                    ValueLayout.JAVA_INT.withName("which"),
                                    ValueLayout.JAVA_BYTE.withName("axis"),
                                    ValueLayout.JAVA_BYTE.withName("padding1"),
                                    ValueLayout.JAVA_BYTE.withName("padding2"),
                                    ValueLayout.JAVA_BYTE.withName("padding3"),
                                    ValueLayout.JAVA_SHORT.withName("value"),
                                    ValueLayout.JAVA_SHORT.withName("padding4"))
                            .withName("jaxis"),
                    MemoryLayout.structLayout(
                                    ValueLayout.JAVA_INT.withName("type"),
                                    ValueLayout.JAVA_INT.withName("timestamp"),
                                    ValueLayout.JAVA_INT.withName("which"),
                                    ValueLayout.JAVA_BYTE.withName("ball"),
                                    ValueLayout.JAVA_BYTE.withName("padding1"),
                                    ValueLayout.JAVA_BYTE.withName("padding2"),
                                    ValueLayout.JAVA_BYTE.withName("padding3"),
                                    ValueLayout.JAVA_SHORT.withName("xrel"),
                                    ValueLayout.JAVA_SHORT.withName("yrel"))
                            .withName("jball"),
                    MemoryLayout.structLayout(
                                    ValueLayout.JAVA_INT.withName("type"),
                                    ValueLayout.JAVA_INT.withName("timestamp"),
                                    ValueLayout.JAVA_INT.withName("which"),
                                    ValueLayout.JAVA_BYTE.withName("hat"),
                                    ValueLayout.JAVA_BYTE.withName("value"),
                                    ValueLayout.JAVA_BYTE.withName("padding1"),
                                    ValueLayout.JAVA_BYTE.withName("padding2"))
                            .withName("jhat"),
                    MemoryLayout.structLayout(
                                    ValueLayout.JAVA_INT.withName("type"),
                                    ValueLayout.JAVA_INT.withName("timestamp"),
                                    ValueLayout.JAVA_INT.withName("which"),
                                    ValueLayout.JAVA_BYTE.withName("button"),
                                    ValueLayout.JAVA_BYTE.withName("state"),
                                    ValueLayout.JAVA_BYTE.withName("padding1"),
                                    ValueLayout.JAVA_BYTE.withName("padding2"))
                            .withName("jbutton"),
                    MemoryLayout.structLayout(
                                    ValueLayout.JAVA_INT.withName("type"),
                                    ValueLayout.JAVA_INT.withName("timestamp"),
                                    ValueLayout.JAVA_INT.withName("which"))
                            .withName("jdevice"),
                    MemoryLayout.structLayout(
                                    ValueLayout.JAVA_INT.withName("type"),
                                    ValueLayout.JAVA_INT.withName("timestamp"),
                                    ValueLayout.JAVA_INT.withName("which"),
                                    ValueLayout.JAVA_BYTE.withName("axis"),
                                    ValueLayout.JAVA_BYTE.withName("padding1"),
                                    ValueLayout.JAVA_BYTE.withName("padding2"),
                                    ValueLayout.JAVA_BYTE.withName("padding3"),
                                    ValueLayout.JAVA_SHORT.withName("value"),
                                    ValueLayout.JAVA_SHORT.withName("padding4"))
                            .withName("caxis"),
                    MemoryLayout.structLayout(
                                    ValueLayout.JAVA_INT.withName("type"),
                                    ValueLayout.JAVA_INT.withName("timestamp"),
                                    ValueLayout.JAVA_INT.withName("which"),
                                    ValueLayout.JAVA_BYTE.withName("button"),
                                    ValueLayout.JAVA_BYTE.withName("state"),
                                    ValueLayout.JAVA_BYTE.withName("padding1"),
                                    ValueLayout.JAVA_BYTE.withName("padding2"))
                            .withName("cbutton"),
                    MemoryLayout.structLayout(
                                    ValueLayout.JAVA_INT.withName("type"),
                                    ValueLayout.JAVA_INT.withName("timestamp"),
                                    ValueLayout.JAVA_INT.withName("which"))
                            .withName("cdevice"),
                    MemoryLayout.structLayout(
                                    ValueLayout.JAVA_INT.withName("type"),
                                    ValueLayout.JAVA_INT.withName("timestamp"),
                                    ValueLayout.JAVA_INT.withName("which"),
                                    ValueLayout.JAVA_INT.withName("touchpad"),
                                    ValueLayout.JAVA_INT.withName("finger"),
                                    ValueLayout.JAVA_FLOAT.withName("x"),
                                    ValueLayout.JAVA_FLOAT.withName("y"),
                                    ValueLayout.JAVA_FLOAT.withName("pressure"))
                            .withName("ctouchpad"),
                    MemoryLayout.structLayout(
                                    ValueLayout.JAVA_INT.withName("type"),
                                    ValueLayout.JAVA_INT.withName("timestamp"),
                                    ValueLayout.JAVA_INT.withName("which"),
                                    ValueLayout.JAVA_INT.withName("sensor"),
                                    MemoryLayout.sequenceLayout(3, ValueLayout.JAVA_FLOAT)
                                            .withName("data"))
                            .withName("csensor"),
                    MemoryLayout.structLayout(
                                    ValueLayout.JAVA_INT.withName("type"),
                                    ValueLayout.JAVA_INT.withName("timestamp"),
                                    ValueLayout.JAVA_INT.withName("which"),
                                    ValueLayout.JAVA_BYTE.withName("iscapture"),
                                    ValueLayout.JAVA_BYTE.withName("padding1"),
                                    ValueLayout.JAVA_BYTE.withName("padding2"),
                                    ValueLayout.JAVA_BYTE.withName("padding3"))
                            .withName("adevice"),
                    MemoryLayout.structLayout(
                                    ValueLayout.JAVA_INT.withName("type"),
                                    ValueLayout.JAVA_INT.withName("timestamp"),
                                    ValueLayout.JAVA_INT.withName("which"),
                                    MemoryLayout.sequenceLayout(6, ValueLayout.JAVA_FLOAT)
                                            .withName("data"))
                            .withName("sensor"),
                    MemoryLayout.structLayout(
                                    ValueLayout.JAVA_INT.withName("type"), ValueLayout.JAVA_INT.withName("timestamp"))
                            .withName("quit"),
                    MemoryLayout.structLayout(
                                    ValueLayout.JAVA_INT.withName("type"),
                                    ValueLayout.JAVA_INT.withName("timestamp"),
                                    ValueLayout.JAVA_INT.withName("windowID"),
                                    ValueLayout.JAVA_INT.withName("code"),
                                    ValueLayout.ADDRESS.withName("data1"),
                                    ValueLayout.ADDRESS.withName("data2"))
                            .withName("user"),
                    MemoryLayout.structLayout(
                                    ValueLayout.JAVA_INT.withName("type"),
                                    ValueLayout.JAVA_INT.withName("timestamp"),
                                    ValueLayout.ADDRESS.withName("msg"))
                            .withName("syswm"),
                    MemoryLayout.structLayout(
                                    ValueLayout.JAVA_INT.withName("type"),
                                    ValueLayout.JAVA_INT.withName("timestamp"),
                                    ValueLayout.JAVA_LONG.withName("touchId"),
                                    ValueLayout.JAVA_LONG.withName("fingerId"),
                                    ValueLayout.JAVA_FLOAT.withName("x"),
                                    ValueLayout.JAVA_FLOAT.withName("y"),
                                    ValueLayout.JAVA_FLOAT.withName("dx"),
                                    ValueLayout.JAVA_FLOAT.withName("dy"),
                                    ValueLayout.JAVA_FLOAT.withName("pressure"),
                                    ValueLayout.JAVA_INT.withName("windowID"))
                            .withName("tfinger"),
                    MemoryLayout.structLayout(
                                    ValueLayout.JAVA_INT.withName("type"),
                                    ValueLayout.JAVA_INT.withName("timestamp"),
                                    ValueLayout.JAVA_LONG.withName("touchId"),
                                    ValueLayout.JAVA_FLOAT.withName("dTheta"),
                                    ValueLayout.JAVA_FLOAT.withName("dDist"),
                                    ValueLayout.JAVA_FLOAT.withName("x"),
                                    ValueLayout.JAVA_FLOAT.withName("y"),
                                    ValueLayout.JAVA_SHORT.withName("numFingers"),
                                    ValueLayout.JAVA_SHORT.withName("padding"),
                                    MemoryLayout.paddingLayout(32))
                            .withName("mgesture"),
                    MemoryLayout.structLayout(
                                    ValueLayout.JAVA_INT.withName("type"),
                                    ValueLayout.JAVA_INT.withName("timestamp"),
                                    ValueLayout.JAVA_LONG.withName("touchId"),
                                    ValueLayout.JAVA_LONG.withName("gestureId"),
                                    ValueLayout.JAVA_INT.withName("numFingers"),
                                    ValueLayout.JAVA_FLOAT.withName("error"),
                                    ValueLayout.JAVA_FLOAT.withName("x"),
                                    ValueLayout.JAVA_FLOAT.withName("y"))
                            .withName("dgesture"),
                    MemoryLayout.structLayout(
                                    ValueLayout.JAVA_INT.withName("type"),
                                    ValueLayout.JAVA_INT.withName("timestamp"),
                                    ValueLayout.ADDRESS.withName("file"),
                                    ValueLayout.JAVA_INT.withName("windowID"),
                                    MemoryLayout.paddingLayout(32))
                            .withName("drop"),
                    MemoryLayout.sequenceLayout(56, ValueLayout.JAVA_BYTE).withName("padding"))
            .withName("SDL_Event");
    private final VarHandle SDL_Event_type_vh = layout.varHandle(groupElement("type"));

    private final VarHandle SDL_Event_keysym_sym_vh =
            layout.varHandle(groupElement("key"), groupElement("keysym"), groupElement("sym"));

    public static SDL_Event empty() {
        return new SDL_Event(MemorySegment.allocateNative(layout.byteSize(), SegmentScope.global()));
    }

    final MemorySegment wrapped;

    public SDL_Event(MemorySegment segment) {
        this.wrapped = segment;
    }

    public int type() {
        return (int) SDL_Event_type_vh.get(wrapped);
    }

    public int keySymbolCode() {
        return (int) SDL_Event_keysym_sym_vh.get(wrapped);
    }
}
