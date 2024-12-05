package LibraryManagementSystem.service.custom;

import LibraryManagementSystem.dto.BookDto;
import LibraryManagementSystem.service.SuperService;

import java.util.List;

public interface BookService extends SuperService {

    boolean saveBook(BookDto dto);
    boolean updateBook(BookDto dto);
    BookDto getBookData(int id);
    List<BookDto> getAllBookId();
    boolean isBookExistsByIsbn(String isbn);
}
