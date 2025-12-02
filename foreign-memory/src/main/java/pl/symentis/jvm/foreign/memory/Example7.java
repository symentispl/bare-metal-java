package pl.symentis.jvm.foreign.memory;

import static java.lang.System.out;
import static java.lang.foreign.MemoryLayout.PathElement.*;

import java.lang.foreign.Arena;
import java.lang.foreign.MemoryLayout;
import java.lang.foreign.MemorySegment;
import java.lang.foreign.ValueLayout;
import java.lang.invoke.MethodHandle;
import java.lang.invoke.VarHandle;

public class Example7 {

    public static void main(String[] args) throws Throwable {
        MemorySegment memorySegment;
        try (var arena = Arena.ofConfined()) {

            var structLayout = MemoryLayout.structLayout(
                    ValueLayout.JAVA_LONG.withName("year"),
                    ValueLayout.JAVA_SHORT.withName("month"),
                    ValueLayout.JAVA_SHORT.withName("day"),
                    MemoryLayout.structLayout(ValueLayout.JAVA_SHORT.withName("offset"))
                            .withName("timezone"));

            memorySegment = arena.allocateFrom("string");
            var yearVarHandler = structLayout.varHandle(groupElement("year"));
            yearVarHandler.set(memorySegment, 1975L);
            var year = yearVarHandler.get(memorySegment);
            out.printf("year is %d%n", year);
            var timezoneOffsetVarHandler = structLayout.varHandle(groupElement("timezone"), groupElement("offset"));
            timezoneOffsetVarHandler.set(memorySegment, (short) 1);
            var timezoneOffset = timezoneOffsetVarHandler.get(memorySegment);
            out.printf("timezone offset is %d%n", timezoneOffset);

            // here comes method handle magic
            var timezoneOffsetmethodHandle = timezoneOffsetVarHandler
                    .toMethodHandle(VarHandle.AccessMode.GET)
                    .bindTo(memorySegment);
            out.println("method handle " + timezoneOffsetmethodHandle.type());
            out.printf("timezone offset using method handler is %d%n", timezoneOffsetmethodHandle.invoke());

            // even more magic with method handle
            timezoneOffsetmethodHandle = timezoneOffsetVarHandler.toMethodHandle(VarHandle.AccessMode.GET_VOLATILE);
            out.printf(
                    "timezone offset using method handler is volatile access is %d%n",
                    timezoneOffsetmethodHandle.invoke(memorySegment));
        }
    }
}
