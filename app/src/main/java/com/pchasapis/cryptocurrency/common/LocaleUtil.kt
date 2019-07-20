package com.pchasapis.cryptocurrency.common

import android.content.Context
import com.pchasapis.cryptocurrency.common.extensions.isNougat
import java.util.*

object LocaleUtil {

    @JvmStatic
    fun getCurrentLocale(context: Context): Locale {
        return if (isNougat()) {
            context.resources.configuration.locales.get(0)
        } else {

            context.resources.configuration.locale
        }
    }
}

