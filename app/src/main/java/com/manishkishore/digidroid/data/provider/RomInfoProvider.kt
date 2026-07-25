package com.manishkishore.digidroid.data.provider

import android.os.Build
import com.manishkishore.digidroid.model.InfoItem
import com.manishkishore.digidroid.model.InfoSection

object RomInfoProvider {
    fun romName(): String {
        return SystemPropertyReader.firstAvailable(
            "ro.lineage.version",
            "ro.modversion",
            "ro.crdroid.version",
            "ro.evolution.version",
            "ro.rising.version",
            "ro.pixelos.version",
            "ro.build.display.id",
            fallback = "Android"
        )
    }

    fun section(): InfoSection {
        return InfoSection(
            title = "ROM & Build",
            items = listOf(
                InfoItem("ROM / Build name", romName()),
                InfoItem("Android release", Build.VERSION.RELEASE.orUnknown()),
                InfoItem("SDK", Build.VERSION.SDK_INT.toString()),
                InfoItem("Security patch", Build.VERSION.SECURITY_PATCH.orUnknown()),
                InfoItem("Build ID", Build.ID.orUnknown()),
                InfoItem("Build type", Build.TYPE.orUnknown()),
                InfoItem("Build tags", Build.TAGS.orUnknown()),
                InfoItem("Build user", Build.USER.orUnknown()),
                InfoItem("Build host", Build.HOST.orUnknown()),
                InfoItem("Build time", Build.TIME.toString()),
                InfoItem("Fingerprint", Build.FINGERPRINT.orUnknown()),
                InfoItem("Vendor patch", SystemPropertyReader.get("ro.vendor.build.security_patch")),
                InfoItem("System build date", SystemPropertyReader.get("ro.system.build.date")),
                InfoItem("Treble enabled", SystemPropertyReader.get("ro.treble.enabled")),
                InfoItem("VNDK version", SystemPropertyReader.get("ro.vndk.version"))
            )
        )
    }
}

private fun String?.orUnknown(): String = this?.takeIf { it.isNotBlank() } ?: "Unavailable"
