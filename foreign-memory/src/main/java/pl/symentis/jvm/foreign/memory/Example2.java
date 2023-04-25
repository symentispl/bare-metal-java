package pl.symentis.jvm.foreign.memory;

import java.lang.foreign.Arena;
import java.lang.foreign.MemorySegment;
import java.lang.foreign.ValueLayout;

public class Example2 {

    public static void main(String[] args) {
        MemorySegment memorySegment;
        try (var arena = Arena.openConfined()) {
            memorySegment = MemorySegment.allocateNative(1024, arena.scope());
        }
        memorySegment.setAtIndex(ValueLayout.JAVA_LONG, 1, 666);
    }
}
