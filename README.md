# exiftool4j

Java / Kotlin wrapper for [exiftool](http://owl.phy.queensu.ca/~phil/exiftool/).

## Prerequisites
* `exiftool` in your PATH
* Java 8

## Usage
First, add exiftool4j to your dependencies using the [jitpack repository](https://jitpack.io/#wowselim/exiftool4j).

### Java
```java
ExifData exifData = ExifExtractor.extract(jpegInputStream);
String aperture = exifData.get(ExifKey.APERTURE);
```

### Kotlin
```kotlin
val exifData = jpegInputStream.extractExifData()
val aperture = exifData[ExifKey.APERTURE]
```
