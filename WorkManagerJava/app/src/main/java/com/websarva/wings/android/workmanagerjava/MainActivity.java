package com.websarva.wings.android.workmanagerjava;

import android.os.Bundle;
import android.util.Log;
import android.view.View;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;
import androidx.work.Constraints;
import androidx.work.NetworkType;
import androidx.work.OneTimeWorkRequest;
import androidx.work.WorkManager;
import androidx.work.WorkRequest;

import com.websarva.wings.android.workmanagerjava.databinding.ActivityMainBinding;

import java.util.concurrent.TimeUnit;

public class MainActivity extends AppCompatActivity {
	private ActivityMainBinding _activityMainBinding;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		EdgeToEdge.enable(this);

		_activityMainBinding = ActivityMainBinding.inflate(getLayoutInflater());
		View contentView = _activityMainBinding.getRoot();
		setContentView(contentView);
		ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
			Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
			v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
			return insets;
		});

		_activityMainBinding.btStartSimple.setOnClickListener(new StartSimpleButtonClickListener());
		_activityMainBinding.btStart3States.setOnClickListener(new Start3StatesButtonClickListener());
		_activityMainBinding.btStartDelay.setOnClickListener(new StartDelayButtonClickListener());
		_activityMainBinding.btStratWifi.setOnClickListener(new StartWifiButtonClickListener());
	}

	private class StartSimpleButtonClickListener implements View.OnClickListener {
		@Override
		public void onClick(View view) {
			WorkRequest workRequest = OneTimeWorkRequest.from(CountUpWorker.class);
			WorkManager workManager = WorkManager.getInstance(MainActivity.this);
			workManager.enqueue(workRequest);
		}
	}

	private class Start3StatesButtonClickListener implements View.OnClickListener {
		@Override
		public void onClick(View view) {
			WorkRequest workRequest = OneTimeWorkRequest.from(ThreeStatesWorker.class);
			WorkManager workManager = WorkManager.getInstance(MainActivity.this);
			workManager.enqueue(workRequest);
		}
	}

	private class StartDelayButtonClickListener implements View.OnClickListener {
		@Override
		public void onClick(View view) {
			OneTimeWorkRequest.Builder workRequestBuilder = new OneTimeWorkRequest.Builder(CountUpWorker.class);
			workRequestBuilder.addTag("DelayedWorker");
			workRequestBuilder.setInitialDelay(5, TimeUnit.SECONDS);
			WorkRequest workRequest = workRequestBuilder.build();
//			WorkRequest workRequest = new OneTimeWorkRequest.Builder(CountUpWorker.class).addTag("DelayedWorker").setInitialDelay(5, TimeUnit.SECONDS).build();
			WorkManager workManager = WorkManager.getInstance(MainActivity.this);
			Log.i("StartDelayButtonClickListener", "5秒遅れのCountUpWorkerをキューに登録。");
			workManager.enqueue(workRequest);
		}
	}

	private class StartWifiButtonClickListener implements View.OnClickListener {
		@Override
		public void onClick(View view) {
			Constraints.Builder constraintsBuilder = new Constraints.Builder();
			constraintsBuilder.setRequiredNetworkType(NetworkType.UNMETERED);
			Constraints constraints = constraintsBuilder.build();
			OneTimeWorkRequest.Builder workRequestBuilder = new OneTimeWorkRequest.Builder(CountUpOnWifiWorker.class);
			workRequestBuilder.setConstraints(constraints);
			WorkRequest workRequest = workRequestBuilder.build();
			WorkManager workManager = WorkManager.getInstance(MainActivity.this);
			workManager.enqueue(workRequest);
		}
	}
}
