package com.example.demo;

import java.util.List;

import com.fasterxml.jackson.annotation.JsonProperty;

import lombok.Data;

@Data
public class CathayResponse {
	@JsonProperty("statusCode")
	private int statusCode;

	@JsonProperty("Message")
	private String message;

	@JsonProperty("Data")
	private List<DataItem> data;

	@Data
	public static class DataItem {
		@JsonProperty("name")
		private String name;

		@JsonProperty("shortName")
		private String shortName;

		@JsonProperty("id")
		private String id;

		@JsonProperty("dataGrouping")
		private boolean dataGrouping;

		@JsonProperty("data")
		private List<List<Double>> data;
	}

}
