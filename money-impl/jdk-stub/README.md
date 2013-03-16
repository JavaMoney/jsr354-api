jdk-stub
========================

What is it?
-----------

This project is a JDK Stub, it contains implementing classes aimed at Java 9 SE in the OpenJDK project. It is a runnable Maven 3 project. 


System requirements
-------------------

All you need to build this project is **Java 8.0 (Java SDK 1.8)** or an equivalent Java version with **JavaFX**, Maven 3.0 or better.

The application this project produces is designed to be run on JavaFX 2 or above.

Setting up the project
-------------------

In order to make Maven build the project, you need to select a Java 8 compliant JVM or another runtime environment including JavaFX. 

###Steps for Command Line
If you run Maven from the command line instead of an IDE like Eclipse or NetBeans, please ensure, the `JAVA_HOME` environment variable points to an appropriate version of Java 8 or above. Either JRE or JDK as long as it contains JavaFX.

###Steps for Eclipse
Add "jdk 1.8.0" or equivalent like "jre8" to your "Installed JREs" in Eclipse, if they don't exist there. 
![Image](/src/site/resources/images/Eclipse_JRE8_0_1.png "Add JRE to Eclipse")

Under "Java Build Path" of "Project Properties" select the JRE System Library in the "Libraries" tab.
![Image](/src/site/resources/images/Eclipse_JRE8_1.png "Java Build Path in Eclipse")


Click "Edit" and select "jdk 1.8.0" or equivalent from the "Alternate JRE" list. Should the "Workspace default JRE" state something like "jdk 1.8.0" or "jre8", you may use that, too. Only if your Eclipse version already supports Java 8 and above, you may also select "JavaSE-1.8" or an appropriate similar value in "Execution environment".

![Image](/src/site/resources/images/Eclipse_JRE8_2.png "Edit Library in Eclipse")

###Steps for NetBeans
Add Java Platform like "JDK 1.8" or equivalent to NetBeans, if they don't exist in the Java Platform Manager. 
![Image](/src/site/resources/images/NB7_JRE8_1.png "Add Java Platform to NetBeans")

Under "Build > Compile" of "Project Properties" select "JDK 1.8" or equivalent from the "Java Platform" list. 
![Image](/src/site/resources/images/NB7_JRE8_2.png "Project Properties in NetBeans")
