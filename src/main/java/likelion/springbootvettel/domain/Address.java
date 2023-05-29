package likelion.springbootvettel.domain;

import jakarta.persistence.Embeddable;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Embeddable //엔티티 내에 다른 객체를 포함하는 임베디드(embedded) 타입을 나타내는 어노테이션, @Embeddable이 선언된 클래스는 엔티티 클래스에 내장되어 엔티티 객체의 일부로 취급.
@Data // getter, setter 등 메서드 자동생성기능
@AllArgsConstructor // 클래스 모든 필드 값을 파라미터로 받는 생성자 기능을 추가
@NoArgsConstructor // 클래스 기본 생성자를 자동으로 추가

public class Address {
    private String city;
    private String state;
    private String street;
    private String zipcode;
}
