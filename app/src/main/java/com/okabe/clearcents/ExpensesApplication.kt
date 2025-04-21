package com.okabe.clearcents

import android.app.Application
import com.okabe.clearcents.di.appModule
import org.koin.android.ext.koin.androidContext
import org.koin.core.context.startKoin

class ExpensesApplication : Application() {
    override fun onCreate() {
        super.onCreate()
        startKoin {
            androidContext(this@ExpensesApplication)
            modules(appModule)
        }
    }
}