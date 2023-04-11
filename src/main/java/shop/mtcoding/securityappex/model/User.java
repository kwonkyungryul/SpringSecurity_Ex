package shop.mtcoding.securityappex.model;

import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.time.LocalDateTime;


@NoArgsConstructor // Hibernate가 ORM시에 new 하기 위해 필요함. Hibernate는 Default Constructor를 때린다.
@Getter
@Entity // Hibernate가 관리(영속, 비영속, 준영속)
@Table(name = "USER_TB")
public class User {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY) // AUTO_INCREMENT 전략
    private Long id;
    private String username;
    private String password;
    private String email;
    private String role; // USER, MANAGER, ADMIN
    private Boolean status; // true, false

    private LocalDateTime createdAt; // type이 LocalDateTime -> Timestamp로 바뀌어서 들어감
    private LocalDateTime updateAt;

    @PrePersist // insert 시에 동작
    public void onCreate() {
        this.createdAt = LocalDateTime.now();
    }

    @PreUpdate // update 시에 동작
    public void onUpdate() {
        this.updateAt = LocalDateTime.now();
    }

    @Builder
    public User(Long id, String username, String password, String email, String role, Boolean status, LocalDateTime createdAt, LocalDateTime updateAt) {
        this.id = id;
        this.username = username;
        this.password = password;
        this.email = email;
        this.role = role;
        this.status = status;
        this.createdAt = createdAt;
        this.updateAt = updateAt;
    }
}
