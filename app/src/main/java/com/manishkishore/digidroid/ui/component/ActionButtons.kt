package com.manishkishore.digidroid.ui.component

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.ContentCopy
import androidx.compose.material.icons.outlined.Share
import androidx.compose.material3.Button
import androidx.compose.material3.Icon
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp

@Composable
fun CopyShareButtons(
    onCopy: () -> Unit,
    onShare: () -> Unit,
    enabled: Boolean = true
) {
    Row(
        modifier = Modifier.fillMaxWidth(),
        horizontalArrangement = Arrangement.spacedBy(12.dp)
    ) {
        OutlinedButton(
            onClick = onCopy,
            enabled = enabled,
            modifier = Modifier.weight(1f)
        ) {
            Icon(Icons.Outlined.ContentCopy, contentDescription = null)
            Text("Copy")
        }
        Button(
            onClick = onShare,
            enabled = enabled,
            modifier = Modifier.weight(1f)
        ) {
            Icon(Icons.Outlined.Share, contentDescription = null)
            Text("Share")
        }
    }
}
