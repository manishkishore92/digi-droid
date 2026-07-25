package com.manishkishore.digidroid.data.provider

import android.os.Build
import android.os.SELinux
import android.system.Os
import com.manishkishore.digidroid.model.InfoItem
import com.manishkishore.digidroid.model.InfoSection
import com.manishkishore.digidroid.model.StatusLevel
import java.util.Locale

object KernelInfoProvider {
    fun kernelVersion(): String = SystemPropertyReader.readFile("/proc/version")

    fun section(): InfoSection {
        val selinuxEnabled = runCatching { SELinux.isSELinuxEnabled() }.getOrDefault(false)
        val selinuxEnforced = runCatching { SELinux.isSELinuxEnforced() }.getOrDefault(false)
        val uptime = runCatching {
            val seconds = SystemPropertyReader.readFile("/proc/uptime", "0").split(" ").first().toDouble().toLong()
            formatUptime(seconds)
        }.getOrDefault("Unavailable")

        return InfoSection(
            title = "Kernel",
            items = listOf(
                InfoItem("Kernel version", kernelVersion()),
                InfoItem("Architecture", Os.uname().machine ?: Build.SUPPORTED_ABIS.firstOrNull().orEmpty()),
                InfoItem("Kernel release", Os.uname().release ?: "Unavailable"),
                InfoItem("Kernel command line", SystemPropertyReader.readFile("/proc/cmdline")),
                InfoItem("Uptime", uptime),
                InfoItem(
                    "SELinux",
                    when {
                        !selinuxEnabled -> "Disabled"
                        selinuxEnforced -> "Enforcing"
                        else -> "Permissive"
                    },
                    status = if (selinuxEnabled && selinuxEnforced) StatusLevel.GOOD else StatusLevel.WARNING
                ),
                InfoItem("CPU ABI", Build.CPU_ABI.orUnknown()),
                InfoItem("CPU ABI 2", Build.CPU_ABI2.orUnknown())
            )
        )
    }

    private fun formatUptime(totalSeconds: Long): String {
        val days = totalSeconds / 86400
        val hours = (totalSeconds % 86400) / 3600
        val minutes = (totalSeconds % 3600) / 60
        return String.format(Locale.US, "%dd %dh %dm", days, hours, minutes)
    }
}

private fun String?.orUnknown(): String = this?.takeIf { it.isNotBlank() } ?: "Unavailable"
