package likelion.springbootvettel.domain;

import jakarta.persistence.*;
import lombok.Data;

import java.util.ArrayList;
import java.util.List;

@Entity //엔티티 내에 다른 객체를 포함하는 임베디드(embedded) 타입을 나타내는 어노테이션
@Data // getter, setter 등 메서드 자동생성기능
public class Member {
    @Id @GeneratedValue
    private Long id;
    //@Id 와 함께 @GeneratedValue는 pk값을 자동으로 생성하는 역할.

    private String name;

    @Embedded
    private Address address;
    //엔티티 내에 다른 객체를 포함하는 임베디드(embedded) 타입

    @OneToMany(mappedBy = "member")

    private List<Order> orderList = new ArrayList<>();
    //@OneToMany 는 "member : Order = 1 : 여러개 " 관계 (== 1 : N)임을 나타내고 있습니다.



}
