import java.text.SimpleDateFormat
import java.util.Date
import java.util.Locale

plugins {
    alias(libs.plugins.androidApplication)
    alias(libs.plugins.jetbrainsKotlinAndroid)
    id("com.google.devtools.ksp")
}
val appName = "WirelessTemperatureMeasurement"
ksp {
    arg("room.schemaLocation", "$projectDir/schemas")
    arg("room.incremental", "true")
}
android {
    namespace = "com.y.wirelesstemperaturemeasurement"
    compileSdk = 34

    defaultConfig {
        applicationId = "com.y.wirelesstemperaturemeasurement"
        minSdk = 23
        targetSdk = 34
        versionCode = 1
        versionName = "1.0.0"

        testInstrumentationRunner = "androidx.test.runner.AndroidJUnitRunner"
        vectorDrawables {
            useSupportLibrary = true
        }

    }
    buildTypes {
        release {
            //代码压缩
            isMinifyEnabled = true
            //移除未引用资源
            isShrinkResources = true
            //代码混淆
            proguardFiles(
                getDefaultProguardFile("proguard-android-optimize.txt"),
                "proguard-rules.pro"
            )
        }
        debug {
            isMinifyEnabled = true
            isShrinkResources = true
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
        kotlinCompilerExtensionVersion = "1.5.8"
    }
    packaging {
        resources {
            excludes += "/META-INF/{AL2.0,LGPL2.1}"
        }
    }
    //JNI
    sourceSets {
        getByName("main").jniLibs.srcDirs("jni")
    }
    signingConfigs {
        // 创建签名配置
        create("signing") {
            storeFile = file(projectDir.parent + "/$appName.jks")
            storePassword = "123456"
            keyAlias = "key"
            keyPassword = "123456"
        }
    }
    //自定义编译打包后的名称 (APP名称-版本号-编译类型-Android版本)
    applicationVariants.all {
        outputs.all {
            (this as com.android.build.gradle.internal.api.BaseVariantOutputImpl).outputFileName =
                "$appName-${buildType.name}-${defaultConfig.versionName}.APK"
        }
    }
}
dependencies {

    //串口
    implementation(libs.jSerialComm)
    //Modbus
    implementation(libs.j2mod)
    //MQTT
    implementation(libs.org.eclipse.paho.mqttv5.client)

    //SQLite 数据库
    implementation(libs.androidx.room.runtime)
    annotationProcessor(libs.androidx.room.room.compiler)
    ksp(libs.androidx.room.room.compiler)
    implementation(libs.androidx.room.ktx)
    implementation(libs.androidx.room.paging)
    testImplementation(libs.androidx.room.testing)

    //导航
    implementation(libs.androidx.navigation.compose)
    //slf4j
    implementation(libs.slf4j.api)

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
//编译的时间
fun releaseTime(): String = SimpleDateFormat("yyyy-MM-dd", Locale.CHINA).format(Date())
