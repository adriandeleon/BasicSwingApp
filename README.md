# Basic Swing App

[![Java CI with Maven](https://github.com/adriandeleon/BasicSwingApp/actions/workflows/maven.yml/badge.svg)](https://github.com/adriandeleon/BasicSwingApp/actions/workflows/maven.yml)

## Building and running the app.

### Build and run a Fat Jar:

To build a Fat Jar (with all the dependencies), run:

```shell
mvn clean package
```
Then look in the `target` folder for a file with the following name:

`SwingApp-X.X-SNAPSHOT-shaded.jar`

to run the app:
```shell
java -jar SwingApp-X.X-SNAPSHOT-shaded.jar
```

### Create an application installer with jpackage:

To create an installer for the app, first compile it running:

```shell
mvn clean package
```
Change to  the target folder and run this `jpackage` command: 
```shell
 jpackage --input . --name SwingApp --main-jar .\SwingApp-X.X-SNAPSHOT-shaded.jar --main-class org.example.App --type exe --java-options '--enable-preview'
```
Where --type can be one of these options:

- `dmg`: Create an MacOS installer.
- `msi` or `exe`: Create a Windows installer. (You will need to install WIX v3 from [here](https://wixtoolset.org/docs/wix3/))
- `deb` or `rpm`: Create a deb or rpm Linux package.

After running `jpackage`, look for a SwingApp package in the target directory.

You don't need to install a JDK to run the package or the app after installing it. Everything is provided inside the installer/package.

For more info on jpackage, check this [Baeldung article.](https://www.baeldung.com/java14-jpackage)

### Create a native executable.

You will need GraalVM to generate native executables from the jar files:

Download and install the latest version of [GraalVM](https://www.graalvm.org/downloads/)
Make sure that GraalVM is your default Java JDK. Create a GRAALVM_HOME environment variable, then point your JAVA_HOME to GRAALVM_HOME. Add JAVA_HOME/bin to your PATH variable.

Test that GraalVM is your default JDK by checking your java version:

```shell
java -version
```

Verify that the output looks something like this:

``java -version
java version "22" 2024-03-19
Java(TM) SE Runtime Environment Oracle GraalVM 22+36.1 (build 22+36-jvmci-b02)
Java HotSpot(TM) 64-Bit Server VM Oracle GraalVM 22+36.1 (build 22+36-jvmci-b02, mixed mode, sharing)``

Once you have GraalVM configured as your default JDK, compile your app:

```shell
mvn clean package
```

You will need to run your app with the graalvm agent to pick up all the reflection/dynamic calls your app makes.

Run this command in your target directory
```shell
java -agentlib:native-image-agent=config-output-dir=config -jar SwingApp-X.X-SNAPSHOT-shaded.jar
```
This will load your app. Click and navigate all around the app, this is important so that the agent picks up all the reflection/dynamic JNI calls.

Once that is done, when you exit the app, the agent will have created a `config` folder on the `target` folder.

For native compiling, you will need a C development setup for your platform, for Linux that means GCC and friends, on Mac you can also use GCC or XCode. On Windows you will need to install the Visual Studio Compiler and CLI Tools.

Now we can create the native executable. Go to the `target` folder and run the following command:

```shell
native-image --no-fallback -H:ConfigurationFileDirectories=config -J-Xmx16G -jar .\SwingApp-X.X-SNAPSHOT-shaded.jar
```
Compiling a native executable takes a lot om memory, so try to give it as much as you can, the default is 16GB `-J-Xmx16GB`

You should see someting similar to this:

```shell
========================================================================================================================
GraalVM Native Image: Generating 'SwingApp-1.0-SNAPSHOT-shaded' (executable)...
========================================================================================================================
For detailed information and explanations on the build output, visit:
https://github.com/oracle/graal/blob/master/docs/reference-manual/native-image/BuildOutput.md
------------------------------------------------------------------------------------------------------------------------
[1/8] Initializing...                                                                                    (3.9s @ 0.23GB)
 Java version: 22+36, vendor version: Oracle GraalVM 22+36.1
 Graal compiler: optimization level: 2, target machine: x86-64-v3, PGO: ML-inferred
 C compiler: cl.exe (microsoft, x64, 19.39.33523)
 Garbage collector: Serial GC (max heap size: 80% of RAM)
 1 user-specific feature(s):
 - com.oracle.svm.thirdparty.gson.GsonFeature
------------------------------------------------------------------------------------------------------------------------
Build resources:
 - 14.22GB of memory (11.1% of 127.77GB system memory, set via '-Xmx16G')
 - 24 thread(s) (100.0% of 24 available processor(s), determined at start)
[2/8] Performing analysis...  [***]                                                                      (8.4s @ 0.78GB)
    9,368 reachable types   (84.8% of   11,047 total)
   18,049 reachable fields  (58.8% of   30,670 total)
   55,879 reachable methods (61.9% of   90,240 total)
    2,955 types,   152 fields, and 1,463 methods registered for reflection
      197 types,   271 fields, and   177 methods registered for JNI access
        3 native libraries: crypt32, ncrypt, version
[3/8] Building universe...                                                                               (1.7s @ 1.05GB)
[4/8] Parsing methods...      [**]                                                                       (2.7s @ 1.18GB)
[5/8] Inlining methods...     [****]                                                                     (0.4s @ 1.21GB)
[6/8] Compiling methods...    [*****]                                                                   (21.3s @ 1.28GB)
[7/8] Laying out methods...   [**]                                                                       (2.9s @ 1.52GB)
[8/8] Creating image...       [**]                                                                       (2.2s @ 1.11GB)
  33.99MB (61.58%) for code area:    31,471 compilation units
  20.77MB (37.62%) for image heap:  256,199 objects and 87 resources
 447.77kB ( 0.79%) for other data
  55.19MB in total
------------------------------------------------------------------------------------------------------------------------
Top 10 origins of code area:                                Top 10 object types in image heap:
  14.65MB java.desktop                                         8.72MB byte[] for code metadata
   8.60MB java.base                                            3.39MB byte[] for java.lang.String
   3.76MB svm.jar (Native Image)                               1.99MB java.lang.String
   3.63MB java.xml                                             1.89MB java.lang.Class
   2.30MB SwingApp-1.0-SNAPSHOT-shaded.jar                   553.17kB byte[] for reflection metadata
 191.94kB com.oracle.svm.svm_enterprise                      478.55kB byte[] for general heap data
 153.93kB java.logging                                       439.13kB com.oracle.svm.core.hub.DynamicHubCompanion
 130.51kB java.datatransfer                                  294.19kB java.util.HashMap$Node
 100.58kB jdk.accessibility                                  280.59kB java.lang.String[]
  80.14kB java.prefs                                         271.50kB c.o.svm.core.hub.DynamicHub$ReflectionMetadata
 181.75kB for 11 more packages                                 2.52MB for 1643 more object types
                              Use '-H:+BuildReport' to create a report with more details.
------------------------------------------------------------------------------------------------------------------------
Security report:
 - Binary includes Java deserialization.
 - Use '--enable-sbom' to embed a Software Bill of Materials (SBOM) in the binary.
------------------------------------------------------------------------------------------------------------------------
Recommendations:
 PGO:  Use Profile-Guided Optimizations ('--pgo') for improved throughput.
 HEAP: Set max heap for improved and more predictable memory usage.
 CPU:  Enable more CPU features with '-march=native' for improved performance.
 QBM:  Use the quick build mode ('-Ob') to speed up builds during development.
------------------------------------------------------------------------------------------------------------------------
                       3.9s (8.6% of total time) in 621 GCs | Peak RSS: 2.65GB | CPU load: 11.27
------------------------------------------------------------------------------------------------------------------------
Produced artifacts:
 S:\src\java\swing\BasicSwingApp\target\awt.dll (jdk_library)
 S:\src\java\swing\BasicSwingApp\target\fontmanager.dll (jdk_library)
 S:\src\java\swing\BasicSwingApp\target\freetype.dll (jdk_library)
 S:\src\java\swing\BasicSwingApp\target\java.dll (jdk_library_shim)
 S:\src\java\swing\BasicSwingApp\target\javaaccessbridge.dll (jdk_library)
 S:\src\java\swing\BasicSwingApp\target\javajpeg.dll (jdk_library)
 S:\src\java\swing\BasicSwingApp\target\jawt.dll (jdk_library)
 S:\src\java\swing\BasicSwingApp\target\jsound.dll (jdk_library)
 S:\src\java\swing\BasicSwingApp\target\jvm.dll (jdk_library_shim)
 S:\src\java\swing\BasicSwingApp\target\lcms.dll (jdk_library)
 S:\src\java\swing\BasicSwingApp\target\SwingApp-1.0-SNAPSHOT-shaded.exe (executable)
========================================================================================================================
Finished generating 'SwingApp-1.0-SNAPSHOT-shaded' in 44.8s.
```
Once finished, go to the `target` folder and look for the SwingApp-X.X-SNAPSHOT-shaded executable. 

If you are getting the following error: `Exception in thread "main" java.lang.Error: java.home property not set`, this is a current bug in GraalVM. The workaround is to include the following parameter while running your executable:
```shell
SwingApp-X.X-SNAPSHOT-shaded.exe -Djava.home="%JAVA_HOME%"
```

