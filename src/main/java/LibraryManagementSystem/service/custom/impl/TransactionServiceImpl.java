package LibraryManagementSystem.service.custom.impl;

import LibraryManagementSystem.controller.user.UserBorrowBooksFormController;
import LibraryManagementSystem.controller.user.UserReturnBookConfirmPopUpFormController;
import LibraryManagementSystem.dto.AdminDto;
import LibraryManagementSystem.dto.BookDto;
import LibraryManagementSystem.dto.TransactionDto;
import LibraryManagementSystem.dto.UserDto;
import LibraryManagementSystem.embedded.TransactionDetailPK;
import LibraryManagementSystem.entity.*;
import LibraryManagementSystem.repository.RepositoryFactory;
import LibraryManagementSystem.repository.custom.BookRepository;
import LibraryManagementSystem.repository.custom.TransactionDetailRepository;
import LibraryManagementSystem.repository.custom.TransactionRepository;
import LibraryManagementSystem.repository.custom.impl.BookRepositoryImpl;
import LibraryManagementSystem.repository.custom.impl.TransactionDetailRepositoryImpl;
import LibraryManagementSystem.repository.custom.impl.TransactionRepositoryImpl;
import LibraryManagementSystem.service.custom.TransactionService;
import LibraryManagementSystem.util.SessionFactoryConfig;
import org.hibernate.Session;

import java.util.ArrayList;
import java.util.List;

public class TransactionServiceImpl implements TransactionService {

    TransactionRepository transactionRepository =
            (TransactionRepository) RepositoryFactory.getInstance()
                    .getRepository(RepositoryFactory.RepositoryTypes.TRANSACTION);

    TransactionDetailRepository transactionDetailRepository =
            (TransactionDetailRepository) RepositoryFactory.getInstance()
                    .getRepository(RepositoryFactory.RepositoryTypes.TRANSACTION_DETAIL);

    BookRepository bookRepository =
            (BookRepository) RepositoryFactory.getInstance()
                    .getRepository(RepositoryFactory.RepositoryTypes.BOOK);

    private Session session;

    public void initializeSession() {
        session = SessionFactoryConfig.getInstance().getSession();
    }

    @Override
    public boolean saveTransaction(TransactionDto dto) {
        Transaction transactionEntity = convertToEntity(dto);

        initializeSession();
        org.hibernate.Transaction transaction = session.beginTransaction();

        TransactionRepositoryImpl.setSession(session);
        transactionRepository.save(transactionEntity);

        for (BookDto borrowedBook : UserBorrowBooksFormController.getInstance().borrowedBooks) {
            Book bookEntity = convertToBookEntity(borrowedBook);

            // Giảm quantity khi mượn sách
            if (bookEntity.getQuantity() > 0) {
                bookEntity.setQuantity(bookEntity.getQuantity() - 1);
                BookRepositoryImpl.setSession(session);
                bookRepository.update(bookEntity);
            }

            TransactionDetail transactionDetail = new TransactionDetail();
            transactionDetail.setTransaction(transactionEntity);
            transactionDetail.setBook(bookEntity);
            transactionDetail.setTransactionDetailPK(
                    new TransactionDetailPK(
                            transactionEntity.getId(),
                            bookEntity.getId()
                    )
            );

            TransactionDetailRepositoryImpl.setSession(session);
            transactionDetailRepository.save(transactionDetail);
        }

        try {
            transaction.commit();
            return true;
        } catch (Exception e) {
            transaction.rollback();
            e.printStackTrace();
            return false;
        } finally {
            session.close();
        }
    }


    @Override
    public boolean updateTransaction(TransactionDto dto) {
        Transaction transactionEntity = convertToEntity(dto);

        initializeSession();
        org.hibernate.Transaction transaction = session.beginTransaction();

        TransactionRepositoryImpl.setSession(session);
        transactionRepository.update(transactionEntity);

        for (Book book : UserReturnBookConfirmPopUpFormController.booksToBeReturned) {
            // Cập nhật lại trạng thái và tăng quantity khi trả sách
            Book bookEntity = updateBookEntityStatus(book);
            if (bookEntity.getQuantity() >= 0) {
                bookEntity.setQuantity(bookEntity.getQuantity() + 1);  // Tăng quantity khi trả sách
                BookRepositoryImpl.setSession(session);
                bookRepository.update(bookEntity);
            }
        }

        try {
            transaction.commit();
            return true;
        } catch (Exception e) {
            transaction.rollback();
            e.printStackTrace();
            return false;
        } finally {
            session.close();
        }
    }


    @Override
    public TransactionDto getTransactionData(int id) {
        try {
            initializeSession();
            TransactionRepositoryImpl.setSession(session);
            return convertToDto(transactionRepository.getData(id));
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        } finally {
            session.close();
        }
    }

    @Override
    public List<TransactionDto> getTransactionAllId() {
        List<TransactionDto> list = new ArrayList<>();
        try {
            initializeSession();
            TransactionRepositoryImpl.setSession(session);
            List<Transaction> allTransactions = transactionRepository.getAllId();
            for (Transaction transaction : allTransactions) {;
                list.add(convertToDto(transaction));
            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            session.close();
        }
        return list;
    }

    @Override
    public List<TransactionDto> getAllOverDueBorrowers() {
        List<TransactionDto> list = new ArrayList<>();
        try {
            initializeSession();
            TransactionRepositoryImpl.setSession(session);
            List<Transaction> overDueBorrowers = transactionRepository.getAllOverDueBorrowers();
            for (Transaction transaction : overDueBorrowers) {;
                list.add(convertToDto(transaction));
            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            session.close();
        }
        return list;
    }

    @Override
    public int getLastTransactionId() {
        try {
            initializeSession();
            TransactionRepositoryImpl.setSession(session);
            return transactionRepository.getLastId();
        } catch (Exception e) {
            e.printStackTrace();
            return 0;
        } finally {
            session.close();
        }
    }

    private Transaction convertToEntity(TransactionDto dto) {
        Transaction entity = new Transaction();
        entity.setId(dto.getId());
        entity.setTransactionType(dto.getTransactionType());
        entity.setBookQty(dto.getBookQty());
        entity.setDueDate(dto.getDueDate());
        entity.setDateAndTime(dto.getDateAndTime());
        entity.setUser(convertToUserEntity(dto.getUser()));
        return entity;
    }

    private TransactionDto convertToDto(Transaction entity) {
        return new TransactionDto(
                entity.getId(),
                entity.getTransactionType(),
                entity.getBookQty(),
                entity.getDueDate(),
                entity.getDateAndTime(),
                entity.getTransactionDetails(),
                convertToUserDto(entity.getUser())
        );
    }

    private Book convertToBookEntity(BookDto dto) {
        Book entity = new Book();
        entity.setId(dto.getId());
        entity.setName(dto.getName());
        entity.setType(dto.getType());
        entity.setLanguage(dto.getLanguage());
        entity.setStatus("Unavailable");
        entity.setAdmin(convertToAdminEntity(dto.getAdmin()));
        entity.setQuantity(dto.getQuantity());
        return entity;
    }

    private Book updateBookEntityStatus(Book entity) {
        entity.setId(entity.getId());
        entity.setName(entity.getName());
        entity.setType(entity.getType());
        entity.setLanguage(entity.getLanguage());
        entity.setStatus("Available");
        entity.setAdmin(entity.getAdmin());
        return entity;
    }

    private BookDto convertToBookDto(Book entity) {
        return new BookDto(
                entity.getId(),
                entity.getName(),
                entity.getType(),
                entity.getLanguage(),
                entity.getStatus(),
                convertToAdminDto(entity.getAdmin()),
                entity.getQuantity(),
                entity.getIsbn(),
                entity.getIsbn()
        );
    }


    private User convertToUserEntity(UserDto dto) {
        User entity = new User();
        entity.setId(dto.getId());
        entity.setName(dto.getName());
        entity.setEmail(dto.getEmail());
        entity.setUsername(dto.getUsername());
        entity.setPassword(dto.getPassword());
        entity.setAdmin(convertToAdminEntity(dto.getAdmin()));
        return entity;
    }

    private UserDto convertToUserDto(User entity) {
        return new UserDto(
                entity.getId(),
                entity.getName(),
                entity.getEmail(),
                entity.getUsername(),
                entity.getPassword(),
                convertToAdminDto(entity.getAdmin())
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
