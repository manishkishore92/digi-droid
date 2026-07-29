package com.manishkishore.digidroid.ui.component

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.unit.dp
import com.manishkishore.digidroid.model.StatusLevel
import com.manishkishore.digidroid.ui.theme.Amber600
import com.manishkishore.digidroid.ui.theme.Blue600
import com.manishkishore.digidroid.ui.theme.Emerald600
import com.manishkishore.digidroid.ui.theme.Rose600

@Composable
fun StatusDot(status: StatusLevel) {
    val color = when (status) {
        StatusLevel.GOOD -> Emerald600
        StatusLevel.WARNING -> Amber600
        StatusLevel.BAD -> Rose600
        StatusLevel.NEUTRAL -> Blue600
    }
    Box(
        modifier = Modifier
            .size(10.dp)
            .clip(CircleShape)
            .background(color)
    )
}
