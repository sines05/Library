package LibraryManagementSystem.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@AllArgsConstructor
@Data
public class BookDto {

    private int id;
    private String name;
    private String type;
    private String language;
    private String status;
    private AdminDto admin;
    private int quantity;
    private String isbn;
    private String author;


}
