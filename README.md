# exiftool4j

Java wrapper for [exiftool](http://owl.phy.queensu.ca/~phil/exiftool/).

## Prerequisites
* `exiftool` in your PATH
* Java 8

## Usage
First, add exiftool4j to your dependencies using the [jitpack repository](https://jitpack.io/#wowselim/exiftool4j).

### Example for retrieving aperture
```java
ExifExtractor exifExtractor = new ExifExtractor();
ExifDocument exifDocument = exifExtractor.extractFromFile(jpegImageFile).get();
Optional<String> aperture = exifDocument.getExifData(ExifKey.APERTURE);
```
