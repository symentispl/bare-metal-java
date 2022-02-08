package pl.symentis.jvm.foreign.memory;

import jdk.incubator.foreign.*;

public class Main {

    public static void main(String[] args) {
        try (var memorySegment = MemorySegment.allocateNative(1024)) {
            MemoryAccess.setIntAtIndex(memorySegment, 0L, 1);

            var newOwnerSegment = memorySegment.handoff(new Thread());
            var sharedMemorySegment = memorySegment.share();
        }
    }

    public static void withNativeScope() {
        try (var nativeScope = NativeScope.boundedScope(4 * 4096)) {
            var memorySegment0 = nativeScope.allocate(4096);
            var memorySegment1 = nativeScope.allocate(4096);
            var memorySegment2 = nativeScope.allocate(4096);
            var memorySegment3 = nativeScope.allocate(4096);
        }
    }

    public static void withMemoryLayout() {
        MemoryLayout.ofSequence(1024, CLinker.C_INT);
        var struct = MemoryLayout.ofStruct(
                CLinker.C_INT.withName("age"),
                CLinker.C_INT.withName("kids"),
                CLinker.C_POINTER.withName("birthDate"),
                MemoryLayout.ofStruct(CLinker.C_POINTER).withName("street")
                        .withName("address"));

        struct.varHandle(Addressable.class,
                MemoryLayout.PathElement.groupElement("address"),
                MemoryLayout.PathElement.groupElement("street"));
    }

}
