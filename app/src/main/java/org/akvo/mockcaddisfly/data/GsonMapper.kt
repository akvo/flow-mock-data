/*
 * Copyright (C) 2017 Stichting Akvo (Akvo Foundation)
 *
 * This file is part of Akvo Flow.
 *
 * Akvo Flow is free software: you can redistribute it and/or modify
 * it under the terms of the GNU General Public License as published by
 * the Free Software Foundation, either version 3 of the License, or
 * (at your option) any later version.
 *
 * Akvo Flow is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.
 *
 * You should have received a copy of the GNU General Public License
 * along with Akvo Flow.  If not, see <http://www.gnu.org/licenses/>.
 *
 */

package org.akvo.mockcaddisfly.data

import com.google.gson.Gson
import com.google.gson.GsonBuilder
import com.google.gson.JsonIOException
import com.google.gson.JsonSyntaxException

import java.io.InputStream
import java.io.InputStreamReader
import java.lang.reflect.Type

import timber.log.Timber

class GsonMapper {

    private val mapper: Gson

    init {
        this.mapper = GsonBuilder().create()
    }

    @Throws(JsonSyntaxException::class)
    fun <T> read(content: String, type: Class<T>): T {
        try {
            return this.mapper.fromJson(content, type)
        } catch (e: JsonSyntaxException) {
            Timber.e("Error mapping json to class '%s' with contents: '%s'", type, content)
            throw e
        }

    }

    @Throws(JsonSyntaxException::class)
    fun <T> read(content: String, type: Type): T? {
        try {
            return this.mapper.fromJson<T>(content, type)
        } catch (e: JsonSyntaxException) {
            Timber.e("Error mapping json to class '%s' with contents: '%s'", type, content)
            throw e
        }

    }

    @Throws(JsonIOException::class, JsonSyntaxException::class)
    fun <T> read(content: InputStream, type: Class<T>): T {
        try {
            return this.mapper.fromJson(InputStreamReader(content), type)
        } catch (e: JsonIOException) {
            Timber.e("Error mapping json to class '%s' with contents: '%s'", type, content)
            throw e
        } catch (e: JsonSyntaxException) {
            Timber.e("Error mapping json to class '%s' with contents: '%s'", type, content)
            throw e
        }

    }

    fun <T> write(content: T): String {
        try {
            return this.mapper.toJson(content)
        } catch (e: JsonIOException) {
            Timber.e(e, "Error mapping class to json with contents: '%s'", content)
            throw e
        } catch (e: JsonSyntaxException) {
            Timber.e(e, "Error mapping class to json with contents: '%s'", content)
            throw e
        }

    }

    fun <T> write(content: T, type: Class<T>): String {
        try {
            return this.mapper.toJson(content, type)
        } catch (e: JsonIOException) {
            Timber.e(e, "Error mapping class '%s' to json with contents: '%s'", type, content)
            throw e
        } catch (e: JsonSyntaxException) {
            Timber.e(e, "Error mapping class '%s' to json with contents: '%s'", type, content)
            throw e
        }

    }
}