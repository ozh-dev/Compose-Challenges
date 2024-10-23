plugins {
    alias(libs.plugins.android.application)
    alias(libs.plugins.kotlin.android)
    alias(libs.plugins.kotlin.compose)
}

android {
    namespace = "ru.ozh.compose.challenges"
    compileSdk = 35

    defaultConfig {
        applicationId = "ru.ozh.compose.challenges"
        minSdk = 22
        targetSdk = 35
        versionCode = 1
        versionName = "1.0"

        vectorDrawables {
            useSupportLibrary = true
        }
    }

    buildTypes {
        debug {
            isMinifyEnabled  = false
            //proguardFiles getDefaultProguardFile ("proguard-android-optimize.txt"), "proguard-rules.pro"
        }
    }
    compileOptions {
        sourceCompatibility = JavaVersion.VERSION_17
        targetCompatibility = JavaVersion.VERSION_17
    }
    kotlinOptions {
        jvmTarget = "17"
    }
    buildFeatures {
        compose = true
    }
}

composeCompiler {
    reportsDestination = layout.buildDirectory.dir("compose_compiler")
    metricsDestination = layout.buildDirectory.dir("compose_compiler")
    stabilityConfigurationFile = rootProject.layout.projectDirectory.file("stability_config.conf")
}

dependencies {

    implementation (libs.androidx.material3)
    implementation (libs.androidx.material3.windows.size)

    implementation (libs.androidx.core.ktx)

    implementation(platform(libs.androidx.compose.bom))
    implementation (libs.androidx.ui)
    implementation (libs.androidx.ui.tooling.preview)

    implementation (libs.androidx.lifecycle.runtime.ktx)
    implementation (libs.androidx.activity.compose)
}