# Basic Swing App

[![Java CI with Maven](https://github.com/adriandeleon/BasicSwingApp/actions/workflows/maven.yml/badge.svg)](https://github.com/adriandeleon/BasicSwingApp/actions/workflows/maven.yml)


## Build and run a Fat Jar:

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
 jpackage --input . --name SwingApp --main-jar .\SwingApp-1.0-SNAPSHOT-shaded.jar --main-class org.example.App --type exe --java-options '--enable-preview'
```
Where --type can be one of these options:

- `dmg`: Create an MacOS installer.
- `msi` or `exe`: Create a Windows installer. (You will need to install WIX v3 from [here](https://wixtoolset.org/docs/wix3/))
- `deb` or `rpm`: Create a deb or rpm Linux package.

After running `jpackage`, look for a SwingApp package in the target directory.

You don't need to install a JDK to run the package or the app after installing it. Everything is provided inside the installer/package.



