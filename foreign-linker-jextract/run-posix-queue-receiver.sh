#!/usr/bin/env bash

[[ -s "$HOME/.sdkman/bin/sdkman-init.sh" ]] && source "$HOME/.sdkman/bin/sdkman-init.sh"
sdk use java 19-panama-ea
#export LD_LIBRARY_PATH=/lib/x86_64-linux-gnu/
java --module-path target/classes --add-modules jdk.incubator.foreign,pl.symentis.jvm.foreign.memory \
     --enable-native-access pl.symentis.jvm.foreign.memory \
     -Djava.library.path=/usr/lib/x86_64-linux-gnu:/usr/java/packages/lib:/usr/lib64:/lib64:/lib:/usr/lib \
     pl.symentis.jvm.foreign.PosixQueueReceiver
