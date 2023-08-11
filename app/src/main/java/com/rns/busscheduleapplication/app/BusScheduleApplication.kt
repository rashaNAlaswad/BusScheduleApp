package com.rns.busscheduleapplication.app

import android.app.Application
import com.rns.busscheduleapplication.db.AppDatabase

class BusScheduleApplication : Application() {
    val database: AppDatabase by lazy { AppDatabase.getInstance(this) }
}