package pl.symentis.jvm.foreign.linker;

import jdk.incubator.foreign.CLinker;
import jdk.incubator.foreign.FunctionDescriptor;
import jdk.incubator.foreign.ValueLayout;

import java.lang.invoke.MethodType;

public class GetPIDExample
{

    public static void main( String[] args ) throws Throwable
    {
        var cLinker = CLinker.systemCLinker();

        var getpidSymbol = cLinker.lookup( "getpid" )
                                  .orElseThrow( () -> new RuntimeException( "getpid symbol not found" ) );

        var functionDescriptor = FunctionDescriptor.of( ValueLayout.JAVA_INT );
        var methodHandle = cLinker
                .downcallHandle( getpidSymbol,
                                 functionDescriptor );
        System.out.println( methodHandle.invoke() );
    }
}





