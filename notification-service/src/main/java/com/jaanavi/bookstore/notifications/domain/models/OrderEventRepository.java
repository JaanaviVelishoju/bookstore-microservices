package com.jaanavi.bookstore.notifications.domain.models;

import com.jaanavi.bookstore.notifications.domain.OrderEventEntity;
import org.springframework.data.jpa.repository.JpaRepository;

public interface OrderEventRepository extends JpaRepository<OrderEventEntity, Long> {

    boolean existsByEventId(String eventId);
}
