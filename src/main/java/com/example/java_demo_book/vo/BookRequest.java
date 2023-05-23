package com.example.java_demo_book.vo;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Set;

import com.example.java_demo_book.entity.Book;
import com.fasterxml.jackson.annotation.JsonProperty;

public class BookRequest {
	@JsonProperty("booklist")

	
	private String isbn; //條碼>バーコード
	private String name ; //書名>書名
	private String writer; //作者>著者
	private Integer price; //價格>価格
	private Integer stock;//庫存>在庫
	private Integer sales;//銷售量>販売量
	private String classification; //分類
	private Integer amount;//數量
	private String identity;//人
	
	public ArrayList<Book> booklist;
	public List<String> booklistString; 
	public Set<String> findclassification; //搜尋類別>
	public Map<String,Integer> buybooks; //購買書籍與數量>購入する本と購入数量が入っている
	
	public ArrayList<Book> getBooklist() {
		return booklist;
	}
	public void setBooklist(ArrayList<Book> booklist) {
		this.booklist = booklist;
	}
	
	public List<String> getBooklistString() {
		return booklistString;
	}
	public void setBooklistString(List<String> booklistString) {
		this.booklistString = booklistString;
	}
	public Set<String> getFindclassification() {
		return findclassification;
	}
	public void setFindclassification(Set<String> findclassification) {
		this.findclassification = findclassification;
	}
	
	public Map<String,Integer> getBuybooks() {
		return buybooks;
	}
	public void setBuybooks(Map<String,Integer> buybooks) {
		this.buybooks = buybooks;
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
	public String getIdentity() {
		return identity;
	}
	public void setIdentity(String person) {
		this.identity = person;
	}
	
	
	
	
	
	
	
}
