# exiftool4j

Java wrapper for exiftool. Requires exiftool to be in your path.

## Usage

First, add exiftool4j to your dependencies using the [jitpack repository](https://jitpack.io/#wowselim/exiftool4j).

### Example for retrieving aperture
```java
ExifExtractor exifExtractor = new ExifExtractor();
ExifDocument exifDocument = exifExtractor.extractFromFile(jpegImageFile);
Optional<String> aperture = exifDocument.getExifData(ExifKey.APERTURE);
```
