package co.selim.exiftool4j;

import java.util.Arrays;

/**
 * An enum of the exif key value pairs that will be extracted.
 */
public enum ExifKey {
    DESCRIPTION("ImageDescription"),
    DATE_TIME("DateTimeOriginal"),
    DEVICE_MAKE("Make"),
    DEVICE_MODEL("Model"),
    LENS("LensModel"),
    APERTURE("Aperture"),
    FOCAL_LENGTH("FocalLength"),
    SHUTTER_SPEED("ShutterSpeed"),
    ISO("ISO"),
    FLASH_STATUS("Flash"),
    WIDTH("ImageWidth"),
    HEIGHT("ImageHeight");

    private String exifToolName;

    ExifKey(String exifToolName) {
        this.exifToolName = exifToolName;
    }

    ExifKey fromExifToolString(String exifToolName) {
        return Arrays.stream(values())
                .filter(exifKey -> exifToolName.equals(exifKey.exifToolName))
                .findFirst()
                .orElseThrow(() -> new IllegalStateException("Exif key not found for name " + exifToolName));
    }

    String getExifToolName() {
        return exifToolName;
    }
}