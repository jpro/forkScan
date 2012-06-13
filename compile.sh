#!/bin/bash
mvn clean install
cd target
ls -l | awk -F'[0-9][0-9]:[0-9][0-9]' '/^d/{print $NF}'| xargs -i rm -rf '{}' \;
mv fork*.jar forkScan.jar