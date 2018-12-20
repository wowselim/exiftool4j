import co.selim.exiftool4j.ExifDocument;
import co.selim.exiftool4j.ExifExtractor;
import co.selim.exiftool4j.ExifKey;
import org.junit.Assert;
import org.junit.Test;

import java.io.File;
import java.io.IOException;
import java.util.Optional;

public class ExifExtractorTest {
    @Test
    public void testExtractDescription() {
        testExtractExifType(ExifKey.DESCRIPTION, "OLYMPUS DIGITAL CAMERA");
    }

    @Test
    public void testExtractDateTime() {
        testExtractExifType(ExifKey.DATE_TIME, "2018:05:28 14:33:09");
    }

    @Test
    public void testExtractDeviceMake() {
        testExtractExifType(ExifKey.DEVICE_MAKE, "OLYMPUS IMAGING CORP.");
    }

    @Test
    public void testExtractDeviceModel() {
        testExtractExifType(ExifKey.DEVICE_MODEL, "E-PL7");
    }

    @Test
    public void testExtractLens() {
        testExtractExifType(ExifKey.LENS, "OLYMPUS M.12-40mm F2.8");
    }

    @Test
    public void testExtractAperture() {
        testExtractExifType(ExifKey.APERTURE, "2.8");
    }

    @Test
    public void testExtractFocalLength() {
        testExtractExifType(ExifKey.FOCAL_LENGTH, "40.0 mm");
    }

    @Test
    public void testExtractShutterSpeed() {
        testExtractExifType(ExifKey.SHUTTER_SPEED, "1/1000");
    }

    @Test
    public void testExtractIso() {
        testExtractExifType(ExifKey.ISO, "200");
    }

    @Test
    public void testExtractFlashStatus() {
        testExtractExifType(ExifKey.FLASH_STATUS, "Auto, Did not fire, Red-eye reduction");
    }

    @Test
    public void testExtractWidth() {
        testExtractExifType(ExifKey.WIDTH, "4608");
    }

    @Test
    public void testExtractHeight() {
        testExtractExifType(ExifKey.HEIGHT, "3456");
    }

    private void testExtractExifType(ExifKey exifKey, String expected) {
        ExifExtractor exifExtractor = new ExifExtractor();
        try {
            ExifDocument exifDocument = exifExtractor.extractFromFile(getFile());
            Optional<String> value = exifDocument.getExifData(exifKey);
            Assert.assertTrue("Failed to extract " + exifKey, value.isPresent());
            Assert.assertEquals(expected, value.get());
        } catch (IOException e) {
            Assert.fail("Unexpected exception");
            e.printStackTrace();
        }
    }

    private static File getFile() {
        return new File(ExifExtractorTest.class.getResource("birdie.jpg").getFile());
    }
}
