package com.dicoding.courseschedule.util

import java.util.concurrent.Executors

const val NOTIFICATION_CHANNEL_NAME = "Course Channel"
const val NOTIFICATION_CHANNEL_ID = "notify-schedule"
const val NOTIFICATION_ID = 32
const val TYPE_REPEATING = "dailyReminder"


private val SINGLE_EXECUTOR = Executors.newSingleThreadExecutor()

fun executeThread(f: () -> Unit) {
    SINGLE_EXECUTOR.execute(f)
}
