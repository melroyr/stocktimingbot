package com.myco.stock.trader.service;

import java.io.IOException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.myco.stock.trader.config.MyStockTraderApplicationConfig;
import com.myco.stock.trader.domain.StockTradeData;
import com.myco.stock.trader.repository.MyStockTraderApplicationRespository;


@Service
public class MyStockTraderApplicationService {
	private final MyStockTraderApplicationConfig config;
	private final RestTemplate restTemplate;
	
	public static String BUY = "BUY";
	public static String SELL = "SELL";
	public static String HOLD = "HOLD";

	@Autowired
	private MyStockTraderApplicationRespository stockrepo;
	
	public MyStockTraderApplicationService(MyStockTraderApplicationConfig config) {
		this.config = config;
		this.restTemplate = new RestTemplate();
	}
	
	public String getStockData(String symbol, String function) throws IOException, InterruptedException {
		System.out.println("Trader Symbol:" + symbol);
		System.out.println("Trader Function:" + function);
		String apiKey = config.getApiKey();
		String apiUrl = "https://www.alphavantage.co/query?function=" + function + "&symbol=" + symbol + "&apikey="
				+ apiKey;
		//https://www.alphavantage.co/query?function=TIME_SERIES_DAILY&symbol=IBM&apikey=demo
		ResponseEntity<String> response = restTemplate.getForEntity(apiUrl, String.class);
		
		String json = response.getBody();
		
		ObjectMapper objectMapper = new ObjectMapper();
		StockTradeData apiData = objectMapper.readValue(json, StockTradeData.class);
		
		return json;
	}
	
	public String getStockPrice(String symbol, String function, double sentiment) throws IOException, InterruptedException {
		String jsonData = getStockData(symbol, function);
		ObjectMapper objectMapper = new ObjectMapper();
		StockTradeData apiData = objectMapper.readValue(jsonData, StockTradeData.class);
		
		double high = apiData.getGlobalQuote().getHigh();
		double low = apiData.getGlobalQuote().getLow();
		double price = apiData.getGlobalQuote().getPrice();
		double prevPrice = apiData.getGlobalQuote().getPreviousClose();
		
		System.out.println("High: " + high);
		System.out.println("Low: " + low);
		System.out.println("Price: " + price);
		System.out.println("Prev Price: " + prevPrice);
		
		double priceRelativeToHigh = high - price;
		double priceRelativeToLow = price - low;
		double priceRelativeToHighLow;
		if (priceRelativeToHigh < priceRelativeToLow) {
			priceRelativeToHighLow = 1;
		} else if (priceRelativeToHigh > priceRelativeToLow) {
			priceRelativeToHighLow = -1;
		} else {
			priceRelativeToHighLow = 0;
		}
		
		double priceDiff = price - prevPrice;
		double priceRelativeToPrevPrice;
		if (priceDiff > 0) {
			priceRelativeToPrevPrice = 1;
		} else if (priceDiff < 0) {
			priceRelativeToPrevPrice = -1;
		} else {
			priceRelativeToPrevPrice = 0;
		}
		
		System.out.println("Price Relative to high low: " + priceRelativeToHighLow);
		System.out.println("Price Relative to prev price: " + priceRelativeToPrevPrice);
		System.out.println("Stock Sentiment: " + sentiment);
		
		String advice = HOLD;
		if ((priceRelativeToHighLow == 0) &&
				(priceRelativeToPrevPrice == 0) &&
				(sentiment == 0)) {
			System.out.println("HOLD");
			advice = HOLD;
		} else if((priceRelativeToPrevPrice < 0) &&
				(sentiment > 0)) {
			System.out.println("BUY");
			advice = BUY;
		} else if ((priceRelativeToPrevPrice > 0) &&
				(sentiment < 0)) {
			System.out.println("SELL");
			advice = SELL;
		} else {
			System.out.println("No advice at the moment");
		}
		
		apiData.setAdvice(advice);
		apiData.setData(jsonData);
		stockrepo.save(apiData);
		return advice;
	}
}
	
	/*
	 * HttpClient httpClient = HttpClient.newHttpClient(); HttpRequest httpRequest =
	 * HttpRequest.newBuilder().GET().uri(URI.create(apiUrl)).build();
	 * 
	 * HttpResponse<String> response = httpClient.send(httpRequest,
	 * HttpResponse.BodyHandlers.ofString()); String json = response.body();
	 * System.out.println(json); //string to stringObject Gson gson = new Gson();
	 * StockPrice stockPrice = gson.fromJson(json, StockPrice.class); MetaData
	 * metaData = stockPrice.getMetaData(); StockData stockData = null;
	 * stockdatarepo.deleteAll();
	 * 
	 * switch (function) { case "TIME_SERIES_DAILY_ADJUSTED": for (Map.Entry<String,
	 * StockData> entry : stockPrice.getStockdaily().entrySet()) { String date =
	 * entry.getKey(); stockData = entry.getValue(); System.out.println(stockData);
	 * stockdatarepo.save(stockData); } break; case "TIME_SERIES_INTRADAY": for
	 * (Map.Entry<String, StockData> entry : stockPrice.getStockdaily().entrySet())
	 * { String date = entry.getKey(); stockData = entry.getValue();
	 * System.out.println(stockData); stockdatarepo.save(stockData); } break;
	 * 
	 * 
	 * case "TIME_SERIES_WEEKLY": for (Map.Entry<String, StockData> entry :
	 * stockPrice.getStockweekly().entrySet()) { String date = entry.getKey();
	 * stockData = entry.getValue(); stockdatarepo.save(stockData); } break; case
	 * "TIME_SERIES_MONTHLY": for (Map.Entry<String, StockData> entry :
	 * stockPrice.getStockMonthly().entrySet()) { String date = entry.getKey();
	 * stockData = entry.getValue(); stockdatarepo.save(stockData);
	 * System.out.println(stockData); } break; }
	 * 
	 * metaData.setSymbol(stockPrice.getMetaData().getSymbol());
	 * metadatarepo.save(metaData); System.out.println(metaData); return
	 * "data has been sent"; }
	 */
//}