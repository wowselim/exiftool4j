package co.selim.exiftool4j;

import java.util.EnumMap;
import java.util.Map;
import java.util.Optional;

public class ExifDocument {
    private Map<ExifKey, String> exifMap = new EnumMap<>(ExifKey.class);

    void putExifData(ExifKey exifKey, String value) {
        exifMap.put(exifKey, value.trim());
    }

    public Optional<String> getExifData(ExifKey exifKey) {
        return Optional.ofNullable(exifMap.get(exifKey));
    }

    @Override
    public String toString() {
        return "ExifDocument{" +
                "exifMap=" + exifMap +
                '}';
    }
}
