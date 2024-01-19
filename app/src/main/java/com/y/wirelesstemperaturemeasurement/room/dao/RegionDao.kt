package com.y.wirelesstemperaturemeasurement.room.dao

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.Query
import androidx.room.Update
import com.y.wirelesstemperaturemeasurement.room.entity.Region

@Dao
interface RegionDao {
    @Insert
    fun addRegion(vararg region: Region)
    @Update
     fun updateRegion(vararg region: Region)
    @Delete
     fun deleteRegion(vararg region: Region)
    @Query("SELECT * FROM region")
     fun selectRegions(): List<Region>
}