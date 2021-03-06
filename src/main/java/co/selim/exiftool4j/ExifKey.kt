package co.selim.exiftool4j

enum class ExifKey(internal val names: List<String>, internal val exclude: List<String> = emptyList()) {
    DATE_TIME(listOf("Date/Time Original", "Create Date", "Digital Creation Date/Time")),
    MAKE(listOf("Make")),
    MODEL(listOf("Model", "Camera Model Name")),
    LENS(listOf("Lens", "Lens Model", "Lens Type", "Lens ID")),
    APERTURE(listOf("Aperture")),
    SHUTTER_SPEED(listOf("Shutter Speed", "Exposure Time")),
    ISO(listOf("ISO", "Program ISO")),
    FLASH_STATUS(listOf("Flash")),

    // exclude equivalent focal lengths
    FOCAL_LENGTH(listOf("Focal Length"), exclude = listOf("equivalent"))
}