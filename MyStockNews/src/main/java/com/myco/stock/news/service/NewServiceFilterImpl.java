package com.myco.stock.news.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import com.myco.stock.news.entities.Article;
import com.myco.stock.news.entities.FilteredArticle;
import com.myco.stock.news.entities.FilteredNews;
import com.myco.stock.news.entities.News;
import com.myco.stock.news.repository.FilteredNewsRepository;

@Service
public class NewServiceFilterImpl implements IServiceFilter {
	
	@Autowired
	private FilteredNewsRepository filteredNewsRepository;
	
    private final String newAPIURl = "https://newsapi.org/v2/top-headlines?country={countryName}&category={categoryValue}&apiKey=e9476942946e4e4298ab8815d16cd689";

    @Override
    public FilteredNews getRelevantNews(String country, String category, String keyword) {

        String endPoint = newAPIURl.replace("{countryName}", country);
        endPoint = endPoint.replace("{categoryValue}", category);
        RestTemplate restTemplate = new RestTemplate();
        News news = restTemplate.getForObject(endPoint, News.class);

        FilteredNews filteredNews = new FilteredNews();
        filteredNews.getCriteria().setCountry(country);
        filteredNews.getCriteria().setCategory(category);
        filteredNews.getCriteria().setKeyword(keyword);
        
        for (Article article : news.getArticles()) {

            StringBuilder resultbuilder = new StringBuilder();
            resultbuilder.append(article.getAuthor() == null ? "" : article.getAuthor());
            resultbuilder.append(article.getTitle() == null ? "" : article.getTitle());
            resultbuilder.append(article.getDescription() == null ? "" : article.getDescription());
            resultbuilder.append(article.getContent() == null ? "" : article.getContent());

            if(resultbuilder.toString().toLowerCase().contains(keyword)) {
            	FilteredArticle filteredArticle = new FilteredArticle();
            	String articleTitle = article.getTitle();
                if(articleTitle != null) {
                    filteredArticle.setTitle(articleTitle);
                }

                String articleDesc = article.getDescription();
                if(articleDesc != null) {
                    filteredArticle.setDescription(articleDesc);
                }
                filteredNews.getFilteredArticles().add(filteredArticle);
            }
        }
        
        filteredNewsRepository.save(filteredNews);
        return filteredNews;
    }
}
