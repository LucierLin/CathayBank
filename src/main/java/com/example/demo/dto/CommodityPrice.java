package com.example.demo.dto;

import java.time.LocalDateTime;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "COMMODITY_PRICE")
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class CommodityPrice {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "ID")
	private long id;

	@Column(name = "Commodity_ID")
	private long commodityId;

	@Column(name = "DATE_TIME")
	private LocalDateTime dateTime;

	@Column(name = "UNIT_PRICE")
	private Double unitPrice;

}
