package pl.symentis.jvm.foreign.memory;

import jdk.incubator.foreign.*;

import java.lang.invoke.VarHandle;
import java.lang.ref.Cleaner;
import java.nio.ByteOrder;

public class Main {

    public static void main(String[] args) {
        try (var memorySegment = MemorySegment.allocateNative(1024)) {
            MemoryAccess.setIntAtIndex(memorySegment, 0L, 1);

            var cleaner = Cleaner.create();

            var newOwnerSegment = memorySegment.handoff(new Thread());
            memorySegment.share();

        }
    }

    public void withNativeScope() {
        try (var nativeScope = NativeScope.boundedScope(4 * 4096)) {
            var memorySegment0 = nativeScope.allocate(4096);
            var memorySegment1 = nativeScope.allocate(4096);
            var memorySegment2 = nativeScope.allocate(4096);
            var memorySegment3 = nativeScope.allocate(4096);
        }
    }

    public void withMemoryLayout() {
        MemoryLayout.ofSequence(1024, CLinker.C_INT);
        var struct = MemoryLayout.ofStruct(
                CLinker.C_INT,
                CLinker.C_INT,
                CLinker.C_POINTER);


    }

    public void dereferrncingMemory() {

        var varHandle = MemoryHandles.varHandle(int.class, ByteOrder.nativeOrder());
        varHandle.toMethodHandle(VarHandle.AccessMode.GET).type();



        try (var memorySegment = MemorySegment.allocateNative(1024)) {
            Memo
        }        
    }
}