package com.myco.stock.common.dto;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class StockAdviceDTO {
	
	private String ticker;
	private String function;
	private double sentiment;

}
