package com.websarva.wings.android.workmanagerjava;

import android.content.Context;
import android.util.Log;

import androidx.annotation.NonNull;
import androidx.work.Worker;
import androidx.work.WorkerParameters;

public class CountUpOnWifiWorker extends Worker {
	public CountUpOnWifiWorker(@NonNull Context context, @NonNull WorkerParameters workerParams) {
		super(context, workerParams);
	}

	@NonNull
	@Override
	public Result doWork() {
		for(int i = 1; i <= 25; i++) {
			Log.i("CountUpOnWifiWorker", "現在のカウント: " + i);
			try {
				Thread.sleep(1000);
			} catch (InterruptedException e) {
				return Result.failure();
			}
		}
		return Result.success();
	}
}
