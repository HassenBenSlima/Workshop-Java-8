package hassen.training.java8.order.work;

import hassen.training.java8.order.entity.*;
import org.springframework.beans.factory.annotation.Autowired;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.*;
import java.util.stream.Collectors;

import static java.util.stream.Collectors.*;

public class TransformStreams {

    /**
     * Transform all entities to DTOs.
     * Discussion:...Make it cleanest!
     */

    public List<OrderDto> p01_toDTOs(List<Order> orders){
        return orders.stream()
                .map(orderMapper::toDto)
                .collect(toList());
    }

    @Autowired
    private OrderMapper orderMapper;

    public static class OrderMapper{
    private  OrderDto toDto(Order order){
        OrderDto dto = new OrderDto();
        dto.totalPrice=order.getTotalPrice();
        dto.createDate=order.getCreateDate();
        return dto;
    }

    }

    /**
     * Note : Order.getPaymentMethod
     * @param customer
     * @return
     */
    public Set<PaymentMethod> p02getUsedPaymentMethods(Customer customer){
        return customer.getOrders().stream()
                .map(Order::getPaymentMethod)
                .collect(toSet());
    }

    /**
     * When did the customer created  orders ?
     * Note: Order.getCreateDate()
     * @param customer
     * @return
     */
    public SortedSet<LocalDate>p03_getOrderDatesAscending(Customer customer){
        return customer.getOrders().stream()
                .map(Order::getCreateDate)
                .collect(Collectors.toCollection(TreeSet::new));
    }


    /**
     * return a map order.id ->order
     *
     * @param customer
     * @return
     */
    public Map<Long,Order> p04_mapOrderById(Customer customer){
        return customer.getOrders().stream()
                .collect(toMap(Order::getId,o->o));
    }

    /**
     * Orders grouped by order.paymentMethod
     * @param customer
     * @return
     */
    public Map<PaymentMethod,List<Order>> p05_getProductByPaymentMethod(Customer customer){
        return customer.getOrders().stream()
                .collect(groupingBy(Order::getPaymentMethod));
    }

    /**
     * A hard one
     * Get total number of products bought by a customer,across all her orders.
     * Customer --->* Order --->* OrderLines(.count .product)
     * The sum of all counts for the same product.
     *  i.e SELECT PROD_ID,SUM(COUNT) FROM PROD GROUPING BY PROD_ID
     */
    public Map<Product,Long> p06_getProductCount(Customer customer){
        //List<OrderLine> allLines=new ArrayList<>();
        /**
        for(Order order : customer.getOrders()){
            allLines.addAll(order.getOrderLines());
        }*/
        /**
        allLines=customer.getOrders().stream()
                .flatMap(order -> order.getOrderLines().stream())//Stream<OrderLine>
                .collect(toList());*/
        /**
        allLines.stream()
        .collect(groupingBy(OrderLine::getProduct,
                        summingLong(OrderLine::getCount)));
         */
        return customer.getOrders().stream()
                .flatMap(order -> order.getOrderLines().stream())
                .collect(groupingBy(OrderLine::getProduct,
                        summingLong(OrderLine::getCount)));
    }

    /**
     * ALL the unique product bought by the customer
     * sorted by Product.name
     */
    public List<Product> p07_getAllOrderProducts(Customer customer){
        return  customer.getOrders().stream()
                .flatMap(order -> order.getOrderLines().stream())
                .map(OrderLine::getProduct)
                .distinct()
                .sorted(Comparator.comparing(Product::getName))
                .collect(toList());
    }

    /**
     * The names of all the products bought by Customer,
     * sorted and then concatenated by ",".
     * Example: "Armchair,Chair,Table".
     * Hint:Reuse the previous function.
     */
    public String p08_getProductsJoined(Customer customer){
        return p07_getAllOrderProducts(customer).stream()
                .map(Product::getName)
                .collect(joining(","));
    }

    public Long p09_getApproximateTotalOrdersPrice(Customer customer){
        return customer.getOrders().stream()
                .map(Order::getTotalPrice)
                .reduce(BigDecimal.ZERO,BigDecimal::add).longValue();
    }
}
