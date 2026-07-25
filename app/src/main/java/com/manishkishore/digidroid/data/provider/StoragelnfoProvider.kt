package com.manishkishore.digidroid.data.provider

import android.app.ActivityManager
import android.content.Context
import android.os.Environment
import android.os.StatFs
import com.manishkishore.digidroid.model.InfoItem
import com.manishkishore.digidroid.model.InfoSection
import com.manishkishore.digidroid.model.StatusLevel
import com.manishkishore.digidroid.util.FileSizeFormatter

object StorageInfoProvider {
    fun section(context: Context): InfoSection {
        val dataStats = StatFs(Environment.getDataDirectory().absolutePath)
        val totalStorage = dataStats.totalBytes
        val freeStorage = dataStats.availableBytes
        val usedStorage = totalStorage - freeStorage

        val activityManager = context.getSystemService(Context.ACTIVITY_SERVICE) as ActivityManager
        val memoryInfo = ActivityManager.MemoryInfo()
        activityManager.getMemoryInfo(memoryInfo)

        return InfoSection(
            title = "Storage & Memory",
            items = listOf(
                InfoItem("Internal storage total", FileSizeFormatter.format(totalStorage)),
                InfoItem("Internal storage used", FileSizeFormatter.format(usedStorage)),
                InfoItem("Internal storage free", FileSizeFormatter.format(freeStorage), status = storageStatus(freeStorage, totalStorage)),
                InfoItem("RAM total", FileSizeFormatter.format(memoryInfo.totalMem)),
                InfoItem("RAM available", FileSizeFormatter.format(memoryInfo.availMem)),
                InfoItem("Low memory state", if (memoryInfo.lowMemory) "Yes" else "No", status = if (memoryInfo.lowMemory) StatusLevel.WARNING else StatusLevel.GOOD)
            )
        )
    }

    private fun storageStatus(free: Long, total: Long): StatusLevel {
        if (total <= 0) return StatusLevel.NEUTRAL
        val ratio = free.toDouble() / total.toDouble()
        return when {
            ratio < 0.08 -> StatusLevel.BAD
            ratio < 0.15 -> StatusLevel.WARNING
            else -> StatusLevel.GOOD
        }
    }
}
