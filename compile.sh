#!/bin/bash
mvn clean install
cd target
ls -l | awk -F'[0-9][0-9]:[0-9][0-9]' '/^d/{print $NF}'| xargs -i rm -rf '{}' \;
mv fork*.jar forkScan.jar
echo "#!/bin/bash" > run.sh
echo "java -jar forkScan.jar /usr ScanRecursive" >> run.sh
echo "java -jar forkScan.jar /usr ScanThread 2" >> run.sh
echo "java -jar forkScan.jar /usr ScanOptimize 2" >> run.sh
chmod +x run.sh
