package LibraryManagementSystem.projection;

import LibraryManagementSystem.embedded.Name;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@AllArgsConstructor
@Data

public class AdminProjection {

    private int id;
    private Name name;

}
