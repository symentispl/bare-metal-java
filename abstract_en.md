Bare metal Java

Java, in fact JVM, has a well-deserved fame as a hardware-unfriendly platform, and therefore nobody (except Apache Kafka, Elastic, Cassandra and Neo4j) will build databases, queues and other solutions where "mechanical sympathy" is crucial , in Java.
Garbage collector, speculating JIT, lack of control over "object layout", terrible support (mostly due to lack of abstraction) for modern processors and operating systems.
And there is also JNI (who was there, I know what I'm talking about). (I do not mention the implementation of IO)

The purpose of this presentation is to disenchant these myths and to show how much effort has recently been going into making JVM more hardware friendly.
It will be about APIs which are still in the incubation phase. You may never see these APIs in your life, but they will be a foundation of databases, queue brokers and game engines (I am not joking) in the future.

I will focus on two JEPs, foreign functions & memory and vector API.

We will learn how to work with native memory (with seatbelts fastened), downcall and upcall C functions from Java (with ease) and squeeze every second
from SIMD instructions on your CPU (so you CPU silicon will melt).

Everything will be dipped in a sauce of broken examples and distorted micro benchmarks and OpenJDK compiled five minutes before the presentation.