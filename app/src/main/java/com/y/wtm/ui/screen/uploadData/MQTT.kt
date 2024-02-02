package com.y.wtm.ui.screen.uploadData

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Button
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.y.wtm.config.Config
import com.y.wtm.room.isIPv4Address
import com.y.wtm.room.isPort
import com.y.wtm.ui.components.TopBar
import com.y.wtm.ui.components.showToast
import com.y.wtm.upload.MyMQTT
import java.util.UUID

@Composable
fun MQTT() {
    Scaffold(modifier = Modifier.fillMaxSize(),
        topBar = { TopBar("MQTT") }) { paddingValues ->
        MQTTContent(paddingValues)
    }
}

@Composable
fun MQTTContent(paddingValues: PaddingValues) {
    var address by remember { mutableStateOf(Config.readConfig("mqtt-IP", "")) }
    var port by remember { mutableStateOf(Config.readConfig("mqtt-port", "")) }
    var clientId by remember { mutableStateOf(Config.readConfig("mqtt-client", "")) }
    var username by remember { mutableStateOf(Config.readConfig("mqtt-username", "")) }
    var password by remember { mutableStateOf(Config.readConfig("mqtt-password", "")) }
    val context = LocalContext.current
    Column(
        modifier = Modifier
            .padding(paddingValues)
            .fillMaxWidth()
            .verticalScroll(rememberScrollState()),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ) {
        TextField(
            modifier = Modifier.width(200.dp),
            label = { Text(text = "MQTT-address(必填)", fontSize = 10.sp) },
            value = address,
            onValueChange = { address = it },
            keyboardOptions = KeyboardOptions.Default.copy(keyboardType = KeyboardType.Number)
        )
        TextField(
            modifier = Modifier.width(200.dp),
            label = { Text(text = "MQTT-port(必填)", fontSize = 10.sp) },
            value = port,
            onValueChange = {
                if (it.isPort()) {
                    port = it
                }
            },
            keyboardOptions = KeyboardOptions.Default.copy(keyboardType = KeyboardType.Number)
        )
        TextField(
            modifier = Modifier.width(200.dp),
            label = { Text(text = "MQTT-clientId(可选)", fontSize = 10.sp) },
            value = clientId,
            onValueChange = { clientId = it },
            keyboardOptions = KeyboardOptions.Default.copy(keyboardType = KeyboardType.Ascii)
        )
        TextField(
            modifier = Modifier.width(200.dp),
            label = { Text(text = "MQTT-username(可选)", fontSize = 10.sp) },
            value = username,
            onValueChange = { username = it },
            keyboardOptions = KeyboardOptions.Default.copy(keyboardType = KeyboardType.Ascii)
        )
        TextField(
            modifier = Modifier.width(200.dp),
            label = { Text(text = "MQTT-password(可选)", fontSize = 10.sp) },
            value = password,
            onValueChange = { password = it },
            keyboardOptions = KeyboardOptions.Default.copy(keyboardType = KeyboardType.Ascii)
        )
        Button(onClick = {
            if (address == "") {
                showToast(context, "未输入地址")
            } else if (!address.isIPv4Address()) {
                showToast(context, "输入的地址有误")
            } else if (port == "") {
                showToast(context, "未输入端口号")
            } else {
                Config.writeConfig("mqtt-IP", address)
                Config.writeConfig("mqtt-port", port)
                Config.writeConfig("mqtt-client", if(clientId=="") UUID.randomUUID().toString() else clientId)
                Config.writeConfig("mqtt-username", username)
                Config.writeConfig("mqtt-password", password)
                MyMQTT.init()
            }
        }) {
            Text(text = "确认")
        }
    }
}
