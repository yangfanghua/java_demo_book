package com.example.java_demo_book.service.impl;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Optional;
import java.util.Set;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;
import com.example.java_demo_book.entity.Book;
import com.example.java_demo_book.repository.BookDao;
import com.example.java_demo_book.service.ifs.BookService;
import com.example.java_demo_book.vo.BookRequest;
import com.example.java_demo_book.vo.BookResponse;

@Service
public class BookServicelmpl implements BookService {
	@Autowired
	BookDao bookDao;

	@Override
	public BookResponse addBook(BookRequest bookRequest) { // 新增書籍
		ArrayList<Book> addbooks = bookRequest.getBooklist(); // 取得新增的書籍資料
		List<Book> haveBookError = new ArrayList<>();
		for (Book item : addbooks) {
			// 確認要新增的書籍資料不能是空值
			if (!StringUtils.hasText(item.getName()) || !StringUtils.hasText(item.getIsbn())
					|| !StringUtils.hasText(item.getWriter()) || item.getPrice() == 0
					|| !StringUtils.hasText(item.getClassification())) {
				return new BookResponse("不得有空白");
			}

			if (bookDao.existsById(item.getName())) { // 判斷如果資料庫已有此ID
				haveBookError.add(item); // 將重複id加到錯誤list
			}

			if (!haveBookError.isEmpty()) { // 判斷錯誤list中是否為空
				return new BookResponse("此書本已存在");
			}
		}
		bookDao.saveAll(addbooks); // 將要新增的書籍資料加到資料庫
		return new BookResponse(addbooks, "新增成功");
	}

	@Override
	public BookResponse editBook(BookRequest bookRequest) { // 修改書籍,一定要有isbn
		String isbn = bookRequest.getIsbn(); // 取得輸入的isdn
		Optional<Book> findId = bookDao.findById(isbn); // 透過ID尋找書籍資料
		if (!findId.isPresent()) { // 判斷有沒有空
			return new BookResponse("找不到此ID編號");
		}
		Book getBook = findId.get();// 得出透過ID尋找的整筆書資料

		// 判斷使用者要修改的資料
		if (StringUtils.hasText(bookRequest.getName())) { // 判斷使用者輸入的內容是否有值
			getBook.setName(bookRequest.getName()); // 設定將要修改的資料
		}
		if (StringUtils.hasText(bookRequest.getIsbn())) {
			getBook.setIsbn(bookRequest.getIsbn());
		}
		if (StringUtils.hasText(bookRequest.getWriter())) {
			getBook.setWriter(bookRequest.getWriter());
		}
		if (!(bookRequest.getPrice() == null)) {
			getBook.setPrice(bookRequest.getPrice());
		}
		if (StringUtils.hasText(bookRequest.getClassification())) {
			getBook.setClassification(bookRequest.getClassification());
		}

		bookDao.save(getBook);// 將新設定的書回傳資料庫
		return new BookResponse("更改成功");
	}

	@Override
	public BookResponse classificationSearch(BookRequest bookRequest) { // 搜尋類別
		Set<String> findClassification = bookRequest.getFindclassification(); // 抓取輸入要搜尋的類別
		List<BookResponse> responseList = new ArrayList<>();
		List<BookResponse> errorList = new ArrayList<>();
		for (String item : findClassification) {
			List<Book> getBook = bookDao.findByClassification(item); // 用Dao方法找資料庫書籍，取得有符合類別的書
			if (getBook.isEmpty()) { // 判斷找的資料是否是空的
				BookResponse messageResponse = new BookResponse();
				bookRequest.setClassification(item);
				messageResponse.setMessage(bookRequest.getClassification() + "，查無此書");
				errorList.add(messageResponse);
			}
			for (Book item2 : getBook) { // 將符合類別的書一本本設定要回傳的指定型式
				BookResponse bookResponse = new BookResponse();
				bookResponse.setIsbn(item2.getIsbn());
				bookResponse.setName(item2.getName());
				bookResponse.setWriter(item2.getWriter());
				bookResponse.setPrice(item2.getPrice());
				bookResponse.setStock(item2.getStock());
				responseList.add(bookResponse); // 將要回傳的指定型式加成一個List回傳
			}
		}
		return new BookResponse(responseList, "找到", errorList);
	}

	@Override
	public BookResponse consumerSearch(BookRequest bookRequest) { // 消費者查詢書籍
		List<Book> search = bookRequest.getBooklist();
		BookResponse bookResponse = new BookResponse();
		List<BookResponse> responseList = new ArrayList<>();
		for (Book item : search) { // 要搜尋的書本清單
			// 下面用Dao方法找資料庫要蒐巡的書籍，取得有符合類別的書
			List<Book> getBook = bookDao.findByIsbnOrNameOrWriter(item.getIsbn(), item.getName(), item.getWriter());
			if (getBook.isEmpty()) { // 判斷有沒有空值
				return new BookResponse("查無此書");
			}
			for (Book item2 : getBook) { // 取得的書籍設定指定回傳型態明細
				bookResponse.setIsbn(item2.getIsbn());
				bookResponse.setName(item2.getName());
				bookResponse.setWriter(item2.getWriter());
				responseList.add(bookResponse); // 將要回傳的指定型式加成一個List回傳
			}
		}
		return new BookResponse(responseList, "找到");
	}

	@Override
	public BookResponse companySearch(BookRequest bookRequest) { // 廠商查詢書籍
		List<Book> search = bookRequest.getBooklist();
		BookResponse bookResponse = new BookResponse();
		ArrayList<BookResponse> Responselist = new ArrayList<>();
		for (Book item : search) {// 要搜尋的書本清單
			// 下面用Dao方法找資料庫要蒐巡的書籍，取得有符合類別的書
			List<Book> getbook = bookDao.findByIsbnOrNameOrWriter(item.getIsbn(), item.getName(), item.getWriter());
			if (getbook.isEmpty()) { // 判斷有沒有空值
				return new BookResponse("查無此書");
			}
			for (Book item2 : getbook) {// 取得的書籍 設定指定回傳型態明細
				bookResponse.setIsbn(item2.getIsbn());
				bookResponse.setName(item2.getName());
				bookResponse.setWriter(item2.getWriter());
				bookResponse.setStock(item2.getStock());
				bookResponse.setSales(item2.getSales());
				Responselist.add(bookResponse); // 將要回傳的指定型式加成一個List回傳
			}
		}
		return new BookResponse(Responselist, "找到");
	}

	public BookResponse purchase(BookRequest bookRequest) { // 庫存更新,一定要有isbn
		String isbn = bookRequest.getIsbn(); // 取得輸入的isbn資料
		Integer addStock = bookRequest.getStock(); // 取得輸入的庫存數量
		if (!StringUtils.hasText(isbn) || addStock == 0) { // 防呆判斷輸入的值
			return new BookResponse("輸入內容錯誤");
		}
		Optional<Book> findId = bookDao.findById(isbn); // 透過ID尋找書籍資料
		if (!findId.isPresent()) { // 判斷有沒有空值
			return new BookResponse("資料庫找不到此書");
		}
		Book getBook = findId.get(); // 得出透過ID尋找的整筆書資料
		Integer newStock = getBook.getStock() + bookRequest.getStock();
		getBook.setStock(newStock);// 將新的庫存設定回Book
		bookDao.save(getBook);// 將有新庫存的書設定回資料庫
		return new BookResponse(getBook.getIsbn(), getBook.getName(), getBook.getWriter(), getBook.getPrice(),
				getBook.getStock(), null, null, "庫存量更新成功");
	}

	@Override
	public BookResponse renew(BookRequest bookRequest) { // 更新書本價錢,一定要有isbn
		String isbn = bookRequest.getIsbn(); // 取得輸入的isbn資料
		Integer newprice = bookRequest.getPrice(); // 取得輸入要更改新的價格
		if (isbn.isBlank() || newprice == 0) { // 判斷輸入的isbn & 價格不能是空的
			return new BookResponse("不得為空");
		}
		Optional<Book> findid = bookDao.findById(isbn); // 透過ID尋找書籍資料
		if (!findid.isPresent()) { // 判斷尋找的ID資料是不是空值
			return new BookResponse("資料庫找不到此書");
		}
		Book getbook = findid.get(); // 得出透過ID尋找的整筆書資料
		Integer getPrice = getbook.getPrice(); // 取得尋找到這本書的價錢

		if (getPrice.equals(newprice)) { // 判斷要更新的價格是否重複
			return new BookResponse("此金額已是該價格");
		}

		getbook.setPrice(newprice); // 將新的價格設定回Book
		bookDao.save(getbook); // 將有新價格的書設定回資料庫

		return new BookResponse(getbook.getIsbn(), getbook.getName(), getbook.getWriter(), getbook.getPrice(),
				getbook.getStock(), getbook.getSales(), null, "成功");

	}

	@Override
	public BookResponse buyBook(BookRequest bookRequest) { // 購買書籍
		Map<String, Integer> books = bookRequest.getBuybooks();
		Integer amount = bookRequest.getAmount();
		List<BookResponse> totalbook = new ArrayList<>();

		Integer total = 0;
		for (Entry<String, Integer> book : books.entrySet()) {
			if (!StringUtils.hasText(book.getKey()) || book.getValue() == 0) {
				return new BookResponse("輸入錯誤");
			}
			Optional<Book> findid = bookDao.findById(book.getKey()); // 透過ID尋找書籍資料
			if (!findid.isPresent()) {
				return new BookResponse("查無此書籍");
			}
			Book getBook = findid.get();// 得出透過ID尋找的整筆書資料

			if (getBook.getStock() < book.getValue()) {
				return new BookResponse("庫存量不夠");
			}
			total = getBook.getPrice() * book.getValue(); // 價格*數量得出總金額
			Integer Stock = getBook.getStock() - book.getValue();
			Integer Sales = getBook.getSales() + book.getValue();
			getBook.setStock(Stock);
			getBook.setSales(Sales);
			bookDao.save(getBook);
			// 以下將取得的書籍 設定指定回傳型態明細
			BookResponse Response = new BookResponse();
			Response.setIsbn(getBook.getIsbn());
			Response.setName(getBook.getName());
			Response.setWriter(getBook.getWriter());
			Response.setPrice(getBook.getPrice());
			Response.setAmount(amount);
			Response.setTotal(total);
			totalbook.add(Response); // 將要回傳的指定型式加成一個List回傳

		}
		return new BookResponse(totalbook, "購買成功");
	}

	@Override
	public List<BookResponse> ranking(Integer sales) { // 銷售量前5排行
		// 下面用Dao方法找資料庫銷售前5名書籍，取得有符合類別的書
		List<Book> top5 = bookDao.findTop5ByOrderBySalesDesc();
		List<BookResponse> fiveBook = new ArrayList<>();
		for (Book getbook : top5) { // 取得的書籍 設定指定回傳型態明細
			BookResponse bookResponse = new BookResponse();
			bookResponse.setIsbn(getbook.getIsbn());
			bookResponse.setName(getbook.getName());
			bookResponse.setWriter(getbook.getWriter());
			bookResponse.setPrice(getbook.getPrice());
			fiveBook.add(bookResponse);// 將要回傳的指定型式加成一個List回傳
		}
		return fiveBook;
	}

}
