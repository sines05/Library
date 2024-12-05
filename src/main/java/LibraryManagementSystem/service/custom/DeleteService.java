package LibraryManagementSystem.service.custom;

import LibraryManagementSystem.service.SuperService;

public interface DeleteService extends SuperService {

    boolean deleteAdmin(int id);
    boolean deleteBook(int id);
    boolean deleteBranch(int id);
    boolean deleteUser(int id);

}
