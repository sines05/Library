package LibraryManagementSystem.dto;

import LibraryManagementSystem.embedded.Name;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@AllArgsConstructor
@Data
public class AdminDto {

    private int id;
    private Name name;
    private String contactNo;
    private String email;
    private String username;
    private String password;
}
