package com.x.lib_network.converter

import com.google.gson.Gson
import okhttp3.ResponseBody
import retrofit2.Converter
import retrofit2.Retrofit
import java.lang.reflect.Type

class StringResponseBodyConverter private constructor(val gson: Gson, val content: String): Converter.Factory() {

    override fun responseBodyConverter(
        type: Type,
        annotations: Array<out Annotation>,
        retrofit: Retrofit
    ): Converter<ResponseBody, *>? {
//        return GsonResponseBodyConverter<Any>(type)
        return super.responseBodyConverter(type, annotations, retrofit)
    }

//    internal inner class GsonResponseBodyConverter<T>(private val type: Type?) : Converter<ResponseBody, T> {
//        override fun convert(value: ResponseBody): T? {
//            val resString = value.string()
//            return gson.fromJson<T>(resString, )
//        }
//    }

    companion object {
        fun create(gson: Gson = Gson(), content: String): StringResponseBodyConverter {
            return StringResponseBodyConverter(gson, content)
        }
    }
}