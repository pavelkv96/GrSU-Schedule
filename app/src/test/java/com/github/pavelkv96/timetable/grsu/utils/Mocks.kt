package com.github.pavelkv96.timetable.grsu.utils

import com.google.gson.Gson
import okhttp3.ResponseBody
import org.junit.Assert
import retrofit2.Response
import java.io.BufferedReader
import java.io.IOException
import java.io.InputStream
import java.io.InputStreamReader
import java.lang.reflect.Type

object Mocks {

    @JvmStatic
    fun <E> getErrorResponse(code: Int, content: String): Response<E> {
        return Response.error<E>(code, ResponseBody.create(null, content)) as Response<E>
    }

    @JvmStatic
    fun <E> getSuccessResponse(pNameFile: String, type: Type): Response<E> {
        return Response.success(getGsonObject<E>(pNameFile, type)) as Response<E>
    }

    fun <T> getGsonObject(pNameFile: String, type: Type): T {
        return Gson().fromJson(stream(pNameFile), type) as T
    }

    private fun stream(pName: String): String {
        val classLoader = Mocks::class.java.classLoader
        var resourceStream: InputStream? = null
        if (classLoader != null) {
            resourceStream = classLoader.getResourceAsStream(pName)
        }
        Assert.assertNotNull("resource not found, maybe you forget add .json?", resourceStream)
        return getStringFromInputStream(resourceStream)
    }

    private fun getStringFromInputStream(stream: InputStream?): String {
        var br: BufferedReader? = null
        val sb = StringBuilder()

        var line: String?
        if (stream == null) return ""
        try {
            br = BufferedReader(InputStreamReader(stream))
            line = br.readLine()
            while (line != null) {
                sb.append(line)
                line = br.readLine()
            }

        } catch (e: Exception) {
            e.printStackTrace()
        } finally {
            if (br != null) {
                try {
                    br.close()
                } catch (e: IOException) {
                    e.printStackTrace()
                }
            }
        }
        return sb.toString()
    }
}