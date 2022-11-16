#!/bin/bash

# I want to make sure that the directory is clean and has nothing left over from
# previous deployments. The servers auto scale so the directory may or may not
# exist.
if [ -d /home/ubuntu/app/zip/ ]; then
    rm -rf /home/ubuntu/app/zip/
fi
mkdir -vp /home/ubuntu/app/zip/