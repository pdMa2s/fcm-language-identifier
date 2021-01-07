# fcm-language-identifier

University of Aveiro

Departamento de Electrónica, Telecomunicações e Informática

MEI

This work was developed for course of **Algorithmic Information Theory** (2017/2018).

The program creates several finite-context models, one for each corpora of a certain language. Then, by providing a file with text it is able to identify its dialect .

### Install dependencies:

If you are not already using jdk-8u151 you can extract it from jdk-8u151-linux-x64.tar.gz and set it as the default java version of the system. Example for ubuntu:
```bash
$ sudo update-java-alternatives --set /path/to/java/version
```

### How to run the programs:

```bash
$ mkdir classes

$ javac -d "classes" src/.java src//*.java

$ cd classes

$ java language
```

Observe the USAGE print for more instructions in how to run and see the recommended example below. To test the application you can use the files on the folder testmodels.

Recommended: Use the -Xmx4g, -Xmx5g or -Xmx750m to control the amount of heap space that the application will use.

```bash

$ java -Xmx4g language ../testModels/LAT 3 0.0001
```
