package com.myco.stock.news.service;

import com.myco.stock.news.entities.FilteredNews;

public interface IServiceFilter {
	public FilteredNews getRelevantNews(String country, String category, String keyword);
}
