package likelion.springbootvettel.repository;

import likelion.springbootvettel.domain.Order;
import likelion.springbootvettel.domain.OrderStatus;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

public interface OrderRepository extends JpaRepository<Order, Long> {
}
