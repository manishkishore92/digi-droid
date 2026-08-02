package com.manishkishore.digidroid.ui.screen

import android.content.Context
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.heightIn
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material3.Button
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.ExposedDropdownMenuBox
import androidx.compose.material3.ExposedDropdownMenuDefaults
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
import com.manishkishore.digidroid.data.repository.ReportRepository
import com.manishkishore.digidroid.model.DeviceReportInput
import com.manishkishore.digidroid.ui.component.CopyShareButtons
import com.manishkishore.digidroid.ui.component.SectionTitle
import com.manishkishore.digidroid.util.ShareUtils

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ReportScreen(context: Context) {
    val categories = listOf(
        "Bootloop",
        "Battery drain",
        "Heating",
        "Camera issue",
        "Bluetooth issue",
        "Wi-Fi issue",
        "Mobile network issue",
        "Fingerprint issue",
        "Performance issue",
        "Random reboot",
        "App crash",
        "Charging issue",
        "Other"
    )
    var expanded by remember { mutableStateOf(false) }
    var category by remember { mutableStateOf(categories.first()) }
    var description by remember { mutableStateOf("") }
    var steps by remember { mutableStateOf("") }
    var notes by remember { mutableStateOf("") }
    var report by remember { mutableStateOf("") }

    LazyColumn(
        modifier = Modifier.fillMaxSize(),
        contentPadding = PaddingValues(16.dp),
        verticalArrangement = Arrangement.spacedBy(14.dp)
    ) {
        item {
            SectionTitle(
                title = "Bug Report Generator",
                subtitle = "Create a ready-to-share report for ROM support groups, maintainers, and GitHub issues."
            )
        }
        item {
            ExposedDropdownMenuBox(expanded = expanded, onExpandedChange = { expanded = !expanded }) {
                OutlinedTextField(
                    value = category,
                    onValueChange = {},
                    readOnly = true,
                    label = { Text("Issue category") },
                    trailingIcon = { ExposedDropdownMenuDefaults.TrailingIcon(expanded = expanded) },
                    modifier = Modifier.menuAnchor().fillMaxWidth()
                )
                ExposedDropdownMenu(expanded = expanded, onDismissRequest = { expanded = false }) {
                    categories.forEach { item ->
                        DropdownMenuItem(
                            text = { Text(item) },
                            onClick = {
                                category = item
                                expanded = false
                            }
                        )
                    }
                }
            }
        }
        item {
            OutlinedTextField(
                value = description,
                onValueChange = { description = it },
                label = { Text("Issue description") },
                minLines = 3,
                modifier = Modifier.fillMaxWidth()
            )
        }
        item {
            OutlinedTextField(
                value = steps,
                onValueChange = { steps = it },
                label = { Text("Reproduction steps") },
                minLines = 4,
                modifier = Modifier.fillMaxWidth()
            )
        }
        item {
            OutlinedTextField(
                value = notes,
                onValueChange = { notes = it },
                label = { Text("Extra notes") },
                minLines = 2,
                modifier = Modifier.fillMaxWidth()
            )
        }
        item {
            Button(
                onClick = {
                    report = ReportRepository(context).generate(
                        DeviceReportInput(
                            category = category,
                            description = description,
                            steps = steps,
                            extraNotes = notes
                        )
                    )
                },
                modifier = Modifier.fillMaxWidth()
            ) {
                Text("Generate Report")
            }
        }
        item {
            CopyShareButtons(
                onCopy = { ShareUtils.copyText(context, "Digi Droid Report", report) },
                onShare = { ShareUtils.shareText(context, "Digi Droid Report", report) },
                enabled = report.isNotBlank()
            )
        }
        if (report.isNotBlank()) {
            item {
                Column(verticalArrangement = Arrangement.spacedBy(8.dp)) {
                    Text("Preview", style = MaterialTheme.typography.titleMedium)
                    OutlinedTextField(
                        value = report,
                        onValueChange = {},
                        readOnly = true,
                        textStyle = MaterialTheme.typography.bodySmall.copy(fontFamily = FontFamily.Monospace),
                        modifier = Modifier
                            .fillMaxWidth()
                            .heightIn(min = 220.dp)
                    )
                }
            }
        }
    }
}
