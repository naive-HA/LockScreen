plugins {
    alias(libs.plugins.android.application)
}

android {
    namespace = "acab.naiveha.lockscreen"
    compileSdk = 34

    defaultConfig {
        applicationId = "acab.naiveha.lockscreen"
        minSdk = 34
        versionCode = 2
        versionName = "2.0.0"

        testInstrumentationRunner = "androidx.test.runner.AndroidJUnitRunner"
    }

    buildTypes {
        release {
            isMinifyEnabled = true
            isShrinkResources = true
            proguardFiles(
                getDefaultProguardFile("proguard-android-optimize.txt"),
                "proguard-rules.pro"
            )
        }

    }
    compileOptions {
        sourceCompatibility = JavaVersion.VERSION_17
        targetCompatibility = JavaVersion.VERSION_17
    }

    androidResources {
        localeFilters.add("en")
    }
}

androidComponents {
    onVariants(selector().withBuildType("release")) { variant ->
        variant.outputs.first().let { output ->
            val outputImpl = output as com.android.build.api.variant.impl.VariantOutputImpl
            output.versionName.get().let {
                outputImpl.outputFileName.set("LockScreen-$it.apk")
            }
        }
    }
}

dependencies {

    implementation(libs.appcompat)
    implementation(libs.material)
    implementation(libs.activity)
    implementation(libs.constraintlayout)
    testImplementation(libs.junit)
    androidTestImplementation(libs.ext.junit)
    androidTestImplementation(libs.espresso.core)
}