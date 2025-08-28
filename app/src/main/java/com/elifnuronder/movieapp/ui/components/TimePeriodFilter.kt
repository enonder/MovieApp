package com.elifnuronder.movieapp.ui.components

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.elifnuronder.movieapp.domain.model.TimePeriod

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun TimePeriodFilter(
    selectedTimePeriod: TimePeriod,
    onTimePeriodSelected: (TimePeriod) -> Unit,
    modifier: Modifier = Modifier
) {
    Row(
        modifier = modifier
            .fillMaxWidth()
            .padding(horizontal = 16.dp, vertical = 8.dp),
        horizontalArrangement = Arrangement.Start
    ) {
        TimePeriod.values().forEach { timePeriod ->
            FilterChip(
                onClick = { onTimePeriodSelected(timePeriod) },
                label = { 
                    Text(
                        text = timePeriod.displayName,
                        style = MaterialTheme.typography.labelMedium
                    ) 
                },
                selected = selectedTimePeriod == timePeriod,
                modifier = Modifier.padding(end = 8.dp),
                shape = RoundedCornerShape(20.dp)
            )
        }
    }
}
