package LibraryManagementSystem.service.custom.impl;

import LibraryManagementSystem.entity.Admin;
import LibraryManagementSystem.entity.Book;
import LibraryManagementSystem.entity.Branch;
import LibraryManagementSystem.entity.User;
import LibraryManagementSystem.repository.RepositoryFactory;
import LibraryManagementSystem.repository.custom.AdminRepository;
import LibraryManagementSystem.repository.custom.BookRepository;
import LibraryManagementSystem.repository.custom.BranchRepository;
import LibraryManagementSystem.repository.custom.UserRepository;
import LibraryManagementSystem.repository.custom.impl.AdminRepositoryImpl;
import LibraryManagementSystem.repository.custom.impl.BookRepositoryImpl;
import LibraryManagementSystem.repository.custom.impl.BranchRepositoryImpl;
import LibraryManagementSystem.repository.custom.impl.UserRepositoryImpl;
import LibraryManagementSystem.service.custom.DeleteService;
import LibraryManagementSystem.util.SessionFactoryConfig;
import org.hibernate.Session;
import org.hibernate.Transaction;

public class DeleteServiceImpl implements DeleteService {

    AdminRepository adminRepository =
            (AdminRepository) RepositoryFactory.getInstance()
                    .getRepository(RepositoryFactory.RepositoryTypes.ADMIN);
    BookRepository bookRepository =
            (BookRepository) RepositoryFactory.getInstance()
                    .getRepository(RepositoryFactory.RepositoryTypes.BOOK);
    BranchRepository branchRepository =
            (BranchRepository) RepositoryFactory.getInstance()
                    .getRepository(RepositoryFactory.RepositoryTypes.BRANCH);
    UserRepository userRepository =
            (UserRepository) RepositoryFactory.getInstance()
                    .getRepository(RepositoryFactory.RepositoryTypes.USER);

    public Session session;

    public void initializeSession() {
        session = SessionFactoryConfig.getInstance().getSession();
    }

    @Override
    public boolean deleteAdmin(int id) {
        initializeSession();
        Transaction transaction = session.beginTransaction();
        try {
            AdminRepositoryImpl.setSession(session);
            Admin admin = adminRepository.getData(id);
            adminRepository.delete(admin);
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
    public boolean deleteBook(int id) {
        initializeSession();
        Transaction transaction = session.beginTransaction();
        try {
            BookRepositoryImpl.setSession(session);
            Book book = bookRepository.getData(id);
            bookRepository.delete(book);
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
    public boolean deleteBranch(int id) {
        initializeSession();
        Transaction transaction = session.beginTransaction();
        try {
            BranchRepositoryImpl.setSession(session);
            Branch branch = branchRepository.getData(id);
            branchRepository.delete(branch);
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
    public boolean deleteUser(int id) {
        initializeSession();
        Transaction transaction = session.beginTransaction();
        try {
            UserRepositoryImpl.setSession(session);
            User user = userRepository.getData(id);
            userRepository.delete(user);
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

}
