package com.example.java_demo_book.vo;

import java.util.ArrayList;
import java.util.List;
import com.example.java_demo_book.entity.Book;
import com.fasterxml.jackson.annotation.JsonInclude;


@JsonInclude(JsonInclude.Include.NON_NULL)
public class BookResponse {
	
	private ArrayList<Book> booklist;
	private List<BookResponse> responselist;
	private String isbn; //條碼
	private String name ; //書名
	private String writer; //作者
	private Integer price; //價格
	private Integer stock;//庫存
	private Integer sales;//銷售量
	private String classification; //分類
	private String message; //訊息
	private Integer amount;//數量
	private Integer total;//總金額
	private ArrayList<Book> errobooklist;
	private List<BookResponse> errobooklistResponse;
	
	public BookResponse() {

	}
	
	public BookResponse(String message) {
		this.message = message;
	}
	
	
	public BookResponse(String classification, String message) {
		super();
		this.classification = classification;
		this.message = message;
	}

	public BookResponse(ArrayList<Book> booklist, String message) {
		this.booklist = booklist;
		this.message = message;
	}
	
	public BookResponse(List<BookResponse> responselist, String message) {
		this.responselist = responselist;
		this.message = message;
	}
	
	public BookResponse(List<BookResponse> responselist, String message,List<BookResponse> errobooklistResponse) {
		this.errobooklistResponse = errobooklistResponse;
		this.responselist = responselist;
		this.message = message;
	}

	
//	public BookResponse(ArrayList<Book> booklist, Integer amount, Integer total, String message) {
//		this.booklist = booklist;
//		this.message = message;
//		this.amount = amount;
//		this.total = total;
//	}

	public BookResponse(String isbn, String name, String writer, Integer price, 
			Integer stock, Integer sales,String classification, String message) {
		this.isbn = isbn;
		this.name = name;
		this.writer = writer;
		this.price = price;
		this.stock = stock;
		this.sales = sales;
		this.classification = classification;
		this.message = message;
	}



	
	public List<BookResponse> getResponselist() {
		return responselist;
	}
	public void setResponselist(List<BookResponse> responselist) {
		this.responselist = responselist;
	}
	
	
	public ArrayList<Book> getBooklist() {
		return booklist;
	}
	public void setBooklist(ArrayList<Book> booklist) {
		this.booklist = booklist;
	}


	public String getIsbn() {
		return isbn;
	}
	public void setIsbn(String isbn) {
		this.isbn = isbn;
	}


	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}



	public String getWriter() {
		return writer;
	}
	public void setWriter(String writer) {
		this.writer = writer;
	}



	public Integer getPrice() {
		return price;
	}
	public void setPrice(Integer price) {
		this.price = price;
	}



	public Integer getStock() {
		return stock;
	}



	public void setStock(Integer stock) {
		this.stock = stock;
	}



	public Integer getSales() {
		return sales;
	}



	public void setSales(Integer sales) {
		this.sales = sales;
	}



	public String getClassification() {
		return classification;
	}



	public void setClassification(String classification) {
		this.classification = classification;
	}


	

	public Integer getAmount() {
		return amount;
	}

	public void setAmount(Integer amount) {
		this.amount = amount;
	}

	public Integer getTotal() {
		return total;
	}

	public void setTotal(Integer total) {
		this.total = total;
	}

	public String getMessage() {
		return message;
	}



	public void setMessage(String message) {
		this.message = message;
	}

	public ArrayList<Book> getErrobooklist() {
		return errobooklist;
	}

	public void setErrobooklist(ArrayList<Book> errobooklist) {
		this.errobooklist = errobooklist;
	}



	public List<BookResponse> getErrobooklistResponse() {
		return errobooklistResponse;
	}

	public void setErrobooklistResponse(List<BookResponse> errobooklistResponse) {
		this.errobooklistResponse = errobooklistResponse;
	}
	
	
	
	
	
	
	
}
