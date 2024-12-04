package fa24.swp391.se1802.group3.capybook.daos;

import fa24.swp391.se1802.group3.capybook.models.OrderDTO;
import fa24.swp391.se1802.group3.capybook.utils.EmailSenderUtil;

import java.util.List;

public interface OrderDAO {
    void save(OrderDTO orderDTO);
    OrderDTO find(int orderID);
    void update(OrderDTO orderDTO);
    void delete(int orderID);
    List<OrderDTO> findAll();
    List<OrderDTO> searchByOrderId(int orderID);
}
