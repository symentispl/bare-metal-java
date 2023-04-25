package pl.symentis.jvm.foreign;

import java.lang.foreign.FunctionDescriptor;
import java.lang.foreign.Linker;
import java.lang.foreign.ValueLayout;

public class GetPIDExample {

    public static void main(String[] args) throws Throwable {
        var linker = Linker.nativeLinker();
        var symbolLookup = linker.defaultLookup();

        var getpidSymbol =
                symbolLookup.find("getpid").orElseThrow(() -> new RuntimeException("getpid symbol not found"));

        var functionDescriptor = FunctionDescriptor.of(ValueLayout.JAVA_INT);

        var methodHandle = linker.downcallHandle(getpidSymbol, functionDescriptor);
        System.out.println(methodHandle.invoke());
    }
}
