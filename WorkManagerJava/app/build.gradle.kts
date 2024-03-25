plugins {
	alias(libs.plugins.androidApplication)
}

android {
	namespace = "com.websarva.wings.android.workmanagerjava"
	compileSdk = 34

	defaultConfig {
		applicationId = "com.websarva.wings.android.workmanagerjava"
		minSdk = 24
		targetSdk = 34
		versionCode = 1
		versionName = "1.0"

		testInstrumentationRunner = "androidx.test.runner.AndroidJUnitRunner"
	}

	buildTypes {
		release {
			isMinifyEnabled = false
			proguardFiles(
				getDefaultProguardFile("proguard-android-optimize.txt"),
				"proguard-rules.pro"
			)
		}
	}
	compileOptions {
		sourceCompatibility = JavaVersion.VERSION_1_8
		targetCompatibility = JavaVersion.VERSION_1_8
	}
	buildFeatures {
		viewBinding = true
	}
}

dependencies {
	val work_version = "2.9.0"
	implementation("androidx.work:work-runtime:$work_version")

	implementation(libs.appcompat)
	implementation(libs.material)
	implementation(libs.activity)
	implementation(libs.constraintlayout)
	testImplementation(libs.junit)
	androidTestImplementation(libs.ext.junit)
	androidTestImplementation(libs.espresso.core)
}