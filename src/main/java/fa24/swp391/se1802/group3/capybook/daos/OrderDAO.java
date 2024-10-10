package fa24.swp391.se1802.group3.capybook.daos;

import fa24.swp391.se1802.group3.capybook.models.Order;
import fa24.swp391.se1802.group3.capybook.models.Promotion;

import java.util.List;

public interface OrderDAO {
    void save(Order order);
    Order find(int orderID);
    void update(Order order);
    void delete(int orderID);
    List<Order> findAll();
}
