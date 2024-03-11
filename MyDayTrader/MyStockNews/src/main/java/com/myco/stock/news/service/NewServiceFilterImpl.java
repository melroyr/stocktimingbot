package com.myco.stock.news.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import com.myco.stock.news.entities.Article;
import com.myco.stock.news.entities.FilteredArticle;
import com.myco.stock.news.entities.FilteredNews;
import com.myco.stock.news.entities.News;
import com.myco.stock.news.repository.FilteredNewsRepository;

import java.util.Date;
import java.util.Calendar;
import java.text.SimpleDateFormat;

@Service
public class NewServiceFilterImpl implements IServiceFilter {
	
	@Autowired
	private FilteredNewsRepository filteredNewsRepository;
	
    private final String newAPIURl = "https://newsapi.org/v2/everything?q={ticker}&from={startDate}&sortBy=popularity&apiKey=e9476942946e4e4298ab8815d16cd689";

    @Override
    public FilteredNews getRelevantNews(String ticker, String keyword) {

        String endPoint = newAPIURl.replace("{ticker}", ticker);
        
        Calendar cal  = Calendar.getInstance();
        //subtracting a day
        cal.add(Calendar.DATE, -1);

        SimpleDateFormat s = new SimpleDateFormat("yyyy-MM-dd");
        String previousDay = s.format(new Date(cal.getTimeInMillis()));
        
        endPoint = endPoint.replace("{startDate}", previousDay);
        RestTemplate restTemplate = new RestTemplate();
        News news = restTemplate.getForObject(endPoint, News.class);

        FilteredNews filteredNews = new FilteredNews();
        filteredNews.setTicker(ticker);
        filteredNews.getCriteria().setKeyword(keyword);
        
        System.out.println("News Article Count: " + news.getArticles().size());
        System.out.println("Loocking for: " + keyword);
        for (Article article : news.getArticles()) {

            StringBuilder resultbuilder = new StringBuilder();
            resultbuilder.append(article.getAuthor() == null ? "" : article.getAuthor());
            resultbuilder.append(article.getTitle() == null ? "" : article.getTitle());
            resultbuilder.append(article.getDescription() == null ? "" : article.getDescription());
            resultbuilder.append(article.getContent() == null ? "" : article.getContent());
            
            System.out.println("ResultBuilder: " + resultbuilder.toString());

            if(resultbuilder.toString().contains(keyword)) {
            	System.out.println("Found it");
            	
            	FilteredArticle filteredArticle = new FilteredArticle();
            	String articleTitle = article.getTitle();
                if(articleTitle != null) {
                	System.out.println("Setting title");
                    filteredArticle.setTitle(articleTitle);
                }

                String articleDesc = article.getDescription();
                if(articleDesc != null) {
                	System.out.println("Setting description");
                    filteredArticle.setDescription(articleDesc);
                }
                filteredNews.getFilteredArticles().add(filteredArticle);
            }
        }
        
        System.out.println("Filtered Article Count:" + filteredNews.getFilteredArticles().size());
        
        filteredNewsRepository.save(filteredNews);
        return filteredNews;
    }
}
