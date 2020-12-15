package com.hjw.fundplan.util

import com.google.gson.Gson
import com.google.gson.GsonBuilder
import com.google.gson.JsonSyntaxException

class JsonUtils private constructor() {
    companion object {
        private val GSON = createGson(true)
        private val GSON_NO_NULLS = createGson(false)
        private fun createGson(serializeNulls: Boolean): Gson {
            val builder = GsonBuilder()
            if (serializeNulls) builder.serializeNulls()
            return builder.create()
        }

        private val GSONFILTER = GsonBuilder().excludeFieldsWithoutExposeAnnotation().create()

        @JvmStatic
        fun <T> parseJson(json: String?, type: Class<T>): T? {
            try {
                if (!json.isNullOrEmpty()) {
                    return GSON.fromJson(json, type)
                }
            } catch (jsonSyntaxException: JsonSyntaxException) {
                jsonSyntaxException.printStackTrace()
            }
            return null
        }

        @JvmStatic
        fun <T> fromJson(json: String?, type: Class<T>): T? {
            return parseJson(json, type)
        }

        @JvmStatic
        @JvmOverloads
        fun toJson(any: Any?, includeNulls: Boolean = true): String {
            return if (includeNulls) GSON.toJson(any) else GSON_NO_NULLS.toJson(any)
        }

        @JvmStatic
        fun toJsonFilter(any: Any?): String {
            return GSONFILTER.toJson(any)
        }


    }

    init {
        throw UnsupportedOperationException("u can't instantiate me...")
    }
}