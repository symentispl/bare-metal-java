package pl.symentis.jvm.foreign.linker;

import jdk.incubator.foreign.CLinker;
import jdk.incubator.foreign.FunctionDescriptor;
import jdk.incubator.foreign.LibraryLookup;

import java.lang.invoke.MethodType;

public class GetPIDExample {

    public static void main(String[] args) throws Throwable {
        var libraryLookup = LibraryLookup.ofDefault();

        var getpidSymbol = libraryLookup.lookup("getpid")
                .orElseThrow(() -> new RuntimeException("getpid symbol not found"));

        var functionDescriptor = FunctionDescriptor.of(CLinker.C_INT);
        var methodType = MethodType.methodType(int.class);
        var methodHandle = CLinker.getInstance().downcallHandle(getpidSymbol,
                methodType,
                functionDescriptor);
        System.out.println(methodHandle.invoke());;
    }
}





