package com.example.java_demo_book.service.lmpl;

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
	public BookResponse addBook(BookRequest bookRequest) { // 新增書籍>ブックを追加する
		ArrayList<Book> addBooks = bookRequest.getBooklist(); // 取得新增的書籍資料>新たに追加された書籍資料を取得する
		List<Book> haveBookError = new ArrayList<>();
		for (Book item : addBooks) {// 用迴圈逐一判斷要新增的書籍資料不能是空值>新たに追加する書籍資料を一つ一つ空欄にしてはならない
			if (!StringUtils.hasText(item.getName()) || !StringUtils.hasText(item.getIsbn())
					|| !StringUtils.hasText(item.getWriter()) || item.getPrice() <= 0
					|| !StringUtils.hasText(item.getClassification())) {
				return new BookResponse("不得有空白");
			}

			if (bookDao.existsById(item.getIsbn())) { // 判斷如果資料庫已有此ID>データベースに既にこのIDが存在するかどうかを比較する
				haveBookError.add(item); // 將重複id加到錯誤list
			}

			if (!haveBookError.isEmpty()) { // 判斷錯誤list中是否為空>Errorlistで空かどかを判断する
				return new BookResponse("此書本已存在");
			}
		}
		bookDao.saveAll(addBooks); // 將要新增的書籍資料加到資料庫>追加する書籍資料をデータベースに追加する
		return new BookResponse(addBooks, "新增成功");
	}

	@Override
	public BookResponse editBook(BookRequest bookRequest) { // 修改書籍>書籍の修正する
		String isbn = bookRequest.getIsbn(); // 取得輸入的isdn>入力isdnの取得
		Optional<Book> bookOpt = bookDao.findById(isbn); // 透過ID尋找書籍資料>Dで書籍資料を探す
		
		
		if (!bookOpt.isPresent()) { // 讀取出來的資料判斷有沒有空>読み出した資料は空かどうかを判断する
			return new BookResponse("ID編號錯誤");
		}
		Book book = bookOpt.get();// 得出透過ID尋找的整筆書資料>IDで検索した全書籍データを取得する

		// 判斷使用者要修改的資料
		if (StringUtils.hasText(bookRequest.getIsbn())) { 
			book.setIsbn(bookRequest.getIsbn()); 
		}
		if (StringUtils.hasText(bookRequest.getName())) { // 判斷使用者輸入的內容是否有值>入力者が入力内容に値があるか否かを判断する
			book.setName(bookRequest.getName()); // 設定將要修改的資料>入力者が修正したい資料を設定する
		} 
		if (StringUtils.hasText(bookRequest.getWriter())) {
			book.setWriter(bookRequest.getWriter());
		}
		if (bookRequest.getPrice() != null) {
			book.setPrice(bookRequest.getPrice());
		}
		if (StringUtils.hasText(bookRequest.getClassification())) {
			book.setClassification(bookRequest.getClassification());
		}else {
			return new BookResponse("至少輸入一項要更改訊息"); //変更するメッセージを少なくとも1つ入力とメッセージを送信します。
		}

		bookDao.save(book);// 將新設定的書回傳資料庫>修正した書籍資料をデータベースに戻す
		return new BookResponse("更改成功");
	}

	@Override
	public BookResponse classificationSearch(BookRequest bookRequest) { // 分類を探す方法
		Set<String> findClassification = bookRequest.getFindclassification(); // 抓取輸入要搜尋的類別＞探したいの分類を取る
		List<BookResponse> responseList = new ArrayList<>();// ListはNULLを作る
		List<BookResponse> errorList = new ArrayList<>();// 間違い資料をバックするための、Listを作る
		System.out.println(findClassification);
		if(findClassification.size()==0) {
			return new BookResponse("不得空白");
		}
		
		for (String item : findClassification) { // foreachでlistの資料を一つ一つに取り出す
			List<Book> getBookListByClass = bookDao.findByClassification(item); // 用Dao方法找資料庫書籍，取得有符合類別的書＞Dao方法でDBから条件に当てはまる資料を取って
			if (getBookListByClass.isEmpty()) { // 判斷找的資料是否是空的＞そして、この資料がnullかどうかを判断する
//				BookResponse messageResponse = new BookResponse(); //建立一個新的物件＞新しい物件を作る
//				bookRequest.setClassification(item); //設定找不到的類別＞探したい分類が探せない時に、探せない分類を設定する
//				messageResponse.setMessage(bookRequest.getClassification() + "，查無此書"); //把輸入方　輸入錯的資訊　設定回傳訊息＞間違いメッセージを指定されるスタイルにレスポンスする
//				errorList.add(messageResponse); //加到錯誤清單

				errorList.add(new BookResponse(item, item + "，查無此書"));
			}
			for (Book item2 : getBookListByClass) { // 將符合類別的書一本本設定要回傳的指定型式
				BookResponse bookResponse = new BookResponse();
				bookResponse.setIsbn(item2.getIsbn());
				bookResponse.setName(item2.getName());
				bookResponse.setWriter(item2.getWriter());
				bookResponse.setPrice(item2.getPrice());
				bookResponse.setStock(item2.getStock());
				bookResponse.setClassification(item2.getClassification());
				responseList.add(bookResponse); // 將要回傳的指定型式加成一個List回傳
			}
		}
		return new BookResponse(responseList, null, errorList);
	}

	@Override
	public BookResponse consumerSearch(BookRequest bookRequest) { // 查詢書籍<消費者+廠商>>消費者とメーカーは書籍を照会する

		String identity=bookRequest.getIdentity();
		
		List<BookResponse> responseList = new ArrayList<>();
		List<Book> getBookList = bookDao.findByIsbnOrNameOrWriter(bookRequest.getIsbn(), bookRequest.getName(), bookRequest.getWriter());
			
		if (getBookList.isEmpty()) { // 判斷有沒有空值
				return new BookResponse("查無此書");
			}
			if(!StringUtils.hasText(identity)) {
				return new BookResponse("請在身分內輸入<客人>或<廠商>");
				} else if (identity.equals("客人")) {
				
				for (Book item2 : getBookList) { // 取得的書籍設定指定回傳型態明細
					BookResponse bookResponse = new BookResponse();
					bookResponse.setIsbn(item2.getIsbn());
					bookResponse.setName(item2.getName());
					bookResponse.setWriter(item2.getWriter());
					responseList.add(bookResponse); // 將要回傳的指定型式加成一個List回傳
				}
				
			} else if (identity.equals("廠商")) {
				
				for (Book item2 : getBookList) {// 取得的書籍 設定指定回傳型態明細
					BookResponse bookResponse = new BookResponse();
					bookResponse.setIsbn(item2.getIsbn());
					bookResponse.setName(item2.getName());
					bookResponse.setWriter(item2.getWriter());
					bookResponse.setStock(item2.getStock());
					bookResponse.setSales(item2.getSales());
					responseList.add(bookResponse); // 將要回傳的指定型式加成一個List回傳
				}
			}else {
				return new BookResponse("請確定身分的輸入格式是否正確");
			}
		
		return new BookResponse(responseList, "找到");
	}

	@Override
//	public BookResponse companySearch(BookRequest bookRequest) { // 廠商查詢書籍>メーカーは書籍を照会する
//		List<Book> search = bookRequest.getBooklist();
//		BookResponse bookResponse = new BookResponse();
//		ArrayList<BookResponse> responseList = new ArrayList<>();
//		for (Book item : search) {// 要搜尋的書本清單
//			// 下面用Dao方法找資料庫要蒐巡的書籍，取得有符合類別的書
//			List<Book> getbook = bookDao.findByIsbnOrNameOrWriter(item.getIsbn(), item.getName(), item.getWriter());
//			if (getbook.isEmpty()) { // 判斷有沒有空值
//				return new BookResponse("查無此書");
//			}
//			for (Book item2 : getbook) {// 取得的書籍 設定指定回傳型態明細
//				bookResponse.setIsbn(item2.getIsbn());
//				bookResponse.setName(item2.getName());
//				bookResponse.setWriter(item2.getWriter());
//				bookResponse.setStock(item2.getStock());
//				bookResponse.setSales(item2.getSales());
//				responseList.add(bookResponse); // 將要回傳的指定型式加成一個List回傳
//			}
//		}
//		return new BookResponse(responseList, "找到");
//	}

	public BookResponse purchase(BookRequest bookRequest) { // 庫存更新,一定要有isbn
		String isbn = bookRequest.getIsbn(); // 取得輸入的isbn資料
		Integer addStock = bookRequest.getStock(); // 取得輸入的庫存數量
		if (!StringUtils.hasText(isbn) || addStock < 0) { // 防呆判斷輸入的值
			return new BookResponse("輸入內容錯誤");
		}
		Optional<Book> OpBookById = bookDao.findById(isbn); // 透過ID尋找書籍資料
		if (!OpBookById.isPresent()) { // 判斷有沒有空值
			return new BookResponse("資料庫找不到此書");
		}
		Book getBook = OpBookById.get(); // 得出透過ID尋找的整筆書資料
		Integer newStock = getBook.getStock() + bookRequest.getStock();
		getBook.setStock(newStock);// 將新的庫存設定回Book
		bookDao.save(getBook);// 將有新庫存的書設定回資料庫
		return new BookResponse(getBook.getIsbn(), getBook.getName(), getBook.getWriter(), getBook.getPrice(),
				getBook.getStock(), null, null, "庫存量更新成功");
	}

	@Override
	public BookResponse renew(BookRequest bookRequest) { // 更新書本價錢,一定要有isbn
		String isbn = bookRequest.getIsbn(); // 取得輸入的isbn資料
		Integer newPrice = bookRequest.getPrice(); // 取得輸入要更改新的價格
		if (StringUtils.hasText(isbn) || newPrice < 0) { // 判斷輸入的isbn & 價格不能是空的
			return new BookResponse("不得為空");
		}
		Optional<Book> optBook = bookDao.findById(isbn); // 透過ID尋找書籍資料
		if (!optBook.isPresent()) { // 判斷尋找的ID資料是不是空值
			return new BookResponse("資料庫找不到此書");
		}
		Book getBook = optBook.get(); // 得出透過ID尋找的整筆書資料
		Integer getPrice = getBook.getPrice(); // 取得尋找到這本書的價錢

		if (getPrice.equals(newPrice)) { // 判斷要更新的價格是否重複
			return new BookResponse("此金額已是該價格");
		}

		getBook.setPrice(newPrice); // 將新的價格設定回Book
		bookDao.save(getBook); // 將有新價格的書設定回資料庫

		return new BookResponse(getBook.getIsbn(), getBook.getName(), getBook.getWriter(), getBook.getPrice(),
				getBook.getStock(), getBook.getSales(), null, "成功");

	}

	@Override
	public BookResponse buyBook(BookRequest bookRequest) { // 購買書籍
		// 讀取輸入ID跟數量
		Map<String, Integer> orderMap = bookRequest.getBuybooks();
		Integer amount = bookRequest.getAmount();
		List<BookResponse> resList = new ArrayList<>();

		Integer total = 0;
		for (Entry<String, Integer> order : orderMap.entrySet()) {
			if (!StringUtils.hasText(order.getKey()) || order.getValue() < 0) {
				return new BookResponse("輸入錯誤");
			}
			Optional<Book> optBook = bookDao.findById(order.getKey()); // 透過ID尋找書籍資料
			if (!optBook.isPresent()) {
				return new BookResponse("查無此書籍");
			}
			Book getBook = optBook.get();// 得出透過ID尋找的整筆書資料

			if (getBook.getStock() < order.getValue()) {
				return new BookResponse("庫存量不夠");
			}
			total = getBook.getPrice() * order.getValue(); // 價格*數量得出總金額
			Integer Stock = getBook.getStock() - order.getValue(); // 減庫存量
			Integer Sales = getBook.getSales() + order.getValue(); // 加銷售量
			getBook.setStock(Stock);
			getBook.setSales(Sales);
			bookDao.save(getBook);
			// 以下將取得的書籍 設定指定回傳型態明細
			BookResponse response = new BookResponse();
			response.setIsbn(getBook.getIsbn());
			response.setName(getBook.getName());
			response.setWriter(getBook.getWriter());
			response.setPrice(getBook.getPrice());
			response.setAmount(amount);
			response.setTotal(total);
			resList.add(response); // 將要回傳的指定型式加成一個List回傳

		}
		return new BookResponse(resList, "購買成功");
	}

	@Override
	public List<BookResponse> ranking(Integer sales) { // 銷售量前5排行
		// 下面用Dao方法找資料庫銷售前5名書籍，取得有符合類別的書
		List<Book> top5BookList = bookDao.findTop5ByOrderBySalesDesc();
		List<BookResponse> resList = new ArrayList<>();
		for (Book getbook : top5BookList) { // 取得的書籍 設定指定回傳型態明細
			BookResponse bookResponse = new BookResponse();
			bookResponse.setIsbn(getbook.getIsbn());
			bookResponse.setName(getbook.getName());
			bookResponse.setWriter(getbook.getWriter());
			bookResponse.setPrice(getbook.getPrice());
			resList.add(bookResponse);// 將要回傳的指定型式加成一個List回傳
		}
		return resList;
	}

}
