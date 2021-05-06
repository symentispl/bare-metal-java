package pl.symentis.jvm.foreign.linker;

import jdk.incubator.foreign.*;
import pl.symentis.foreign.posix.errno_h;
import pl.symentis.foreign.posix.mqueue_h;

import java.io.IOException;

import static java.lang.String.format;
import static java.lang.System.out;

public class PosixQueueReceive {

    private static int MSG_SIZE = 4096;

    public static void main(String[] args) throws IOException {


        try (var mqueue_name = CLinker.toCString("/queue");
             var mq_attr = mqueue_h.mq_attr.allocate();) {

            mqueue_h.mq_attr.mq_maxmsg$set(mq_attr, 300);
            mqueue_h.mq_attr.mq_msgsize$set(mq_attr, MSG_SIZE);
            mqueue_h.mq_attr.mq_flags$set(mq_attr, 0);

            var queue_desc = mqueue_h.mq_open(mqueue_name,
                    mqueue_h.O_RDWR() | mqueue_h.O_CREAT(),
                    0664, // permission of queue
                    mq_attr.address()); // additional attributes

            if (queue_desc == -1) {
                var errno = errno_h.__errno_location().asSegmentRestricted(4);
                var anInt = MemoryAccess.getInt(errno);
                out.println(anInt);
            }

            out.println("POSIX queue opened");

            while (true) {
                try (var msg = MemorySegment.allocateNative(MSG_SIZE);
                     var msg_prio = MemorySegment.allocateNative(CLinker.C_LONG)) {
                    mqueue_h.mq_receive(queue_desc, msg, MSG_SIZE, msg_prio);
                    out.println(format("Message received: %s", CLinker.toJavaString(msg)));
                }
            }

        }
    }
}
