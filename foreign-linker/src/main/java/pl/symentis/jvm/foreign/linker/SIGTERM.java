package pl.symentis.jvm.foreign.linker;

import jdk.incubator.foreign.CLinker;
import jdk.incubator.foreign.FunctionDescriptor;
import jdk.incubator.foreign.LibraryLookup;
import jdk.incubator.foreign.MemoryAddress;

import java.lang.invoke.MethodHandles;
import java.lang.invoke.MethodType;

import static java.lang.System.out;

public class SIGTERM {

    public static void main(String[] args) throws Throwable {
        var libraryLookup = LibraryLookup.ofDefault();

        var cLinker = CLinker.getInstance();

        out.println("creating signal handler stub");
        var signalHandler = MethodHandles.lookup()
                .findStatic(SIGTERM.class, "onSignal",
                        MethodType.methodType(void.class, int.class));
        var signalHandlerStub = cLinker.upcallStub(signalHandler,
                FunctionDescriptor.ofVoid(CLinker.C_INT));

        out.println("installing signal handler " + signalHandlerStub);
        var signal = libraryLookup.lookup("signal")
                .orElseThrow(() -> new RuntimeException("signal symbol not found"));
        var signalHandle = cLinker.downcallHandle(signal,
                MethodType.methodType(void.class, int.class, MemoryAddress.class),
                FunctionDescriptor.ofVoid(CLinker.C_INT, CLinker.C_POINTER));
        signalHandle.invoke(15, signalHandlerStub.address());

        out.println("Press <ENTER> to quit");
        System.in.read();
    }

    public static void onSignal(int signal) {
        out.println("Received signal " + signal);
    }
}
