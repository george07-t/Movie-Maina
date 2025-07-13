plugins {
    alias(libs.plugins.android.application)
}

android {
    namespace = "com.example.moviemania"
    compileSdk = 34

    defaultConfig {
        applicationId = "com.example.moviemania"
        minSdk = 26
        targetSdk = 34
        versionCode = 1
        versionName = "1.0"

        testInstrumentationRunner = "androidx.test.runner.AndroidJUnitRunner"
    }


    buildFeatures {
        viewBinding = true
        buildConfig = true
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
        isCoreLibraryDesugaringEnabled = true
        sourceCompatibility = JavaVersion.VERSION_11
        targetCompatibility = JavaVersion.VERSION_11
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
    coreLibraryDesugaring(libs.desugar.jdk.libs)
    implementation(libs.core)
    implementation(libs.html)
    implementation(libs.retrofit)
    implementation(libs.converter.gson)

    implementation(libs.github.glide)
    //noinspection UseTomlInstead
    annotationProcessor("com.github.bumptech.glide:compiler:4.15.1")

    implementation(libs.gson)
}
