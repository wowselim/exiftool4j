package co.selim.exiftool4j

import java.util.*

data class ExifData internal constructor(
        private val fields: Map<ExifKey, String>
) {
    operator fun get(key: ExifKey): String? = fields[key]

    fun getOptional(key: ExifKey): Optional<String> = Optional.ofNullable(fields[key])

    fun getAll(): MutableMap<ExifKey, String> = fields.toMutableMap()

    /**
     * Returns a (mutable) copy of the internal map. The original values will remain unchanged.
     * @return a copy of the internal map
     */
    val data: MutableMap<ExifKey, String>
        get() = fields.toMutableMap()
}
