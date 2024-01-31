package com.y.wirelesstemperaturemeasurement.upload

import org.eclipse.paho.mqttv5.client.IMqttToken
import org.eclipse.paho.mqttv5.client.MqttCallback
import org.eclipse.paho.mqttv5.client.MqttClient
import org.eclipse.paho.mqttv5.client.MqttDisconnectResponse
import org.eclipse.paho.mqttv5.common.MqttException
import org.eclipse.paho.mqttv5.common.MqttMessage
import org.eclipse.paho.mqttv5.common.packet.MqttProperties


object MyMQTT {
    private lateinit var mqtt: MqttClient
    private var connect:Boolean = false
    const val dataTopic = "data"
    const val eventTopic = "event"
    fun init() {
        mqtt.connect()
    }

    private fun String.message() = MqttMessage(this.toByteArray())
    fun sendData(data: String) {
        if (mqtt.isConnected) {
            mqtt.publish(dataTopic, data.message())
        }
    }

    fun sendEvent(data: String) {
        if (mqtt.isConnected) {
            mqtt.publish(eventTopic, data.message())
        }
    }

    object MQTTCallBack : MqttCallback {
        override fun disconnected(disconnectResponse: MqttDisconnectResponse?) {
            TODO("Not yet implemented")
        }

        override fun mqttErrorOccurred(exception: MqttException?) {
            TODO("Not yet implemented")
        }

        override fun messageArrived(topic: String?, message: MqttMessage?) {
            TODO("Not yet implemented")
        }

        override fun deliveryComplete(token: IMqttToken?) {
            TODO("Not yet implemented")
        }

        override fun connectComplete(reconnect: Boolean, serverURI: String?) {
            TODO("Not yet implemented")
        }

        override fun authPacketArrived(reasonCode: Int, properties: MqttProperties?) {
            TODO("Not yet implemented")
        }

    }
}
