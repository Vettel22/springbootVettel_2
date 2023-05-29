package likelion.springbootvettel.domain;

import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.ArrayList;
import java.util.List;

import static jakarta.persistence.EnumType.STRING;
import static likelion.springbootvettel.domain.Delivery.DeliveryStatus.ESTABLISHED;
import static lombok.AccessLevel.PROTECTED;

@Entity //엔티티 내에 다른 객체를 포함하는 임베디드(embedded) 타입을 나타내는 어노테이션
@NoArgsConstructor(access = PROTECTED) //인자를 받지 않는 기본 생성자를 자동으로 생성하면서, 접근 수준을 PROTECTED로 지정. 따라서 해당 생성자를 클래스 내부 또는 서브클래스에서만 접근할 수 있도록 설정.
@Getter //클래스 모든 필드에 Getter 메서드 생성.
public class Delivery {
    @Id @GeneratedValue
    private Long id;
    //@Id 와 함께 @GeneratedValue는 pk값을 자동으로 생성하는 역할.

    @OneToOne(mappedBy = "delivery")
    private Order order;
    //@OneToOne 은 "Order : Delivery = 1 : 1" 관계 (== 1 : 1)임을 나타내고 있습니다.

    @Enumerated(STRING)
    private DeliveryStatus deliveryStatus;
    //열거형 타입의 필드를 매핑. 필드의 값을 문자열로 변환해 데이터베이스에 저장, 검색할 수 있도록함.

    private String city;
    private String state;
    private String street;
    private String zipcode;

    //Delivery 클래스를 생성한다.
    public static Delivery createDelivery(Order order, String city, String state, String street, String zipcode) {
        Delivery delivery = new Delivery();
        delivery.order = order;
        delivery.deliveryStatus = ESTABLISHED;
        delivery.city = city;
        delivery.state = state;
        delivery.street = street;
        delivery.zipcode = zipcode;
        return delivery;
    }
    //DeliveryStatus 열거형이 ESTABLISHED, PROGRESS, DONE이라는 세 상수를 포함한다는 것을 나타낸다.
    public enum DeliveryStatus {
        ESTABLISHED, PROGRESS, DONE
    }
}