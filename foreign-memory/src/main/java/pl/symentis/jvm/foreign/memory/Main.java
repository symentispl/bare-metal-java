package pl.symentis.jvm.foreign.memory;

import jdk.incubator.foreign.*;

import java.lang.invoke.MethodHandles;
import java.lang.invoke.VarHandle;
import java.nio.ByteOrder;

import static java.lang.System.out;
import static jdk.incubator.foreign.MemoryLayout.PathElement.groupElement;

public class Main
{

    public static void main( String[] args )
    {
        try ( ResourceScope resourceScope = ResourceScope.newConfinedScope() )
        {
            var memorySegment = MemorySegment.allocateNative( 1024, resourceScope );
        }

        try ( ResourceScope resourceScope = ResourceScope.newSharedScope() )
        {
            var memorySegment = MemorySegment.allocateNative( 1024, resourceScope );
        }

        withMemoryHandles();
        withMemoryLayout();
    }

    public static void withMemoryHandles()
    {
        var varHandleGet = MemoryHandles.varHandle( ValueLayout.JAVA_INT );
        out.println( "Var handle for GET access mode: " + varHandleGet.toMethodHandle( VarHandle.AccessMode.GET ).type() );

        var varHandleSet = MemoryHandles.varHandle( ValueLayout.JAVA_INT );
        out.println( "Var handle for SET access mode: " + varHandleSet.toMethodHandle( VarHandle.AccessMode.SET ).type() );

        try ( var resourceScope = ResourceScope.newConfinedScope() )
        {
            var memorySegment = MemorySegment.allocateNative( 1024, resourceScope );
            var offset = 512;
            var value = 2048;
            varHandleGet.set( memorySegment, offset, value );

            out.println( "value = " + varHandleGet.get( memorySegment, offset ) );

            var varHandle = MemoryHandles.insertCoordinates( varHandleGet, 1, offset );
            out.println( "modified var handle for GET access mode" + varHandle.toMethodHandle( VarHandle.AccessMode.GET ).type() );
            out.println( "value = " + varHandle.get( memorySegment ) );
        }
    }

    public static void withMemoryLayout()
    {
        var sequenceLayout = MemoryLayout.sequenceLayout( 1024, ValueLayout.JAVA_INT );
        var seqVarHandle = sequenceLayout.varHandle( MemoryLayout.PathElement.sequenceElement( 0 ) );
        out.println( "set array[0] method handle type: " + seqVarHandle.toMethodHandle( VarHandle.AccessMode.SET ).type() );
        out.println( "get array[0] method handle type: " + seqVarHandle.toMethodHandle( VarHandle.AccessMode.GET ).type() );

        var struct = MemoryLayout.structLayout(
                ValueLayout.JAVA_INT.withName( "age" ),
                ValueLayout.JAVA_INT.withName( "kids" ),
                ValueLayout.ADDRESS.withName( "birthDate" ),
                MemoryLayout.structLayout( ValueLayout.ADDRESS.withName( "street" ) )
                            .withName( "address" ) );

        var varHandle = struct.varHandle( groupElement( "age" ) );
        out.println( "set age field in struct method handle type: " + varHandle.toMethodHandle( VarHandle.AccessMode.SET ).type() );
        out.println( "get age field in struct method handle type: " + varHandle.toMethodHandle( VarHandle.AccessMode.GET ).type() );

        var streetVarHandle = struct.varHandle( groupElement( "address" ), groupElement( "street" ) );
        out.println( "set address.street field in struct method handle type: " + streetVarHandle.toMethodHandle( VarHandle.AccessMode.SET ).type() );
        out.println( "get address.street field in struct method handle type: " + streetVarHandle.toMethodHandle( VarHandle.AccessMode.GET ).type() );

        try ( var resourceScope = ResourceScope.newConfinedScope() )
        {
            SegmentAllocator segmentAllocator = SegmentAllocator.nativeAllocator( resourceScope );
            MemorySegment structSegment = segmentAllocator.allocate( struct );
            var stringSegment = segmentAllocator.allocateUtf8String( "Wielkiego Stacha 16b, Psie Pole" );
            streetVarHandle.set( structSegment, stringSegment.address() );
            MemoryAddress o = (MemoryAddress) streetVarHandle.get( structSegment );
            out.println( o.getUtf8String( 0 ) );
        }
    }
}
