package com.myco.stock.news.repository;

import com.myco.stock.news.entities.FilteredNews;

import org.springframework.data.jpa.repository.JpaRepository;

public interface FilteredNewsRepository extends JpaRepository<FilteredNews, Long> {
    // Repository queries if needed
}
