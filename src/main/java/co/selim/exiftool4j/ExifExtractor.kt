package co.selim.exiftool4j

import java.io.InputStream

fun InputStream.extractExifData(): ExifData {
    val exiftoolProcess = ProcessBuilder("exiftool", "-")
        .redirectError(ProcessBuilder.Redirect.INHERIT)
        .start()

    exiftoolProcess.outputStream.use {
        this.copyTo(it)
    }

    val fields = exiftoolProcess.inputStream
        .bufferedReader()
        .useLines(Sequence<String>::toExifMap)

    return ExifData(fields)
}

private fun Sequence<String>.toExifMap(): Map<ExifKey, String> = associateNotNull { line ->
    val key = line.substringBefore(":").trim()
    val value = line.substringAfter(":").trim()
    val exifKey = getExifKey(key)

    if (exifKey != null && exifKey.exclude.none { value.contains(it, true) }) {
        exifKey to value
    } else {
        null
    }
}

private fun <T, K, V> Sequence<T>.associateNotNull(transform: (T) -> Pair<K, V>?): Map<K, V> {
    return mapNotNull(transform).toMap()
}


private fun getExifKey(key: String): ExifKey? = ExifKey.values()
    .firstOrNull { it.names.any { name -> name.equals(key, true) } }
