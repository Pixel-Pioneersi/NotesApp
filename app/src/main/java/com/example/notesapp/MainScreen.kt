package com.example.notesapp

import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.safeDrawingPadding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.wrapContentSize
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.ElevatedCard
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.SmallFloatingActionButton
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.scale
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.modifier.modifierLocalConsumer
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import org.threeten.bp.Instant
import org.threeten.bp.ZoneId
import org.threeten.bp.format.DateTimeFormatter

@Composable
fun MainScreen() {
    var itemsList by remember { mutableStateOf(emptyList<String>()) }
    var expandedStates by remember { mutableStateOf(emptyList<Boolean>()) }

    Box(modifier = Modifier.fillMaxSize()) {
        Image(
            painter = painterResource(R.drawable.background_img),
            contentDescription = "Background",
            contentScale = ContentScale.FillBounds,
            modifier = Modifier.matchParentSize()
        )

        LazyVerticalGrid(
            columns = GridCells.Fixed(3),
            contentPadding = PaddingValues(16.dp),
            horizontalArrangement = Arrangement.spacedBy(16.dp)
        ) {
            items(itemsList.size) { index ->
                val cardCreationDate =
                    Instant.now()

                val dateFormatter = DateTimeFormatter.ofPattern("dd.MM.yy")
                val formattedDate =
                    cardCreationDate.atZone(ZoneId.systemDefault()).format(dateFormatter)

                var cardSizes by remember { mutableStateOf(List(itemsList.size) { 1f }) }

                ElevatedCard(
                    modifier = Modifier
                        .padding(top = 16.dp)
                        .size(110.dp)
                        .clickable {
                            val currentIndex = index
                            expandedStates = expandedStates.toMutableList().also {
                                it[currentIndex] = !it[currentIndex]
                            }
                        }
                ) {
                    Box(modifier = Modifier.padding(8.dp)) {
                        Text(
                            text = itemsList[index],
                            modifier = Modifier.align(Alignment.TopCenter),
                            style = TextStyle(fontWeight = FontWeight.Bold)
                        )

                        Text(
                            text = if (expandedStates[index]) itemsList[index] else itemsList[index].take(
                                50
                            ) + " ...",
                            modifier = Modifier
                                .padding(top = 15.dp, start = 0.dp)
                                .scale(0.8f),
                            style = TextStyle(fontWeight = FontWeight.Bold)
                        )
                    }
                    Box(
                        modifier = Modifier
                            .padding(8.dp)
                            .fillMaxSize()
                            .wrapContentSize(Alignment.BottomEnd)
                            .scale(0.5f)
                    ) {
                        Text(
                            text = "$formattedDate",
                            style = TextStyle(fontWeight = FontWeight.Normal)
                        )
                    }
                }
            }
        }

        SmallFloatingActionButton(
            onClick = {
                itemsList = itemsList + "New Card ${itemsList.size + 1}"
                expandedStates = expandedStates + false
            },
            modifier = Modifier
                .align(Alignment.BottomEnd)
                .safeDrawingPadding(),
            containerColor = MaterialTheme.colorScheme.onPrimary
        ) {
            Icon(Icons.Filled.Add, "Floating action button.")
        }
    }
}
