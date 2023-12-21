package kopo.poly.repository.entity;

import lombok.*;
import org.hibernate.annotations.DynamicInsert;
import org.hibernate.annotations.DynamicUpdate;

import javax.persistence.*;

@Getter
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "USER_INFO")
@DynamicUpdate
@DynamicInsert
@Builder
@Cacheable
@Entity
public class UserInfoEntity {

    @Id
    @Column(name = "USER_ID")
    private String userId;

    @NonNull
    @Column(name = "USER_NAME", length = 500, nullable = false)
    private String userName;

    @NonNull
    @Column(name = "PASSWORD", length = 1, nullable = false)
    private String password;

    @NonNull
    @Column(name = "EMAIL", nullable = false)
    private String email;

    @NonNull
    @Column(name = "TEL", nullable = false)
    private String tel;

    @NonNull
    @Column(name = "ADDR1", nullable = false)
    private String addr1;

    @NonNull
    @Column(name = "ADDR2", nullable = false)
    private String addr2;

    @NonNull
    @Column(name = "reg_id", nullable = false)
    private String regId;

    @NonNull
    @Column(name = "reg_dt", nullable = false)
    private String regDt;

    @NonNull
    @Column(name = "chg_id", nullable = false)
    private String chgId;

    @NonNull
    @Column(name = "chg_dt", nullable = false)
    private String chgDt;

}
