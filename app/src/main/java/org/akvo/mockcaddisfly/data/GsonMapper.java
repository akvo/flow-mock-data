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

package org.akvo.mockcaddisfly.data;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonIOException;
import com.google.gson.JsonSyntaxException;

import java.io.InputStream;
import java.io.InputStreamReader;
import java.lang.reflect.Type;

import timber.log.Timber;

public class GsonMapper {

    private final Gson mapper;

    public GsonMapper() {
        this.mapper = new GsonBuilder().create();
    }

    public <T> T read(final String content, final Class<T> type) throws JsonSyntaxException {
        try {
            return this.mapper.fromJson(content, type);
        } catch (JsonSyntaxException e) {
            Timber.e("Error mapping json to class '%s' with contents: '%s'", type, content);
            throw e;
        }
    }

    public <T> T read(final String content, final Type type) throws JsonSyntaxException {
        try {
            return this.mapper.fromJson(content, type);
        } catch (JsonSyntaxException e) {
            Timber.e("Error mapping json to class '%s' with contents: '%s'", type, content);
            throw e;
        }
    }

    public <T> T read(final InputStream content, final Class<T> type)
            throws JsonIOException, JsonSyntaxException {
        try {
            return this.mapper.fromJson(new InputStreamReader(content), type);
        } catch (JsonIOException | JsonSyntaxException e) {
            Timber.e("Error mapping json to class '%s' with contents: '%s'", type, content);
            throw e;
        }
    }

    public <T> String write(final T content) {
        try {
            return this.mapper.toJson(content);
        } catch (JsonIOException | JsonSyntaxException e) {
            Timber.e(e, "Error mapping class to json with contents: '%s'", content);
            throw e;
        }
    }

    public <T> String write(final T content, final Class<T> type) {
        try {
            return this.mapper.toJson(content, type);
        } catch (JsonIOException | JsonSyntaxException e) {
            Timber.e(e, "Error mapping class '%s' to json with contents: '%s'", type, content);
            throw e;
        }
    }
}