package shop.mtcoding.securityappex.service;

import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import shop.mtcoding.securityappex.dto.UserRequest;
import shop.mtcoding.securityappex.dto.UserResponse;
import shop.mtcoding.securityappex.model.User;
import shop.mtcoding.securityappex.model.UserRepository;

@Service
public class UserService {

    private final BCryptPasswordEncoder bCryptPasswordEncoder;

    private final UserRepository userRepository;

    public UserService(BCryptPasswordEncoder bCryptPasswordEncoder, UserRepository userRepository) {
        this.bCryptPasswordEncoder = bCryptPasswordEncoder;
        this.userRepository = userRepository;
    }

    /**
     * 1. 트랜잭션 관리
     * 2. 영속성 객체 변경감지
     * 3. RequestDTO 받기
     * 4. 비즈니스 로직 처리하기
     * 5. ResponseDTO 응답하기
     */

    @Transactional // 횡단관심사
    public UserResponse.JoinDTO registration(UserRequest.JoinDTO joinDTO) {
        joinDTO.setPassword(bCryptPasswordEncoder.encode(joinDTO.getPassword()));

        User userPS = userRepository.save(joinDTO.toEntity());
        return new UserResponse.JoinDTO(userPS);
    }
}
