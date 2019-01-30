package com.codex.trial.trial;

import static org.apache.commons.lang3.RandomStringUtils.randomAlphabetic;
import static org.apache.commons.lang3.RandomStringUtils.randomNumeric;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

import java.util.List;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.context.SpringBootTest.WebEnvironment;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit4.SpringRunner;

import com.codex.trial.trial.model.Application;
import com.codex.trial.trial.model.Book;

import io.restassured.RestAssured;
import io.restassured.response.Response;

@RunWith(SpringRunner.class)
@SpringBootTest(classes = { Application.class }, webEnvironment = WebEnvironment.DEFINED_PORT)
public class LiveTest {

	private static final String API_ROOT = "http://localhost:8081/api/books";

	private Book createRandomBook() {
		Book book = new Book();
		book.setTitle(randomAlphabetic(10));
		book.setAuthor(randomAlphabetic(15));
		return book;
	}

	private String createBookAsUri(Book book) {
		Response response = RestAssured.given().contentType(MediaType.APPLICATION_JSON_VALUE).body(book).post(API_ROOT);
		return API_ROOT + "/" + response.jsonPath().get("id");
	}

	@Test
	public void whenGetAllBooks_thenOK() {
		Response response = RestAssured.get(API_ROOT);

		assertEquals(HttpStatus.OK.value(), response.getStatusCode());
	}

	@Test
	public void whenGetBooksByTitle_thenOK() {
		Book book = createRandomBook();
		createBookAsUri(book);
		Response response = RestAssured.get(API_ROOT + "/title/" + book.getTitle());

		assertEquals(HttpStatus.OK.value(), response.getStatusCode());
		assertTrue(response.as(List.class).size() > 0);
	}

	@Test
	public void whenGetCreatedBookById_thenOK() {
		Book book = createRandomBook();
		String location = createBookAsUri(book);
		Response response = RestAssured.get(location);

		assertEquals(HttpStatus.OK.value(), response.getStatusCode());
		assertEquals(book.getTitle(), response.jsonPath().get("title"));
	}

	@Test
	public void whenGetNotExistBookById_thenNotFound() {
		Response response = RestAssured.get(API_ROOT + "/" + randomNumeric(4));

		assertEquals(HttpStatus.NOT_FOUND.value(), response.getStatusCode());
	}

	@Test
	public void whenCreateNewBook_thenCreated() {
		Book book = createRandomBook();
		Response response = RestAssured.given().contentType(MediaType.APPLICATION_JSON_VALUE).body(book).post(API_ROOT);

		assertEquals(HttpStatus.CREATED.value(), response.getStatusCode());
	}

	@Test
	public void whenInvalidBook_thenError() {
		Book book = createRandomBook();
		book.setAuthor(null);
		Response response = RestAssured.given().contentType(MediaType.APPLICATION_JSON_VALUE).body(book).post(API_ROOT);

		assertEquals(HttpStatus.BAD_REQUEST.value(), response.getStatusCode());
	}

	@Test
	public void whenUpdateCreatedBook_thenUpdated() {
		Book book = createRandomBook();
		String location = createBookAsUri(book);
		book.setId(Long.parseLong(location.split("api/books/")[1]));
		book.setAuthor("newAuthor");
		Response response = RestAssured.given().contentType(MediaType.APPLICATION_JSON_VALUE).body(book).put(location);

		assertEquals(HttpStatus.OK.value(), response.getStatusCode());

		response = RestAssured.get(location);

		assertEquals(HttpStatus.OK.value(), response.getStatusCode());
		assertEquals("newAuthor", response.jsonPath().get("author"));
	}

	@Test
	public void whenDeleteCreatedBook_thenOk() {
		Book book = createRandomBook();
		String location = createBookAsUri(book);
		Response response = RestAssured.delete(location);

		assertEquals(HttpStatus.OK.value(), response.getStatusCode());

		response = RestAssured.get(location);
		assertEquals(HttpStatus.NOT_FOUND.value(), response.getStatusCode());
	}
}