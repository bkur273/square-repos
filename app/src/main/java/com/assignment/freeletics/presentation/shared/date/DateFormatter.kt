package com.assignment.freeletics.presentation.shared.date

import android.content.Context
import android.text.format.DateFormat
import kotlinx.datetime.LocalDateTime
import kotlinx.datetime.TimeZone
import kotlinx.datetime.toInstant
import java.util.*


interface DateFormatter {

    fun getFormatted(localDateTime: LocalDateTime): String

    class Impl(
        private val context: Context
    ) : DateFormatter {


        override fun getFormatted(localDateTime: LocalDateTime): String {
            val date = Date(
                localDateTime.toInstant(TimeZone.currentSystemDefault()).toEpochMilliseconds()
            )
            return DateFormat.getLongDateFormat(context).format(date)
        }

    }

}