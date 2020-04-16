package co.selim.exiftool4j;

import java.io.InputStream;

public class ExifExtractor {
    private ExifExtractor() {
    }

    /**
     * Returns exif data that the image contains.
     * This method calls the command line utility 'exiftool' to extract exif data from an image.
     * The input stream will not be closed.
     *
     * @param imageInputStream input stream containing the image
     * @return exif data that the image contains
     */
    public static ExifData extractExifData(InputStream imageInputStream) {
        return ExifExtractorKt.extractExifData(imageInputStream);
    }
}
