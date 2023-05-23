package com.example.java_demo_book.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name="Book")
public class Book {
	@Id
	@Column(name = "isbn")
	String isbn;//條碼>isbnコード
	@Column(name = "name")
	String name ;//書名
	@Column(name = "writer")
	String writer;//作者>著者
	@Column(name = "price")
	Integer price;//價格>価格
	@Column(name = "stock")
	Integer stock;//庫存>在庫
	@Column(name = "sales")
	Integer sales;//銷售量>販売量
	@Column(name = "classification")
	String classification; //分類
	
	
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getIsbn() {
		return isbn;
	}
	public void setIsbn(String isbn) {
		this.isbn = isbn;
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
	
	
	
	
}
