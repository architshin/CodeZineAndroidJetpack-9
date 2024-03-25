package com.websarva.wings.android.workmanagerkotlin

import android.content.Context
import android.util.Log
import androidx.work.Worker
import androidx.work.WorkerParameters

class ThreeStatesWorker(context: Context, workerParams: WorkerParameters) : Worker(context, workerParams) {
	override fun doWork(): Result {
		var returnVal = Result.success()
		val failureCount = (1..20).random()
		val retryCount = (1..20).random()
		Log.i("ThreeStatesWorker", "失敗と判定する数値: ${failureCount}")
		Log.i("ThreeStatesWorker", "リトライと判定する数値: ${retryCount}")
		for(i in 1..25) {
			Log.i("ThreeStatesWorker", "現在のカウント: ${i}")
			if(i == failureCount) {
				returnVal = Result.failure()
				break
			}
			else if(i == retryCount) {
				returnVal = Result.retry()
				break
			}
			else {
			Thread.sleep(1000)
			}
		}
		return returnVal
	}
}
