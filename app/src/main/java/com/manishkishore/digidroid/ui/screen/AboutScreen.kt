package com.manishkishore.digidroid.ui.screen

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.manishkishore.digidroid.BuildConfig
import com.manishkishore.digidroid.model.InfoItem
import com.manishkishore.digidroid.model.InfoSection
import com.manishkishore.digidroid.ui.component.InfoCard
import com.manishkishore.digidroid.ui.component.SectionTitle

@Composable
fun AboutScreen() {
    val section = InfoSection(
        title = "About",
        items = listOf(
            InfoItem("App", "Digi Droid"),
            InfoItem("Version", BuildConfig.VERSION_NAME),
            InfoItem("Purpose", "Android ROM tester and maintainer companion app"),
            InfoItem("Developer focus", "Device diagnostics, ROM information, kernel details, reports, logs, and checksum verification")
        )
    )

    LazyColumn(
        modifier = Modifier.fillMaxSize(),
        contentPadding = PaddingValues(16.dp),
        verticalArrangement = Arrangement.spacedBy(12.dp)
    ) {
        item { SectionTitle("About Digi Droid") }
        section.items.forEach { info -> item { InfoCard(info) } }
    }
}
