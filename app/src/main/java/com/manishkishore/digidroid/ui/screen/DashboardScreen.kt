package com.manishkishore.digidroid.ui.screen

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.manishkishore.digidroid.model.InfoSection
import com.manishkishore.digidroid.ui.component.InfoCard
import com.manishkishore.digidroid.ui.component.SectionTitle

@Composable
fun DashboardScreen(sections: List<InfoSection>) {
    LazyColumn(
        modifier = Modifier.fillMaxSize(),
        contentPadding = PaddingValues(16.dp),
        verticalArrangement = Arrangement.spacedBy(12.dp)
    ) {
        item {
            SectionTitle(
                title = "Digi Droid",
                subtitle = "Device diagnostics and ROM maintainer report toolkit."
            )
        }
        sections.forEach { section ->
            item { SectionTitle(section.title) }
            items(section.items.take(if (section.title == "ROM & Build") 5 else section.items.size)) { item ->
                InfoCard(item)
            }
        }
    }
}
