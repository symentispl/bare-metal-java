package pl.symentis.jvm.foreign.linker;

import jdk.incubator.foreign.CLinker;
import jdk.incubator.foreign.FunctionDescriptor;
import jdk.incubator.foreign.MemoryAddress;
import jdk.incubator.foreign.ResourceScope;
import jdk.incubator.foreign.ValueLayout;

import java.lang.invoke.MethodHandles;
import java.lang.invoke.MethodType;

import static java.lang.System.out;

public class SIGTERM
{

    public static void main( String[] args ) throws Throwable
    {
        var cLinker = CLinker.systemCLinker();

        out.println( "creating signal handler stub" );
        // QUESTION: why the hell global scope?
        var resourceScope = ResourceScope.globalScope();
        var signalHandler = MethodHandles.lookup()
                                         .findStatic( SIGTERM.class, "onSignal",
                                                      MethodType.methodType( void.class, int.class ) );
        var signalHandlerStub = cLinker.upcallStub( signalHandler,
                                                    FunctionDescriptor.ofVoid( ValueLayout.JAVA_INT ), resourceScope );

        out.println( "installing signal handler " + signalHandlerStub );
        var signal = cLinker.lookup( "signal" )
                            .orElseThrow( () -> new RuntimeException( "signal symbol not found" ) );
        var signalHandle = cLinker.downcallHandle( signal,
                                                   FunctionDescriptor.ofVoid( ValueLayout.JAVA_INT, ValueLayout.ADDRESS ) );
        out.println( signalHandle.type() );
        signalHandle.invoke( 15, signalHandlerStub.address() );

        out.println( "Press <ENTER> to quit" );
        System.in.read();
    }

    public static void onSignal( int signal )
    {
        out.println( "Received signal " + signal );
    }
}
