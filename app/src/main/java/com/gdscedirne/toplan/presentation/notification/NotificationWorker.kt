package com.gdscedirne.toplan.presentation.notification

import android.content.Context
import androidx.hilt.work.HiltWorker
import androidx.work.CoroutineWorker
import androidx.work.Data
import androidx.work.WorkerParameters
import com.gdscedirne.toplan.R
import com.gdscedirne.toplan.common.Constants.notificationList
import dagger.assisted.Assisted
import dagger.assisted.AssistedInject

@HiltWorker
class NotificationWorker @AssistedInject constructor(
    @Assisted context: Context,
    @Assisted workerParams: WorkerParameters,
) : CoroutineWorker(context, workerParams) {

    override suspend fun doWork(): Result {
        val randomNumber = (notificationList.indices).random()
        try {
            createNotification(
                id = notificationList[randomNumber].id,
                title = notificationList[randomNumber].title,
                content = notificationList[randomNumber].content
            )
        } catch (e: Exception) {
            Result.failure()
        }
        val data = Data.Builder()
            .putString(
                applicationContext.getString(R.string.key),
                applicationContext.getString(R.string.value)
            )
            .build()

        return Result.success(data)
    }

    private fun createNotification(
        id: String,
        title: String,
        content: String,
    ) {
        with(NotificationMaker()) {
            sendOnDefaultChannel(
                context = applicationContext,
                notificationId = id,
                notificationTitle = title,
                notificationContent = content,
                notificationImage = R.drawable.toplan_icon)
        }
    }
}

data class NotificationItem(
    val id: String,
    val title: String,
    val content: String
)