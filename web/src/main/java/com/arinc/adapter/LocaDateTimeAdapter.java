package com.arinc.adapter;

import com.google.gson.TypeAdapter;
import com.google.gson.stream.JsonReader;
import com.google.gson.stream.JsonWriter;

import java.io.IOException;
import java.time.LocalDateTime;

public class LocaDateTimeAdapter extends TypeAdapter<LocalDateTime> {
    @Override
    public void write(JsonWriter jsonWriter, LocalDateTime localDateTime) throws IOException {
        if (localDateTime == null) {
            jsonWriter.nullValue();
            return;
        }
            String result = localDateTime.toString();
        jsonWriter.value(result);
    }

    @Override
    public LocalDateTime read(JsonReader jsonReader) throws IOException {
        return null;
    }
}
