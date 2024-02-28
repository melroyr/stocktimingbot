package com.myco.stock.trader.service;

import java.io.IOException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import com.myco.stock.trader.config.MyStockTraderApplicationConfig;
import com.myco.stock.trader.domain.StocTradeData;
import com.myco.stock.trader.repository.MyStockTraderApplicationRespository;


@Service
public class MyStockTraderApplicationService {
	private final MyStockTraderApplicationConfig config;
	private final RestTemplate restTemplate;

	@Autowired
	private MyStockTraderApplicationRespository stockrepo;
	
	public MyStockTraderApplicationService(MyStockTraderApplicationConfig config) {
		this.config = config;
		this.restTemplate = new RestTemplate();
	}
	
	public String getStockData(String symbol, String function) throws IOException, InterruptedException {
		String apiKey = config.getApiKey();
		String apiUrl = "https://www.alphavantage.co/query?function=" + function + "&symbol=" + symbol + "&apikey="
				+ apiKey;
		ResponseEntity<String> response = restTemplate.getForEntity(apiUrl, String.class);
		
		String json = response.getBody();
		
		ObjectMapper objectMapper = new ObjectMapper();
		StocTradeData apiData = objectMapper.readValue(json, StocTradeData.class);
		
		return json;
	}
	
	public String getStockPrice(String symbol, String function) throws IOException, InterruptedException {
		String jsonData = getStockData(symbol, function);
		ObjectMapper objectMapper = new ObjectMapper();
		StocTradeData apiData = objectMapper.readValue(jsonData, StocTradeData.class);
		apiData.setData(jsonData);
		stockrepo.save(apiData);
		return "Work Done";
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