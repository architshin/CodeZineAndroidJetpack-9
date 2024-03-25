package com.websarva.wings.android.workmanagerkotlin

import android.os.Bundle
import android.util.Log
import android.view.View
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.work.Constraints
import androidx.work.NetworkType
import androidx.work.OneTimeWorkRequest
import androidx.work.OneTimeWorkRequestBuilder
import androidx.work.WorkManager
import com.websarva.wings.android.workmanagerkotlin.databinding.ActivityMainBinding
import java.util.concurrent.TimeUnit

class MainActivity : AppCompatActivity() {
	private lateinit var _activityMainBinding: ActivityMainBinding

	override fun onCreate(savedInstanceState: Bundle?) {
		super.onCreate(savedInstanceState)
		enableEdgeToEdge()
		_activityMainBinding = ActivityMainBinding.inflate(layoutInflater)
		setContentView(_activityMainBinding.root)
		ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
			val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
			v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
			insets
		}
		_activityMainBinding.btStartSimple.setOnClickListener(StartButtonClickListener())
		_activityMainBinding.btStart3States.setOnClickListener(Start3StatesButtonClickListener())
		_activityMainBinding.btStartDelay.setOnClickListener(StartDelayButtonClickListener())
		_activityMainBinding.btStratWifi.setOnClickListener(StartWifiButtonClickListener())
	}

	private inner class StartButtonClickListener : View.OnClickListener {
		override fun onClick(view: View?) {
			val workRequest = OneTimeWorkRequest.from(CountUpWorker::class.java)
			val workManager = WorkManager.getInstance(this@MainActivity)
			workManager.enqueue(workRequest)
		}
	}

	private inner class Start3StatesButtonClickListener : View.OnClickListener {
		override fun onClick(view: View?) {
			val workRequest = OneTimeWorkRequest.from(ThreeStatesWorker::class.java)
			val workManager = WorkManager.getInstance(this@MainActivity)
			workManager.enqueue(workRequest)
		}
	}

	private inner class StartDelayButtonClickListener : View.OnClickListener {
		override fun onClick(view: View?) {
			val workRequestBuilder = OneTimeWorkRequestBuilder<CountUpWorker>()
			workRequestBuilder.addTag("DelayedWorker")
			workRequestBuilder.setInitialDelay(5, TimeUnit.SECONDS)
			val workRequest = workRequestBuilder.build()
			val workManager = WorkManager.getInstance(this@MainActivity)
			Log.i("StartDelayButtonClickListener", "5秒遅れのCountUpWorkerをキューに登録。")
			workManager.enqueue(workRequest)
		}
	}

	private inner class StartWifiButtonClickListener : View.OnClickListener {
		override fun onClick(view: View?) {
			val constraintsBuilder = Constraints.Builder()
			constraintsBuilder.setRequiredNetworkType(NetworkType.UNMETERED)
			val constraints = constraintsBuilder.build()
			val workRequestBuilder = OneTimeWorkRequestBuilder<CountUpWorker>()
			workRequestBuilder.setConstraints(constraints)
			val workRequest = workRequestBuilder.build()
			val workManager = WorkManager.getInstance(this@MainActivity)
			workManager.enqueue(workRequest)
		}
	}
}
