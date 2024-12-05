package LibraryManagementSystem.service.custom;

import LibraryManagementSystem.dto.AdminDto;
import LibraryManagementSystem.dto.UserDto;
import LibraryManagementSystem.service.SuperService;

import java.util.List;

public interface UserService extends SuperService {

    boolean saveUser(UserDto dto);
    boolean updateUser(UserDto dto);
    UserDto getUserData(int id);
    List<UserDto> getAllUserId();
    AdminDto getAdminData(int id);
    boolean checkUsernameAndPassword(String username, String password);
    UserDto getUser(String username);

}
