package pl.symentis.jvm.foreign.memory;

import java.lang.foreign.MemorySegment;
import java.lang.foreign.SegmentScope;

public class Example4 {

    public static void main(String[] args) {
        MemorySegment.allocateNative(1024, SegmentScope.auto());
    }
}
