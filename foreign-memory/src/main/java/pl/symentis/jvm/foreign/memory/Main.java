package pl.symentis.jvm.foreign.memory;

import jdk.incubator.foreign.*;

import java.lang.invoke.VarHandle;

import static java.lang.System.out;
import static jdk.incubator.foreign.MemoryLayout.PathElement.groupElement;

public class Main {

    public static void main(String[] args) {
        try (ResourceScope resourceScope = ResourceScope.newConfinedScope()) {
            var memorySegment = MemorySegment.allocateNative(1024, resourceScope);
        }

        try (ResourceScope resourceScope = ResourceScope.newSharedScope()) {
            var memorySegment = MemorySegment.allocateNative(1024, resourceScope);
        }

        withMemoryLayout();

    }

    public static void withMemoryLayout() {
        var sequenceLayout = MemoryLayout.sequenceLayout(1024, CLinker.C_INT);
        var seqVarHandle = sequenceLayout.varHandle(int.class, MemoryLayout.PathElement.sequenceElement(0));
        out.println("set array[0] method handle type: " + seqVarHandle.toMethodHandle(VarHandle.AccessMode.SET).type());
        out.println("get array[0] method handle type: " + seqVarHandle.toMethodHandle(VarHandle.AccessMode.GET).type());

        var struct = MemoryLayout.structLayout(
                CLinker.C_INT.withName("age"),
                CLinker.C_INT.withName("kids"),
                CLinker.C_POINTER.withName("birthDate"),
                MemoryLayout.structLayout(CLinker.C_POINTER.withName("street"))
                        .withName("address"));

        var varHandle = struct.varHandle(int.class,
                groupElement("age"));
        out.println("set age field in struct method handle type: " + varHandle.toMethodHandle(VarHandle.AccessMode.SET).type());
        out.println("get age field in struct method handle type: " + varHandle.toMethodHandle(VarHandle.AccessMode.GET).type());

        var streetVarHandle = struct.varHandle(long.class,
                groupElement("address"), groupElement("street"));
        out.println("set address.street field in struct method handle type: " + streetVarHandle.toMethodHandle(VarHandle.AccessMode.SET).type());
        out.println("get address.street field in struct method handle type: " + streetVarHandle.toMethodHandle(VarHandle.AccessMode.GET).type());

    }

}
