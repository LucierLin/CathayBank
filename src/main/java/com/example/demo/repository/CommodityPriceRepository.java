package com.example.demo.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.example.demo.dto.CommodityPrice;

@Repository
public interface CommodityPriceRepository extends JpaRepository<CommodityPrice, Long> {

}