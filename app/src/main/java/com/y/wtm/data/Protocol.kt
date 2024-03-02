package com.y.wtm.data

import java.nio.ByteBuffer

/**
 * 数据字节格式顺序如下：
 * [0]：数据头		0x53
 * [1]：模块的当前地址,     最小值为1，最大值为254  0或FF时为广播式操作读写
 * [2]：功能码即主标识      读(0x03)或写(0x10) ,接收模块主动上传温度值是读(0x03)
 * [3]：功能码即副标识
 *      1-参数值的读写操作
 *      2-温度值传送,
 *      3-错误,
 *      4-返回成功，
 *      5-不为本模块数据，但是通信收发正确
 *      6-读取模块硬件版本号,
 *      7-读到模块软件版本号
 * [4]：数据长度：从数据长度本身下一个字节开始到校验和前一个数据字节长度。1个字节
 * [5]，发送模块点地址最低字节
 * [6]，发送模块点地址次高字节
 * [7]，发送模块点地址中高字节
 * [8]，发送模块点地址最高字节
 * [9]，温度低8位
 * [10]，温度高8位
 * [11]，供电电压低8位 无，默认为0
 * [12]，供电电压高8位	无，默认为0
 * [13]，默认为0
 * [14]，默认为0
 * [15]，附加信息1：RSSI值
 * [16]，附加信息2：默认为0
 * [17]：校验和  校验和字前的所有的字节数据累加和操作
 * [18]：0x16结束符
 */

/**查询硬件版本**/
val HARD: ByteArray = byteArrayOf(0x53, 0x00, 0x03, 0x06, 0x00, 0x5C, 0x16)

/**查询软件版本**/
val SOFT: ByteArray = byteArrayOf(0x53, 0x00, 0x03, 0x07, 0x00, 0x5D, 0x16)

const val TEMP = "℃"
const val VOLTAGE = "V"
const val RH = "%RH"
const val MIN_MESSAGE_SIZE = 11

enum class MainFunctionCode(byte: Byte) {
    READ(0x03), WRITE(0x10);
}

enum class MinorFunctionCode(val byte: Byte) {
    RW(0x01),
    TEMP(0x02),
    ERROR(0X03),
    SUCCESS(0x04),
    OTHER_DATA(0x05),
    HARDWARE(0x06),
    SOFTWARE(0x07);
}

/**
 * 解析模块地址(序列号)
 * @param [maxLow] 地址最低字节
 * @param [low] 地址次高字节
 * @param [high] 地址中高字节
 * @param [maxHigh] 地址最高字节
 */
fun address(maxLow: Byte, low: Byte, high: Byte, maxHigh: Byte): UInt = restoreData(maxLow, low, high, maxHigh).toUInt()
/**
 * 解析温度(℃)
 * @param [low] 低
 * @param [high] 高
 */
fun temperature(low: Byte, high: Byte): Int = restoreData(low, high).toInt()

/**
 * 解析电压(mv)/ 或者湿度 RH
 * @param [low] 低
 * @param [high] 高
 */
fun voltageRH(low: Byte, high: Byte): Int = restoreData(low, high).toInt()

/**
 * 解析软件版本或者硬件版本
 * @param [data] 数据
 * @return [String]
 */
fun version(data: ByteArray): String = String(data.sliceArray(5 until data.size - 2))



fun restoreData(low: Byte, high: Byte): Short {
    val buffer = ByteBuffer.allocate(2).order(java.nio.ByteOrder.LITTLE_ENDIAN)
    buffer.put(low)
    buffer.put(high)
    buffer.flip()
    return buffer.getShort()
}

fun restoreData(byte1: Byte, byte2: Byte, byte3: Byte, byte4: Byte): Int {
    val buffer = ByteBuffer.allocate(4).order(java.nio.ByteOrder.LITTLE_ENDIAN)
    buffer.put(byte1)
    buffer.put(byte2)
    buffer.put(byte3)
    buffer.put(byte4)
    buffer.flip()
    return buffer.getInt()
}

fun restoreData(
    byte1: Byte,
    byte2: Byte,
    byte3: Byte,
    byte4: Byte,
    byte5: Byte,
    byte6: Byte,
    byte7: Byte,
    byte8: Byte
): Long {
    val buffer = ByteBuffer.allocate(8).order(java.nio.ByteOrder.LITTLE_ENDIAN)
    buffer.put(byte1)
    buffer.put(byte2)
    buffer.put(byte3)
    buffer.put(byte4)
    buffer.put(byte5)
    buffer.put(byte6)
    buffer.put(byte7)
    buffer.put(byte8)
    buffer.flip()
    return buffer.getLong()
}



