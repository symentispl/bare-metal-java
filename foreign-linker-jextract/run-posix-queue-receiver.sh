#!/usr/bin/env bash
export LD_LIBRARY_PATH=/lib/x86_64-linux-gnu/
java --module-path target/classes --add-modules jdk.incubator.foreign,pl.symentis.jvm.foreign.memory \
     --enable-native-access pl.symentis.jvm.foreign.memory pl.symentis.jvm.foreign.linker.PosixQueueReceiver