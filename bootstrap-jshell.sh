#!/usr/bin/env zsh
[[ -s "$HOME/.sdkman/bin/sdkman-init.sh" ]] && source "$HOME/.sdkman/bin/sdkman-init.sh"
sdk use java 17.0.0-tem
jshell --add-modules jdk.incubator.foreign,jdk.incubator.vector -R -Dforeign.restricted=permit
