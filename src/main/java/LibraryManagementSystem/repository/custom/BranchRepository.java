package LibraryManagementSystem.repository.custom;

import LibraryManagementSystem.entity.Branch;
import LibraryManagementSystem.repository.CrudRepository;

public interface BranchRepository extends CrudRepository<Branch> {

    int getAllBranchCount();

}
