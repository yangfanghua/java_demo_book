package com.example.java_demo_book.repository;

import java.util.List;
import java.util.Set;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.example.java_demo_book.entity.Book;

@Repository
public interface BookDao extends JpaRepository<Book, String> {
	
	
	List<Book> findByClassification(String classification); //查詢類別
	
	List<Book> findByIsbnOrNameOrWriter(String isbn,String name,String writer);//尋找Isbn或書名或作者
	
	List<Book> findByName(String name); //透過書名尋找
	
	List<Book> findTop5ByOrderBySalesDesc();//尋找銷售量前5

}
