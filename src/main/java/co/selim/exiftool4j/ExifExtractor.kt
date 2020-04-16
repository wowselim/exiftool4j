package co.selim.exiftool4j

import java.io.InputStream

private val command = listOf("exiftool", "-")

fun InputStream.extractExifData(): ExifData {
    val exiftoolProcess = ProcessBuilder(command)
            .redirectError(ProcessBuilder.Redirect.INHERIT)
            .start()

    exiftoolProcess.outputStream.use {
        this.copyTo(it)
    }

    val output = exiftoolProcess.inputStream.reader().use {
        it.readText()
    }

    val fields = extractKeyValuePairs(output)
            .mapNotNull { (key, value) ->
                getExifKey(key)?.let { exifKey ->
                    if (exifKey.blacklist.none { value.contains(it, true) })
                        exifKey to value
                    else
                        null
                }
            }.toMap()

    return ExifData(fields)
}

private fun extractKeyValuePairs(input: String): Sequence<Pair<String, String>> = input.lineSequence()
        .map {
            listOf(
                    it.substringBefore(":").trim(),
                    it.substringAfter(":").trim()
            )
        }
        .map { it[0] to it[1] }

private fun getExifKey(key: String): ExifKey? = ExifKey.values()
        .firstOrNull { it.names.contains(key) }
