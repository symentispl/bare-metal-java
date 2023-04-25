package pl.symentis.jvm.foreign.memory;

import java.lang.foreign.Arena;
import java.lang.foreign.MemorySegment;
import java.lang.foreign.ValueLayout;

public class Example3 {

    public static void main(String[] args) {
        MemorySegment memorySegment;
        try (var arena = Arena.openShared()) {
            memorySegment = MemorySegment.allocateNative(1024, arena.scope());
            var thread = new Thread(() -> {
                memorySegment.setAtIndex(ValueLayout.JAVA_LONG, 1, 666);
            });
            thread.start();
            thread.join();
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }
    }
}
