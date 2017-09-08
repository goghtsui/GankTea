package com.gogh.afternoontea.convert;


import android.support.annotation.NonNull;

import com.gogh.afternoontea.utils.Logger;
import com.google.gson.Gson;
import com.google.gson.TypeAdapter;
import com.google.gson.stream.JsonReader;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.Reader;
import java.nio.charset.Charset;

import okhttp3.MediaType;
import okhttp3.ResponseBody;
import retrofit2.Converter;

final class CustomGsonResponseBodyConverter<T> implements Converter<ResponseBody, T> {

    private final Gson gson;
    private final TypeAdapter<T> adapter;

    private static final Charset UTF_8 = Charset.forName("UTF-8");

    CustomGsonResponseBodyConverter(Gson gson, TypeAdapter<T> adapter) {
        this.gson = gson;
        this.adapter = adapter;
    }

    @Override
    public T convert(@NonNull ResponseBody value) throws IOException {
        String response = value.string();
        Logger.d("CustomGsonResponseBodyConverter", "convert ResponseBody before = " + response);
        if(response.startsWith("{") && response.endsWith("}")){
            response = response.substring(response.indexOf("[") + 1, response.lastIndexOf("]"));
            Logger.d("CustomGsonResponseBodyConverter", "convert ResponseBody after = " + response);
        }

        MediaType contentType = value.contentType();
        Charset charset = contentType != null ? contentType.charset(UTF_8) : UTF_8;
        InputStream inputStream = new ByteArrayInputStream(response.getBytes());
        Reader reader = new InputStreamReader(inputStream, charset);
        JsonReader jsonReader = gson.newJsonReader(reader);
        jsonReader.setLenient(true);
        try {
            return adapter.read(jsonReader);
        } finally {
            value.close();
        }
    }
}