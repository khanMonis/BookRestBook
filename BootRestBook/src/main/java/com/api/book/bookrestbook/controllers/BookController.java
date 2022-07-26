package com.api.book.bookrestbook.controllers;

import java.util.List;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.api.book.bookrestbook.entities.Book;
import com.api.book.bookrestbook.services.BookService;

@RestController
public class BookController {

	@Autowired
	private BookService bookService;

	// get all books handler
	@GetMapping("/books")
	public ResponseEntity<List<Book>> getBooks() {
		List<Book> list = bookService.getAllBooks();

		if (list.size() <= 0) {
			return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
		}
		return ResponseEntity.status(HttpStatus.CREATED).body(list);
	}

	// get single book handler
	@GetMapping("/books/{id}")
	public ResponseEntity<Book> getBook(@PathVariable("id") int id) {
		Book book = bookService.getBookById(id);
		if (book == null) {
			return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
		}

		return ResponseEntity.of(Optional.of(book));
	}

	// create a new book handler
	@PostMapping("/books")
	public ResponseEntity<Book> addBook(@RequestBody Book book) {

		Book b = null;

		try {

			b = this.bookService.addBook(book);
			System.out.println(book);
			return ResponseEntity.of(Optional.of(b));

		} catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
			return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
		}
	}

	// delete book handler
	@DeleteMapping("/books/{bookId}")
	public ResponseEntity<Book> deleteBook (@PathVariable("bookId") int bookId) {
		
		try {
			this.bookService.deleteBook(bookId);
			return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
			
		} catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
			return ResponseEntity.status(HttpStatus.
					INTERNAL_SERVER_ERROR).build();
		}
	}

	// Update book Handler
	@PutMapping("/books/{bookId}")
	public ResponseEntity<Book> updateBook(@RequestBody Book book, @PathVariable("bookId") int bookId) {
		try {
			this.bookService.updateBook(book, bookId);
			return ResponseEntity.ok().body(book);
		} catch (Exception e) {
			// TODO: handle exception
			return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
		}

	}
}
