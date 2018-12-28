package co.selim.exiftool4j;

import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;

import java.io.IOException;
import java.io.InputStream;
import java.net.URI;
import java.net.URL;
import java.nio.file.Paths;
import java.util.*;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.CompletionException;

/**
 * A utility that can be used to extract exif data from a {@link URL} that points to a local file.
 * It will create an {@link ExifDocument} ideally containing all values specified in {@link ExifKey}.
 */
public class ExifExtractor {
    private static final List<String> EXIFTOOL_COMMANDS = Arrays.asList("exiftool", "-json");
    private ProcessBuilder processBuilder = new ProcessBuilder();

    /**
     * Creates an {@link ExifDocument} from a given {@link URI}.
     *
     * @param uri the {@link URI} pointing to the local file
     * @return a future that, on completion, returns an {@link ExifDocument}
     */
    public CompletableFuture<ExifDocument> extractFromFile(URI uri) {
        return extractFromFile(uri, ForkJoinPool.commonPool());
    }

    /**
     * Creates an {@link ExifDocument} from a given {@link URI}.
     *
     * @param uri      the {@link URI} pointing to the local file
     * @param executor the executor to use for async execution
     * @return a future that, on completion, returns an {@link ExifDocument}
     */
    public CompletableFuture<ExifDocument> extractFromFile(URI uri, Executor executor) {
        Objects.requireNonNull(uri, "URI may not be null");
        Objects.requireNonNull(executor, "Executor may not be null");
        return CompletableFuture.supplyAsync(() -> getExifDocument(uri), executor);
    }

    private ExifDocument getExifDocument(URI uri) {
        final List<String> commandList = new ArrayList<>(EXIFTOOL_COMMANDS);
        commandList.add(Paths.get(uri).toString());

        try {
            Process process = processBuilder.command(commandList)
                    .redirectError(ProcessBuilder.Redirect.INHERIT)
                    .start();

            try (InputStream processInputStream = process.getInputStream();
                 Scanner scanner = new Scanner(processInputStream)) {
                StringBuilder stringBuilder = new StringBuilder(8192);
                while (scanner.hasNextLine()) {
                    stringBuilder.append(scanner.nextLine());
                }

                return createFromString(stringBuilder.toString());
            }
        } catch (Exception e) {
            throw new CompletionException(e);
        }
    }

    private ExifDocument createFromString(String json) {
        ExifDocument exifDocument = new ExifDocument();
        JsonArray jsonArray = new JsonParser().parse(json).getAsJsonArray();
        if (jsonArray.size() == 0) {
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
