package com.yelai.wearable.net.gson;

import com.google.gson.TypeAdapter;
import com.google.gson.stream.JsonReader;
import com.google.gson.stream.JsonToken;
import com.google.gson.stream.JsonWriter;

import java.io.IOException;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;

public final class EmptyStringTypeAdapter extends TypeAdapter<String> {

    private EmptyStringTypeAdapter() {}

    @Override
    @SuppressWarnings("resource")
    public void write(final JsonWriter jsonWriter, @Nullable final String s)
            throws IOException {
        if ( s == null || s.isEmpty() ) {
            jsonWriter.nullValue();
        } else {
            jsonWriter.value(s);
        }
    }

    @Override
    @Nonnull
    @SuppressWarnings("EnumSwitchStatementWhichMissesCases")
    public String read(final JsonReader jsonReader)
            throws IOException {
        final JsonToken token = jsonReader.peek();
        switch ( token ) {
        case NULL:
            return "";
        case STRING:
            return jsonReader.nextString();
        default:
            throw new IllegalStateException("Unexpected token: " + token);
        }
    }

}