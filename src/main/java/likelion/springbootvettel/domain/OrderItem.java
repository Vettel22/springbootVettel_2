package likelion.springbootvettel.domain;

import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.Comment;

import static jakarta.persistence.FetchType.LAZY;
import static lombok.AccessLevel.PROTECTED;

@Entity //엔티티 내에 다른 객체를 포함하는 임베디드(embedded) 타입을 나타내는 어노테이션
@NoArgsConstructor(access = PROTECTED) //인자를 받지 않는 기본 생성자를 자동으로 생성하면서, 접근 수준을 PROTECTED로 지정. 따라서 해당 생성자를 클래스 내부 또는 서브클래스에서만 접근할 수 있도록 설정.
@Getter
public class OrderItem {
    @Id
    @GeneratedValue
    private Long id;
    //@Id 와 함께 @GeneratedValue는 pk값을 자동으로 생성하는 역할.

    @ManyToOne(fetch = LAZY)
    @JoinColumn(name = "order_id")
    private Order order;
    //@ManyToOne 은 "OrderItem : Order = 여러개 : 1" 관계 (== N : 1)임을 나타내고 있습니다. 또한 fetch는 지연로딩을 사용하기 위해 LAZY로 되어 있습니다.
    //@JoinColumn은 연관관계를 풀어내는 방식을 "FK Column으로 넣어주겠다."는 설정이며, name을 설정한건 OrderItem테이블의 해당 FK 칼럼 이름을 order_id로 설정하겠단 의미입니다.

    @ManyToOne(fetch = LAZY)
    @JoinColumn(name = "item_id")
    private Item item;
    //@ManyToOne 은 "OrderItem : Item = 여러개 : 1" 관계 (== N : 1)임을 나타내고 있습니다. 또한 fetch는 지연로딩을 사용하기 위해 LAZY로 되어 있습니다.
    //@JoinColumn은 연관관계를 풀어내는 방식을 "FK Column으로 넣어주겠다."는 설정이며, name을 설정한건 OrderItem테이블의 해당 FK 칼럼 이름을 item_id로 설정하겠단 의미입니다.

    private Integer price;
    private Integer count;

    /**
     * 스태틱 팩토리 메서드
     */
    public static OrderItem createOrderItem(Item item, int orderPrice, int orderCount) {
        OrderItem orderItem = new OrderItem();
        orderItem.setItem(item);
        orderItem.price = orderPrice;
        orderItem.count = orderCount;
        // 연관관계 편의 메서드
        item.removeStock(orderCount);
        return orderItem;
    }
    //OrderItem 클래스를 생성. orderItem 반환.

    public void setOrder(Order order) {
        this.order = order;
        order.getOrderItemList().add(this);
    }
    //setOrder, this를 추가

    public void setItem(Item item) {
        this.item = item;
        item.getOrderItem().add(this);
    }
    //setItem, this를 추가
    /**
     * 비즈니스 로직
     */
    public int getTotalPrice() {
        return this.getPrice() * this.getCount();
    }
    //가격과 수량을 곱하는 정수형 getTotalPrice

    public void cancel() {
        this.getItem().addStock(count);
    }
}
