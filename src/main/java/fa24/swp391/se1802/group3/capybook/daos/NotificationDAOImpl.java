package fa24.swp391.se1802.group3.capybook.daos;

import fa24.swp391.se1802.group3.capybook.models.NotificationDTO;
import jakarta.persistence.EntityManager;
import jakarta.persistence.TypedQuery;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Repository
public class NotificationDAOImpl implements NotificationDAO{
    EntityManager entityManager;

    @Autowired
    public NotificationDAOImpl(EntityManager entityManager) {
        this.entityManager = entityManager;
    }

    @Override
    @Transactional
    public void save(NotificationDTO notificationDTO) {
        entityManager.merge(notificationDTO);
    }

    @Override
    public NotificationDTO find(Integer notID) {
        return entityManager.find(NotificationDTO.class,notID);
    }

    @Override
    @Transactional
    public void delete(Integer notID) {
        entityManager.remove(this.find(notID));
    }

    @Override
    public List<NotificationDTO> findAll() {
        TypedQuery<NotificationDTO> query = entityManager.createQuery("SELECT n FROM NotificationDTO n",NotificationDTO.class);
        return query.getResultList();
    }

    @Override
    public List<NotificationDTO> search(String notTitle) {
        TypedQuery<NotificationDTO> query = entityManager.createQuery("SELECT n FROM NotificationDTO n WHERE n.notTitle LIKE :nottitle",NotificationDTO.class);
        query.setParameter("nottitle","%"+notTitle+"%");
        return query.getResultList();
    }
}
