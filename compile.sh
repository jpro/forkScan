#!/bin/bash
mvn install
cd target/classes
jar -cvfm forkScan.jar ../../src/MANIFEST.MF com/exoplatform/forkScan/*.class
cd ..
rm *.jar
mv classes/forkScan.jar ./forkScan.jar
mv classes/jsr166y-1.7.0.jar ./jsr166y-1.7.0.jar
rm -r classes maven-archiver