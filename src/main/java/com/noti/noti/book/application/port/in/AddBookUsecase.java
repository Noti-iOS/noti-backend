package com.noti.noti.book.application.port.in;

import com.noti.noti.book.domain.model.Book;
import java.util.function.Function;

public interface AddBookUsecase extends Function<AddBookCommand, Book> {

}
