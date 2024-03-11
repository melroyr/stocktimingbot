package com.myco.stock.common.dto;

import java.util.Date;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class TickerNetSentimentDTO {
	
	private Long id;
	
	private String ticker;
	private Date startDatetime;
	private Date endDatetime;
	private double netSentiment;
	

}
