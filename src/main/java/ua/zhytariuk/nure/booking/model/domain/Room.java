package ua.zhytariuk.nure.booking.model.domain;

import static ua.zhytariuk.nure.booking.model.domain.Room.SELECT_ROOMS_BY_FILTER;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.UUID;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.ManyToOne;
import javax.persistence.NamedNativeQueries;
import javax.persistence.NamedNativeQuery;
import javax.persistence.OneToMany;
import javax.persistence.PrePersist;
import javax.persistence.QueryHint;
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
@NamedNativeQueries(
        @NamedNativeQuery(
                name = SELECT_ROOMS_BY_FILTER,
                query = "SELECT * FROM rooms left join hotels ON hotel_id = fk_hotel_id " +
                        "full outer join reservations ON fk_room_id = room_id " +
                        "WHERE (hotel_id = :hotelId) " +
                        "AND ((:minPrice IS NULL OR price> :minPrice) AND (:maxPrice IS NULL OR price < :maxPrice)) " +
                        "AND (max_adult >= :adult AND max_child >= :child) " +
                        "AND check_in_date IS NULL OR NOT (check_in_date <= :checkIn AND check_out_date >= :checkIn) " +
                        "AND check_in_date IS NULL OR NOT (check_in_date >= :checkIn AND check_in_date <= :checkOut) ",
                resultClass = Room.class,
                hints = @QueryHint(
                        name = org.hibernate.annotations.QueryHints.READ_ONLY,
                        value = "true")
        )
)
@Entity
@AllArgsConstructor
@NoArgsConstructor
@ToString
@EqualsAndHashCode
@Getter
@Setter
@Builder(toBuilder = true)
@Table(name = "rooms")
public class Room {

    public static final String SELECT_ROOMS_BY_FILTER = "Room.selectRoomsByFilter";

    @Id
    @Column(name = "room_id")
    private String id;

    @Column(name = "type")
    private String type;

    @Column(name = "area")
    private Integer area;

    @Column(name = "number", nullable = false)
    private Integer number;

    @Column(name = "max_adult")
    private Integer maxAdult;

    @Column(name = "max_child")
    private Integer maxChild;

    @Column(name = "price", nullable = false)
    private BigDecimal price;

    @Column(name = "description", nullable = false)
    private String description;

    @ToString.Exclude
    @EqualsAndHashCode.Exclude
    @ManyToOne(cascade = CascadeType.PERSIST)
    @JoinColumn(name = "fk_hotel_id")
    private Hotel hotel;

    @ManyToMany
    @JoinTable(name = "room_discounts",
            joinColumns = @JoinColumn(name = "fk_room_id"),
            inverseJoinColumns = @JoinColumn(name = "fk_discount_id"))
    private Set<Discount> discounts = new HashSet<>();

    @OneToMany(mappedBy = "room")
    @ToString.Exclude
    @EqualsAndHashCode.Exclude
    private List<Reservation> reservations = new ArrayList<>();

    @OneToMany(orphanRemoval = true,
            cascade = CascadeType.ALL,
            mappedBy = "room")
    private List<Image> images = new ArrayList<>();

    @PrePersist
    private void prePersist() {
        this.id = UUID.randomUUID().toString();
    }
}
