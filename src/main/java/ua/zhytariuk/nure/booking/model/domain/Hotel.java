package ua.zhytariuk.nure.booking.model.domain;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.Id;
import javax.persistence.NamedNativeQueries;
import javax.persistence.NamedNativeQuery;
import javax.persistence.OneToMany;
import javax.persistence.PrePersist;
import javax.persistence.QueryHint;
import javax.persistence.Table;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import lombok.ToString;

/**
 * @author oleksandr.zhytariuk (ozhytari)
 * @since 0.1
 */
@NamedNativeQueries(
        @NamedNativeQuery(
                name = Hotel.SELECT_HOTELS,
                query = "SELECT * FROM hotels",
                resultClass = Hotel.class,
                hints = @QueryHint(
                        name = org.hibernate.annotations.QueryHints.READ_ONLY,
                        value = "true")
        )
)
@ToString
@EqualsAndHashCode
@Entity
@AllArgsConstructor
@NoArgsConstructor
@Data
@Builder(toBuilder = true)
@Table(name = "hotels")
public class Hotel {

    public static final String SELECT_HOTELS = "Hotel.selectHotels";

    @Id
    @Column(name = "hotel_id")
    private String id;

    @Column(name = "name", nullable = false)
    private String name;

    @Column(name = "address", nullable = false)
    private String address;

    @Column(name = "telephone", nullable = false)
    private String telephone;

    @ToString.Exclude
    @EqualsAndHashCode.Exclude
    @OneToMany(mappedBy = "hotel",
            fetch = FetchType.LAZY,
            orphanRemoval = true,
            cascade = CascadeType.ALL)
    private List<Room> rooms = new ArrayList<>();

    @PrePersist
    private void prePersist() {
        this.id = UUID.randomUUID().toString();
    }
}
