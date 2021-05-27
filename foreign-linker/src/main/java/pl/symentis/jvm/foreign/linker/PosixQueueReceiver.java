package pl.symentis.jvm.foreign.linker;

import jdk.incubator.foreign.CLinker;
import jdk.incubator.foreign.MemoryAccess;
import jdk.incubator.foreign.MemorySegment;
import pl.symentis.foreign.posix.errno_h;
import pl.symentis.foreign.posix.mqueue_h;

import static java.lang.String.format;
import static java.lang.System.out;

public class PosixQueueReceiver {

    private static int MSG_SIZE = 8192;

    public static void main(String[] args) {

        try (var mqueue_name = CLinker.toCString("/queue");
             var mq_attr = mqueue_h.mq_attr.allocate();) {

            mqueue_h.mq_attr.mq_maxmsg$set(mq_attr, 300);
            mqueue_h.mq_attr.mq_msgsize$set(mq_attr, MSG_SIZE);
            mqueue_h.mq_attr.mq_flags$set(mq_attr, 0);

            var queue_desc = mqueue_h.mq_open(mqueue_name,
                    mqueue_h.O_RDWR() | mqueue_h.O_CREAT(),
                    0664, // permission of queue
                    0); // additional attributes

            if (queue_desc == -1) {
                int errno = errno();
                throw new RuntimeException("failed to mq_open with errno " + errno);
            }

            out.println("POSIX queue opened");

            while (true) {
                try (var msg = MemorySegment.allocateNative(MSG_SIZE);
                     var msg_prio = MemorySegment.allocateNative(CLinker.C_LONG)) {
                    var receivedBytes = mqueue_h.mq_receive(queue_desc, msg, MSG_SIZE, msg_prio);

                    if (receivedBytes != -1) {
                        out.println(format("Message received: %s", CLinker.toJavaString(msg)));
                    } else {
                        int errno = errno();
                        throw new RuntimeException("failed to mq_receive with errno " + errno);
                    }
                    Thread.sleep(1000);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }

        }
    }

    private static int errno() {
        var errno = errno_h.__errno_location().asSegmentRestricted(4);
        return MemoryAccess.getInt(errno);
    }
}
