package ua.zhytariuk.nure.booking.model.domain;

import java.math.BigDecimal;
import java.time.Instant;
import java.util.UUID;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.PrePersist;
import javax.persistence.Table;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import lombok.ToString;
import org.hibernate.annotations.NamedNativeQueries;
import org.hibernate.annotations.NamedNativeQuery;
import ua.zhytariuk.nure.booking.model.domain.authentication.User;
import ua.zhytariuk.nure.booking.model.domain.enums.ReservationStatus;

/**
 * TODO: Change class description
 *
 * @author oleksandr.zhytariuk (ozhytari)
 * @since 0.1
 */
@NamedNativeQueries({
        @NamedNativeQuery(
                name = Reservation.SELECT_RESERVED_ROOMS_IN_DATE_RANGE,
                query = "SELECT * FROM reservations " +
                        "INNER JOIN rooms ON fk_room_id = room_id " +
                        "WHERE fk_room_id = :roomId AND " +
                        "check_in_date <= :checkOut AND " +
                        "check_out_date >= :checkIn",
                resultClass = Reservation.class
        )
}
)
@Table(name = "reservations")
@Entity
@Data
@Builder(toBuilder = true)
@AllArgsConstructor
@NoArgsConstructor
@EqualsAndHashCode
@ToString
public class Reservation {

    public static final String SELECT_RESERVED_ROOMS_IN_DATE_RANGE = "Reservation.isRoomReservedInTimeInterval";

    @Id
    @Column(name = "reservation_id")
    private String reservationId;

    @Column(name = "additional_information")
    private String additionInformation;

    @Column(name = "check_in_date")
    private Instant checkInDate;

    @Column(name = "check_out_date")
    private Instant checkOutDate;

    @Column(name = "reservation_date")
    private Instant reservationDate;

    @Column(name = "total_price")
    private BigDecimal price;

    @Column(name = "status")
    @Enumerated(EnumType.STRING)
    private ReservationStatus status;

    @JoinColumn(name = "fk_username")
    @ManyToOne
    private User user;

    @ManyToOne
    @ToString.Exclude
    @EqualsAndHashCode.Exclude
    @JoinColumn(name = "fk_room_id")
    private Room room;

    @PrePersist
    private void prePersist() {
        this.reservationId = UUID.randomUUID().toString();
        this.reservationDate = Instant.now();
    }
}
