package pl.symentis.alge;

import jdk.incubator.foreign.CLinker;
import jdk.incubator.foreign.FunctionDescriptor;

import java.lang.invoke.MethodType;

public class GetPIDExample {

    public static void main(String[] args) throws Throwable {
        var libraryLookup = CLinker.systemLookup();

        var getpidSymbol = libraryLookup.lookup("getpid")
                .orElseThrow(() -> new RuntimeException("getpid symbol not found"));

        var functionDescriptor = FunctionDescriptor.of(CLinker.C_INT);
        var methodType = MethodType.methodType(int.class);
        var methodHandle = CLinker.getInstance()
                .downcallHandle(getpidSymbol,
                        methodType,
                        functionDescriptor);
        System.out.println(methodHandle.invoke());
    }
}





