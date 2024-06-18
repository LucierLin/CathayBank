package com.example.demo.fund;

import freemarker.core.ParseException;

public interface FundService {

	FundChangeResponse getPriceChange(String startDate, String endDate) throws ParseException;
}
