package com.myco.stock.sentiment.util;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.ObjectWriter;
import com.myco.stock.common.dto.FilteredNewsDTO;

public class Java2JSON {
	
	public static void main(String[] args) throws JsonProcessingException {
		ObjectWriter ow = new ObjectMapper().writer().withDefaultPrettyPrinter();
		FilteredNewsDTO filteredNewsDTO = new FilteredNewsDTO();
		filteredNewsDTO.setTicker("GOOG");
		filteredNewsDTO.setStartDate(new Date());
		filteredNewsDTO.setEndDate(new Date());
		
		List<String> titles = new ArrayList<>();
		titles.add("Google posts amazing profits");
		titles.add("Google had a good day");
		titles.add("Google posts shocking revenue loss");
		filteredNewsDTO.setNewsTitles(titles);
		
		List<String> descs = new ArrayList<>();
		descs.add("Desc Google posts amazing profits");
		descs.add("Desc Google had a good day");
		descs.add("Desc Google posts shocking revenue loss");
		filteredNewsDTO.setNewsDescs(descs);
		
		String json = ow.writeValueAsString(filteredNewsDTO);
		
		System.out.println("JSON: " + json);
	}

}
