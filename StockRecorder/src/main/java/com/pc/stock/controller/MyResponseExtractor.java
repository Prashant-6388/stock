package com.pc.stock.controller;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.nio.charset.Charset;
import java.nio.charset.StandardCharsets;
import java.util.Map;
import java.util.Scanner;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.client.ClientHttpResponse;
import org.springframework.web.client.ResponseExtractor;

import com.google.gson.Gson;
import com.pc.stock.model.AVResponse;

public class MyResponseExtractor<T> implements ResponseExtractor<String> {
	
	@Override
	public String extractData(ClientHttpResponse response) throws IOException {
		System.out.println("Trying to extract response "+response);
		String output = convert(response.getBody(), StandardCharsets.UTF_8);
		System.out.println("response = "+output);
		Gson gson = new Gson();
		Map map = gson.fromJson(output, Map.class);
		return output;
	}

	public String convert(InputStream inputStream, Charset charset) throws IOException {
		 
		try (BufferedReader br = new BufferedReader(new InputStreamReader(inputStream, charset))) {
			return br.lines().collect(Collectors.joining(System.lineSeparator()));
		}
	}
}
