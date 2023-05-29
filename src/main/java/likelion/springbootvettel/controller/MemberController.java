package likelion.springbootvettel.controller;

import likelion.springbootvettel.domain.Member;
import likelion.springbootvettel.service.MemberService;
import likelion.springbootvettel.service.MemberServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.List;

/*
 * @Controller라는 어노테이션이 이 클래스가 Controller임을 말하고 스프링이 Controller라고 알게해줍니다.
 @RequestMapping("members") 어노테이션은 멤버스에 대한 요청을 받아처리하게해줍니다.
 public class MemberController로 MemberController라는 클래스임을 안다.
 */

@Controller
@RequestMapping("members")
public class MemberController {



    /*
     * (예시)
     * 이 것은 생성자입니다.
     * @Autowired라는 어노테이션은 MemberController 객체를 실행해야 할 때 필요한 의존성을 주입해달라고 선언하기 위해 명시하는 어노테이션이며, 생성자 주입 방식을 선언하고 있습니다.
     * MemberController의 필드를 MemberService 타입으로 선언하였지만, 생성자 paramer에는 MemberServiceImpl이 주입되게 함으로써 느슨한 결합(Loosen Coupling)을 구현하였습니다.
     * @참고 : 실제로는 MemberController 생성자의 파라미터에 MemberServiceImpl이 아니라 MemberService로 쓰여있어도 스프링이 알아서 구현체 클래스의 인스턴스 (MemberServiceImpl memberserviceimpl)를 넣어주게 됩니다.
     *       즉, public MemberController(MemberService memberService) {this.memberService = memberService;} 와 같이 작성해도 에러가 없고, 이게 사실 정석입니다.
     *       아래처럼 작성해 둔 이유는, 실제로는 아래와 같이 동작한다는 것을 여러분 눈으로 먼저 보길 바랐던 제 마음이었습니다.
     *       지금, MemberController의 필드가 MemberService 타입의 데이터인데, 생성자로 주입되는 것은 MemberServiceImpl 타입이라는 것을 충분히 음미하시길 바랍니다.
     **/
    @Autowired
    public MemberController(MemberServiceImpl memberServiceImpl) {
        this.memberService = memberServiceImpl;
    }

    /*
     * @GetMapping 어노테이션이 요청을 Get 요청을 처리, 요청이 있으면 호출.
     * createForm이라는 메서드가 파라미터로 모델객체를 받고, 모델객체는 컨트롤러에서 뷰로 데이터를 전달한다.
     * 매서드 내부에서 model.addAttribute()메서드로 memberForm라는 새로운 멤버객체를 모델에 추가해준다.
     * 따라서 뷰에서 memberForm이라는 이름으로 멤버객체에 접근할 수 있다.
     * return "members/createMemberForm";에서 members/createMemberForm을 반환
     *
     **/
    @GetMapping("new")
    public String createForm(Model model) {
        model.addAttribute("memberForm", new Member());
        return "members/createMemberForm";
    }

    /*
     * @PostMapping 어노테이션은 post요청을 처리하고 요청이 들어오면 호출된다.
     * create라는 메서드는 파라미터로 멤버객체를 받고, 파라미터가 자동생성된다.
     * 그리고 멤버객체의 필드와 매핑.
     * 메서드내에서 memberService.save(member)를 호출해 MemberService로 멤버객체를 저장
     * 세이브메서드는 MemberService클래스에서 구현.
     * redirect:/을 반환, 멤버생성작업이 완료된 후에 사용자를 다른 페이지로 이동시킬 수 있다.
     **/
    @PostMapping("new")
    public String create(Member member) {
        memberService.save(member);
        return "redirect:/";
    }

    /*
     *@GetMapping("")은 Get요청을 처리하고 웹, 앱의 첫 화면을 보여줍니다.
     * 모델객체는 데이터를 전달하는 용도로 쓰이고, model.addAttribute에서 memberList라는 이름으로 멤버리스트 변수를 모델에 추가해줍니다.
     * "members/memberList"라는 문자열을 반환합니다. 이는 뷰의 이름을 나타내며, Spring MVC는 해당 이름의 뷰를 찾아 렌더링하여 클라이언트에게 응답으로 전송합니다. 따라서 이 코드는 "members/memberList"라는 뷰를 반환하여 회원 리스트를 표시하는 역할을 합니다.
     * private final 키워드는 해당 멤버 변수가 클래스 내부에서만 접근가능하고 변경되지 않는 상수 변수임을 나타내주고, final 키워드는 변수에 한 번 할당된 값이 변경되지 않음을 알려줍니다.
     **/
    @GetMapping("")
    public String findAll(Model model) {
        List<Member> memberList = memberService.findAll();
        model.addAttribute("memberList",memberList);
        return "members/memberList";
    }

        private final MemberServiceImpl memberService;
}
