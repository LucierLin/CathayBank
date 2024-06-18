package com.example.demo;

import com.fasterxml.jackson.annotation.JsonProperty;

import lombok.Data;

@Data
public class FundRequest {
	@JsonProperty("id")
	private String id;

	@JsonProperty("dateTime")
	private String dateTime;

	@JsonProperty("unitPrice")
	private Double unitPrice;

	@JsonProperty("From")
	private String from;

	@JsonProperty("To")
	private String to;

}
