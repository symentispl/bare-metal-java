package pl.symentis.jvm.foreign.memory;

import static java.lang.System.out;

import java.lang.foreign.*;
import java.lang.foreign.MemoryLayout.PathElement;
import java.lang.invoke.VarHandle;

public class Example6 {

    public static void main(String[] args) {
        MemorySegment memorySegment;
        try (var arena = Arena.openConfined()) {
            var sequenceLayout = MemoryLayout.sequenceLayout(16, ValueLayout.JAVA_INT);
            memorySegment = MemorySegment.allocateNative(sequenceLayout, arena.scope());
            var varHandle = sequenceLayout.varHandle(PathElement.sequenceElement(1));
            varHandle.set(memorySegment, 666);
            var value = varHandle.get(memorySegment);
            out.printf("value at index zero is %d%n", value);
            out.printf(
                    "method handle type for get access mode is %s\n",
                    varHandle.toMethodHandle(VarHandle.AccessMode.GET).type());
            out.printf(
                    "method handle type for set access mode is %s\n",
                    varHandle.toMethodHandle(VarHandle.AccessMode.SET).type());
        }
    }
}
