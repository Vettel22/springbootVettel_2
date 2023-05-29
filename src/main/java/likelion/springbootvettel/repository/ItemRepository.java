package likelion.springbootvettel.repository;

import likelion.springbootvettel.domain.Item;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ItemRepository extends JpaRepository<Item, Long> {
}

