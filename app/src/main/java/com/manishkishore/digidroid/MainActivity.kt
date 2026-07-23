package com.manishkishore.digidroid

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.BatteryChargingFull
import androidx.compose.material.icons.outlined.BugReport
import androidx.compose.material.icons.outlined.DeveloperBoard
import androidx.compose.material.icons.outlined.Dns
import androidx.compose.material.icons.outlined.Home
import androidx.compose.material.icons.outlined.Info
import androidx.compose.material.icons.outlined.Memory
import androidx.compose.material.icons.outlined.Menu
import androidx.compose.material.icons.outlined.NetworkCheck
import androidx.compose.material.icons.outlined.PhoneAndroid
import androidx.compose.material.icons.outlined.Route
import androidx.compose.material.icons.outlined.SdStorage
import androidx.compose.material.icons.outlined.Security
import androidx.compose.material.icons.outlined.Sensors
import androidx.compose.material.icons.outlined.Settings
import androidx.compose.material.icons.outlined.Terminal
import androidx.compose.material3.DrawerValue
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.ModalDrawerSheet
import androidx.compose.material3.ModalNavigationDrawer
import androidx.compose.material3.NavigationDrawerItem
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.rememberDrawerState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.vector.ImageVector
import com.manishkishore.digidroid.data.repository.DeviceRepository
import com.manishkishore.digidroid.data.repository.SettingsRepository
import com.manishkishore.digidroid.model.InfoSection
import com.manishkishore.digidroid.ui.screen.AboutScreen
import com.manishkishore.digidroid.ui.screen.DashboardScreen
import com.manishkishore.digidroid.ui.screen.InfoListScreen
import com.manishkishore.digidroid.ui.screen.LogsScreen
import com.manishkishore.digidroid.ui.screen.MultiSectionScreen
import com.manishkishore.digidroid.ui.screen.ReportScreen
import com.manishkishore.digidroid.ui.screen.SettingsScreen
import com.manishkishore.digidroid.ui.screen.SystemPropertiesScreen
import com.manishkishore.digidroid.ui.screen.ZipVerifyScreen
import com.manishkishore.digidroid.ui.theme.DigiDroidTheme
import kotlinx.coroutines.launch

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            DigiDroidTheme {
                DigiDroidApp()
            }
        }
    }
}

private enum class Screen(val title: String, val icon: ImageVector) {
    Dashboard("Dashboard", Icons.Outlined.Home),
    Device("Device", Icons.Outlined.PhoneAndroid),
    Rom("ROM", Icons.Outlined.Route),
    Kernel("Kernel", Icons.Outlined.Memory),
    Battery("Battery", Icons.Outlined.BatteryChargingFull),
    Storage("Storage", Icons.Outlined.SdStorage),
    Network("Network", Icons.Outlined.NetworkCheck),
    Sensors("Sensors", Icons.Outlined.Sensors),
    Root("Root & System", Icons.Outlined.Security),
    Logs("Logs", Icons.Outlined.Terminal),
    Report("Report", Icons.Outlined.BugReport),
    ZipVerify("ZIP Verify", Icons.Outlined.Dns),
    SystemProperties("System Properties", Icons.Outlined.DeveloperBoard),
    Settings("Settings", Icons.Outlined.Settings),
    About("About", Icons.Outlined.Info)
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
private fun DigiDroidApp() {
    val context = androidx.compose.ui.platform.LocalContext.current
    val repository = remember(context) { DeviceRepository(context.applicationContext) }
    val settingsRepository = remember(context) { SettingsRepository(context.applicationContext) }
    val drawerState = rememberDrawerState(DrawerValue.Closed)
    val scope = rememberCoroutineScope()
    var currentScreen by remember { mutableStateOf(Screen.Dashboard) }
    var maintainerMode by remember { mutableStateOf(settingsRepository.maintainerMode) }

    val screens = remember(maintainerMode) {
        Screen.entries.filter { screen ->
            maintainerMode || screen != Screen.SystemProperties
        }
    }

    ModalNavigationDrawer(
        drawerState = drawerState,
        drawerContent = {
            ModalDrawerSheet {
                Text("Digi Droid", modifier = Modifier.padding(horizontal = 24.dp, vertical = 20.dp))
                screens.forEach { screen ->
                    NavigationDrawerItem(
                        label = { Text(screen.title) },
                        icon = { Icon(screen.icon, contentDescription = null) },
                        selected = currentScreen == screen,
                        onClick = {
                            currentScreen = screen
                            scope.launch { drawerState.close() }
                        }
                    )
                }
            }
        }
    ) {
        Scaffold(
            topBar = {
                TopAppBar(
                    title = { Text(currentScreen.title) },
                    navigationIcon = {
                        IconButton(onClick = { scope.launch { drawerState.open() } }) {
                            Icon(Icons.Outlined.Menu, contentDescription = "Open navigation")
                        }
                    }
                )
            }
        ) { paddingValues ->
            androidx.compose.foundation.layout.Box(modifier = Modifier.padding(paddingValues)) {
                when (currentScreen) {
                    Screen.Dashboard -> DashboardScreen(repository.dashboardSections())
                    Screen.Device -> InfoListScreen(repository.deviceSection(), "Hardware identity and Android build-visible device fields.")
                    Screen.Rom -> InfoListScreen(repository.romSection(), "ROM, build, fingerprint, security patch, and vendor properties.")
                    Screen.Kernel -> InfoListScreen(repository.kernelSection(), "Kernel release, command line, uptime, ABI, and SELinux status.")
                    Screen.Battery -> InfoListScreen(repository.batterySection(), "Battery level, health, temperature, charging source, and voltage.")
                    Screen.Storage -> InfoListScreen(repository.storageSection(), "Internal storage and RAM status for build issue reports.")
                    Screen.Network -> InfoListScreen(repository.networkSection(), "Wi-Fi, mobile network, local IP, operator, and transport details.")
                    Screen.Sensors -> MultiSectionScreen(listOf(repository.sensorSection(), InfoSection("All Sensors", repository.allSensors())))
                    Screen.Root -> InfoListScreen(repository.rootSection(), "Root hints, verified boot properties, and known root manager checks.")
                    Screen.Logs -> LogsScreen(context)
                    Screen.Report -> ReportScreen(context)
                    Screen.ZipVerify -> ZipVerifyScreen(context)
                    Screen.SystemProperties -> SystemPropertiesScreen(context)
                    Screen.Settings -> SettingsScreen(context, onMaintainerModeChanged = { maintainerMode = it })
                    Screen.About -> AboutScreen()
                }
            }
        }
    }
}
