package com.example.java_demo_book.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;
import com.example.java_demo_book.service.impl.BookServicelmpl;
import com.example.java_demo_book.vo.BookRequest;
import com.example.java_demo_book.vo.BookResponse;

@RestController
public class BookController {
	
	@Autowired
	private BookServicelmpl bookService;

	@PostMapping("addBook") //新增書籍
	public BookResponse addBook(@RequestBody BookRequest bookRequest){
		return bookService.addBook(bookRequest);
	}
	
	@PostMapping("editBook") //修改書籍,一定要有isbn
	public BookResponse editBook(@RequestBody BookRequest bookRequest){
		return bookService.editBook(bookRequest);
	}
	
	@PostMapping("find_classification") //搜尋類別
	public BookResponse classificationSearch(@RequestBody BookRequest bookRequest){
		return bookService.classificationSearch(bookRequest);
	}
	
	@PostMapping("consumer_Search") //消費者查詢書籍
	public BookResponse consumerSearch(@RequestBody BookRequest bookRequest){
		return bookService.consumerSearch(bookRequest);
	}
	
	@PostMapping("company_Search") //廠商查詢書籍,一定要有isbn
	public BookResponse companySearch(@RequestBody BookRequest bookRequest){
		return bookService.companySearch(bookRequest);
	}
	
	@PostMapping("purchase") //庫存更新
	public BookResponse purchase(@RequestBody BookRequest bookRequest){
		return bookService.purchase(bookRequest);
	}
	
	@PostMapping("renew") // 價錢更新
	public BookResponse renew(@RequestBody BookRequest bookRequest){
		return bookService.renew(bookRequest);
	}
	
	@PostMapping("buyBook") //購買書籍
	public BookResponse buyBook(@RequestBody BookRequest bookRequest){
		return bookService.buyBook(bookRequest);
	}
	
	@PostMapping("ranking") //銷售量前5排行
	public List<BookResponse> ranking(@RequestBody BookRequest bookRequest){
		return bookService.ranking(bookRequest.getSales());
	}
	
}
