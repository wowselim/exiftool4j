import co.selim.exiftool4j.ExifData;
import co.selim.exiftool4j.ExifExtractor;
import co.selim.exiftool4j.ExifKey;
import org.junit.Assert;
import org.junit.Test;

import java.io.InputStream;

public class ExifExtractorTest {
    @Test
    public void testExtractDateTime() {
        testExtractExifType(ExifKey.DATE_TIME, "2018:05:28 14:33:09");
    }

    @Test
    public void testExtractDeviceMake() {
        testExtractExifType(ExifKey.MAKE, "OLYMPUS IMAGING CORP.");
    }

    @Test
    public void testExtractDeviceModel() {
        testExtractExifType(ExifKey.MODEL, "E-PL7");
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

    private void testExtractExifType(ExifKey exifKey, String expected) {
        ExifData exifData = ExifExtractor.extract(getInputStream());
        String value = exifData.get(exifKey);
        Assert.assertNotNull("Failed to extract " + exifKey, value);
        Assert.assertEquals(expected, value);
    }

    private static InputStream getInputStream() {
        return ExifExtractorTest.class.getClassLoader().getResourceAsStream("birdie.jpg");
    }
}
