package com.example.android.politicalpreparedness.network.jsonadapter

import android.util.Log
import com.squareup.moshi.FromJson
import com.squareup.moshi.ToJson
import java.text.SimpleDateFormat
import java.util.*

class DateAdapter {
    @FromJson
    fun dateFromJson (date: String): Date {
        Log.d("FLUX","Date "+date)
        val dateFormat = SimpleDateFormat("yyyy-MM-dd")
        return dateFormat.parse(date)
    }

    @ToJson
    fun dateToJson (date: Date): String {
        val dateFormat = SimpleDateFormat("yyyy-MM-dd")
        return dateFormat.format(date)
    }
}