object AppConfig {
    const val compileSdk = 29
    const val minSdk = 21
    const val targetSdk = 29
    const val versionCode = 1
    const val versionName = "1.0"
    const val applicationId = "br.com.jdscaram.covid19radar"
}

object Versions {
    const val kotlin = "1.3.72"
    const val gradle = "3.6.3"
    const val retrofit = "2.8.1"
    const val androidSupportLib = "27.0.2"
    const val kotlinVersion = "1.3.72"
    const val constraintLayout = "1.1.3"
    const val ktx = "1.2.0"
    const val recyclerView = "1.1.0"
    const val appcompat = "1.1.0"

    const val junit = "4.12"
    const val extInstrumentation = "1.1.1"
    const val espresso = "3.2.0"

    const val lifecycle = "2.2.0"
    const val lifecycleExtension = "2.0.0"
    const val lifecycleTesting = "2.1.0"
}

object Libs {
    const val stdlib = "org.jetbrains.kotlin:kotlin-stdlib-jdk8:${Versions.kotlin}"

    const val ktx = "androidx.core:core-ktx:${Versions.ktx}"
    const val recyclerview = "androidx.recyclerview:recyclerview:${Versions.recyclerView}"
    const val supportAppCompat = "androidx.appcompat:appcompat:${Versions.appcompat}"

    const val constraintLayout = "androidx.constraintlayout:constraintlayout:${Versions.constraintLayout}"

    const val retrofit = "com.squareup.retrofit2:retrofit:${Versions.retrofit}"
    const val gsonConverter = "com.squareup.retrofit2:converter-gson:${Versions.retrofit}"

    const val jUnit = "junit:junit:${Versions.junit}"
    const val extInstrumentation = "androidx.test.ext:junit:${Versions.extInstrumentation}"
    const val espresso = "androidx.test.espresso:espresso-core:${Versions.espresso}"

    const val viewModel = "androidx.lifecycle:lifecycle-viewmodel-ktx:${Versions.lifecycle}"
    const val liveData = "androidx.lifecycle:lifecycle-livedata-ktx:${Versions.lifecycle}"
    const val lifeCycleExtension = "androidx.lifecycle:lifecycle-extensions:${Versions.lifecycleExtension}"

    const val coreTesting = "androidx.arch.core:core-testing:${Versions.lifecycleTesting}"
}

object Modules {
    const val webservice = ":webservice"
}
