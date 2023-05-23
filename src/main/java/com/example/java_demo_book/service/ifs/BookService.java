package com.example.java_demo_book.service.ifs;

import java.util.List;
import com.example.java_demo_book.vo.BookRequest;
import com.example.java_demo_book.vo.BookResponse;

public interface BookService {

	public BookResponse addBook(BookRequest bookRequest);//新增書籍
	
	public BookResponse editBook(BookRequest bookRequest);//修改書籍,一定要有isbn
	
	public BookResponse classificationSearch(BookRequest bookRequest);//搜尋類別
	
	public BookResponse consumerSearch(BookRequest bookRequest);//查詢書籍<消費者+廠商>
	
//	public BookResponse companySearch(BookRequest bookRequest);//廠商查詢書籍,一定要有isbn
	
	public BookResponse purchase(BookRequest bookRequest);//庫存更新
	
	public BookResponse renew(BookRequest bookRequest) ;// 價錢更新
	
	public BookResponse buyBook(BookRequest bookRequest) ;//購買書籍
	
	public List<BookResponse> ranking(Integer sales);//銷售量前5排行
		
	
		
	
		
	
}
