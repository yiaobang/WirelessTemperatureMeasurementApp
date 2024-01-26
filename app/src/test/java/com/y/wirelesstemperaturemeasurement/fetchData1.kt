package com.y.wirelesstemperaturemeasurement

import kotlinx.coroutines.async
import kotlinx.coroutines.delay
import kotlinx.coroutines.runBlocking

suspend fun fetchData1(): String {
    delay(2000) // 模拟耗时操作
    return "Data from fetchData1"
}

suspend fun fetchData2(): String {
    delay(1000) // 模拟耗时操作
    return "Data from fetchData2"
}

suspend fun fetchData3(): String {
    delay(1500) // 模拟耗时操作
    return "Data from fetchData3"
}
fun main() {
    test()
}
fun test() = runBlocking {
    val result1 = async { fetchData1() }
    val result2 = async { fetchData2() }
    val result3 = async { fetchData3() }

    // 顺序等待每个任务的完成，并获取结果
    val data1 = result1.await()
    val data2 = result2.await()
    val data3 = result3.await()

    // 打印结果
    println("Data 1: $data1")
    println("Data 2: $data2")
    println("Data 3: $data3")
}