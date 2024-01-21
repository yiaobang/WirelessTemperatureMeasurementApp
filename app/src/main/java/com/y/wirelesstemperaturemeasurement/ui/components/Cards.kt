package com.y.wirelesstemperaturemeasurement.ui.components

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.paddingFromBaseline
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.wrapContentSize
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.filled.KeyboardArrowLeft
import androidx.compose.material3.Card
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.y.wirelesstemperaturemeasurement.ui.CardInfo
import com.y.wirelesstemperaturemeasurement.ui.theme.ThemeColor
import com.y.wirelesstemperaturemeasurement.viewmodel.NavHostViewModel

@Composable
fun GridCards(paddingValues: PaddingValues, cards: Array<CardInfo>, columns: Int = 5) {
    LazyVerticalGrid(
        modifier = Modifier
            .padding(paddingValues = paddingValues)
            .fillMaxSize(),
        verticalArrangement = Arrangement.spacedBy(10.dp),
        horizontalArrangement = Arrangement.spacedBy(10.dp),
        contentPadding = PaddingValues(5.dp),
        columns = GridCells.Fixed(columns)
    ) {
        items(cards) {
            CardButton(it.route, it.image, it.text)
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun CardButton(route: String, image: Int, text: String) {
    Card(onClick = { NavHostViewModel.navigate(route) }) {
        Column(
            modifier = Modifier.fillMaxSize(),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Image(
                modifier = Modifier
                    .fillMaxWidth()
                    .aspectRatio(1f),
                painter = painterResource(id = image),
                contentDescription = text
            )
            Text(
                text = text,
                fontSize = 16.sp,
                modifier = Modifier
                    .paddingFromBaseline(top = 8.dp, bottom = 8.dp),
                //文本样式
                 style = MaterialTheme.typography.bodyMedium,
                fontWeight = FontWeight.Bold
            )
        }
    }
}

@Composable
fun TopBar(msg: String) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .height(40.dp)
            .background(ThemeColor),
        horizontalArrangement = Arrangement.Center,
        verticalAlignment = Alignment.CenterVertically
    ) {
        IconButton(
            modifier = Modifier.padding(2.dp),
            onClick = { NavHostViewModel.popBackStack() }) {
            Icon(
                modifier = Modifier.size(40.dp),
                imageVector = Icons.Default.KeyboardArrowLeft,
                contentDescription = "back"
            )
        }
        Text(
            text = msg,
            fontSize = 20.sp,
            modifier = Modifier
                .fillMaxWidth()
                .wrapContentSize(Alignment.Center)
        )
    }
}