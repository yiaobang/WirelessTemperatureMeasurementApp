package com.y.wirelesstemperaturemeasurement

//fun initApp(context: Context) {
//    Config.initialize(context)
//    //DATA_BASE = initDataBase(context)
//    PARTS_DAO = database.partsDao()
//    DATE_DAO = database.dataDao()
//    EVENT_DAO = database.eventDao()
//    JOINT_DAO = database.jointDao()
//    //删除30天前的数据
//    DATE_DAO.deleteOldData()
//    EVENT_DAO.deleteOldEvent()
//    timeSumInit()
//    PARTS_DAO.insert(
//        Parts(0, "空调", "1911036766", 1911036766, 0),
//        Parts(0, "空调", "1911036771", 1911036771, 0),
//        Parts(0, "空调", "1911036777", 1911036777, 0),
//        Parts(0, "空调", "1911036782", 1911036782, 0),
//        Parts(0, "空调", "1911036826", 1911036826, 0),
//        Parts(0, "空调", "2005075887", 2005075887, 0),
//        Parts(0, "空调", "2005075898", 2005075898, 0),
//
//        Parts(0, "自由移动", "1812400098", 1812400098, 1),
//        Parts(0, "自由移动", "2007271002", 2007271002, 1),
//        Parts(0, "自由移动", "2007271006", 2007271006, 1),
//        Parts(0, "自由移动", "2007271009", 2007271009, 1),
//        Parts(0, "自由移动", "2007271010", 2007271010, 1)
//    )
//
//    StateViewModel.updateParts()
//    PARTS_DAO.selectAllParts().forEach { Log.d(TAG, "initApp: $it") }
//    connection()
//    Thread {
//        while (true) {
//            StateViewModel.updateData()
//            Log.e(TAG, "Home: 更新数据")
//            //30S一次
//            Thread.sleep(30000)
//        }
//    }.start()
//}
