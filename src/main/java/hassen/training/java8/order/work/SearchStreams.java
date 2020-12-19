package hassen.training.java8.order.work;

import hassen.training.java8.order.entity.Customer;
import hassen.training.java8.order.entity.Order;
import hassen.training.java8.order.entity.OrderLine;
import hassen.training.java8.order.entity.Status;

import java.time.LocalDate;
import java.util.Comparator;
import java.util.List;
import java.util.Optional;

import static java.util.Comparator.comparing;
import static java.util.stream.Collectors.toList;

public class SearchStreams {




    public List<Order>p1_getActiveOrder(Customer customer){
        return customer.getOrders().stream()
                .filter(t -> t.getStatus().equals(Status.ACTIVE))
                .collect(toList());
    }


    /**
     *
     * @param orders
     * @param orderId
     *
     * return the Order in the list with the given id
     * -...Any or ...First ?
     * -What do you do when you don't find it ? null/throw/Optional ?
     **/

    public Order p2_getOrderById(List<Order> orders,long orderId){
        return orders.stream().
                filter(order -> order.getId().equals(orderId))
                .findAny()
                .get();
    }

    /**
     * return  true if customer has at least one order with status ACTIVE
     * @param customer
     * @return
     */
    public boolean p3_hasActiveOrders(Customer customer){
        return customer.getOrders().stream()
                .anyMatch(Order::isActive);
    }


    /**
     * An Order can be returned if it doesn't contain
     * any OrderLine with isSpecialOffer()==true
     *
     * @param order
     * @return
     */
    public boolean p4_canBeReturned(Order order){
        return order.getOrderLines().stream()
                .noneMatch(OrderLine::isSpecialOffer);
    }

    //-------------Select the best ----------------------

    /**
     *
     * The Order with maximum getTotalPrice
     * i.e the most expensive Order, or null if no Orders
     * - Challenge: return an Optional<creationDate>
     */
    public Optional<LocalDate> p5_getMaxPriceOrder(Customer customer){
        return customer.getOrders().stream()
                .max(comparing(Order::getTotalPrice))
                .map(Order::getCreateDate);
    }


    /**
     * last 3 Orders sorted descending by creatingDate
     */
    public List<Order>p6_getLast3Orders(Customer customer){
        return customer.getOrders().stream()
                .sorted(Comparator.comparing(Order::getCreateDate).reversed())
                .collect(toList());
    }

}
