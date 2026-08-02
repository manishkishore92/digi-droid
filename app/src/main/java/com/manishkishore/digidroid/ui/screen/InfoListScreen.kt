package com.manishkishore.digidroid.ui.screen

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.manishkishore.digidroid.model.InfoSection
import com.manishkishore.digidroid.ui.component.InfoCard
import com.manishkishore.digidroid.ui.component.SectionTitle

@Composable
fun InfoListScreen(section: InfoSection, subtitle: String? = null) {
    LazyColumn(
        modifier = Modifier.fillMaxSize(),
        contentPadding = PaddingValues(16.dp),
        verticalArrangement = Arrangement.spacedBy(12.dp)
    ) {
        item {
            SectionTitle(section.title, subtitle)
        }
        items(section.items) { item ->
            InfoCard(item)
        }
    }
}

@Composable
fun MultiSectionScreen(sections: List<InfoSection>) {
    LazyColumn(
        modifier = Modifier.fillMaxSize(),
        contentPadding = PaddingValues(16.dp),
        verticalArrangement = Arrangement.spacedBy(12.dp)
    ) {
        sections.forEach { section ->
            item {
                SectionTitle(section.title)
            }
            items(section.items) { item ->
                InfoCard(item)
            }
        }
    }
}
