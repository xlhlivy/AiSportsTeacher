/*
 * Copyright (C) 2015 Square, Inc.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package retrofit2.converter.gson;

import com.google.gson.Gson;
import com.google.gson.TypeAdapter;
import com.google.gson.stream.JsonReader;

import org.json.JSONArray;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.io.StringReader;

import okhttp3.ResponseBody;
import retrofit2.Converter;

final class HrGsonResponseBodyConverter<T> implements Converter<ResponseBody, T> {
  private final Gson gson;
  private final TypeAdapter<T> adapter;

  HrGsonResponseBodyConverter(Gson gson, TypeAdapter<T> adapter) {
    this.gson = gson;
    this.adapter = adapter;
  }

  @Override public T convert(ResponseBody value) throws IOException {
    String json = value.string();

    try {
        return tryParseAsObject(json);
    }catch (Exception e){
      return tryParseAsList(json);
    }
    finally {
      value.close();
    }
  }

  public T tryParseAsObject(String json) throws IOException{
    try {
      JSONObject jsonObject = new JSONObject(json);

      if(jsonObject.get("data") instanceof String){

        if(jsonObject.getString("data").length() < 13){

          JSONObject obj = new JSONObject();

//        obj.put("data",jsonObject.get("data").toString().replace("/",""));
//
        jsonObject.put("data",obj);

          json = jsonObject.toString();

        }
      }

    } catch (JSONException e) {
      e.printStackTrace();
    }

    try{

      StringReader reader = new StringReader(json);

      JsonReader jsonReader = gson.newJsonReader(reader);

      T data = adapter.read(jsonReader);

      return data;

    }catch (Exception e){

      e.printStackTrace();

      throw e;

    }

  }

  public T tryParseAsList(String json) throws IOException{
    try {
      JSONObject jsonObject = new JSONObject(json);

      if(jsonObject.get("data") instanceof String){
        jsonObject.put("data",new JSONArray());

        json = jsonObject.toString();
      }

    } catch (JSONException e) {
      e.printStackTrace();
    }

    JsonReader jsonReader = gson.newJsonReader(new StringReader(json));

    return adapter.read(jsonReader);

  }
}
