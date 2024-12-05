package LibraryManagementSystem.service.custom;

import LibraryManagementSystem.dto.TransactionDto;
import LibraryManagementSystem.service.SuperService;

import java.util.List;

public interface TransactionService extends SuperService {

    boolean saveTransaction(TransactionDto dto);
    boolean updateTransaction(TransactionDto dto);
    TransactionDto getTransactionData(int id);
    List<TransactionDto> getTransactionAllId();
    List<TransactionDto> getAllOverDueBorrowers();
    int getLastTransactionId();

}
