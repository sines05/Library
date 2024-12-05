package LibraryManagementSystem.repository.custom;

import LibraryManagementSystem.entity.Admin;
import LibraryManagementSystem.projection.AdminProjection;
import LibraryManagementSystem.repository.CrudRepository;

import java.util.List;

public interface AdminRepository extends CrudRepository<Admin> {

    boolean checkUsernameAndPassword(String username, String password);
    Admin getAdmin(String username);
    List<AdminProjection> getAdminIdAndName();

}
