package co.selim.exiftool4j;

import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.Scanner;

/**
 * A utility that can be used to extract exif data from a {@link File} or a {@link Path}.
 * It will create an {@link ExifDocument} ideally containing all values specified in {@link ExifKey}.
 */
public class ExifExtractor {
    private static final Logger LOGGER = LoggerFactory.getLogger(ExifExtractor.class);

    private static final List<String> EXIFTOOL_COMMANDS = List.of("exiftool", "-json");
    private final ProcessBuilder processBuilder;

    public ExifExtractor() {
        this.processBuilder = new ProcessBuilder();
    }

    /**
     * Creates an {@link ExifDocument} from a given path.
     *
     * @param path the path to extract exif data from
     * @return a new {@link ExifDocument} that contains the exif data of the file
     * @throws IOException if reading the file fails
     */
    public ExifDocument extractFromPath(Path path) throws IOException {
        return extractFromFile(path.toFile());
    }

    /**
     * Creates an {@link ExifDocument} from a given file.
     *
     * @param file the file to extract exif data from
     * @return a new {@link ExifDocument} that contains the exif data of the file
     * @throws IOException if reading the file fails
     */
    public ExifDocument extractFromFile(File file) throws IOException {
        Objects.requireNonNull(file, "Image file may not be null");

        final List<String> commandList = new ArrayList<>(EXIFTOOL_COMMANDS);
        commandList.add(file.getPath());
        try {
            Process process = processBuilder.command(commandList).start();
            try (InputStream processInputStream = process.getInputStream();
                 Scanner scanner = new Scanner(processInputStream)) {
                StringBuilder stringBuilder = new StringBuilder(8192);
                while (scanner.hasNextLine()) {
                    stringBuilder.append(scanner.nextLine());
                }

                return createFromString(stringBuilder.toString());
            }
        } catch (IOException e) {
            LOGGER.error("Failed to create exif document from {}", file.getPath());
            throw e;
        }
    }

    private ExifDocument createFromString(String json) {
        ExifDocument exifDocument = new ExifDocument();
        JsonArray jsonArray = new JsonParser().parse(json).getAsJsonArray();
        if (jsonArray.size() == 0) {
            LOGGER.warn("No exif data available");
            return exifDocument;
        }

        JsonObject exifObject = jsonArray.get(0).getAsJsonObject();
        for (ExifKey exifKey : ExifKey.values()) {
            String exifToolName = exifKey.getExifToolName();
            JsonElement exifValue = exifObject.get(exifToolName);
            if (exifValue != null) {
                exifDocument.putExifData(exifKey, exifValue.getAsString());
            }
        }

        return exifDocument;
    }
}
