package LibraryManagementSystem.repository.custom;

import LibraryManagementSystem.entity.Transaction;
import LibraryManagementSystem.repository.CrudRepository;

import java.util.List;

public interface TransactionRepository extends CrudRepository<Transaction> {

    List<Transaction> getAllOverDueBorrowers();
    int getLastId();

}
