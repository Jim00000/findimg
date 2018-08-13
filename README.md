[![Build Status](https://travis-ci.org/Jim00000/findimg.svg?branch=master)](https://travis-ci.org/Jim00000/findimg)
[![Build status](https://ci.appveyor.com/api/projects/status/qugsnaf2l66kk3mf/branch/master?svg=true)](https://ci.appveyor.com/project/Jim00000/findimg/branch/master)
[![codecov](https://codecov.io/gh/Jim00000/findimg/branch/master/graph/badge.svg)](https://codecov.io/gh/Jim00000/findimg)
[![License: MIT](https://img.shields.io/badge/License-MIT-yellow.svg)](https://opensource.org/licenses/MIT)

# findimg
A simple tool to find out image URLs inside web pages and download them all

# Dependency

Make sure that your machine contains required packages below

- Java 1.8+ (Oracle JDK 1.8 is recommended)
- maven

## On Ubuntu

```bash=
sudo add-apt-repository ppa:webupd8team/java
sudo apt update
sudo apt install oracle-java8-installer maven
```

# Build

## On Ubuntu

```bash=
git clone https://github.com/Jim00000/findimg.git
cd findimg
mvn package
```

The runnable JAR are located at /path/to/findimg/target/findimg-\<version\>-SNAPSHOT-jar-with-dependencies.jar

# Usage

```bash=
java -jar findimg-0.0.1-SNAPSHOT-jar-with-dependencies.jar -i <input-HTML-file> -o <output-image-folder> [-sn]
```

# Licensing

MIT License