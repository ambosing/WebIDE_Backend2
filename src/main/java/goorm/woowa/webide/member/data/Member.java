package goorm.woowa.webide.member.data;

import jakarta.persistence.*;
import lombok.*;

import java.util.ArrayList;
import java.util.List;

@Entity
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
@ToString(exclude = "roleList")
public class Member {

    @Id
    @GeneratedValue
    @Column(name = "member_id")
    private Long id;
    private String email;
    private String pwd;
    private String nickname;
    @ElementCollection(fetch = FetchType.LAZY)
    @Builder.Default
    @Enumerated(EnumType.STRING)
    private List<MemberRole> roleList = new ArrayList<>();
    // 프로젝트 연관관계 매핑

    public void addRole(MemberRole role) {
        this.getRoleList().add(role);
    }

    public void clearRole() {
        this.getRoleList().clear();
    }

    public void changeNickname(String nickname) {
        this.nickname = nickname;
    }

    public void changePwd(String pwd) {
        this.pwd = pwd;
    }
}