package LibraryManagementSystem.repository.custom;

import LibraryManagementSystem.entity.Book;
import LibraryManagementSystem.repository.CrudRepository;

public interface BookRepository extends CrudRepository<Book> {

    int getAllBookCount();
    Book getBookByIsbn(String isbn);
}
