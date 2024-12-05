package LibraryManagementSystem.service.custom;

import LibraryManagementSystem.dto.BranchDto;
import LibraryManagementSystem.service.SuperService;

import java.util.List;

public interface BranchService extends SuperService {

    boolean saveBranch(BranchDto dto);
    boolean updateBranch(BranchDto dto);
    BranchDto getBranchData(int id);
    List<BranchDto> getAllBranchId();

}
