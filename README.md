# Basic Swing App

[![Java CI with Maven](https://github.com/adriandeleon/BasicSwingApp/actions/workflows/maven.yml/badge.svg)](https://github.com/adriandeleon/BasicSwingApp/actions/workflows/maven.yml)


## Build and run a Fat Jar:

To build a Fat Jar (with all the dependencies) run:

```shell
mvn clean package
```
Then look in the `target` folder for a file with the following name:

`SwingApp-X.X-SNAPSHOT-shaded.jar`

to run the app:
```shell
java -jar SwingApp-X.X-SNAPSHOT-shaded.jar
```

