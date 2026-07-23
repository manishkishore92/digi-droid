package com.manishkishore.digidroid.model

enum class StatusLevel {
    GOOD,
    WARNING,
    BAD,
    NEUTRAL
}

data class InfoItem(
    val title: String,
    val value: String,
    val description: String? = null,
    val status: StatusLevel = StatusLevel.NEUTRAL
)

data class InfoSection(
    val title: String,
    val items: List<InfoItem>
)

data class DeviceReportInput(
    val category: String,
    val description: String,
    val steps: String,
    val extraNotes: String
)
