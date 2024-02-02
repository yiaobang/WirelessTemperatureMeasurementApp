package com.y.wtm.upload

import android.util.Log
import com.y.wtm.TAG
import com.y.wtm.config.Config
import com.y.wtm.room.Data
import com.y.wtm.room.temp
import com.y.wtm.room.toDateTime
import com.y.wtm.room.voltageRH
import com.y.wtm.serialport.EventLevelAndMsg
import kotlinx.coroutines.DelicateCoroutinesApi
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch
import org.eclipse.paho.mqttv5.client.IMqttToken
import org.eclipse.paho.mqttv5.client.MqttCallback
import org.eclipse.paho.mqttv5.client.MqttClient
import org.eclipse.paho.mqttv5.client.MqttConnectionOptions
import org.eclipse.paho.mqttv5.client.MqttDisconnectResponse
import org.eclipse.paho.mqttv5.common.MqttException
import org.eclipse.paho.mqttv5.common.MqttMessage
import org.eclipse.paho.mqttv5.common.packet.MqttProperties
import java.util.Timer
import java.util.TimerTask
import java.util.UUID

/**
 * MQTT客户端回调处理
 * 用于处理与客户端相关的异步事件。
 * @author Y
 * @date 2024/02/01
 */
object MyMQTT : MqttCallback {
    @Volatile
    private lateinit var MQTT: MqttClient

    @Volatile
    private var conning: Boolean = false
    private val mqttConnectionOptions: MqttConnectionOptions = MqttConnectionOptions()
    private const val dataTopic = "data"
    private const val eventTopic = "event"
    private var address = "127.0.0.1"
    private var port = 1883
    private var clientId = UUID.randomUUID().toString()
    private var userName = ""
    private var password = ""
    private val timer = Timer()

    /**
     * MQTT连接任务
     */
    private val timerTask = object : TimerTask() {
        override fun run() {
            try {
                MQTT.connect(mqttConnectionOptions)
                if (MQTT.isConnected) {
                    Log.d(TAG, "MQTT连接成功")
                    //初始化完成关闭任务
                    conning = false
                    timer.cancel()
                }
            } catch (e: Exception) {
                Log.e(TAG, "MQTT移除 ${e.message}")
            }
        }
    }

    private fun read() {
        address = Config.readConfig("mqtt-IP", address)
        port = Config.readConfig("mqtt-port", "1883").toInt()
        clientId = Config.readConfig("mqtt-client", clientId)
        userName = Config.readConfig("mqtt-username", "")
        password = Config.readConfig("mqtt-password", "")
    }

    @OptIn(DelicateCoroutinesApi::class)
    fun init() = GlobalScope.launch {
        read()
        timer.cancel()
        conning = true
        if (::MQTT.isInitialized) {
            MQTT.disconnect()
            MQTT.close()
        }
        MQTT = MqttClient("tcp://$address:$port", clientId, null)
        MQTT.setCallback(MyMQTT)
        mqttConnectionOptions.userName = userName
        mqttConnectionOptions.password = password.toByteArray()
        //启动任务
        timer.schedule(timerTask, 0, 60_000)
    }

    private fun String.message() = MqttMessage(this.toByteArray())
    private fun sendData(data: String) {
        if (MQTT.isConnected) {
            Log.d(TAG, "MQTT发送数据:[$data] ")
            MQTT.publish(dataTopic, data.message())
        }
    }

    private fun sendEvent(data: String) {
        if (MQTT.isConnected) {
            Log.d(TAG, "MQTT发送事件:[$data] ")
            MQTT.publish(eventTopic, data.message())
        }
    }

    /**
     * 当服务器通过发送断开连接数据包正常断开与客户端的连接，或者由于网络问题或客户端遇到错误而导致 TCP 连接丢失时，调用此方法。
     */
    override fun disconnected(disconnectResponse: MqttDisconnectResponse?) {
        Log.d(TAG, "disconnected: Mqtt客户端断开连接")
        if (!conning) {
            conning = true
            //启动任务
            timer.schedule(timerTask, 0, 60_000)
        }
    }

    /**
     * MQTT 客户端内部抛出异常时调用此方法。异常的原因可能是各种原因，从格式错误的数据包到协议错误，甚至是 MQTT 客户端本身的错误。
     */
    override fun mqttErrorOccurred(exception: MqttException?) {
        Log.e(TAG, "mqttErrorOccurred: MQTT客户端异常", exception)
    }

    /**
     * 当从服务器接收到消息时调用此方法。方法同步由 MQTT 客户端调用，直到该方法干净地返回，才会向服务器发送确认。
     * 如果此方法的实现抛出异常，则客户端将被关闭。当客户端下次重新连接时，服务器将重新传递任何 QoS 1 或 2 的消息。
     */
    override fun messageArrived(topic: String?, message: MqttMessage?) {
        TODO("Not yet implemented")
    }

    /**
     * 在消息的传递完成并且已收到所有确认时调用此方法。对于 QoS 0 的消息，当消息被传递到网络进行传递时调用。对于 QoS 1，
     *  在接收到 PUBACK 时调用，对于 QoS 2，在接收到 PUBCOMP 时调用。传递令牌将与消息发布时返回的令牌相同。
     */
    override fun deliveryComplete(token: IMqttToken?) {
        TODO("Not yet implemented")
    }

    /**
     * 在与服务器的连接成功完成时调用此方法。
     *
     * @param reconnect 如果为 true，则连接是自动重新连接的结果。
     * @param serverURI 连接所使用的服务器 URI。
     */
    override fun connectComplete(reconnect: Boolean, serverURI: String?) {
        Log.d(TAG, "Mqtt客户端与${serverURI} ${if (reconnect) "重连" else "连接"}成功")
    }

    /**
     * 在客户端接收到 AUTH 数据包时调用此方法。
     *
     * @param reasonCode 原因代码，可能为 Success (0)，Continue authentication (24) 或 Re-authenticate (25)。
     * @param properties 包含认证方法、认证数据和任何必需用户定义属性的 {@link MqttProperties} 对象。
     */
    override fun authPacketArrived(reasonCode: Int, properties: MqttProperties?) {
        TODO("Not yet implemented")
    }

    fun sendData(id: Int, type: Int, newData: Data) {
        //测温点id,温度,湿度/电压,时间
        sendData(
            "$id,${temp(newData.temperature)},${
                voltageRH(
                    type,
                    newData.voltageRH
                )
            },${newData.time.toDateTime()}"
        )
    }

    fun sendEvent(id: Int, type: Int, newData: Data, event: EventLevelAndMsg) {
        //测温点id,温度,湿度/电压,事件级别,事件描述,时间
        sendEvent(
            "$id,${temp(newData.temperature)},${
                voltageRH(
                    type,
                    newData.voltageRH
                )
            },${event.eventLevel},${event.eventMsg},${newData.time.toDateTime()}"
        )
    }
}
