package LibraryManagementSystem.repository.custom;

import LibraryManagementSystem.entity.User;
import LibraryManagementSystem.repository.CrudRepository;

public interface UserRepository extends CrudRepository<User> {

    boolean checkUsernameAndPassword(String username, String password);
    User getUser(String username);
    int getAllUserCount();

}
