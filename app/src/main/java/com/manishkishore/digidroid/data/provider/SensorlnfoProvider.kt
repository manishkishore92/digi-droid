package com.manishkishore.digidroid.data.provider

import android.content.Context
import android.hardware.Sensor
import android.hardware.SensorManager
import android.hardware.fingerprint.FingerprintManager
import android.os.Build
import com.manishkishore.digidroid.model.InfoItem
import com.manishkishore.digidroid.model.InfoSection
import com.manishkishore.digidroid.model.StatusLevel

object SensorInfoProvider {
    fun section(context: Context): InfoSection {
        val sensorManager = context.getSystemService(Context.SENSOR_SERVICE) as SensorManager
        val sensors = sensorManager.getSensorList(Sensor.TYPE_ALL)
        val keySensors = listOf(
            "Accelerometer" to Sensor.TYPE_ACCELEROMETER,
            "Gyroscope" to Sensor.TYPE_GYROSCOPE,
            "Light" to Sensor.TYPE_LIGHT,
            "Proximity" to Sensor.TYPE_PROXIMITY,
            "Magnetometer" to Sensor.TYPE_MAGNETIC_FIELD,
            "Barometer" to Sensor.TYPE_PRESSURE,
            "Step counter" to Sensor.TYPE_STEP_COUNTER
        )

        val basic = keySensors.map { (name, type) ->
            val available = sensorManager.getDefaultSensor(type) != null
            InfoItem(name, if (available) "Available" else "Missing", status = if (available) StatusLevel.GOOD else StatusLevel.WARNING)
        }

        val fingerprint = runCatching {
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                val manager = context.getSystemService(Context.FINGERPRINT_SERVICE) as? FingerprintManager
                if (manager?.isHardwareDetected == true) "Available" else "Unavailable"
            } else {
                "Unsupported API"
            }
        }.getOrDefault("Restricted")

        return InfoSection(
            title = "Sensors",
            items = listOf(InfoItem("Total sensors", sensors.size.toString())) + basic + InfoItem("Fingerprint hardware", fingerprint)
        )
    }

    fun allSensors(context: Context): List<InfoItem> {
        val sensorManager = context.getSystemService(Context.SENSOR_SERVICE) as SensorManager
        return sensorManager.getSensorList(Sensor.TYPE_ALL).map { sensor ->
            InfoItem(
                title = sensor.name,
                value = sensor.vendor,
                description = "Type ${sensor.type} • Version ${sensor.version} • ${sensor.power} mA"
            )
        }
    }
}
