package com.manishkishore.digidroid.data.provider

object LogProvider {
    fun recentLogcat(lines: Int = 300): String {
        return runCommand(listOf("logcat", "-d", "-t", lines.toString()), "Unable to read logcat on this device.")
    }

    fun lastRebootReason(): String {
        val propertyReason = SystemPropertyReader.firstAvailable(
            "sys.boot.reason",
            "ro.boot.bootreason",
            "persist.sys.boot.reason",
            fallback = ""
        )
        if (propertyReason.isNotBlank()) return propertyReason

        val files = listOf(
            "/proc/sys/kernel/boot_reason",
            "/sys/fs/pstore/console-ramoops-0",
            "/sys/fs/pstore/console-ramoops"
        )
        return files.asSequence()
            .map { SystemPropertyReader.readFile(it, "") }
            .firstOrNull { it.isNotBlank() }
            ?: "Unavailable"
    }

    fun dmesg(): String {
        return runCommand(listOf("dmesg"), "dmesg is unavailable or requires root access.")
    }

    private fun runCommand(command: List<String>, fallback: String): String {
        return try {
            val process = ProcessBuilder(command)
                .redirectErrorStream(true)
                .start()
            val output = process.inputStream.bufferedReader().readText().trim()
            process.waitFor()
            output.ifBlank { fallback }
        } catch (_: Exception) {
            fallback
        }
    }
}
