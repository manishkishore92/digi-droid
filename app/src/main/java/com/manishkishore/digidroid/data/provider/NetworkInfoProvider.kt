package com.manishkishore.digidroid.data.provider

import android.bluetooth.BluetoothAdapter
import android.content.Context
import android.net.ConnectivityManager
import android.net.NetworkCapabilities
import android.net.wifi.WifiManager
import android.provider.Settings
import android.telephony.TelephonyManager
import com.manishkishore.digidroid.model.InfoItem
import com.manishkishore.digidroid.model.InfoSection
import com.manishkishore.digidroid.model.StatusLevel
import java.net.NetworkInterface

object NetworkInfoProvider {
    fun section(context: Context): InfoSection {
        val connectivity = context.getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager
        val network = connectivity.activeNetwork
        val capabilities = network?.let { connectivity.getNetworkCapabilities(it) }
        val isOnline = capabilities?.hasCapability(NetworkCapabilities.NET_CAPABILITY_INTERNET) == true
        val transport = when {
            capabilities?.hasTransport(NetworkCapabilities.TRANSPORT_WIFI) == true -> "Wi-Fi"
            capabilities?.hasTransport(NetworkCapabilities.TRANSPORT_CELLULAR) == true -> "Mobile data"
            capabilities?.hasTransport(NetworkCapabilities.TRANSPORT_ETHERNET) == true -> "Ethernet"
            capabilities?.hasTransport(NetworkCapabilities.TRANSPORT_VPN) == true -> "VPN"
            else -> "Unavailable"
        }

        val wifiManager = context.applicationContext.getSystemService(Context.WIFI_SERVICE) as? WifiManager
        val telephony = context.getSystemService(Context.TELEPHONY_SERVICE) as? TelephonyManager

        return InfoSection(
            title = "Network",
            items = listOf(
                InfoItem("Internet", if (isOnline) "Connected" else "Disconnected", status = if (isOnline) StatusLevel.GOOD else StatusLevel.WARNING),
                InfoItem("Transport", transport),
                InfoItem("Wi-Fi enabled", wifiManager?.isWifiEnabled?.let { if (it) "Yes" else "No" } ?: "Unavailable"),
                InfoItem("Wi-Fi SSID", wifiManager?.connectionInfo?.ssid?.cleanSsid() ?: "Restricted or unavailable"),
                InfoItem("IP address", localIpAddress()),
                InfoItem("Network operator", telephony?.networkOperatorName?.takeIf { it.isNotBlank() } ?: "Unavailable"),
                InfoItem("SIM operator", telephony?.simOperatorName?.takeIf { it.isNotBlank() } ?: "Unavailable"),
                InfoItem("Mobile network type", telephony?.dataNetworkType?.let { networkTypeName(it) } ?: "Unavailable"),
                InfoItem("Airplane mode", if (isAirplaneMode(context)) "On" else "Off"),
                InfoItem("Bluetooth", bluetoothStatus())
            )
        )
    }

    private fun String.cleanSsid(): String {
        if (this == "<unknown ssid>") return "Restricted by Android"
        return trim('"')
    }

    private fun localIpAddress(): String {
        return runCatching {
            NetworkInterface.getNetworkInterfaces().toList()
                .flatMap { it.inetAddresses.toList() }
                .firstOrNull { !it.isLoopbackAddress && it.hostAddress?.contains(':') == false }
                ?.hostAddress ?: "Unavailable"
        }.getOrDefault("Unavailable")
    }

    private fun isAirplaneMode(context: Context): Boolean {
        return Settings.Global.getInt(context.contentResolver, Settings.Global.AIRPLANE_MODE_ON, 0) == 1
    }

    private fun bluetoothStatus(): String {
        return runCatching {
            val adapter = BluetoothAdapter.getDefaultAdapter() ?: return "Unavailable"
            if (adapter.isEnabled) "Enabled" else "Disabled"
        }.getOrDefault("Restricted or unavailable")
    }

    private fun networkTypeName(type: Int): String = when (type) {
        TelephonyManager.NETWORK_TYPE_NR -> "5G NR"
        TelephonyManager.NETWORK_TYPE_LTE -> "4G LTE"
        TelephonyManager.NETWORK_TYPE_HSPAP -> "HSPA+"
        TelephonyManager.NETWORK_TYPE_HSPA -> "HSPA"
        TelephonyManager.NETWORK_TYPE_EDGE -> "EDGE"
        TelephonyManager.NETWORK_TYPE_GPRS -> "GPRS"
        TelephonyManager.NETWORK_TYPE_UNKNOWN -> "Unknown"
        else -> "Type $type"
    }
}
