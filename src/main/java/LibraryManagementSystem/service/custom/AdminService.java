package LibraryManagementSystem.service.custom;

import LibraryManagementSystem.dto.AdminDto;
import LibraryManagementSystem.service.SuperService;

import java.util.List;

public interface AdminService extends SuperService {

    boolean saveAdmin(AdminDto dto);
    boolean updateAdmin(AdminDto dto);
    AdminDto getAdminData(int id);
    List<AdminDto> getAllAdminId();
    boolean checkUsernameAndPassword(String username, String password);
    AdminDto getAdmin(String username);

}
