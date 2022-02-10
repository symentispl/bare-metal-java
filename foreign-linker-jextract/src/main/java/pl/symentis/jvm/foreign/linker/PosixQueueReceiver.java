package pl.symentis.jvm.foreign.linker;

import jdk.incubator.foreign.*;
import pl.symentis.foreign.posix.errno.errno_h;
import pl.symentis.foreign.posix.mqueue.mq_attr;
import pl.symentis.foreign.posix.mqueue.mqueue_h;

import static java.lang.System.out;

public class PosixQueueReceiver {

    private static int MSG_SIZE = 8192;

    public static void main(String[] args) {
        System.loadLibrary("rt");
        try (var resourceScope = ResourceScope.newConfinedScope()) {

            var segmentAllocator = SegmentAllocator.nativeAllocator(resourceScope);
            var mqueue_name = segmentAllocator.allocateUtf8String("/queue");

            var mqAttr = mq_attr.allocate(resourceScope);
            mq_attr.mq_maxmsg$set(mqAttr, 300);
            mq_attr.mq_msgsize$set(mqAttr, MSG_SIZE);
            mq_attr.mq_flags$set(mqAttr, 0);

            var queue_desc = mqueue_h.mq_open(mqueue_name,
                    mqueue_h.O_RDWR() | mqueue_h.O_CREAT(),
                    0664, // permission of queue
                    0); // additional attributes

            if (queue_desc == -1) {
                int errno = errno(resourceScope);
                throw new RuntimeException("failed to mq_open with errno " + errno);
            }

            out.println("POSIX queue opened");

            while (true) {
                try (var innerResourceScope = ResourceScope.newConfinedScope()) {

                    var msg = MemorySegment.allocateNative(MSG_SIZE, innerResourceScope);
                    var msg_prio = MemorySegment.allocateNative(ValueLayout.JAVA_LONG, innerResourceScope);
                    var receivedBytes = mqueue_h.mq_receive(queue_desc, msg, MSG_SIZE, msg_prio);

                    if (receivedBytes != -1) {
                        out.printf("Message received: %s%n", msg.getUtf8String(0));
                    } else {
                        int errno = errno(innerResourceScope);
                        throw new RuntimeException("failed to mq_receive with errno " + errno);
                    }
                    Thread.sleep(1000);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }

            }
        }

    }

    private static int errno(ResourceScope resourceScope) {
        return errno_h.__errno_location().get(ValueLayout.JAVA_INT, 0);
    }
}
