package com.manishkishore.digidroid.ui.screen

import android.content.Context
import android.net.Uri
import android.os.Handler
import android.os.Looper
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
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
import com.manishkishore.digidroid.data.provider.ChecksumProvider
import com.manishkishore.digidroid.ui.component.CopyShareButtons
import com.manishkishore.digidroid.ui.component.SectionTitle
import com.manishkishore.digidroid.util.ShareUtils

@Composable
fun ZipVerifyScreen(context: Context) {
    var selectedUri by remember { mutableStateOf<Uri?>(null) }
    var resultText by remember { mutableStateOf("") }
    var isProcessing by remember { mutableStateOf(false) }
    val mainHandler = remember { Handler(Looper.getMainLooper()) }

    val launcher = rememberLauncherForActivityResult(ActivityResultContracts.GetContent()) { uri ->
        selectedUri = uri
        if (uri != null) {
            isProcessing = true
            resultText = "Calculating SHA-256..."
            Thread {
                val text = runCatching {
                    val result = ChecksumProvider.sha256(context, uri)
                    buildString {
                        appendLine("File: ${result.fileName}")
                        appendLine("Size: ${result.fileSize}")
                        appendLine("SHA-256: ${result.sha256}")
                    }
                }.getOrElse { error ->
                    "Failed to calculate checksum: ${error.message ?: "Unknown error"}"
                }
                mainHandler.post {
                    resultText = text
                    isProcessing = false
                }
            }.start()
        }
    }

    LazyColumn(
        modifier = Modifier.fillMaxSize(),
        contentPadding = PaddingValues(16.dp),
        verticalArrangement = Arrangement.spacedBy(14.dp)
    ) {
        item {
            SectionTitle(
                title = "ZIP Verify",
                subtitle = "Generate SHA-256 checksums for ROM ZIPs, kernels, recoveries, and release files."
            )
        }
        item {
            Button(
                onClick = { launcher.launch("*/*") },
                modifier = Modifier.fillMaxWidth(),
                enabled = !isProcessing
            ) {
                Text(if (isProcessing) "Processing..." else "Select File")
            }
        }
        if (selectedUri != null || resultText.isNotBlank()) {
            item {
                Column(verticalArrangement = Arrangement.spacedBy(8.dp)) {
                    Text("Checksum Result", style = MaterialTheme.typography.titleMedium)
                    OutlinedTextField(
                        value = resultText,
                        onValueChange = {},
                        readOnly = true,
                        textStyle = MaterialTheme.typography.bodyMedium.copy(fontFamily = FontFamily.Monospace),
                        modifier = Modifier
                            .fillMaxWidth()
                            .heightIn(min = 160.dp)
                    )
                }
            }
            item {
                CopyShareButtons(
                    onCopy = { ShareUtils.copyText(context, "Digi Droid Checksum", resultText) },
                    onShare = { ShareUtils.shareText(context, "Digi Droid Checksum", resultText) },
                    enabled = resultText.isNotBlank() && !isProcessing
                )
            }
        }
    }
}
