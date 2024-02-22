plugins {
    id("com.android.application")
    id("org.jetbrains.kotlin.android")
    kotlin("kapt")
    id("com.google.dagger.hilt.android")
    id("com.google.android.libraries.mapsplatform.secrets-gradle-plugin")
    id("com.google.gms.google-services")
    id("com.google.firebase.crashlytics")
}

android {
    namespace = "com.gdscedirne.toplan"
    compileSdk = 34

    defaultConfig {
        applicationId = "com.gdscedirne.toplan"
        minSdk = 24
        targetSdk = 34
        versionCode = 1
        versionName = "1.0"

        testInstrumentationRunner = "androidx.test.runner.AndroidJUnitRunner"
        vectorDrawables {
            useSupportLibrary = true
        }
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
    buildFeatures {
        compose = true
        viewBinding = true
        buildConfig = true
    }
    composeOptions {
        kotlinCompilerExtensionVersion = "1.5.1"
    }
    packaging {
        resources {
            excludes += "/META-INF/{AL2.0,LGPL2.1}"
        }
    }
}

secrets {
    propertiesFileName = "secrets.properties"

    defaultPropertiesFileName = "local.defaults.properties"

    ignoreList.add("keyToIgnore")
    ignoreList.add("sdk.*")
}

dependencies {

    val coreKtxVersion = "1.12.0"
    val composeVersion = "1.6.2" // Güncel sürümü kontrol edin
    val composeActivityVersion = "1.8.2" // Güncel sürümü kontrol edin
    val composeMaterial3Version = "1.2.0" // Güncel sürümü kontrol edin
    val googleMapsVersion = "18.2.0"
    val materialVersion = "1.11.0"
    val navigationVersion = "2.7.7"
    val junitVersion = "4.13.2"
    val espressoVersion = "3.5.1"
    val accompanistPermissionsVersion = "0.31.3-beta"
    val daggerHiltVersion = "2.48"
    val hiltNavigationComposeVersion = "1.1.0"
    val firebaseAuthVersion = "22.3.1"
    val firebaseFirestoreVersion = "24.10.2"
    val firebaseCrashlyticsVersion = "18.6.2"
    val firebaseAnalyticsVersion = "21.5.1"
    val firebaseStorageVersion = "20.3.0"
    val accompanistSystemUiControllerVersion = "0.32.0"
    val workManagerVersion = "2.9.0"
    val coilComposeVersion = "2.5.0"
    val generativeAiVersion = "0.2.0"
    val lifecycle = "2.7.0"
    val mapsCompose = "4.3.0"
    val playServiceLocation = "21.1.0"
    val hiltWorker = "1.1.0"
    val work = "2.9.0"
    val junit = "1.1.5"
    val composeMaterial = "1.6.1"

    // KTX
    implementation("androidx.core:core-ktx:$coreKtxVersion")
    implementation("androidx.lifecycle:lifecycle-runtime-ktx:$lifecycle")
    implementation("androidx.activity:activity-compose:$composeActivityVersion")

    // Compose
    implementation("androidx.compose.ui:ui:$composeVersion")
    implementation("androidx.compose.ui:ui-graphics:$composeVersion")
    implementation("androidx.compose.ui:ui-tooling-preview:$composeVersion")
    implementation("androidx.compose.material3:material3:$composeMaterial3Version")

    // Google Maps
    implementation("com.google.android.gms:play-services-maps:$googleMapsVersion")
    implementation("com.google.maps.android:maps-compose:$mapsCompose")
    implementation("com.google.android.gms:play-services-location:$playServiceLocation")

    // Material
    implementation("androidx.compose.material3:material3")
    implementation("com.google.android.material:material:$materialVersion")
    implementation("androidx.compose.material:material:$composeMaterial")

    // Accompanist
    implementation("com.google.accompanist:accompanist-permissions:$accompanistPermissionsVersion")
    implementation("com.google.accompanist:accompanist-systemuicontroller:$accompanistSystemUiControllerVersion")

    // Dependency Injection
    implementation("com.google.dagger:hilt-android:$daggerHiltVersion")
    kapt("com.google.dagger:hilt-android-compiler:$daggerHiltVersion")
    implementation("androidx.hilt:hilt-work:$hiltWorker")
    kapt("androidx.hilt:hilt-compiler:$hiltWorker")
    implementation("androidx.work:work-runtime-ktx:$work")
    implementation("androidx.hilt:hilt-navigation-compose:$hiltNavigationComposeVersion")

    // Navigation
    implementation("androidx.navigation:navigation-compose:$navigationVersion")

    // Lifecycle
    implementation("androidx.lifecycle:lifecycle-viewmodel-compose:$lifecycle")
    implementation("androidx.lifecycle:lifecycle-viewmodel-ktx:$lifecycle")
    implementation("androidx.lifecycle:lifecycle-runtime-compose:$lifecycle")

    // Firebase
    implementation("com.google.firebase:firebase-auth:$firebaseAuthVersion")
    implementation("com.google.firebase:firebase-firestore:$firebaseFirestoreVersion")
    implementation("com.google.firebase:firebase-crashlytics:$firebaseCrashlyticsVersion")
    implementation("com.google.firebase:firebase-analytics:$firebaseAnalyticsVersion")
    implementation("com.google.firebase:firebase-storage:$firebaseStorageVersion")

    // Coil
    implementation("io.coil-kt:coil-compose:$coilComposeVersion")

    // Gemini
    implementation("com.google.ai.client.generativeai:generativeai:$generativeAiVersion")

    // Work Manager
    implementation("androidx.work:work-runtime:$workManagerVersion")

    // Test
    testImplementation("junit:junit:$junitVersion")
    androidTestImplementation("androidx.test.ext:junit:$junit")
    androidTestImplementation("androidx.test.espresso:espresso-core:$espressoVersion")
    androidTestImplementation("androidx.compose.ui:ui-test-junit4:$composeVersion")
    debugImplementation("androidx.compose.ui:ui-tooling:$composeVersion")
    debugImplementation("androidx.compose.ui:ui-test-manifest:$composeVersion")
}


secrets {
    defaultPropertiesFileName = "local.defaults.properties"
}