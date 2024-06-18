package com.example.demo.fund;

import com.fasterxml.jackson.annotation.JsonProperty;

import lombok.Data;

@Data
public class FundChangeResponse {
	@JsonProperty("upsAndDowns")
	private Double upsAndDowns;

	@JsonProperty("quoteChange")
	private Double quoteChange;

}
