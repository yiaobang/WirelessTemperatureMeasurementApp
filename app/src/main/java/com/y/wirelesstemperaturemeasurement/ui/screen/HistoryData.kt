package com.y.wirelesstemperaturemeasurement.ui.screen

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.wrapContentSize
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Search
import androidx.compose.material3.FabPosition
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.y.wirelesstemperaturemeasurement.ui.components.TopBar
import com.y.wirelesstemperaturemeasurement.ui.theme.ThemeColor

@Composable
fun HistoryData() {
    Scaffold(modifier = Modifier.fillMaxSize(),
        floatingActionButton = { SearchHistory() },
        floatingActionButtonPosition = FabPosition.End,
        topBar = { TopBar("历史数据") }) { paddingValues ->
        HistoryContent(paddingValues)
    }
}

@Composable
fun HistoryContent(paddingValues: PaddingValues) {
    Column(
        modifier = Modifier
            .padding(paddingValues)
            .fillMaxSize()
    ) {
        Title()
        LazyColumn(
            modifier = Modifier
                .fillMaxSize()
        ) {

        }
    }
}

@Composable
fun SearchHistory() {
    var expand by remember { mutableStateOf(false) }
    IconButton(onClick = { }) {
        Icon(
            modifier = Modifier
                .background(ThemeColor)
                .size(50.dp),
            imageVector = Icons.Default.Search,
            contentDescription = "Search"
        )
    }
}

@Composable
private fun Title() {
    Row(
        Modifier
            .fillMaxWidth(),
        horizontalArrangement = Arrangement.Center
    ) {
        Text(
            fontSize = 20.sp,
            fontWeight = FontWeight.Bold,
            modifier = Modifier
                .weight(0.2f)
                .height(30.dp)
                .border(1.dp, Color.Black)
                .wrapContentSize(Alignment.Center),
            text = "id"
        )
        Text(
            fontSize = 20.sp,
            fontWeight = FontWeight.Bold,
            modifier = Modifier
                .weight(0.3f)
                .height(30.dp)
                .border(1.dp, Color.Black)
                .wrapContentSize(Alignment.Center),
            text = "温度"
        )
        Text(
            fontSize = 20.sp,
            fontWeight = FontWeight.Bold,
            modifier = Modifier
                .weight(0.3f)
                .height(30.dp)
                .border(1.dp, Color.Black)
                .wrapContentSize(Alignment.Center),
            text = "电压/湿度"
        )
        Text(
            fontSize = 20.sp,
            fontWeight = FontWeight.Bold,
            modifier = Modifier
                .weight(0.3f)
                .height(30.dp)
                .border(1.dp, Color.Black)
                .wrapContentSize(Alignment.Center),
            text = "时间"
        )
    }
}
