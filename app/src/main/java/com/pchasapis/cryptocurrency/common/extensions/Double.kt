package com.pchasapis.cryptocurrency.common.extensions

import android.content.Context
import com.pchasapis.cryptocurrency.common.LocaleUtil.getCurrentLocale
import java.math.BigDecimal
import java.math.RoundingMode
import java.text.DecimalFormat
import java.text.NumberFormat

fun Double?.getAmountWithDecimalAndCurrencySymbolEnd(context: Context, currencyCode: String): String {
    if (this == null) {
        return "-"
    }
    val formatter = NumberFormat.getInstance(getCurrentLocale(context)) as DecimalFormat
    formatter.applyPattern("#,##0.00")
    val formattedAmount = formatter.format(BigDecimal(this).setScale(2, RoundingMode.HALF_EVEN))
    return "$formattedAmount $currencyCode"
}

