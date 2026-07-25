package com.manishkishore.digidroid.data.provider

import java.io.File

object SystemPropertyReader {
    fun get(name: String, fallback: String = "Unavailable"): String {
        return try {
            val process = ProcessBuilder("getprop", name)
                .redirectErrorStream(true)
                .start()
            val output = process.inputStream.bufferedReader().readText().trim()
            process.waitFor()
            output.ifBlank { fallback }
        } catch (_: Exception) {
            fallback
        }
    }

    fun firstAvailable(vararg names: String, fallback: String = "Unavailable"): String {
        for (name in names) {
            val value = get(name, "")
            if (value.isNotBlank()) return value
        }
        return fallback
    }

    fun all(): List<Pair<String, String>> {
        return try {
            val process = ProcessBuilder("getprop")
                .redirectErrorStream(true)
                .start()
            val output = process.inputStream.bufferedReader().readText()
            process.waitFor()
            output.lineSequence()
                .mapNotNull { line ->
                    val match = Regex("\\[(.+)]\\: \\[(.*)]").find(line)
                    match?.let { it.groupValues[1] to it.groupValues[2] }
                }
                .sortedBy { it.first }
                .toList()
        } catch (_: Exception) {
            emptyList()
        }
    }

    fun readFile(path: String, fallback: String = "Unavailable"): String {
        return try {
            File(path).takeIf { it.exists() && it.canRead() }
                ?.readText()
                ?.trim()
                ?.ifBlank { fallback }
                ?: fallback
        } catch (_: Exception) {
            fallback
        }
    }
}
