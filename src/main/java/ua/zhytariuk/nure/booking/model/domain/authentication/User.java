package ua.zhytariuk.nure.booking.model.domain.authentication;

import java.time.Instant;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Objects;
import java.util.Set;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.NamedNativeQueries;
import javax.persistence.NamedNativeQuery;
import javax.persistence.OneToMany;
import javax.persistence.PrePersist;
import javax.persistence.PreUpdate;
import javax.persistence.Table;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import lombok.ToString;
import ua.zhytariuk.nure.booking.model.domain.Reservation;

/**
 * TODO: Change class description
 *
 * @author oleksandr.zhytariuk (ozhytari)
 * @since 0.18.0
 */
@NamedNativeQueries(
        @NamedNativeQuery(
                name = User.SELECT_USER_BY_EMAIL,
                query = "SELECT * FROM users WHERE email = :email",
                resultClass = User.class
        )
)
@Table(name = "users")
@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode
@ToString
@Builder(toBuilder = true)
public class User {

    public static final String SELECT_USER_BY_EMAIL = "User.selectUserByEmail";

    @Id
    @Column(name = "username", nullable = false, unique = true)
    private String username;

    @Column(name = "password")
    private String password;

    @Column(name = "email", nullable = false, unique = true)
    private String email;

    @Column(name = "telephone", nullable = false, unique = true)
    private String telephone;

    @Column(name = "date_of_birth")
    private Instant birthDate;

    @Column(name = "creation_date")
    private Instant creationDate;

    @Column(name = "is_enabled")
    private Boolean isEnabled;

    @Column(name = "is_account_non_locked")
    private Boolean isAccountNonLocked;

    @Column(name = "is_account_non_expired")
    private Boolean isAccountNonExpired;

    @Column(name = "is_credentials_non_expired")
    private Boolean isCredentialsNonExpired;

    @ManyToMany(fetch = FetchType.EAGER, cascade = CascadeType.ALL)
    @JoinTable(name = "user_roles",
            joinColumns = @JoinColumn(name = "fk_username"),
            inverseJoinColumns = @JoinColumn(name = "fk_name"))
    private Set<Role> roles = new HashSet<>();

    @ToString.Exclude
    @EqualsAndHashCode.Exclude
    @OneToMany(mappedBy = "user", fetch = FetchType.LAZY)
    private List<Reservation> reservations = new ArrayList<>();

    @PrePersist
    private void prePersist() {
        this.creationDate = Instant.now();

        if (Objects.isNull(isAccountNonExpired)) {
            this.isAccountNonExpired = true;
        }

        if (Objects.isNull(isAccountNonLocked)) {
            this.isAccountNonLocked = true;
        }

        if (Objects.isNull(isEnabled)) {
            this.isEnabled = true;
        }

        if (Objects.isNull(isCredentialsNonExpired)) {
            this.isCredentialsNonExpired = true;
        }
    }

    @PreUpdate
    private void preUpdate(){
        if (Objects.isNull(isAccountNonExpired)) {
            this.isAccountNonExpired = true;
        }

        if (Objects.isNull(isAccountNonLocked)) {
            this.isAccountNonLocked = true;
        }

        if (Objects.isNull(isEnabled)) {
            this.isEnabled = true;
        }

        if (Objects.isNull(isCredentialsNonExpired)) {
            this.isCredentialsNonExpired = true;
        }
    }
}
