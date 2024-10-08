package com.rns.busscheduleapplication.db

import androidx.room.Dao
import androidx.room.Query
import kotlinx.coroutines.flow.Flow

@Dao
interface ScheduleDao {

    @Query("SELECT * FROM Schedule ORDER BY arrival_time ASC")
    fun getAllSchedule(): Flow<List<Schedule>>

    @Query("SELECT * FROM Schedule WHERE stop_name = :stopName ORDER BY arrival_time ASC")
    fun getByStopName(stopName: String): Flow<List<Schedule>>
}