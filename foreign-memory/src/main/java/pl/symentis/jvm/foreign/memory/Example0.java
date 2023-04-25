package pl.symentis.jvm.foreign.memory;

import static java.lang.System.out;

import java.lang.foreign.MemorySegment;
import java.lang.foreign.SegmentScope;
import java.lang.foreign.ValueLayout;

public class Example0 {
    public static void main(String[] args) {
        var memorySegment = MemorySegment.allocateNative(1024, SegmentScope.auto());
        memorySegment.set(ValueLayout.JAVA_LONG, 0, 666);
        out.printf("value at offset zero is %d\n", memorySegment.get(ValueLayout.JAVA_LONG, 0));
    }
}
