package LibraryManagementSystem.repository.custom.impl;

import LibraryManagementSystem.entity.Book;
import LibraryManagementSystem.repository.custom.BookRepository;
import lombok.Setter;
import org.hibernate.Session;
import org.hibernate.query.Query;

import java.util.List;

public class BookRepositoryImpl implements BookRepository {

    @Setter
    private static Session session;

    @Override
    public Book getBookByIsbn(String isbn) {
        String hql = "FROM Book WHERE isbn = :isbn";
        Query<Book> query = session.createQuery(hql, Book.class);
        query.setParameter("isbn", isbn);
        return query.uniqueResult();
    }

    @Override
    public void save(Book entity) {
        // Kiểm tra xem id đã tồn tại chưa trước khi lưu
        if (getData(entity.getId()) != null) {
            throw new IllegalArgumentException("Book ID already exists.");
        }
        session.save(entity);
    }


    @Override
    public void update(Book entity) {
        session.update(entity);
    }

    @Override
    public void delete(Book entity) {
        entity.setStatus("Removed");
        session.update(entity);
    }

    @Override
    public Book getData(int id) {
        return session.get(Book.class, id);
    }

    @Override
    public List<Book> getAllId() {
        String hqlQuery = "From Book";
        Query<Book> query = session.createQuery(hqlQuery);
        return query.list();
    }

    @Override
    public int getAllBookCount() {
        String jpqlQuery = "SELECT COUNT(B) FROM Book B " +
                "WHERE B.status != :removed";

        Query query = session.createQuery(jpqlQuery)
                .setParameter("removed", "Removed");
        Long count = (Long) query.uniqueResult();

        return Math.toIntExact(count);
    }

}
