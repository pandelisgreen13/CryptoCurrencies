package com.pchasapis.cryptocurrency.network.client

import androidx.annotation.Nullable
import com.google.gson.*
import com.pchasapis.cryptocurrency.models.parsers.crypto.timeFrame.TimeFrameResponse
import java.lang.reflect.Type
import com.google.gson.JsonElement
import com.pchasapis.cryptocurrency.models.parsers.crypto.timeFrame.TimeFrame


class Deserializer : JsonDeserializer<TimeFrameResponse> {

    companion object {
        const val KEY_RATES = "rates"
        const val KEY_TIME_FRAME = "timeframe"
        const val KEY_START_DATE = "start_date"
        const val KEY_END_DATE = "end_date"
        const val KEY_TARGET = "target"
        const val KEY_SUCCESS = "success"
    }

    @Throws(JsonParseException::class)
    override fun deserialize(json: JsonElement, typeOfT: Type, context: JsonDeserializationContext): TimeFrameResponse {
        val jsonObject = json.asJsonObject

        // Read simple String values.
        val success = jsonObject.get(KEY_SUCCESS).asBoolean
        val timeFrame = jsonObject.get(KEY_TIME_FRAME).asBoolean
        val startDate = jsonObject.get(KEY_START_DATE).asString
        val endDate = jsonObject.get(KEY_END_DATE).asString
        val target = jsonObject.get(KEY_TARGET).asString

        // Read the dynamic parameters object.
        val rates = readParametersMap(jsonObject)

        return TimeFrameResponse(
                success = success,
                timeFrame = timeFrame,
                startDate = startDate,
                endDate = endDate,
                target = target,
                rates = rates)
    }

    @Nullable
    private fun readParametersMap(jsonObject: JsonObject): MutableList<TimeFrame> {
        val paramsElement = jsonObject.get(KEY_RATES) ?: return mutableListOf()

        val parametersObject = paramsElement.asJsonObject
        val parameters = hashMapOf<String, String>()
        val list = mutableListOf<TimeFrame>()
        for ((key, value1) in parametersObject.entrySet()) {
            val value = value1.toString()
            list.add(TimeFrame(key, transformRate(value)))
            parameters[key] = value
        }
        return list
    }

    private fun transformRate(rate: String): Double {
        return rate.substringAfter(":").substringBefore("}").toDouble()
    }


}