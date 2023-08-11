package com.rns.busscheduleapplication.db

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase

@Database(entities = [Schedule::class], version = 1)
abstract class AppDatabase : RoomDatabase() {
    abstract fun scheduleDao(): ScheduleDao

    companion object{
        @Volatile
        private var instance : AppDatabase? =null

        fun getInstance(context: Context) : AppDatabase{
            return instance?: synchronized(this){
                buildDatabase(context).also {
                    instance = it
                }
            }
        }

        private fun buildDatabase(context: Context) : AppDatabase{
            return Room.databaseBuilder(
                context,
                AppDatabase::class.java,
                "DB_Bus")
                .createFromAsset("database/bus_schedule.db")
                .build()
        }
    }
}