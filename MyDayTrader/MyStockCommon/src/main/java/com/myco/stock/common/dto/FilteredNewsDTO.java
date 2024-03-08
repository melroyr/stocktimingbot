package com.myco.stock.common.dto;

import java.util.Date;
import java.util.List;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class FilteredNewsDTO {
	
	private String ticker;
	private List<String> newsTitles;
	private List<String> newsDescs;
	private Date startDate;
	private Date endDate;
	
}
