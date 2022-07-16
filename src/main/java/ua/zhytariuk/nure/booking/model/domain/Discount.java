package ua.zhytariuk.nure.booking.model.domain;

import java.math.BigDecimal;
import java.time.Instant;
import java.util.HashSet;
import java.util.Set;
import java.util.UUID;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.PrePersist;
import javax.persistence.Table;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

/**
 * TODO: Change class description
 *
 * @author oleksandr.zhytariuk (ozhytari)
 * @since 0.1
 */
@Table(name = "discounts")
@Entity
@NoArgsConstructor
@AllArgsConstructor
@Builder(toBuilder = true)
@EqualsAndHashCode
@ToString
@Getter
@Setter
public class Discount {

    @Id
    @Column(name = "discount_id")
    private String id;

    @Column(name = "description")
    private String description;

    @Column(name = "number")
    private Integer number;

    @Column(name = "percent_amount")
    private BigDecimal percentAmount;

    @Column(name = "start_date")
    private Instant startDate;

    @Column(name = "end_date")
    private Instant endDate;

    @EqualsAndHashCode.Exclude
    @ToString.Exclude
    @ManyToMany(fetch = FetchType.LAZY)
    @JoinTable(name = "room_discounts",
            joinColumns = @JoinColumn(name = "fk_discount_id"),
            inverseJoinColumns = @JoinColumn(name = "fk_room_id"))
    private Set<Room> rooms = new HashSet<>();

    @PrePersist
    private void prePersist() {
        this.id = UUID.randomUUID().toString();
    }
}
