package com.manishkishore.digidroid.ui.screen

import android.content.Context
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.manishkishore.digidroid.data.provider.SystemPropertyReader
import com.manishkishore.digidroid.model.InfoItem
import com.manishkishore.digidroid.ui.component.InfoCard
import com.manishkishore.digidroid.ui.component.SectionTitle

@Composable
fun SystemPropertiesScreen(context: Context) {
    val properties = remember(context) {
        SystemPropertyReader.all().map { (key, value) -> InfoItem(key, value.ifBlank { "Empty" }) }
    }

    LazyColumn(
        modifier = Modifier.fillMaxSize(),
        contentPadding = PaddingValues(16.dp),
        verticalArrangement = Arrangement.spacedBy(12.dp)
    ) {
        item {
            SectionTitle(
                title = "System Properties",
                subtitle = "Readable Android properties from getprop. Useful for ROM and vendor debugging."
            )
        }
        items(properties) { item ->
            InfoCard(item)
        }
    }
}
