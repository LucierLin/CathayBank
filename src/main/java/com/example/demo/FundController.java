package com.example.demo;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.example.demo.dto.CommodityPrice;
import com.example.demo.fund.FundChangeResponse;
import com.example.demo.fund.FundService;
import com.example.demo.repository.CathayUnitedBankRepository;

import freemarker.core.ParseException;

@RestController
@RequestMapping("/fund")
public class FundController {
	@Autowired
	CathayUnitedBankRepository cathayUnitedBankRepository;

	@Autowired
	FundService fundService;

	@PostMapping(value = "/findByDateTime", produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<List<CommodityPrice>> findByDateTime(@RequestBody FundRequest request) {
		DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy/MM/dd");
		LocalDate localDate = LocalDate.parse(request.getDateTime(), formatter);
		LocalDateTime startOfDay = localDate.atStartOfDay();
		LocalDateTime endOfDay = localDate.atTime(23, 59, 59);

		List<CommodityPrice> result = cathayUnitedBankRepository.findByDateTimeBetween(startOfDay, endOfDay);
		return new ResponseEntity<>(result, HttpStatus.OK);
	}

	@PostMapping(value = "/updatePriceByDate", produces = MediaType.APPLICATION_JSON_VALUE)
	public void updatePriceByDate(@RequestBody FundRequest request) {
		DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy/MM/dd");
		LocalDate localDate = LocalDate.parse(request.getDateTime(), formatter);
		LocalDateTime startOfDay = localDate.atStartOfDay();
		LocalDateTime endOfDay = localDate.atTime(23, 59, 59);

		cathayUnitedBankRepository.updatePriceByDate(request.getId(), startOfDay, endOfDay, request.getUnitPrice());
	}

	@PostMapping(value = "/insertPrice", produces = MediaType.APPLICATION_JSON_VALUE)
	public void insertPrice(@RequestBody FundRequest request) {
		LocalDateTime dateTime = LocalDateTime.now();
		try {
			cathayUnitedBankRepository.insertPrice(request.getId(), dateTime, request.getUnitPrice());
		} catch (Exception e) {
			System.out.println("insertPrice error");
		}
	}

	@PostMapping(value = "/deletePriceByDate", produces = MediaType.APPLICATION_JSON_VALUE)
	public void deletePriceByDate(@RequestBody FundRequest request) {
		DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy/MM/dd");
		LocalDate localDate = LocalDate.parse(request.getDateTime(), formatter);
		LocalDateTime startOfDay = localDate.atStartOfDay();
		LocalDateTime endOfDay = localDate.atTime(23, 59, 59);
		cathayUnitedBankRepository.deletePriceByDate(request.getId(), startOfDay, endOfDay);
	}

	@PostMapping(value = "/caculatePriceByDate", produces = MediaType.APPLICATION_JSON_VALUE)
	public FundChangeResponse caculatePriceByDate(@RequestBody FundRequest request) throws ParseException {

		return fundService.getPriceChange(request.getFrom(), request.getTo());
	}

}
