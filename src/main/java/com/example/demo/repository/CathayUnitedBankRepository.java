package com.example.demo.repository;

import java.time.LocalDateTime;
import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import com.example.demo.dto.CommodityPrice;

@Repository
public interface CathayUnitedBankRepository extends JpaRepository<CommodityPrice, Long> {
	/**
	 * findByDateTime 查詢商品價格資訊
	 * 
	 * @param dateTime 指定日期
	 * @return 商品價格資訊
	 */
	List<CommodityPrice> findByDateTimeBetween(LocalDateTime startOfDay, LocalDateTime endOfDay);

	/**
	 * updateCommodityDetail 修改某日價格
	 * 
	 * @param commodityId 商品id
	 * @param dateTime    指定日期
	 * @param unitPrice   價格
	 */
	@Transactional
	@Modifying
	@Query("UPDATE CommodityPrice cp SET cp.unitPrice = :unitPrice WHERE cp.commodityId = :id AND cp.dateTime BETWEEN :startOfDay AND :endOfDay")
	void updatePriceByDate(@Param("id") String id, @Param("startOfDay") LocalDateTime startOfDay,
			@Param("endOfDay") LocalDateTime endOfDay, @Param("unitPrice") double unitPrice);

	/**
	 * insertCommodityPrice 新增價格
	 * 
	 * @param id        商品id
	 * @param dateTime  當日日期
	 * @param unitPrice 價格
	 */
	@Modifying
	@Transactional
	@Query("INSERT INTO CommodityPrice (commodityId, dateTime, unitPrice) VALUES (:id, :dateTime, :unitPrice)")
	void insertPrice(String id, LocalDateTime dateTime, double unitPrice);

	/**
	 * deleteCommodityDetail 刪除某日價格
	 * 
	 * @param id
	 * @param dateTime 指定日期
	 */
	@Transactional
	@Modifying
	@Query("DELETE FROM CommodityPrice cp WHERE cp.commodityId = :id AND cp.dateTime BETWEEN :startOfDay AND :endOfDay")
	void deletePriceByDate(@Param("id") String id, @Param("startOfDay") LocalDateTime startOfDay,
			@Param("endOfDay") LocalDateTime endOfDay);

	/**
	 * stockPriceIncrease 計算漲跌和漲跌幅
	 * 
	 * @param to   開始時間
	 * @param from 結束時間
	 * @return 商品價格資訊
	 */
//	List<CommodityPrice> stockPriceIncrease(String to, String from);

	@Query("SELECT cp FROM CommodityPrice cp WHERE cp.dateTime BETWEEN :startDate AND :endDate")
	List<CommodityPrice> findPriceBetweenDates(@Param("startDate") LocalDateTime startDate,
			@Param("endDate") LocalDateTime endDate);

}
