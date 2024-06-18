package com.example.demo.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.example.demo.dto.Commodity;

@Repository
public interface CommodityRepository extends JpaRepository<Commodity, Integer>{

}
