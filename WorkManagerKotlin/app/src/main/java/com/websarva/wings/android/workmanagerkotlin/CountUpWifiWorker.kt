package com.websarva.wings.android.workmanagerkotlin

import android.content.Context
import android.util.Log
import androidx.work.Worker
import androidx.work.WorkerParameters

class CountUpWifiWorker(context: Context, workerParams: WorkerParameters) : Worker(context, workerParams) {
	override fun doWork(): Result {
		for(i in 1..25) {
			Log.i("CountUpWifiWorker", "現在のカウント: ${i}")
			Thread.sleep(1000)
		}
		return Result.success()
	}
}
