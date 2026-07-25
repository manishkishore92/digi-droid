package com.manishkishore.digidroid.data.provider

import android.os.Build
import com.manishkishore.digidroid.model.InfoItem
import com.manishkishore.digidroid.model.InfoSection

object DeviceInfoProvider {
    fun section(): InfoSection {
        return InfoSection(
            title = "Device",
            items = listOf(
                InfoItem("Manufacturer", Build.MANUFACTURER.orUnknown()),
                InfoItem("Brand", Build.BRAND.orUnknown()),
                InfoItem("Model", Build.MODEL.orUnknown()),
                InfoItem("Device codename", Build.DEVICE.orUnknown()),
                InfoItem("Product", Build.PRODUCT.orUnknown()),
                InfoItem("Board", Build.BOARD.orUnknown()),
                InfoItem("Hardware", Build.HARDWARE.orUnknown()),
                InfoItem("Bootloader", Build.BOOTLOADER.orUnknown()),
                InfoItem("Supported ABIs", Build.SUPPORTED_ABIS.joinToString(", ").ifBlank { "Unavailable" })
            )
        )
    }

    fun summaryItems(): List<InfoItem> = listOf(
        InfoItem("Device", "${Build.MANUFACTURER.orUnknown()} ${Build.MODEL.orUnknown()}"),
        InfoItem("Codename", Build.DEVICE.orUnknown()),
        InfoItem("Android", "${Build.VERSION.RELEASE} / API ${Build.VERSION.SDK_INT}"),
        InfoItem("Security patch", Build.VERSION.SECURITY_PATCH.orUnknown())
    )
}

private fun String?.orUnknown(): String = this?.takeIf { it.isNotBlank() } ?: "Unavailable"
