package com.gdscedirne.toplan

import android.app.Application
import androidx.hilt.work.HiltWorkerFactory
import androidx.work.Configuration
import androidx.work.Constraints
import androidx.work.Data
import androidx.work.NetworkType
import androidx.work.PeriodicWorkRequest
import androidx.work.WorkManager
import com.gdscedirne.toplan.presentation.notification.NotificationWorker
import dagger.hilt.android.HiltAndroidApp
import java.util.concurrent.TimeUnit
import javax.inject.Inject

@HiltAndroidApp
class HiltAndroidApp : Application(), Configuration.Provider{

    @Inject
    lateinit var workerFactory: HiltWorkerFactory

    override fun onCreate(){
        super.onCreate()
        startWorker()
    }

    override val workManagerConfiguration: Configuration
        get() = Configuration.Builder()
            .setWorkerFactory(workerFactory)
            .build()
}

fun Application.startWorker(){

    val workManager = WorkManager.getInstance(applicationContext)

    val constraints = Constraints.Builder()
        .setRequiredNetworkType(NetworkType.CONNECTED)
        .build()

    val data = Data.Builder()

    val dailyWorkRequest = PeriodicWorkRequest.Builder(
        NotificationWorker::class.java,
        1,
        TimeUnit.DAYS
    )
        .setInitialDelay(5, TimeUnit.HOURS)
        .setConstraints(constraints)
        .setInputData(data.build())
        .build()

    val periodicWorkRequest = PeriodicWorkRequest.Builder(
        NotificationWorker::class.java,
        7,
        TimeUnit.HOURS
    )
        .setInitialDelay(2, TimeUnit.HOURS)
        .setConstraints(constraints)
        .setInputData(data.build())
        .build()

    // Requesti sıraya alma
    workManager.enqueue(dailyWorkRequest)
    workManager.enqueue(periodicWorkRequest)
}