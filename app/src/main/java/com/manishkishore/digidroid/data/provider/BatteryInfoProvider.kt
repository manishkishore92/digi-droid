package com.manishkishore.digidroid.data.provider

import android.content.Context
import android.content.Intent
import android.content.IntentFilter
import android.os.BatteryManager
import com.manishkishore.digidroid.model.InfoItem
import com.manishkishore.digidroid.model.InfoSection
import com.manishkishore.digidroid.model.StatusLevel
import java.util.Locale

object BatteryInfoProvider {
    fun section(context: Context): InfoSection {
        val battery = context.registerReceiver(null, IntentFilter(Intent.ACTION_BATTERY_CHANGED))
        val level = battery?.getIntExtra(BatteryManager.EXTRA_LEVEL, -1) ?: -1
        val scale = battery?.getIntExtra(BatteryManager.EXTRA_SCALE, -1) ?: -1
        val percent = if (level >= 0 && scale > 0) (level * 100 / scale) else -1
        val tempRaw = battery?.getIntExtra(BatteryManager.EXTRA_TEMPERATURE, Int.MIN_VALUE) ?: Int.MIN_VALUE
        val temperature = if (tempRaw != Int.MIN_VALUE) String.format(Locale.US, "%.1f °C", tempRaw / 10f) else "Unavailable"
        val voltage = battery?.getIntExtra(BatteryManager.EXTRA_VOLTAGE, -1) ?: -1

        return InfoSection(
            title = "Battery",
            items = listOf(
                InfoItem("Level", if (percent >= 0) "$percent%" else "Unavailable"),
                InfoItem("Status", statusName(battery?.getIntExtra(BatteryManager.EXTRA_STATUS, -1) ?: -1)),
                InfoItem("Health", healthName(battery?.getIntExtra(BatteryManager.EXTRA_HEALTH, -1) ?: -1)),
                InfoItem("Charging source", pluggedName(battery?.getIntExtra(BatteryManager.EXTRA_PLUGGED, -1) ?: -1)),
                InfoItem("Temperature", temperature, status = batteryTempStatus(tempRaw)),
                InfoItem("Voltage", if (voltage > 0) "$voltage mV" else "Unavailable"),
                InfoItem("Technology", battery?.getStringExtra(BatteryManager.EXTRA_TECHNOLOGY) ?: "Unavailable")
            )
        )
    }

    private fun statusName(status: Int): String = when (status) {
        BatteryManager.BATTERY_STATUS_CHARGING -> "Charging"
        BatteryManager.BATTERY_STATUS_DISCHARGING -> "Discharging"
        BatteryManager.BATTERY_STATUS_FULL -> "Full"
        BatteryManager.BATTERY_STATUS_NOT_CHARGING -> "Not charging"
        else -> "Unknown"
    }

    private fun healthName(health: Int): String = when (health) {
        BatteryManager.BATTERY_HEALTH_GOOD -> "Good"
        BatteryManager.BATTERY_HEALTH_OVERHEAT -> "Overheat"
        BatteryManager.BATTERY_HEALTH_DEAD -> "Dead"
        BatteryManager.BATTERY_HEALTH_OVER_VOLTAGE -> "Over voltage"
        BatteryManager.BATTERY_HEALTH_UNSPECIFIED_FAILURE -> "Unspecified failure"
        BatteryManager.BATTERY_HEALTH_COLD -> "Cold"
        else -> "Unknown"
    }

    private fun pluggedName(plugged: Int): String = when (plugged) {
        BatteryManager.BATTERY_PLUGGED_AC -> "AC charger"
        BatteryManager.BATTERY_PLUGGED_USB -> "USB"
        BatteryManager.BATTERY_PLUGGED_WIRELESS -> "Wireless"
        BatteryManager.BATTERY_PLUGGED_DOCK -> "Dock"
        0 -> "Not plugged"
        else -> "Unknown"
    }

    private fun batteryTempStatus(raw: Int): StatusLevel {
        if (raw == Int.MIN_VALUE) return StatusLevel.NEUTRAL
        val c = raw / 10f
        return when {
            c >= 45f -> StatusLevel.BAD
            c >= 40f -> StatusLevel.WARNING
            else -> StatusLevel.GOOD
        }
    }
}
