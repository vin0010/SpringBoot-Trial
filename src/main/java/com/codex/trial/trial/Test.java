package com.codex.trial.trial;

import java.io.IOException;

import com.squareup.okhttp.MediaType;
import com.squareup.okhttp.OkHttpClient;
import com.squareup.okhttp.Request;
import com.squareup.okhttp.RequestBody;
import com.squareup.okhttp.Response;

public class Test {
public static void main(String[] args) throws IOException {
	OkHttpClient client = new OkHttpClient();

	for (int i=2;i<11;i++) {
		System.out.println("Calling");
		MediaType mediaType = MediaType.parse("application/json");
		RequestBody body = RequestBody.create(mediaType, "{\r\n  \"id\": \""+i+"\",\r\n  \"title\": \"test book"+ i +"\",\r\n  \"author\": \"test author"+i+"\"\r\n}");
		Request request = new Request.Builder()
				.url("http://localhost:9090/api/books")
				.post(body)
				.addHeader("Content-Type", "application/json")
				.addHeader("cache-control", "no-cache")
				.addHeader("Postman-Token", "a23daf99-792c-4ac2-aaf2-ad35b536fc30")
				.build();
		
		Response response = client.newCall(request).execute();
	}
}
}
