package com.myco.stock.trader.domain;

import java.util.Date;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.OneToOne;
import jakarta.persistence.Table;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Data
@NoArgsConstructor
@Table(name = "globalquote")
@JsonIgnoreProperties(ignoreUnknown = true)
//@JsonSerialize(include = JsonSerialize.Inclusion.NON_NULL)
public class GlobalQuote {
	
	@Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;
	
	@JsonProperty(value = "01. symbol")
	private String symbol;
	
	@JsonProperty(value = "02. open")
    private double open;
	
	@JsonProperty(value = "03. high")
    private double high;
	
	@JsonProperty(value = "04. low")
    private double low;
	
	@JsonProperty(value = "05. price")
    private double price;
	
	@JsonProperty(value = "06. volume")
    private int volume;
	
	@JsonProperty(value = "07. latest trading day")
    private Date ltd;
	
	@JsonProperty(value = "08. previous close")
    private double previousClose;
	
	@JsonProperty(value = "09. change")
    private double priceChange;
	
	@OneToOne(mappedBy = "globalQuote")
    private StocTradeData apiData;
	
	//private Long globalquote_id;
	
	//@JsonProperty(value = "10. change percent")
    //private double changePercent;

}
