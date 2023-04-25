#!/usr/bin/env zsh
[[ -s "$HOME/.sdkman/bin/sdkman-init.sh" ]] && source "$HOME/.sdkman/bin/sdkman-init.sh"
sdk use java 20-tem
jshell -R -Dforeign.restricted=permit --enable-preview

