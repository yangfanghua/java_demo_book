package com.example.java_demo_book.controller;

import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;
import com.example.java_demo_book.service.lmpl.BookServicelmpl;
import com.example.java_demo_book.vo.BookRequest;
import com.example.java_demo_book.vo.BookResponse;

@CrossOrigin
@RestController
public class BookController {
	
	@Autowired
	private BookServicelmpl bookService;

	@PostMapping("addBook") //新增書籍>ブックを追加する
	public BookResponse addBook(@RequestBody BookRequest bookRequest){
		return bookService.addBook(bookRequest);
	}
	
	@PostMapping("editBook") //修改書籍>書籍の修正する
	public BookResponse editBook(@RequestBody BookRequest bookRequest){
		return bookService.editBook(bookRequest);
	}
	
	@PostMapping("find_classification") //搜尋類別>分類の検索する
	public BookResponse classificationSearch(@RequestBody BookRequest bookRequest){
		return bookService.classificationSearch(bookRequest);
	}
	
	@PostMapping("book_Search") //查詢書籍<消費者+廠商>>消費者とメーカーは書籍を照会する
	public BookResponse consumerSearch(@RequestBody BookRequest bookRequest){
		return bookService.consumerSearch(bookRequest);
	}
	
//	@PostMapping("company_Search") //廠商查詢書籍,一定要有isbn>メーカーは書籍を照会する
//	public BookResponse companySearch(@RequestBody BookRequest bookRequest){
//		return bookService.companySearch(bookRequest);
//	}
	
	
	@PostMapping("purchase") //庫存更新>在庫を更新する
	public BookResponse purchase(@RequestBody BookRequest bookRequest){
		return bookService.purchase(bookRequest);
	}
	
	@PostMapping("renew") // 價錢更新＞価格を更新する
	public BookResponse renew(@RequestBody BookRequest bookRequest){
		return bookService.renew(bookRequest);
	}
	
	@PostMapping("buyBook") //購買書籍>ブックを購入する
	public BookResponse buyBook(@RequestBody BookRequest bookRequest){
		return bookService.buyBook(bookRequest);
	}
	
	@PostMapping("ranking") //銷售量前5排行>トップ5の販売量を参照する
	public List<BookResponse> ranking(@RequestBody BookRequest bookRequest){
		return bookService.ranking(bookRequest.getSales());
	}
	
}
