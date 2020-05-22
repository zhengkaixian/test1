package com.zero.base.common.rest.client;

import java.net.URI;
import java.util.Map;

import org.springframework.web.client.RestTemplate;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;

public class DmRestTemplate {
	
	private RestTemplate restTemplate;

	public DmRestTemplate(RestTemplate restTemplate) {
		this.restTemplate = restTemplate;
	}

	public <T> T getForObject(String url, TypeReference<T> tr, Object... urlVariables) throws Exception {
		ObjectMapper mapper = new ObjectMapper();
		return mapper.readValue(restTemplate.getForObject(url, String.class, urlVariables),tr);
	}

	public <T> T getForObject(String url, TypeReference<T> tr,  Map<String, ?> urlVariables) throws Exception {
		ObjectMapper mapper = new ObjectMapper();
		return mapper.readValue(restTemplate.getForObject(url, String.class, urlVariables),tr);
	}

	public <T> T getForObject(URI url, TypeReference<T> tr) throws Exception {
		ObjectMapper mapper = new ObjectMapper();
		return mapper.readValue(restTemplate.getForObject(url, String.class),tr);
	}

	public <T> T postForObject(String url, Object request, TypeReference<T> tr ,Object... uriVariables)
			throws Exception {
		ObjectMapper mapper = new ObjectMapper();
		return mapper.readValue(restTemplate.postForObject(url, request, String.class, uriVariables),tr);
	}

	public <T> T postForObject(String url, Object request, TypeReference<T> tr,  Map<String, ?> uriVariables)
			throws Exception {
		ObjectMapper mapper = new ObjectMapper();
		return mapper.readValue(restTemplate.postForObject(url, request, String.class, uriVariables),tr);
	}

	public <T> T postForObject(URI url, Object request, TypeReference<T> tr) throws Exception {
		ObjectMapper mapper = new ObjectMapper();
		return mapper.readValue(restTemplate.postForObject(url, request, String.class),tr);
	}
}
