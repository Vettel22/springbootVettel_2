package likelion.springbootvettel.domain;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

import static jakarta.persistence.CascadeType.ALL;
import static jakarta.persistence.FetchType.LAZY;
import static lombok.AccessLevel.PROTECTED;

@Entity //객체와 데이터베이스간의 매핑을 설정할 수 있다.
@Table(name = "orders") // 이거 안하면 에러, 테이블 생성
@Getter
@NoArgsConstructor(access = PROTECTED)
public class Order {
    @Id
    @GeneratedValue
    private Long id;
    //@Id 와 함께 @GeneratedValue는 pk값을 자동으로 생성하는 역할.

    @ManyToOne(fetch = LAZY)
    @JoinColumn(name = "member_id")
    private Member member;
/**
 * 이 필드는 Order와 Member 클래스 간의 연관관계를 설정하기 위한 어노테이션으로 이루어져 있습니다.
 * @ManyToOne 은 "Order : Member = 여러개 : 1" 관계 (== N : 1)임을 나타내고 있습니다.
 * 또한 @ManyToOne의 속성 중 하나인 fetch는 지연로딩을 사용하기 위해 LAZY로 되어 있습니다.
 * @JoinColumn은 연관관계를 풀어내는 방식을 "FK Column으로 넣어주겠다."는 설정이며, name을 설정한건 Order테이블의 해당 FK 칼럼 이름을 member_id로 설정하겠단 의미입니다.
 * 그리고 접근제어자를 private으로 설정함으로써 해당 필드에 대한 접근 포인트를 클래스 내부로 최소화함으로써 안정적인 구조를 취하였습니다.**/

    @OneToOne(fetch = LAZY, cascade = ALL)
    @JoinColumn(name = "delivery_id")
    private Delivery delivery;
    /**
     * 이 필드는 Order와 Delivery 클래스 간의 연관관계를 설정하기 위한 어노테이션으로 이루어져 있습니다.
     * @OneToOne 은 "Order : Delivery = 1 : 1" 관계 (== 1 : 1)임을 나타내고 있습니다.
     * 또한 fetch 속성은 지연로딩을 사용하기 위해 LAZY로 되어있습니다.
     * @JoinColumn은 연관관계를 풀어내는 방식을 "FK Column으로 넣어주겠다."는 설정이며, name을 설정한건 Order테이블의 해당 FK 칼럼 이름을 delivery_id로 설정하겠단 의미입니다.**/

    @OneToMany(mappedBy = "order", cascade = ALL)
    private List<OrderItem> orderItemList = new ArrayList<>();
    /**
     * @OneToMany 는 "Order : OrderItem = 1 : 여러개 " 관계 (== 1 : N)임을 나타내고 있습니다.
     * mappedBy = "order"를 통해 OrderItem 엔티티 클래스의 order 속성에 의해 매핑되고 있음을 의미합니다.
     * cascade = ALL 을 통해  Order 엔티티에 대한 모든 변경 작업(생성, 수정, 삭제 등)이 OrderItem 엔티티에도 전파되도록 설정되어 있습니다. 이를 통해 Order 엔티티의 변경이 OrderItem 엔티티에 자동으로 반영될 수 있습니다.
     * orderItemList은 OrderItem 엔티티 객체들의 컬렉션인 List<OrderItem> 타입의 속성으로 초기화되며, 해당 엔티티와의 일대다 관계를 표현합니다.**/

    private LocalDateTime orderDate;

    @Enumerated(EnumType.STRING)
    private OrderStatus orderStatus;
    //@Enumerated(EnumType.STRING)은 열거형 타입의 필드를 매핑할 때 사용합니다. 그리고 열거형 상수가 문자열로 저장되고 검색될 수 있습니다.

    // 연관관계 편의 메서드
    public void setMember(Member member) {
        this.member = member;
        member.getOrderList().add(this);
    }
    //member 객체의 getOrderList()를 호출해 this를 추가.

    //Order 클래스를 생성한다.
    public static Order createOrder(Member member, OrderItem... orderItems) {
        Order order = new Order();
        order.setMember(member);
        order.orderDate = LocalDateTime.now();
        order.orderStatus = OrderStatus.ORDERED;
        order.delivery = Delivery.createDelivery(order, member.getAddress().getCity(),
                member.getAddress().getState(),
                member.getAddress().getStreet(),
                member.getAddress().getZipcode());
        for (OrderItem orderItem : orderItems) {
            order.orderItemList.add(orderItem);
            //Order 객체의 orderItemList에 추가.
            orderItem.setOrder(order);
        }
        return order;
    }

    //cancel 주문 취소
    public void cancel() {
        //배송상태가 완료이면 주문 취소를 하지 않는 조건문
        if (delivery.getDeliveryStatus() == Delivery.DeliveryStatus.DONE) {
            throw new IllegalStateException("배송 완료했다 양아치야");
        }
        this.orderStatus = OrderStatus.CANCELED;
        // OrderItem 전체를 cancel 하는 조건문
        for (OrderItem orderItem : orderItemList) {
            orderItem.cancel();
        }
    }

    public int getTotalPrice() {
        int totalPrice = 0;
        for (OrderItem orderItem : orderItemList) {
            totalPrice += orderItem.getTotalPrice();
        }
        return totalPrice;
        //정수형 getTotalPrice()에서 조건문으로 계산해 totalPrice 반환
    }
}
