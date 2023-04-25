package pl.symentis.jvm.foreign;

import static java.lang.System.out;

import java.lang.foreign.FunctionDescriptor;
import java.lang.foreign.Linker;
import java.lang.foreign.SegmentScope;
import java.lang.foreign.ValueLayout;
import java.lang.invoke.MethodHandles;
import java.lang.invoke.MethodType;

public class SIGTERM {

    public static void main(String[] args) throws Throwable {
        var linker = Linker.nativeLinker();
        var symbolLookup = linker.defaultLookup();

        out.println("creating signal handler stub");
        // QUESTION: why the hell global scope?
        var globalSession = SegmentScope.global();
        var signalHandler = MethodHandles.lookup()
                .findStatic(SIGTERM.class, "onSignal", MethodType.methodType(void.class, int.class));
        var signalHandlerStub =
                linker.upcallStub(signalHandler, FunctionDescriptor.ofVoid(ValueLayout.JAVA_INT), globalSession);

        out.println("installing signal handler " + signalHandlerStub);
        var signal = symbolLookup.find("signal").orElseThrow(() -> new RuntimeException("signal symbol not found"));
        var signalHandle =
                linker.downcallHandle(signal, FunctionDescriptor.ofVoid(ValueLayout.JAVA_INT, ValueLayout.ADDRESS));
        out.println(signalHandle.type());
        signalHandle.invoke(15, signalHandlerStub);

        out.println("Press <ENTER> to quit");
        System.in.read();
    }

    public static void onSignal(int signal) {
        out.println("Received signal " + Thread.currentThread());
    }
}
