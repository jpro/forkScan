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

	#chmod +x compile.sh

	#./compile.sh

	#cd target

	#java -jar forkScan.jar path
where:

path - path to view files

#### Result for view /usr

	Recursive algorithm:
	----------------------------------------------------------------------------------------------------
	|Path                | Threads| Directories|      Files|            Summary files size|   Used time|
	----------------------------------------------------------------------------------------------------
	|/usr                |       1|        9882|     119710|        4383390289b(  4,1 GiB)|      6751ms|
	----------------------------------------------------------------------------------------------------

	Only on threads algorithm:
	----------------------------------------------------------------------------------------------------
	|Path                | Threads| Directories|      Files|            Summary files size|   Used time|
	----------------------------------------------------------------------------------------------------
	|/usr                |       2|        9882|     119710|        4383390289b(  4,1 GiB)|      4231ms|
	----------------------------------------------------------------------------------------------------

	Optimized algorithm:
	----------------------------------------------------------------------------------------------------
	|Path                | Threads| Directories|      Files|            Summary files size|   Used time|
	----------------------------------------------------------------------------------------------------
	|/usr                |       2|        9882|     119710|        4383390289b(  4,1 GiB)|      3692ms|
	----------------------------------------------------------------------------------------------------
