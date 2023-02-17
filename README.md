# DSLBuilder
DSLBuilder is zero dependency Java library for creating domain specific languages.

You can easily create your own domain specific language and supply your own runtime using this library.

## How To Add As Library
Add it as a maven dependency or just [download the latest release](https://github.com/Konloch/DSLBuilder/releases).
```xml
<dependency>
  <groupId>com.konloch</groupId>
  <artifactId>DSLBuilder</artifactId>
  <version>1.0.0</version>
</dependency>
```

## How To Use
Defining and using the DSL is split up into 3 sections, each part is available as a full file over at [the test section of the repo](https://github.com/Konloch/DSLBuilder/). This includes a guide to help explain how you can write your own DSL using DSLBuilder.

## Disclaimer
This offers **very** limited delimiter customization and **does not** have support for escaping reserved characters in strings.
Because of those reasons and more, this Java library is currently in an Experimental state.