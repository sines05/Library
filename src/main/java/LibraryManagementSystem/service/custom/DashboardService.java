package LibraryManagementSystem.service.custom;

import LibraryManagementSystem.dto.TransactionDto;
import LibraryManagementSystem.projection.AdminProjection;
import LibraryManagementSystem.service.SuperService;

import java.util.List;

public interface DashboardService extends SuperService {

    List<TransactionDto> getAllTransactions();
    List<AdminProjection> getAdminIdAndName();
    List<TransactionDto> getAllOverDueBorrowers();
    int getAllUserCount();
    int getAllBookCount();
    int getAllBranchCount();

}
