package com.example.demo;

import java.util.List;

import com.fasterxml.jackson.annotation.JsonProperty;

import lombok.Data;

@Data
public class CathayRequest {

	private Req req;

	@Data
	public static class Req {
		@JsonProperty("Keys")
		private List<String> Keys;
		@JsonProperty("From")
		private String From;
		@JsonProperty("To")
		private String To;
	}

}
