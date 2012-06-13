##forkScan

#### What is it?
forkScan is a utilite that help you calculate files size, file and directories count in specify path. It used fork and join framework from jdk 1.7 but implement with fork and join library that ported to 1.6 version.
**** 

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
****

#### Installing forkScan
Download all files.
Go to folder which consist compile.sh. Then run in command line:

	#chmod +x compile.sh

	#./compile.sh

	#cd target

	#./run.sh


or manualy run:

	#chmod +x compile.sh

	#./compile.sh

	#cd target && java -jar forkScan.jar path algorithm [thread count]

where

- **path** - path to view

- **algorithm** - one of three algorithm:
	- ScanRecursive
	- ScanThread
	- ScanOptimize

- **thread count** - thread count which application will be used:
	- min value **1**
	- max value **100**
	- def value **2**

ScanRecursive algorithm doesn't require thread count.

****

#### Description

- **Recursive algorithm** perfom calculations in one thread with no optimizations. Simple iteration on each file and forlder in search path.

- **Only threads algorithm** perform calculations with specify threads count. Each viewed directory is a future task, which will be runned to view nested files and directories.

- **Optimized algorithm** perform calculations with specify optimizations. When application view specify path it count nested directories, if they are more than 10, then current application work as previous algorithm. But if directories count are less than 10 application view nested directories directly with recursive algorithm. 

****

#### Result for view /usr

	Recursive algorithm:
	----------------------------------------------------------------------------------------------------
	|Path                | Threads| Directories|      Files|            Summary files size|   Used time|
	----------------------------------------------------------------------------------------------------
	|/usr                |       1|        9882|     119710|        4383390289b(  4,1 GiB)|      6560ms|
	----------------------------------------------------------------------------------------------------

	Only on threads algorithm:
	----------------------------------------------------------------------------------------------------
	|Path                | Threads| Directories|      Files|            Summary files size|   Used time|
	----------------------------------------------------------------------------------------------------
	|/usr                |       2|        9882|     119710|        4383390289b(  4,1 GiB)|      5542ms|
	----------------------------------------------------------------------------------------------------

	Optimized algorithm:
	----------------------------------------------------------------------------------------------------
	|Path                | Threads| Directories|      Files|            Summary files size|   Used time|
	----------------------------------------------------------------------------------------------------
	|/usr                |       2|        9882|     119710|        4383390289b(  4,1 GiB)|      5382ms|
	----------------------------------------------------------------------------------------------------
****
#### Conclusion
From result of work of these algorithms we can say, that multithreading applications with some optimizations are wins before non multitasking applications. Multitasking increase speed of the program.
