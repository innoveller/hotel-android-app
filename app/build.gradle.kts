plugins {
    alias(libs.plugins.android.application)
}

android {
    namespace = "com.innoveller.hotelbookingandroidapp"
    compileSdk = 34

    defaultConfig {
        applicationId = "com.innoveller.hotelbookingandroidapp"
        minSdk = 24
        targetSdk = 34
        versionCode = 1
        versionName = "1.0"

        testInstrumentationRunner = "androidx.test.runner.AndroidJUnitRunner"
    }
    signingConfigs {
        create("release") {
            storeFile = File("storeFile")  // Use `File` instead of `file` in Kotlin DSL
            storePassword = "storePassword"         // Add `=` for assignments
            keyAlias = "keyAlias"
            keyPassword = "keyPassword"
        }
    }

    buildTypes {
        getByName("release") {
            signingConfig = signingConfigs.getByName("release")
            isMinifyEnabled = false
            proguardFiles(
                getDefaultProguardFile("proguard-android-optimize.txt"),
                "proguard-rules.pro"
            )
        }

        //Uncomment if needed
        getByName("debug") {
            isMinifyEnabled = false
            proguardFiles(
                getDefaultProguardFile("proguard-android.txt"),
                "proguard-rules.pro"
            )
        }
    }

    applicationVariants.all {
        outputs.all {
            if (buildType.name == "release") {
                val newApkName = "My App Name.apk"
                (this as com.android.build.gradle.internal.api.BaseVariantOutputImpl).outputFileName = newApkName
            }
        }
    }

    compileOptions {
        sourceCompatibility = JavaVersion.VERSION_1_8
        targetCompatibility = JavaVersion.VERSION_1_8
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