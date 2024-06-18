package com.example.demo;

import java.time.Instant;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;

import com.example.demo.CathayResponse.DataItem;
import com.example.demo.dto.Commodity;
import com.example.demo.dto.CommodityPrice;
import com.example.demo.repository.CommodityPriceRepository;
import com.example.demo.repository.CommodityRepository;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

@RestController
@RequestMapping("/api")
public class CathayController {
	@Autowired
	private RestTemplate restTemplate;
	
	@Autowired
    private CommodityRepository commodityRepository;
	
	@Autowired
	private CommodityPriceRepository commodityPriceRepository;
	

	@PostMapping("/processRequest")
	public List<DataItem> processRequest(@RequestBody CathayRequest request) {
		CathayRequest cRequest = new CathayRequest();
		// 处理 requestBean
		CathayRequest.Req req = new CathayRequest.Req();
		List<String> keysArr = new ArrayList<>();
		keysArr.add(request.getReq().getKeys().get(0));
		req.setKeys(keysArr);
		req.setFrom(request.getReq().getFrom());
		req.setTo(request.getReq().getTo());
		cRequest.setReq(req);
		ObjectMapper objectMapper = new ObjectMapper();
		HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);
        headers.set(HttpHeaders.CONTENT_TYPE, "application/json; charset=UTF-8");


		try {
			// 將 Req 物件轉換為 JSON 字符串
			String jsonReq = objectMapper.writeValueAsString(cRequest);
			HttpEntity<String> entity = new HttpEntity<>(jsonReq, headers);
			System.out.println(jsonReq);
			String url = "https://www.cathaybk.com.tw/cathaybk/service/newwealth/fund/chartservice.asmx/GetFundNavChart";
			ResponseEntity<CathayResponse> response = restTemplate.postForEntity(url, entity, CathayResponse.class);
			CathayResponse cResponse = response.getBody();
			System.out.println(cResponse);
			List<DataItem> dataList = cResponse.getData();
			for (CathayResponse.DataItem dataItem : dataList) {
	            Commodity commodity = new Commodity();
	            commodity.setId(Integer.valueOf(dataItem.getId()));
	            commodity.setName(dataItem.getName());
	            commodity.setShortName(dataItem.getShortName());
	            commodity.setDataGrouping(dataItem.isDataGrouping());
	            commodityRepository.save(commodity);
	            for (List<Double> priceData : dataItem.getData()) {
	                CommodityPrice price = new CommodityPrice();
	                price.setCommodityId(Integer.valueOf(dataItem.getId()));
//	                price.setSeqPkId(dataItem.getId());
	                long timestamp = priceData.get(0).longValue(); // Convert Double to Long
	                LocalDateTime localDateTime = Instant.ofEpochMilli(timestamp).atZone(ZoneId.systemDefault()).toLocalDateTime();
	                price.setDateTime(localDateTime);
	                price.setUnitPrice(priceData.get(1));

	                commodityPriceRepository.save(price);
	            }

	         
	        }
	 
			return dataList;
		} catch (JsonProcessingException e) {
			e.printStackTrace();
			return null;
		}

//		return "Request processed for keys: " + keys + ", from: " + from + ", to: " + to;

	}

}
