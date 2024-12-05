package LibraryManagementSystem.service.custom.impl;

import LibraryManagementSystem.dto.AdminDto;
import LibraryManagementSystem.dto.BookDto;
import LibraryManagementSystem.entity.Admin;
import LibraryManagementSystem.entity.Book;
import LibraryManagementSystem.repository.RepositoryFactory;
import LibraryManagementSystem.repository.custom.BookRepository;
import LibraryManagementSystem.repository.custom.impl.BookRepositoryImpl;
import LibraryManagementSystem.service.custom.BookService;
import LibraryManagementSystem.util.SessionFactoryConfig;
import org.hibernate.Session;
import org.hibernate.Transaction;

import java.util.ArrayList;
import java.util.List;

public class BookServiceImpl implements BookService {
    @Override
    public boolean isBookExistsByIsbn(String isbn) {
        try {
            initializeSession();
            BookRepositoryImpl.setSession(session);

            Book book = bookRepository.getBookByIsbn(isbn);
            return book != null;
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        } finally {
            if (session != null && session.isOpen()) {
                session.close();
            }
        }
    }

    BookRepository bookRepository =
            (BookRepository) RepositoryFactory.getInstance()
                    .getRepository(RepositoryFactory.RepositoryTypes.BOOK);

    private Session session;

    public void initializeSession() {
        session = SessionFactoryConfig.getInstance().getSession();
    }

    @Override
    public boolean saveBook(BookDto dto) {
        Book book = convertToEntity(dto);
        initializeSession();
        Transaction transaction = session.beginTransaction();
        try {
            BookRepositoryImpl.setSession(session);

            // Kiểm tra id có tồn tại hay không
            if (bookRepository.getData(dto.getId()) != null) {
                throw new IllegalArgumentException("Book ID already exists.");
            }

            bookRepository.save(book);
            transaction.commit();
            return true;
        } catch (Exception e) {
            e.printStackTrace();
            transaction.rollback();
            return false;
        } finally {
            session.close();
        }
    }


    @Override
    public boolean updateBook(BookDto dto) {
        // Chuyển đối tượng BookDto thành đối tượng Book
        Book book = convertToEntity(dto);

        // Khởi tạo session
        initializeSession();
        Transaction transaction = session.beginTransaction();

        try {
            // Đảm bảo repository sử dụng đúng session
            BookRepositoryImpl.setSession(session);

            // Thay thế phương thức update() bằng merge() để tránh lỗi liên quan đến session
            Book existingBook = session.find(Book.class, book.getId());
            if (existingBook != null) {
                existingBook.setQuantity(book.getQuantity());
                existingBook.setStatus(book.getStatus());
                session.merge(existingBook); // Or merge, depending on your needs
            }
            // Gọi merge() thay vì update()

            // Commit transaction nếu không có lỗi
            transaction.commit();

            return true;  // Trả về true nếu update thành công
        } catch (Exception e) {
            // Nếu có lỗi, in ra lỗi và rollback transaction
            e.printStackTrace();
            if (transaction != null) {
                transaction.rollback();
            }
            return false;  // Trả về false nếu có lỗi
        } finally {
            // Đảm bảo session được đóng
            session.close();
        }
    }



    @Override
    public BookDto getBookData(int id) {
        try {
            initializeSession();
            BookRepositoryImpl.setSession(session);
            return convertToDto(bookRepository.getData(id));
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        } finally {
            session.close();
        }
    }

    @Override
    public List<BookDto> getAllBookId() {
        List<BookDto> idList = new ArrayList<>();
        try {
            initializeSession();
            BookRepositoryImpl.setSession(session);
            List<Book> allBookId = bookRepository.getAllId();
            for (Book book : allBookId) {;
                idList.add(convertToDto(book));
            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            session.close();
        }
        return idList;
    }

    private Book convertToEntity(BookDto dto) {
        Book entity = new Book();
        entity.setId(dto.getId());
        entity.setName(dto.getName());
        entity.setType(dto.getType());
        entity.setLanguage(dto.getLanguage());
        entity.setStatus(dto.getStatus());
        entity.setQuantity(dto.getQuantity());
        entity.setAdmin(convertToAdminEntity(dto.getAdmin()));
        entity.setIsbn(dto.getIsbn());
        entity.setAuthor(dto.getAuthor());
        return entity;
    }

    private BookDto convertToDto(Book entity) {
        return new BookDto(
                entity.getId(),
                entity.getName(),
                entity.getType(),
                entity.getLanguage(),
                entity.getStatus(),
                convertToAdminDto(entity.getAdmin()),
                entity.getQuantity(),
                entity.getIsbn(),
                entity.getAuthor()
        );
    }

    private AdminDto convertToAdminDto(Admin entity) {
        return new AdminDto(
                entity.getId(),
                entity.getName(),
                entity.getContactNo(),
                entity.getEmail(),
                entity.getUsername(),
                entity.getPassword()
        );
    }

    private Admin convertToAdminEntity(AdminDto dto) {
        Admin admin = new Admin();
        admin.setId(dto.getId());
        admin.setName(dto.getName());
        admin.setContactNo(dto.getContactNo());
        admin.setEmail(dto.getEmail());
        admin.setUsername(dto.getUsername());
        admin.setPassword(dto.getPassword());
        return admin;
    }

}