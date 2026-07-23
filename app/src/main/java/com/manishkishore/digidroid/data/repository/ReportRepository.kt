package com.manishkishore.digidroid.data.repository

import android.content.Context
import com.manishkishore.digidroid.model.DeviceReportInput
import com.manishkishore.digidroid.model.InfoSection
import com.manishkishore.digidroid.util.DateFormatter

class ReportRepository(context: Context) {
    private val deviceRepository = DeviceRepository(context.applicationContext)

    fun generate(input: DeviceReportInput): String {
        val sections = listOf(
            deviceRepository.deviceSection(),
            deviceRepository.romSection(),
            deviceRepository.kernelSection(),
            deviceRepository.batterySection(),
            deviceRepository.storageSection(),
            deviceRepository.networkSection(),
            deviceRepository.rootSection()
        )

        return buildString {
            appendLine("# Digi Droid Maintainer Report")
            appendLine()
            appendLine("Generated: ${DateFormatter.now()}")
            appendLine("Issue category: ${input.category}")
            appendLine()
            appendLine("## Issue Description")
            appendLine(input.description.ifBlank { "No description provided." })
            appendLine()
            appendLine("## Reproduction Steps")
            appendLine(input.steps.ifBlank { "No reproduction steps provided." })
            appendLine()
            if (input.extraNotes.isNotBlank()) {
                appendLine("## Extra Notes")
                appendLine(input.extraNotes)
                appendLine()
            }
            sections.forEach { section -> appendSection(section) }
        }
    }

    private fun StringBuilder.appendSection(section: InfoSection) {
        appendLine("## ${section.title}")
        section.items.forEach { item ->
            val value = item.value.replace("\n", " ").trim()
            appendLine("- ${item.title}: $value")
        }
        appendLine()
    }
}
