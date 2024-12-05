package LibraryManagementSystem.repository.custom;

import LibraryManagementSystem.entity.TransactionDetail;
import LibraryManagementSystem.repository.SuperRepository;

public interface TransactionDetailRepository extends SuperRepository {

    void save(TransactionDetail entity);

}
