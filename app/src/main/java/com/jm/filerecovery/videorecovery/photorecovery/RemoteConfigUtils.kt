package com.jm.filerecovery.videorecovery.photorecovery

import com.google.firebase.ktx.Firebase
import com.google.firebase.remoteconfig.FirebaseRemoteConfig
import com.google.firebase.remoteconfig.ktx.remoteConfig
import com.google.firebase.remoteconfig.ktx.remoteConfigSettings
import java.lang.Exception

object RemoteConfigUtils {

    private const val TAG = "RemoteConfigUtils"
    private const val ON_BANNER_HOME = "on_banner_home"
    private const val ON_BANNER_TOOL = "on_banner_tool"
    private const val ON_BANNER_PERSONAL = "on_banner_personal"
    private const val ON_NATIVE_LANGUAGE = "on_native_language"
    private const val ON_NATIVE_HOME = "on_native_home"
    private const val ON_NATIVE_RESULT = "on_native_result_activity"
    private const val ON_INTER_LUNCH = "on_inter_lunch"
    private const val ON_INTER_LUNCH_HIGH = "on_inter_lunch_high"
    private const val ON_APP_OPEN_LUNCH = "on_app_open_lunch"
    private const val ON_INTER_INTRODUCE = "on_inter_introduce"
    private const val ON_INTER_RESULT = "on_inter_result"
    private const val ON_OPEN_HIGH_SPLASH_2_ID = "open_high_splash_2_id"
    private const val ON_OPEN_HIGH_SPLASH_3_ID = "open_high_splash_3_id"

    //    private const val HELLO_BUTTON_COLOR = "hello_button_color"
    var completed = false
    private val DEFAULTS: HashMap<String, Any> =
        hashMapOf(
            ON_BANNER_HOME to "on",
            ON_NATIVE_HOME to "on",
            ON_OPEN_HIGH_SPLASH_2_ID to "on",
            ON_OPEN_HIGH_SPLASH_3_ID to "on",
            ON_BANNER_TOOL to "on",
            ON_BANNER_PERSONAL to "on",
            ON_NATIVE_LANGUAGE to "on",
            ON_NATIVE_RESULT to "on",
            ON_INTER_LUNCH to "on",
            ON_INTER_LUNCH_HIGH to "off",
            ON_APP_OPEN_LUNCH to "on",
            ON_INTER_INTRODUCE to "on",
            ON_INTER_RESULT to "on"
        )

    interface Listener {
        fun loadSuccess()
    }

    lateinit var listener: Listener
    private lateinit var remoteConfig: FirebaseRemoteConfig

    fun init(mListener: Listener) {
        listener = mListener
        remoteConfig = getFirebaseRemoteConfig()
    }

    private fun getFirebaseRemoteConfig(): FirebaseRemoteConfig {
        val remoteConfig = Firebase.remoteConfig

        val configSettings = remoteConfigSettings {
            minimumFetchIntervalInSeconds = if (BuildConfig.DEBUG) {
                0
            } else {
                60 * 60
            }
        }
        remoteConfig.apply {
            setConfigSettingsAsync(configSettings)
            setDefaultsAsync(DEFAULTS)
            fetchAndActivate().addOnCompleteListener {
                listener.loadSuccess()
                completed = true
            }
        }
        return remoteConfig
    }

    fun getOnNativeHome(): String {
        try {
            return if (!completed) {
                "off"
            } else {
                remoteConfig.getString(ON_NATIVE_HOME)
            }
        } catch (e: Exception) {

        }
        return "off"
    }

    fun getOnBannerHome(): String {
        try {
            return if (!completed) {
                "off"
            } else {
                remoteConfig.getString(ON_BANNER_HOME)
            }
        } catch (e: Exception) {

        }
        return "off"
    }

    fun getOnBannerTool(): String {
        try {
            return if (!completed) {
                "off"
            } else {
                remoteConfig.getString(ON_BANNER_TOOL)
            }
        } catch (e: Exception) {

        }
        return "off"
    }

    fun getOnBannerPersonal(): String {
        try {
            return if (!completed) {
                "off"
            } else {
                remoteConfig.getString(ON_BANNER_PERSONAL)
            }
        } catch (e: Exception) {

        }
        return "off"
    }

    fun getOnNativeLanguage(): String {
        try {
            return if (!completed) {
                "off"
            } else {
                remoteConfig.getString(ON_NATIVE_LANGUAGE)
            }
        } catch (e: Exception) {

        }
        return "off"
    }

    fun getOnNativeResultActivity(): String {
        try {
            return if (!completed) {
                "off"
            } else {
                remoteConfig.getString(ON_NATIVE_RESULT)
            }
        } catch (e: Exception) {

        }
        return "off"
    }


    fun getOnInterLunch(): String {
        try {
            return if (!completed) {
                "off"
            } else {
                remoteConfig.getString(ON_INTER_LUNCH)
            }
        } catch (e: Exception) {

        }
        return "off"
    }

    fun getOnInterLunchHigh(): String {
        try {
            return if (!completed) {
                "off"
            } else {
                remoteConfig.getString(ON_INTER_LUNCH_HIGH)
            }
        } catch (e: Exception) {

        }
        return "off"
    }

    fun getOnOpenLunch(): String {
        try {
            return if (!completed) {
                "off"
            } else {
                remoteConfig.getString(ON_INTER_LUNCH)
            }
        } catch (e: Exception) {

        }
        return "off"
    }
    fun getOnInterIntroduce(): String {
        try {
            return if (!completed) {
                "off"
            } else {
                remoteConfig.getString(ON_INTER_INTRODUCE)
            }
        } catch (e: Exception) {

        }
        return "off"
    }

    fun getOnOpenHighSplash2Id(): String {
        try {
            return if (!completed) {
                "off"
            } else {
                remoteConfig.getString(ON_OPEN_HIGH_SPLASH_2_ID)
            }
        } catch (e: Exception) {
            e.printStackTrace()
        }
        return "off"
    }

    fun getOnOpenHighSplash3Id(): String {
        try {
            return if (!completed) {
                "off"
            } else {
                remoteConfig.getString(ON_OPEN_HIGH_SPLASH_3_ID)
            }
        } catch (e: Exception) {
            e.printStackTrace()
        }
        return "off"
    }

}