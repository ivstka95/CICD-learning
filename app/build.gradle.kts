plugins {
    alias(libs.plugins.android.application)
    alias(libs.plugins.jetbrains.kotlin.android)
    alias(libs.plugins.compose.compiler)
    alias(libs.plugins.dependency.guard)
}

android {
    namespace = "ivan.karpiuk.cicdlearning"
    compileSdk = 35

    defaultConfig {
        applicationId = "ivan.karpiuk.cicdlearning"
        minSdk = 24
        targetSdk = 35
        versionCode = 1
        versionName = "1.0"

        testInstrumentationRunner = "androidx.test.runner.AndroidJUnitRunner"
        vectorDrawables {
            useSupportLibrary = true
        }
    }

    lint {
        // ./gradlew updateLintBaseline
        // to get only new issues on lint checks
        baseline = file("lint-baseline.xml")
//        warningsAsErrors = true
        enable.addAll(listOf("Interoperability"))
        ignoreTestSources = true
    }

    signingConfigs {

        create("release") {
            System.getenv("KEYSTORE_FILE")?.let {
                storeFile = File(rootDir, it)
            }
            storePassword = System.getenv("SIGNING_STORE_PASSWORD")
            keyAlias = System.getenv("SIGNING_KEY_ALIAS")
            keyPassword = System.getenv("SIGNING_KEY_PASSWORD")
        }
    }

    buildTypes {
        release {
            signingConfig = signingConfigs.getByName("release")
            isMinifyEnabled = false
            proguardFiles(getDefaultProguardFile("proguard-android-optimize.txt"), "proguard-rules.pro")
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
    packaging {
        resources {
            excludes += "/META-INF/{AL2.0,LGPL2.1}"
        }
    }
}

dependencies {

    implementation(libs.androidx.core.ktx)
    implementation(libs.androidx.lifecycle.runtime.ktx)
    implementation(libs.androidx.activity.compose)
    implementation(platform(libs.androidx.compose.bom))
    implementation(libs.androidx.ui)
    implementation(libs.androidx.ui.graphics)
    implementation(libs.androidx.ui.tooling.preview)
    implementation(libs.androidx.material3)
    testImplementation(libs.junit)
    androidTestImplementation(libs.androidx.junit)
    androidTestImplementation(libs.androidx.espresso.core)
    androidTestImplementation(platform(libs.androidx.compose.bom))
    androidTestImplementation(libs.androidx.ui.test.junit4)
    debugImplementation(libs.androidx.ui.tooling)
    debugImplementation(libs.androidx.ui.test.manifest)
}

dependencyGuard {
    configuration("releaseRuntimeClasspath") {
        // What is included in the list report
        artifacts = true // Defaults to true
        modules = false // Defaults to false

        // Tree Report
        tree = false // Defaults to false

        // Filter through dependencies and return true if allowed.  Build will fail if unallowed.
        allowedFilter = { dependencyName: String ->
            // Disallow dependencies with a name containing "junit"
            !dependencyName.contains("junit")
        }
        // Modify a dependency name or remove it (by returning null) from the baseline file
        baselineMap = { dependencyName: String ->
            dependencyName // Defaults to return itself
        }
    }
}