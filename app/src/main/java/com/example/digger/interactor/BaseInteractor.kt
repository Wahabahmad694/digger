package com.example.digger.interactor


import com.example.dig.utils.DigLogger
import okhttp3.ResponseBody
import org.json.JSONObject

open class BaseInteractor {

    /*fun convertErrorBody(response: ResponseBody): String {
        val res: String? = response.string()
        var jsonObject: JSONObject? = null
        var json: String? = null
        try {
            jsonObject = JSONObject(res)
            json = jsonObject.getString("message")
        } catch (e: JSONException) {
            e.printStackTrace()
        }

        return json!!
    }*/
    fun convertErrorBody(errorBody: ResponseBody?): String {

        return try {
            val errorObject =
                errorBody?.charStream()?.readText()?.let { JSONObject(it) }
            errorObject?.getString("message") ?: ""
        } catch (e: Exception) {
            DigLogger.logErrorMsg("convertErrorBody", e.message.toString())
            ""
        }
    }
}

