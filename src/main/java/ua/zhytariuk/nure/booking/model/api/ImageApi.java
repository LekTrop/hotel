package ua.zhytariuk.nure.booking.model.api;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

/**
 * TODO: Change class description
 *
 * @author oleksandr.zhytariuk (ozhytari)
 * @since 0.18.0
 */
@Getter
@Setter
@Builder
public class ImageApi {
    private String imageId;
    private String src;
}
