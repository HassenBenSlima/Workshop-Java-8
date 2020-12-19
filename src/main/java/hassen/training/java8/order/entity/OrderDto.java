package hassen.training.java8.order.entity;

import java.math.BigDecimal;
import java.time.LocalDate;

public class OrderDto {

    Long id;
    Status status;
    boolean active;
    public BigDecimal totalPrice;
    public LocalDate createDate;

    public OrderDto() {
    }


}
