package com.manishkishore.digidroid.ui.screen

import android.content.Context
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material3.Card
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Switch
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import com.manishkishore.digidroid.data.repository.SettingsRepository
import com.manishkishore.digidroid.ui.component.SectionTitle

@Composable
fun SettingsScreen(context: Context, onMaintainerModeChanged: (Boolean) -> Unit = {}) {
    val repository = remember { SettingsRepository(context.applicationContext) }
    var maintainerMode by remember { mutableStateOf(repository.maintainerMode) }

    LazyColumn(
        modifier = Modifier.fillMaxSize(),
        contentPadding = PaddingValues(16.dp),
        verticalArrangement = Arrangement.spacedBy(14.dp)
    ) {
        item {
            SectionTitle(
                title = "Settings",
                subtitle = "Control advanced features for ROM maintainers and testers."
            )
        }
        item {
            Card(modifier = Modifier.fillMaxWidth()) {
                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(16.dp),
                    horizontalArrangement = Arrangement.SpaceBetween,
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Column(modifier = Modifier.weight(1f)) {
                        Text("Maintainer Mode", fontWeight = FontWeight.SemiBold)
                        Text(
                            "Shows advanced tools such as system properties and extra debug checks.",
                            style = MaterialTheme.typography.bodySmall,
                            color = MaterialTheme.colorScheme.onSurfaceVariant
                        )
                    }
                    Switch(
                        checked = maintainerMode,
                        onCheckedChange = {
                            maintainerMode = it
                            repository.maintainerMode = it
                            onMaintainerModeChanged(it)
                        }
                    )
                }
            }
        }
    }
}
