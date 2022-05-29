package com.x.lib_base.utils.util

import android.os.Build
import android.os.Environment
import android.text.TextUtils
import androidx.annotation.StringDef
import java.io.File
import java.io.FileInputStream
import java.io.IOException
import java.lang.annotation.Retention
import java.lang.annotation.RetentionPolicy
import java.util.*

/**
 * @desc  获取手机相关信息工具类
 * @author wei
 * @date  2021/12/27
 **/
object RomUtil {
    const val ROM_MIUI = "MIUI"
    const val ROM_EMUI = "EMUI"
    const val ROM_VIVO = "VIVO"
    const val ROM_OPPO = "OPPO"
    const val ROM_FLYME = "FLYME"
    const val ROM_SMARTISAN = "SMARTISAN"
    const val ROM_QIKU = "QIKU"
    const val ROM_LETV = "LETV"
    const val ROM_LENOVO = "LENOVO"
    const val ROM_NUBIA = "NUBIA"
    const val ROM_ZTE = "ZTE"
    const val ROM_COOLPAD = "COOLPAD"
    const val ROM_UNKNOWN = "UNKNOWN"

    @StringDef(
        ROM_MIUI,
        ROM_EMUI,
        ROM_VIVO,
        ROM_OPPO,
        ROM_FLYME,
        ROM_SMARTISAN,
        ROM_QIKU,
        ROM_LETV,
        ROM_LENOVO,
        ROM_ZTE,
        ROM_COOLPAD,
        ROM_UNKNOWN
    )
    @Target(
        AnnotationTarget.FUNCTION,
        AnnotationTarget.PROPERTY_GETTER,
        AnnotationTarget.PROPERTY_SETTER
    )
    @Retention(
        RetentionPolicy.SOURCE
    )
    annotation class RomName

    private const val SYSTEM_VERSION_MIUI = "ro.miui.ui.version.name"
    private const val SYSTEM_VERSION_EMUI = "ro.build.version.emui"
    private const val SYSTEM_VERSION_VIVO = "ro.vivo.os.version"
    private const val SYSTEM_VERSION_OPPO = "ro.build.version.opporom"
    private const val SYSTEM_VERSION_FLYME = "ro.build.display.id"
    private const val SYSTEM_VERSION_SMARTISAN = "ro.smartisan.version"
    private const val SYSTEM_VERSION_LETV = "ro.letv.eui"
    private const val SYSTEM_VERSION_LENOVO = "ro.lenovo.lvp.version"

    internal object AvailableRomType {
        const val MIUI = 1
        const val FLYME = 2
        const val ANDROID_NATIVE = 3
        const val NA = 4
    }

    fun getLightStatusBarAvailableRomType(): Int {
        //开发版 7.7.13 及以后版本采用了系统API，旧方法无效但不会报错
        if (isMiUIV7OrAbove()) {
            return AvailableRomType.ANDROID_NATIVE
        }
        if (isMiUIV6OrAbove()) {
            return AvailableRomType.MIUI
        }
        if (isFlymeV4OrAbove()) {
            return AvailableRomType.FLYME
        }
        return if (isAndroidMOrAbove()) {
            AvailableRomType.ANDROID_NATIVE
        } else AvailableRomType.NA
    }

    //Flyme V4的displayId格式为 [Flyme OS 4.x.x.xA]
    //Flyme V5的displayId格式为 [Flyme 5.x.x.x beta]
    private fun isFlymeV4OrAbove(): Boolean {
        val displayId = Build.DISPLAY
        if (!TextUtils.isEmpty(displayId) && displayId.contains("Flyme")) {
            val displayIdArray = displayId.split(" ".toRegex()).toTypedArray()
            for (temp in displayIdArray) {
                //版本号4以上，形如4.x.
                  if (temp.matches(Regex("^[4-9]\\\\.(\\\\d+\\\\.)+\\\\S*"))) {
                      return true
                  }
            }
        }
        return false
    }

    //Android Api 23以上
    private fun isAndroidMOrAbove(): Boolean {
        return Build.VERSION.SDK_INT >= Build.VERSION_CODES.M
    }

    private const val KEY_MIUI_VERSION_CODE = "ro.miui.ui.version.code"

    private fun isMiUIV6OrAbove(): Boolean {
        var stream: FileInputStream? = null
        return try {
            val properties = Properties()
            stream = FileInputStream(File(Environment.getRootDirectory(), "build.prop"))
            properties.load(stream)
            val uiCode = properties.getProperty(KEY_MIUI_VERSION_CODE, null)
            if (uiCode != null) {
                val code = uiCode.toInt()
                code >= 4
            } else {
                false
            }
        } catch (e: Exception) {
            false
        } finally {
            if (stream != null) {
                try {
                    stream.close()
                } catch (e: IOException) {
                    e.printStackTrace()
                }
            }
        }
    }

    fun isMiUIV7OrAbove(): Boolean {
        var stream: FileInputStream? = null
        return try {
            val properties = Properties()
            stream = FileInputStream(File(Environment.getRootDirectory(), "build.prop"))
            properties.load(stream)
            val uiCode = properties.getProperty(KEY_MIUI_VERSION_CODE, null)
            if (uiCode != null) {
                val code = uiCode.toInt()
                code >= 5
            } else {
                false
            }
        } catch (e: Exception) {
            false
        } finally {
            if (stream != null) {
                try {
                    stream.close()
                } catch (e: IOException) {
                    e.printStackTrace()
                }
            }
        }
    }

    private fun getSystemProperty(propName: String): String {
        return ""
        //return SystemProperties.get(propName, null);
    }

    @RomName
    fun getRomName(): String? {
        if (isMiuiRom()) {
            return ROM_MIUI
        }
        if (isHuaweiRom()) {
            return ROM_EMUI
        }
        if (isVivoRom()) {
            return ROM_VIVO
        }
        if (isOppoRom()) {
            return ROM_OPPO
        }
        if (isMeizuRom()) {
            return ROM_FLYME
        }
        if (isSmartisanRom()) {
            return ROM_SMARTISAN
        }
        if (is360Rom()) {
            return ROM_QIKU
        }
        if (isLetvRom()) {
            return ROM_LETV
        }
        if (isLenovoRom()) {
            return ROM_LENOVO
        }
        if (isZTERom()) {
            return ROM_ZTE
        }
        return if (isCoolPadRom()) {
            ROM_COOLPAD
        } else ROM_UNKNOWN
    }

    fun getDeviceManufacture(): String? {
        if (isMiuiRom()) {
            return "小米"
        }
        if (isHuaweiRom()) {
            return "华为"
        }
        if (isVivoRom()) {
            return ROM_VIVO
        }
        if (isOppoRom()) {
            return ROM_OPPO
        }
        if (isMeizuRom()) {
            return "魅族"
        }
        if (isSmartisanRom()) {
            return "锤子"
        }
        if (is360Rom()) {
            return "奇酷"
        }
        if (isLetvRom()) {
            return "乐视"
        }
        if (isLenovoRom()) {
            return "联想"
        }
        if (isZTERom()) {
            return "中兴"
        }
        return if (isCoolPadRom()) {
            "酷派"
        } else Build.MANUFACTURER
    }

    fun isMiuiRom(): Boolean {
        return !TextUtils.isEmpty(getSystemProperty(SYSTEM_VERSION_MIUI))
    }

    fun isHuaweiRom(): Boolean {
        return !TextUtils.isEmpty(getSystemProperty(SYSTEM_VERSION_EMUI))
    }

    fun isVivoRom(): Boolean {
        return !TextUtils.isEmpty(getSystemProperty(SYSTEM_VERSION_VIVO))
    }

    fun isOppoRom(): Boolean {
        return !TextUtils.isEmpty(getSystemProperty(SYSTEM_VERSION_OPPO))
    }

    fun isMeizuRom(): Boolean {
        val meizuFlymeOSFlag = getSystemProperty(SYSTEM_VERSION_FLYME)
        return !TextUtils.isEmpty(meizuFlymeOSFlag) && meizuFlymeOSFlag.toUpperCase()
            .contains(ROM_FLYME)
    }

    fun isSmartisanRom(): Boolean {
        return !TextUtils.isEmpty(getSystemProperty(SYSTEM_VERSION_SMARTISAN))
    }

    fun is360Rom(): Boolean {
        val manufacturer = Build.MANUFACTURER
        return !TextUtils.isEmpty(manufacturer) && manufacturer.toUpperCase().contains(ROM_QIKU)
    }

    fun isLetvRom(): Boolean {
        return !TextUtils.isEmpty(getSystemProperty(SYSTEM_VERSION_LETV))
    }

    fun isLenovoRom(): Boolean {
        return !TextUtils.isEmpty(getSystemProperty(SYSTEM_VERSION_LENOVO))
    }

    fun isCoolPadRom(): Boolean {
        val model = Build.MODEL
        val fingerPrint = Build.FINGERPRINT
        return (!TextUtils.isEmpty(model) && model.toLowerCase().contains(ROM_COOLPAD)
                || !TextUtils.isEmpty(fingerPrint) && fingerPrint.toLowerCase()
            .contains(ROM_COOLPAD))
    }

    fun isZTERom(): Boolean {
        val manufacturer = Build.MANUFACTURER
        val fingerPrint = Build.FINGERPRINT
        return (!TextUtils.isEmpty(manufacturer) && (fingerPrint.toLowerCase().contains(ROM_NUBIA)
                || fingerPrint.toLowerCase().contains(ROM_ZTE))
                || !TextUtils.isEmpty(fingerPrint) && (fingerPrint.toLowerCase().contains(ROM_NUBIA)
                || fingerPrint.toLowerCase().contains(ROM_ZTE)))
    }

    fun isDomesticSpecialRom(): Boolean {
        return (isMiuiRom()
                || isHuaweiRom()
                || isMeizuRom()
                || is360Rom()
                || isOppoRom()
                || isVivoRom()
                || isLetvRom()
                || isZTERom()
                || isLenovoRom()
                || isCoolPadRom())
    }
}