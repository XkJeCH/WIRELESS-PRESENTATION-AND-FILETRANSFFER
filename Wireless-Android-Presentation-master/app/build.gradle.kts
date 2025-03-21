plugins {
    id("org.jetbrains.kotlin.android")
    id("com.google.gms.google-services")
    id("com.android.application")
}

android {
    namespace = "com.example.wirelessproject"
    compileSdk = 34

    defaultConfig {
        applicationId = "com.example.wirelessproject"
        minSdk = 27
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
    }
    composeOptions {
        kotlinCompilerExtensionVersion = "1.4.3"
    }
    packaging {
        resources {
            excludes += "/META-INF/{AL2.0,LGPL2.1}"
        }
    }
}

dependencies {

    implementation("androidx.core:core-ktx:1.12.0")
    implementation("androidx.lifecycle:lifecycle-runtime-ktx:2.7.0")
    implementation("androidx.activity:activity-compose:1.8.2")
    implementation(platform("androidx.compose:compose-bom:2024.02.01"))
    implementation("androidx.compose.ui:ui")
    implementation("androidx.compose.ui:ui-graphics")
    implementation("androidx.compose.ui:ui-tooling-preview")
    implementation("androidx.compose.material3:material3")
    implementation("com.google.firebase:firebase-database:20.3.1")
    implementation("com.google.firebase:firebase-database-ktx:20.3.1")
    implementation("androidx.navigation:navigation-compose:2.7.7")
    implementation("androidx.wear.compose:compose-material:1.3.0")
    testImplementation("junit:junit:4.13.2")
    androidTestImplementation("androidx.test.ext:junit:1.1.5")
    androidTestImplementation("androidx.test.espresso:espresso-core:3.5.1")
    androidTestImplementation(platform("androidx.compose:compose-bom:2024.02.01"))
    androidTestImplementation("androidx.compose.ui:ui-test-junit4")
    debugImplementation("androidx.compose.ui:ui-tooling")
    debugImplementation("androidx.compose.ui:ui-test-manifest")

    implementation("org.jetbrains.kotlin:kotlin-stdlib:1.9.22")
    implementation("androidx.appcompat:appcompat:1.6.1")
    implementation("androidx.constraintlayout:constraintlayout:2.1.4")

    implementation(platform("com.google.firebase:firebase-bom:32.7.2"))
    implementation("com.google.firebase:firebase-analytics")

    implementation ("com.google.firebase:firebase-auth")
    implementation ("com.google.firebase:firebase-firestore")

    //firebase
    implementation("com.google.firebase:firebase-database:20.3.1")
    implementation("com.google.firebase:firebase-storage:20.3.0")
    //navigateion component
    implementation("androidx.navigation:navigation-fragment-ktx:2.7.7")
    implementation("androidx.navigation:navigation-ui-ktx:2.7.7")
    implementation("io.coil-kt:coil-compose:2.6.0")
    implementation ("androidx.navigation:navigation-compose:2.7.7")
    implementation("androidx.lifecycle:lifecycle-runtime-compose:2.7.0")
    implementation ("com.squareup.retrofit2:retrofit:2.9.0")
    implementation ("com.squareup.retrofit2:converter-gson:2.9.0")
    implementation ("com.google.code.gson:gson:2.10.1")
    implementation ("androidx.activity:activity-compose:1.8.2")
    implementation ("androidx.compose.foundation:foundation:1.6.2")
    implementation ("androidx.compose.material3:material3:1.2.0")


}