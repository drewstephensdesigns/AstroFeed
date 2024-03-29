plugins {
    id("com.android.application")
    id("org.jetbrains.kotlin.android")
    id("com.google.devtools.ksp")
    id ("androidx.navigation.safeargs.kotlin")
    id("com.google.android.gms.oss-licenses-plugin")
}

android {
    namespace = "com.github.drewstephensdesigns.astrofeed"
    compileSdk = 34

    defaultConfig {
        applicationId = "com.github.drewstephensdesigns.astrofeed"
        minSdk = 26
        targetSdk = 34
        versionCode = 1
        versionName = "1.0"

        testInstrumentationRunner = "androidx.test.runner.AndroidJUnitRunner"
    }

    applicationVariants.all { resValue("string", "versionName",versionName)}

    buildTypes {
        release {
            multiDexEnabled = true
            isMinifyEnabled = false
            proguardFiles(
                getDefaultProguardFile("proguard-android-optimize.txt"),
                "proguard-rules.pro"
            )
        }
        debug {
            isDebuggable = true
        }
    }
    compileOptions {
        sourceCompatibility = JavaVersion.VERSION_1_8
        targetCompatibility = JavaVersion.VERSION_1_8
    }
    kotlinOptions {
        jvmTarget = "1.8"
    }
    buildFeatures {
        viewBinding = true
    }
}

dependencies {

    // Core
    implementation("androidx.core:core-ktx:1.12.0")
    implementation("androidx.appcompat:appcompat:1.6.1")
    implementation("com.google.android.material:material:1.11.0")
    implementation("androidx.constraintlayout:constraintlayout:2.1.4")
    implementation("androidx.lifecycle:lifecycle-livedata-ktx:2.7.0")
    implementation("androidx.lifecycle:lifecycle-viewmodel-ktx:2.7.0")
    implementation("androidx.navigation:navigation-fragment-ktx:2.7.6")
    implementation("androidx.navigation:navigation-ui-ktx:2.7.6")

    // Retrofit
    implementation ("com.squareup.retrofit2:retrofit:2.9.0")
    implementation ("com.squareup.retrofit2:converter-gson:2.9.0")
    implementation ("com.squareup.picasso:picasso:2.8")

    // Room
    implementation ("androidx.room:room-runtime:2.6.1")
    implementation ("androidx.room:room-ktx:2.6.1")
    implementation("androidx.preference:preference:1.2.1")
    ksp ("androidx.room:room-compiler:2.6.1")

    // Toasty
    implementation ("com.github.GrenderG:Toasty:1.5.2")

    // Sheets - https://github.com/maxkeppeler/sheets
    implementation ("com.maxkeppeler.sheets:core:2.3.1")
    implementation ("com.maxkeppeler.sheets:info:2.3.1")
    implementation ("com.maxkeppeler.sheets:option:2.3.1")
    implementation ("com.maxkeppeler.sheets:input:2.3.1")
    implementation ("com.maxkeppeler.sheets:lottie:2.3.1")

    // Lottie Files
    implementation ("com.airbnb.android:lottie:6.1.0")

    implementation ("androidx.paging:paging-runtime-ktx:3.2.1")

    // Tests
    testImplementation("junit:junit:4.13.2")
    androidTestImplementation("androidx.test.ext:junit:1.1.5")
    androidTestImplementation("androidx.test.espresso:espresso-core:3.5.1")

    implementation("com.google.android.gms:play-services-oss-licenses:17.0.1")
}