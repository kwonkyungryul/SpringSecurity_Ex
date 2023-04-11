package shop.mtcoding.securityappex.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import shop.mtcoding.securityappex.core.auth.MyUserDetails;
import shop.mtcoding.securityappex.dto.ResponseDTO;
import shop.mtcoding.securityappex.dto.UserRequest;
import shop.mtcoding.securityappex.dto.UserResponse;
import shop.mtcoding.securityappex.model.User;
import shop.mtcoding.securityappex.service.UserService;


@Controller
public class HelloController {

    private final UserService userService;

    public HelloController(UserService userService) {
        this.userService = userService;
    }

    @GetMapping("/users/{id}")
    public ResponseEntity<?> userCheck(@PathVariable Long id, @AuthenticationPrincipal MyUserDetails myUserDetails) {
        String username =  myUserDetails.getUser().getUsername();
        String role = myUserDetails.getUser().getRole();
        return ResponseEntity.ok().body(username + " : " + role);
    }

    @GetMapping("/")
    public ResponseEntity<?> hello() {
        return ResponseEntity.ok().body("ok");
    }

    @GetMapping("/joinForm")
    public String joinForm() {
        return "joinForm";
    }

    @GetMapping("/loginForm")
    public String loginForm() {
        return "loginForm";
    }

    @PostMapping("/join")
    public ResponseEntity<?> join(UserRequest.JoinDTO joinDTO) {
        UserResponse.JoinDTO data = userService.registration(joinDTO);
        ResponseDTO<?> responseDTO = new ResponseDTO<>().data(data);
        return ResponseEntity.ok().body(responseDTO);
    }
}
