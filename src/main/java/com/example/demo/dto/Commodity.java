package com.example.demo.dto;


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
@Table(name = "COMMODITY")
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Commodity {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "SEQ_PK_ID")
	private int seqPkId;

	@Column(name = "NAME")
	private String name;

	@Column(name = "SHORT_NAME")
	private String shortName;

	@Column(name = "ID")
	private long id;

	@Column(name = "DATA_GROUPING")
	private boolean dataGrouping;

}
