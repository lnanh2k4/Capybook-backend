package fa24.swp391.se1802.group3.capybook.daos;

import fa24.swp391.se1802.group3.capybook.exceptions.AccountExceptionNotFound;
import fa24.swp391.se1802.group3.capybook.models.AccountDTO;
import jakarta.persistence.EntityManager;
import jakarta.persistence.TypedQuery;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

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
    public AccountDTO save(AccountDTO accountDTO) {
            return entityManager.merge(accountDTO);
    }

    @Override
    public AccountDTO find(String username) {
        AccountDTO object = entityManager.find(AccountDTO.class,username);
        if(object!=null){
            return object;
        } else{
            throw new AccountExceptionNotFound();
        }
    }

    @Override
    @Transactional
    public void delete(String username) {
        entityManager.remove(this.find(username));
    }

    @Override
    public List<AccountDTO> findAll() {
        TypedQuery<AccountDTO> query = entityManager.createQuery("From AccountDTO", AccountDTO.class);
        return query.getResultList();
    }
}
