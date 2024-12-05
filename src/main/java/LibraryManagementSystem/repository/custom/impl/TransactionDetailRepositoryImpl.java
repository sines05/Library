package LibraryManagementSystem.repository.custom.impl;

import LibraryManagementSystem.entity.TransactionDetail;
import LibraryManagementSystem.repository.custom.TransactionDetailRepository;
import lombok.Setter;
import org.hibernate.Session;

public class TransactionDetailRepositoryImpl implements TransactionDetailRepository {

    @Setter
    private static Session session;

    @Override
    public void save(TransactionDetail entity) {
        session.save(entity);
    }

}
