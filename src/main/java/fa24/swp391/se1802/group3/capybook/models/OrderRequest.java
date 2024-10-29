package fa24.swp391.se1802.group3.capybook.models;

import java.util.List;

public class OrderRequest {
    private OrderDTO orderDTO;
    private List<OrderDetailDTO> orderDetails;

    // Getters and setters
    public OrderDTO getOrderDTO() {
        return orderDTO;
    }

    public void setOrderDTO(OrderDTO orderDTO) {
        this.orderDTO = orderDTO;
    }

    public List<OrderDetailDTO> getOrderDetails() {
        return orderDetails;
    }

    public void setOrderDetails(List<OrderDetailDTO> orderDetails) {
        this.orderDetails = orderDetails;
    }
}

