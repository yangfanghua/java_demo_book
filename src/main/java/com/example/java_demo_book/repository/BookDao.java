package com.example.java_demo_book.repository;

import java.util.List;
import java.util.Set;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.example.java_demo_book.entity.Book;

@Repository
public interface BookDao extends JpaRepository<Book, String> {
	
	
	List<Book> findByClassification(String classification); //透過分類來尋找>分類を通して探す
	
	List<Book> findByIsbnOrNameOrWriter(String isbn,String name,String writer);//尋找Isbn或書名或作者>バーコードや本の書名や著者を通して探す
	
	List<Book> findByName(String name); //透過書名尋找>本の書名を通して探す
	
	List<Book> findTop5ByOrderBySalesDesc();//尋找銷售量前5名>売上高上位5位を探す

}
