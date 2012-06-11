##forkScan

#### What is it?
forkScan is a utilite that help you calculate files size, file and directories count in specify path. It used fork and join framework from jdk 1.7 but implement with fork and join library that ported to 1.6 version. 

#### System Requirments
    JDK:
      1.6 or above with jsr166y library that can bee download here (http://search.maven.org/remotecontent?filepath=org/codehaus/jsr166-mirror/jsr166y/1.7.0/jsr166y-1.7.0.jar).
    
    Memory:
      No minimum requirment.

    Disk:
      No minimum requirment.

    Operating system:
      No minimum requirment. Linux, Windows or Mac OS X. Tested on openSuse 11.2(JDK 1.6.0_32 x64).

    Other tools:
      Maven 2 or higher.(For compilation)

#### Installing forkScan
Download all files.
Go to folder which consist compile.sh. Then run in command line:

	#chmod +x

	#./compile.sh

	#cd target

	#java -jar forkScan.jar path thread_count
where:

path - path to view files

thread_count - count of threads, must be in range of 1 and 100

#### ScreenShots:
http://img402.imageshack.us/img402/81/shot1r.png

http://img854.imageshack.us/img854/9711/shot2.png

http://img687.imageshack.us/img687/4302/shot3x.png

http://img560.imageshack.us/img560/391/shot4.png