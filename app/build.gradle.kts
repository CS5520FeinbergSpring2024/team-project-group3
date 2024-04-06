plugins {
    alias(libs.plugins.androidApplication)
    id("com.google.gms.google-services")
}



android {
    namespace = "com.example.final_project"
    compileSdk = 34

    defaultConfig {
        applicationId = "com.example.final_project"
        minSdk = 26
        targetSdk = 34
        versionCode = 1
        versionName = "1.0"

        testInstrumentationRunner = "androidx.test.runner.AndroidJUnitRunner"
    }

    buildTypes {
        release {
            isMinifyEnabled = false
            proguardFiles(getDefaultProguardFile("proguard-android-optimize.txt"), "proguard-rules.pro")
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
    implementation(libs.firebase.database)
    implementation(libs.firebase.storage)
    implementation(libs.firebase.messaging)
    implementation(libs.recyclerview)
    testImplementation(libs.junit)
    androidTestImplementation(libs.ext.junit)
    androidTestImplementation(libs.espresso.core)
    implementation("com.google.firebase:firebase-auth:22.3.1")
    implementation("com.google.firebase:firebase-firestore:24.11.0")
    implementation("com.google.android.material:material:1.11.0")
    implementation("com.firebase:geofire-android-common:3.1.0")
    implementation("com.google.android.gms:play-services-location:21.2.0")
    implementation("com.github.imperiumlabs:GeoFirestore-Android:v1.1.1")
    implementation("com.firebase:geofire-android:3.0.0")

    // implemented glide.
    implementation ("com.github.bumptech.glide:glide:4.12.0")
    annotationProcessor ("com.github.bumptech.glide:compiler:4.12.0")
}