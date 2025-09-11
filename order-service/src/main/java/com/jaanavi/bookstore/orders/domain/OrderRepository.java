package com.jaanavi.bookstore.orders.domain;

import com.jaanavi.bookstore.orders.domain.models.OrderStatus;
import com.jaanavi.bookstore.orders.domain.models.OrderSummary;
import java.util.List;
import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

interface OrderRepository extends JpaRepository<OrderEntity, Long> {

    public List<OrderEntity> findByStatus(OrderStatus status);

    Optional<OrderEntity> findByOrderNumber(String orderNumber);

    default void updateOrderStatus(String orderNumber, OrderStatus status) {
        OrderEntity order = this.findByOrderNumber(orderNumber).orElseThrow();
        order.setStatus(status);
        this.save(order);
    }

    // instend of using derived query and fetch all unrequird feilds from order entity,
    //    we can write our own query by using jpql
    // and also creating new OrderSummery instance for every record that is matched with username

    @Query(
            """
            select new com.jaanavi.bookstore.orders.domain.models.OrderSummary(o.orderNumber ,o.status)
            from OrderEntity o
            where o.userName = :userName
            """)
    List<OrderSummary> findByUserName(String userName);

    @Query(
            """
        select distinct o
        from OrderEntity o left join fetch o.items
        where o.userName = :userName and o.orderNumber = :orderNumber
        """)
    Optional<OrderEntity> findByUserNameAndOrderNumber(String userName, String orderNumber);
}
