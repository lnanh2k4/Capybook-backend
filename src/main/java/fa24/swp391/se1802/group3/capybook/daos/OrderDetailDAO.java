package fa24.swp391.se1802.group3.capybook.daos;

import fa24.swp391.se1802.group3.capybook.models.OrderDetail;

import java.util.List;

public interface OrderDetailDAO {
    void save(OrderDetail orderDetail);
    OrderDetail find(int ODID);
    void update(OrderDetail orderDetail);
    void delete(int ODID);
    List<OrderDetail> findAll();
}
