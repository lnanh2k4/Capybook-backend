package fa24.swp391.se1802.group3.capybook.daos;

import fa24.swp391.se1802.group3.capybook.models.OrderDetailDTO;

import java.util.List;

public interface OrderDetailDAO {
    void save(OrderDetailDTO orderDetailDTO);
    OrderDetailDTO find(int ODID);
    void update(OrderDetailDTO orderDetailDTO);
    void delete(int ODID);
    List<OrderDetailDTO> findAll();
    List<OrderDetailDTO> findByOrderID(int orderID);
}
