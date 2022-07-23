package ua.zhytariuk.nure.booking.model.domain;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
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
 * @since 0.18.0
 */
@Builder
@ToString
@EqualsAndHashCode
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Getter
@Setter
@Table(name = "images")
public class Image {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "image_id")
    private String imageId;

    @Column(name = "src")
    private String src;

    @ManyToOne
    @JoinColumn(name = "fk_hotel_id")
    @ToString.Exclude
    @EqualsAndHashCode.Exclude
    private Hotel hotel;

    @ManyToOne
    @JoinColumn(name = "fk_room_id")
    @ToString.Exclude
    @EqualsAndHashCode.Exclude
    private Room room;
}
