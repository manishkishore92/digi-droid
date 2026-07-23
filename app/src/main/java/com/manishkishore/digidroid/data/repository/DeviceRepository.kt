package com.manishkishore.digidroid.data.repository

import android.content.Context
import com.manishkishore.digidroid.data.provider.BatteryInfoProvider
import com.manishkishore.digidroid.data.provider.DeviceInfoProvider
import com.manishkishore.digidroid.data.provider.KernelInfoProvider
import com.manishkishore.digidroid.data.provider.NetworkInfoProvider
import com.manishkishore.digidroid.data.provider.RomInfoProvider
import com.manishkishore.digidroid.data.provider.RootStatusProvider
import com.manishkishore.digidroid.data.provider.SensorInfoProvider
import com.manishkishore.digidroid.data.provider.StorageInfoProvider
import com.manishkishore.digidroid.model.InfoSection

class DeviceRepository(private val context: Context) {
    fun dashboardSections(): List<InfoSection> = listOf(
        InfoSection("Quick Summary", DeviceInfoProvider.summaryItems()),
        RomInfoProvider.section(),
        RootStatusProvider.section(context),
        BatteryInfoProvider.section(context),
        StorageInfoProvider.section(context)
    )

    fun deviceSection(): InfoSection = DeviceInfoProvider.section()
    fun romSection(): InfoSection = RomInfoProvider.section()
    fun kernelSection(): InfoSection = KernelInfoProvider.section()
    fun batterySection(): InfoSection = BatteryInfoProvider.section(context)
    fun storageSection(): InfoSection = StorageInfoProvider.section(context)
    fun networkSection(): InfoSection = NetworkInfoProvider.section(context)
    fun sensorSection(): InfoSection = SensorInfoProvider.section(context)
    fun rootSection(): InfoSection = RootStatusProvider.section(context)
    fun allSensors() = SensorInfoProvider.allSensors(context)
}
