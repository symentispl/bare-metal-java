package pl.symentis.jvm.foreign.memory;

import static java.lang.System.out;

import java.lang.foreign.Arena;
import java.lang.foreign.MemorySegment;
import java.lang.foreign.ValueLayout;

public class Example1 {

    public static void main(String[] args) {
        try (var arena = Arena.openConfined()) {
            var memorySegment = MemorySegment.allocateNative(1024, arena.scope());
            memorySegment.setAtIndex(ValueLayout.JAVA_LONG, 1, 666);
            out.printf("value at index zero is %d%n", memorySegment.getAtIndex(ValueLayout.JAVA_LONG, 1));
            out.printf("value at offset zero is %d%n", memorySegment.get(ValueLayout.JAVA_LONG, 1));
        }
    }
}
