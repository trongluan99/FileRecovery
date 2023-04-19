package com.jm.filerecovery.videorecovery.photorecovery

import com.google.firebase.ktx.Firebase
import com.google.firebase.remoteconfig.FirebaseRemoteConfig
import com.google.firebase.remoteconfig.ktx.remoteConfig
import com.google.firebase.remoteconfig.ktx.remoteConfigSettings

object RemoteConfigUtils {

    private const val TAG = "RemoteConfigUtils"
    private const val ON_BANNER_HOME = "on_banner_home"
    private const val ON_BANNER_TOOL = "on_banner_tool"
    private const val ON_BANNER_PERSONAL = "on_banner_personal"

    private const val ON_NATIVE_LANGUAGE = "on_native_language"
    private const val ON_NATIVE_LANGUAGE_HIGH = "on_native_language_high"

    private const val ON_NATIVE_HOME = "on_native_home"
    private const val ON_NATIVE_HOME_HIGH = "on_native_home_high"

    private const val ON_NATIVE_TUTORIAL = "on_native_tutorial"
    private const val ON_NATIVE_TUTORIAL_HIGH = "on_native_tutorial_high"

    private const val ON_INTER_TUTORIAL = "on_inter_tutorial"

    private const val ON_INTER_ALL_HIGH_FLOOR = "on_inter_all_high_floor"

    private const val ON_INTER_CLICK_HOME = "on_inter_click_home"

    private const val ON_INTER_CLICK_ITEM = "on_inter_click_item"

    private const val ON_INTER_RECOVERY = "on_inter_recovery"

    private const val ON_NATIVE_RECOVERY_ITEM = "on_native_recovery_item"
    private const val ON_NATIVE_RECOVERY_ITEM_HIGH = "on_native_recovery_item_high"

    private const val ON_NATIVE_LIST_ITEM = "on_native_list_item"
    private const val ON_NATIVE_LIST_ITEM_HIGH = "on_native_list_item_high"

    private const val ON_NATIVE_SCAN = "on_native_scan"
    private const val ON_NATIVE_SCAN_HIGH = "on_native_scan_high"

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
            ON_NATIVE_HOME_HIGH to "on",

            ON_NATIVE_TUTORIAL to "on",
            ON_NATIVE_TUTORIAL_HIGH to "on",

            ON_INTER_TUTORIAL to "on",

            ON_NATIVE_LANGUAGE to "on",
            ON_NATIVE_LANGUAGE_HIGH to "on",

            ON_INTER_CLICK_HOME to "on",
            ON_INTER_CLICK_ITEM to "on",

            ON_INTER_RECOVERY to "on",
            ON_INTER_ALL_HIGH_FLOOR to "on",

            ON_NATIVE_RECOVERY_ITEM to "on",
            ON_NATIVE_RECOVERY_ITEM_HIGH to "on",

            ON_NATIVE_LIST_ITEM to "on",
            ON_NATIVE_LIST_ITEM_HIGH to "on",

            ON_NATIVE_SCAN to "on",
            ON_NATIVE_SCAN_HIGH to "on",

            ON_OPEN_HIGH_SPLASH_2_ID to "on",
            ON_OPEN_HIGH_SPLASH_3_ID to "on",
            ON_BANNER_TOOL to "on",
            ON_BANNER_PERSONAL to "on",
            ON_NATIVE_RESULT to "on",
            ON_INTER_LUNCH to "on",
            ON_INTER_LUNCH_HIGH to "off",
            ON_APP_OPEN_LUNCH to "on",
            ON_INTER_INTRODUCE to "on",
            ON_INTER_RESULT to "on",
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

    fun getOnNativeHomeHigh(): String {
        try {
            return if (!completed) {
                "off"
            } else {
                remoteConfig.getString(ON_NATIVE_HOME_HIGH)
            }
        } catch (e: Exception) {

        }
        return "off"
    }

    fun getOnNativeTutorial(): String {
        try {
            return if (!completed) {
                "off"
            } else {
                remoteConfig.getString(ON_NATIVE_TUTORIAL)
            }
        } catch (e: Exception) {

        }
        return "off"
    }


    fun getOnNativeTutorialHigh(): String {
        try {
            return if (!completed) {
                "off"
            } else {
                remoteConfig.getString(ON_NATIVE_TUTORIAL_HIGH)
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

    fun getOnNativeLanguageHigh(): String {
        try {
            return if (!completed) {
                "off"
            } else {
                remoteConfig.getString(ON_NATIVE_LANGUAGE_HIGH)
            }
        } catch (e: Exception) {
            e.printStackTrace()
        }
        return "off"
    }

    fun getOnInterTutorial(): String {
        try {
            return if (!completed) {
                "off"
            } else {
                remoteConfig.getString(ON_INTER_TUTORIAL)
            }
        } catch (e: Exception) {

        }
        return "off"
    }

    fun getOnInterAllHigh(): String {
        try {
            return if (!completed) {
                "off"
            } else {
                remoteConfig.getString(ON_INTER_ALL_HIGH_FLOOR)
            }
        } catch (e: Exception) {

        }
        return "off"
    }

    fun getOnInterClickHome(): String {
        try {
            return if (!completed) {
                "off"
            } else {
                remoteConfig.getString(ON_INTER_CLICK_HOME)
            }
        } catch (e: Exception) {

        }
        return "off"
    }

    fun getOnInterRecovery(): String {
        try {
            return if (!completed) {
                "off"
            } else {
                remoteConfig.getString(ON_INTER_RECOVERY)
            }
        } catch (e: Exception) {

        }
        return "off"
    }

    fun getOnInterClickItem(): String {
        try {
            return if (!completed) {
                "off"
            } else {
                remoteConfig.getString(ON_INTER_CLICK_ITEM)
            }
        } catch (e: Exception) {

        }
        return "off"
    }

    fun getOnNativeRecoveryItem(): String {
        try {
            return if (!completed) {
                "off"
            } else {
                remoteConfig.getString(ON_NATIVE_RECOVERY_ITEM)
            }
        } catch (e: Exception) {

        }
        return "off"
    }

    fun getOnNativeRecoveryItemHigh(): String {
        try {
            return if (!completed) {
                "off"
            } else {
                remoteConfig.getString(ON_NATIVE_RECOVERY_ITEM_HIGH)
            }
        } catch (e: Exception) {

        }
        return "off"
    }

    fun getOnNativeScan(): String {
        try {
            return if (!completed) {
                "off"
            } else {
                remoteConfig.getString(ON_NATIVE_SCAN)
            }
        } catch (e: Exception) {

        }
        return "off"
    }


    fun getOnNativeScanHigh(): String {
        try {
            return if (!completed) {
                "off"
            } else {
                remoteConfig.getString(ON_NATIVE_SCAN_HIGH)
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

    fun getOnNativeListItem(): String {
        try {
            return if (!completed) {
                "off"
            } else {
                remoteConfig.getString(ON_NATIVE_LIST_ITEM)
            }
        } catch (e: Exception) {

        }
        return "off"
    }

    fun getOnNativeListItemHigh(): String {
        try {
            return if (!completed) {
                "off"
            } else {
                remoteConfig.getString(ON_NATIVE_LIST_ITEM_HIGH)
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