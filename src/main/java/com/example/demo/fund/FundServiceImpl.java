package com.example.demo.fund;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.demo.dto.CommodityPrice;
import com.example.demo.repository.CathayUnitedBankRepository;

import freemarker.core.ParseException;

@Service
public class FundServiceImpl implements FundService {

	@Autowired
	CathayUnitedBankRepository cathayUnitedBankRepository;

	@Override
	public FundChangeResponse getPriceChange(String startDate, String endDate) throws ParseException {
		FundChangeResponse res = new FundChangeResponse();
		DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy/MM/dd");
		LocalDate localStartDate = LocalDate.parse(startDate, formatter);
		LocalDateTime startOfDay = localStartDate.atStartOfDay();
		LocalDate localEndDate = LocalDate.parse(endDate, formatter);
		LocalDateTime endOfDay = localEndDate.atTime(23, 59, 59);
		List<CommodityPrice> commodityResults = cathayUnitedBankRepository.findPriceBetweenDates(startOfDay, endOfDay);
		if (commodityResults.size() < 2) {
			return res;
		} else {
			Double upsAndDowns = commodityResults.get(commodityResults.size() - 1).getUnitPrice() - commodityResults.get(0).getUnitPrice();
			upsAndDowns = Math.round(upsAndDowns * 100.0) / 100.0;
			res.setUpsAndDowns(upsAndDowns);
			Double quoteChange = upsAndDowns / commodityResults.get(0).getUnitPrice();
			quoteChange = Math.round(quoteChange * 100.0) / 100.0;
			res.setQuoteChange(quoteChange);
			return res;
		}
	}

}
