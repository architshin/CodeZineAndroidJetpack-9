package com.websarva.wings.android.workmanagerjava;

import android.content.Context;
import android.util.Log;

import androidx.annotation.NonNull;
import androidx.work.Worker;
import androidx.work.WorkerParameters;

public class ThreeStatesWorker extends Worker {
	public ThreeStatesWorker(@NonNull Context context, @NonNull WorkerParameters workerParams) {
		super(context, workerParams);
	}

	@NonNull
	@Override
	public Result doWork() {
		Result returnVal = Result.success();
		long failureCount = Math.round(Math.random() * 20);
		long retryCount = Math.round(Math.random() * 20);
		Log.i("ThreeStatesWorker", "失敗と判定する数値: " + failureCount);
		Log.i("ThreeStatesWorker", "リトライと判定する数値: " + retryCount);
		for(int i = 1; i <= 10; i++) {
			Log.i("ThreeStatesWorker", "現在のカウント: " + i);
			if(i == failureCount) {
				returnVal = Result.failure();
				break;
			}
			else if(i == retryCount) {
				returnVal = Result.retry();
				break;
			}
			else {
				try {
					Thread.sleep(1000);
				} catch (InterruptedException e) {
					returnVal = Result.failure();
				}
			}
		}
		return returnVal;
	}
}
