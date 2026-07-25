package com.manishkishore.digidroid.data.provider

import android.content.Context
import android.content.pm.PackageManager
import android.os.Build
import com.manishkishore.digidroid.model.InfoItem
import com.manishkishore.digidroid.model.InfoSection
import com.manishkishore.digidroid.model.StatusLevel
import java.io.File

object RootStatusProvider {
    private val suPaths = listOf(
        "/system/bin/su",
        "/system/xbin/su",
        "/sbin/su",
        "/vendor/bin/su",
        "/su/bin/su",
        "/data/adb/magisk/busybox"
    )

    fun section(context: Context): InfoSection {
        val suFile = suPaths.firstOrNull { File(it).exists() }
        val whichSu = runCatching {
            val process = ProcessBuilder("which", "su").redirectErrorStream(true).start()
            val result = process.inputStream.bufferedReader().readText().trim()
            process.waitFor()
            result
        }.getOrDefault("")
        val rootDetected = suFile != null || whichSu.isNotBlank() || Build.TAGS?.contains("test-keys") == true

        return InfoSection(
            title = "Root & System Status",
            items = listOf(
                InfoItem("Root status", if (rootDetected) "Possible root detected" else "Not detected", status = if (rootDetected) StatusLevel.WARNING else StatusLevel.GOOD),
                InfoItem("su path", suFile ?: whichSu.ifBlank { "Not found" }),
                InfoItem("Build tags", Build.TAGS ?: "Unavailable"),
                InfoItem("Magisk app", appStatus(context, "com.topjohnwu.magisk")),
                InfoItem("KernelSU app", appStatus(context, "me.weishu.kernelsu")),
                InfoItem("APatch app", appStatus(context, "me.bmax.apatch")),
                InfoItem("Verified boot state", SystemPropertyReader.get("ro.boot.verifiedbootstate")),
                InfoItem("Flash lock state", SystemPropertyReader.get("ro.boot.flash.locked")),
                InfoItem("Bootloader unlocked", SystemPropertyReader.get("ro.boot.vbmeta.device_state"))
            )
        )
    }

    private fun appStatus(context: Context, packageName: String): String {
        return try {
            context.packageManager.getPackageInfo(packageName, 0)
            "Installed"
        } catch (_: PackageManager.NameNotFoundException) {
            "Not detected"
        } catch (_: Exception) {
            "Restricted"
        }
    }
}
