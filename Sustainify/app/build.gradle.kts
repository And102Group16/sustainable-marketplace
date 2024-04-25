plugins {
    id("com.android.application")
    id("org.jetbrains.kotlin.android")
    id("com.google.gms.google-services")
}

android {
    namespace = "com.example.sustainify"
    compileSdk = 34

    defaultConfig {
        applicationId = "com.example.sustainify"
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
    kotlinOptions {
        jvmTarget = "1.8"
    }

    buildFeatures{
        viewBinding = true
    }
}

dependencies {

    implementation("androidx.core:core-ktx:1.13.0")
    implementation("androidx.appcompat:appcompat:1.6.1")
    implementation("com.google.android.material:material:1.11.0")
    implementation("androidx.constraintlayout:constraintlayout:2.1.4")
    testImplementation("junit:junit:4.13.2")
    androidTestImplementation("androidx.test.ext:junit:1.1.5")
    androidTestImplementation("androidx.test.espresso:espresso-core:3.5.1")

    implementation("com.google.android.material:material:1.11.0")
    implementation(platform("com.google.firebase:firebase-bom:32.8.1"))
    // When using bom(bill of materials), do not specify version the firebase libraries
    implementation("com.google.firebase:firebase-auth")
    implementation("com.google.firebase:firebase-storage")
    implementation("com.google.firebase:firebase-database")
    implementation("com.google.firebase:firebase-firestore")


    implementation("org.jetbrains.kotlinx:kotlinx-coroutines-android:1.3.9")

    implementation("com.github.bumptech.glide:glide:4.16.0")
    implementation(fileTree(mapOf("dir" to "libs", "include" to listOf("*.jar"))))
    implementation("com.google.android.gms:play-services-maps:18.2.0")
    androidTestImplementation("androidx.test:runner:1.5.2")

    implementation("com.google.android.gms:play-services-location:16.0.0")
    implementation("com.google.android.libraries.places:places:3.4.0")
}