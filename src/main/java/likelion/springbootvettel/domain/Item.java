package likelion.springbootvettel.domain;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;
import lombok.*;
import org.hibernate.annotations.Comment;

import java.util.ArrayList;
import java.util.List;

import static lombok.AccessLevel.PROTECTED;

@Entity //엔티티 내에 다른 객체를 포함하는 임베디드(embedded) 타입을 나타내는 어노테이션
@Getter
@Setter
@NoArgsConstructor //인자를 받지 않는 기본 생성자를 자동으로 생성
public class Item {
    @Id @GeneratedValue
    private Long id;
    //@Id 와 함께 @GeneratedValue는 pk값을 자동으로 생성하는 역할.

    @OneToMany(mappedBy = "item")
    private List<OrderItem> orderItem = new ArrayList<>();
    //@OneToMany 는 "Item : OrderItem = 1 : 여러개 " 관계 (== 1 : N)임을 나타내고 있습니다.
    //mappedBy = "item"를 통해 OrderItem 엔티티 클래스의 item 속성에 의해 매핑되고 있음을 의미합니다.

    private String brand; /*스트링*/
    private String name;
    private Integer price; /*인티저*/
    private Integer stock;

    /**
     * 비즈니스 로직
     */
    @Comment("재고 추가")
    public void addStock(int quantity) {
        this.stock += quantity;
    }
    //현재 객체에다가 quantity를 더하여 재고 추가

    @Comment("재고 감소")
    public void removeStock(int stockQuantity) {
        int restStock = this.stock - stockQuantity;
        if (restStock < 0) {
            throw new IllegalStateException("need more stock");
            //재고가 0보다 작은 경우 재고 감소를 수행하지 않는다.
        }
        this.stock = restStock;
    }
}
