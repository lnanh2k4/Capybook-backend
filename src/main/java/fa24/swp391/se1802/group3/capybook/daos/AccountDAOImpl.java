package fa24.swp391.se1802.group3.capybook.daos;

import fa24.swp391.se1802.group3.capybook.models.Account;
import jakarta.persistence.EntityManager;
import jakarta.persistence.TypedQuery;
import org.hibernate.query.Query;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Repository
public class AccountDAOImpl implements AccountDAO{
    //define entity manager
    EntityManager entityManager;
    //inject entity manager using constructor injection

    @Autowired
    public AccountDAOImpl(EntityManager entityManager) {
        this.entityManager = entityManager;
    }

    //implements method
    @Override
    @Transactional
    public void save(Account account) {
            entityManager.persist(account);
    }

    @Override
    public Account find(String username) {
        return entityManager.find(Account.class,username);
    }

    @Override
    @Transactional
    public void update(Account account) {
        entityManager.merge(account);
    }

    @Override
    public void delete(String username) {
        entityManager.remove(this.find(username));
    }

    @Override
    public List<Account> findAll() {
        TypedQuery<Account> query = entityManager.createQuery("From Account", Account.class);
        return query.getResultList();
    }
}
