package com.y.wirelesstemperaturemeasurement.ui.screen

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.wrapContentSize
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Menu
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.y.wirelesstemperaturemeasurement.R
import com.y.wirelesstemperaturemeasurement.ui.theme.ThemeColor
import com.y.wirelesstemperaturemeasurement.viewmodel.NavHostViewModel
import com.y.wirelesstemperaturemeasurement.viewmodel.StateViewModel

@Composable
fun Home() {
    Scaffold(modifier = Modifier.fillMaxSize(),
        topBar = { HomeTopBar() },
        bottomBar = { HomeBottom() }) { paddingValues -> HomeContent(paddingValues) }
}

@Composable
fun HomeContent(paddingValues: PaddingValues) {

}

@Composable
private fun HomeTopBar() {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .height(66.dp)
            .background(ThemeColor),
        horizontalArrangement = Arrangement.Center
    ) {
        IconButton(
            modifier = Modifier.padding(10.dp),
            onClick = { NavHostViewModel.navigate("Menu") }) {
            Icon(
                modifier = Modifier.size(40.dp),
                imageVector = Icons.Default.Menu,
                contentDescription = "菜单"
            )
        }
        Text(
            text = stringResource(R.string.app_name),
            fontSize = 30.sp,
            modifier = Modifier
                .fillMaxWidth()
                .padding(10.dp)
                .wrapContentSize(Alignment.Center)
        )
    }
}

@Composable
private fun HomeBottom() {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .height(50.dp)
            .background(ThemeColor),
        horizontalArrangement = Arrangement.SpaceBetween
    ) {
        IconButton(onClick = { StateViewModel.updateAudible() }) {
            Image(
                modifier = Modifier.size(30.dp),
                painter = painterResource(if (StateViewModel.audible) R.drawable.mute_normal else R.drawable.mute_press),
                contentDescription = ""
            )
        }
        SensorMapping()
        WarnMsg()
        ErrorMsg()
        EventMsg()
        Spacer(modifier = Modifier.width(20.dp))
    }
}

@Composable
private fun SensorMapping() {
    Row(verticalAlignment = Alignment.CenterVertically) {
        IconButton(onClick = { }) {
            Image(
                modifier = Modifier.size(30.dp),
                painter = painterResource(R.drawable.bbar_point),
                contentDescription = ""
            )
        }
        Text(
            modifier = Modifier
                .height(30.dp)
                .wrapContentSize(Alignment.Center),
            text = "系统共接入${StateViewModel.sensor}个测温点"
        )
    }
}

@Composable
private fun WarnMsg() {
    Row(verticalAlignment = Alignment.CenterVertically) {
        IconButton(onClick = { }) {
            Image(
                modifier = Modifier.size(30.dp),
                painter = painterResource(R.drawable.bbar_warn),
                contentDescription = ""
            )
        }
        Text(
            modifier = Modifier
                .height(30.dp)
                .wrapContentSize(Alignment.Center),
            text = "${StateViewModel.warn}条预警信息"
        )
    }
}

@Composable
private fun ErrorMsg() {
    Row(verticalAlignment = Alignment.CenterVertically) {
        IconButton(onClick = { }) {
            Row {
                Image(
                    modifier = Modifier.size(30.dp),
                    painter = painterResource(R.drawable.bbar_new),
                    contentDescription = ""
                )
            }

        }
        Text(
            modifier = Modifier
                .height(30.dp)
                .wrapContentSize(Alignment.Center),
            text = "${StateViewModel.error}条报警消息"
        )
    }
}

@Composable
private fun EventMsg() {
    Row(verticalAlignment = Alignment.CenterVertically) {
        IconButton(onClick = { }) {
            Image(
                modifier = Modifier.size(30.dp),
                painter = painterResource(R.drawable.bbar_msg),
                contentDescription = ""
            )
        }
        Text(
            modifier = Modifier
                .height(30.dp)
                .wrapContentSize(Alignment.Center),
            text = "${StateViewModel.sensor}条新事件消息"
        )
    }
}