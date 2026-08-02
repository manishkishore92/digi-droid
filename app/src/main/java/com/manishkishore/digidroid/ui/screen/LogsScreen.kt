package com.manishkishore.digidroid.ui.screen

import android.content.Context
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.heightIn
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material3.Button
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.unit.dp
import com.manishkishore.digidroid.data.provider.LogProvider
import com.manishkishore.digidroid.ui.component.CopyShareButtons
import com.manishkishore.digidroid.ui.component.SectionTitle
import com.manishkishore.digidroid.util.ShareUtils

@Composable
fun LogsScreen(context: Context) {
    var output by remember { mutableStateOf("") }

    LazyColumn(
        modifier = Modifier.fillMaxSize(),
        contentPadding = PaddingValues(16.dp),
        verticalArrangement = Arrangement.spacedBy(14.dp)
    ) {
        item {
            SectionTitle(
                title = "Logs",
                subtitle = "Collect readable logs and reboot hints. Android may restrict some system logs without root."
            )
        }
        item {
            Button(onClick = { output = LogProvider.recentLogcat(300) }, modifier = Modifier.fillMaxWidth()) {
                Text("Read Recent Logcat")
            }
        }
        item {
            Button(onClick = { output = LogProvider.lastRebootReason() }, modifier = Modifier.fillMaxWidth()) {
                Text("Read Last Reboot Reason")
            }
        }
        item {
            Button(onClick = { output = LogProvider.dmesg() }, modifier = Modifier.fillMaxWidth()) {
                Text("Try dmesg")
            }
        }
        item {
            CopyShareButtons(
                onCopy = { ShareUtils.copyText(context, "Digi Droid Logs", output) },
                onShare = { ShareUtils.shareText(context, "Digi Droid Logs", output) },
                enabled = output.isNotBlank()
            )
        }
        if (output.isNotBlank()) {
            item {
                OutlinedTextField(
                    value = output,
                    onValueChange = {},
                    readOnly = true,
                    textStyle = MaterialTheme.typography.bodySmall.copy(fontFamily = FontFamily.Monospace),
                    modifier = Modifier
                        .fillMaxWidth()
                        .heightIn(min = 280.dp)
                )
            }
        }
    }
}
