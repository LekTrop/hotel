package ua.zhytariuk.nure.booking.model.mapper;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import ua.zhytariuk.nure.booking.model.api.ImageApi;
import ua.zhytariuk.nure.booking.model.domain.Image;

/**
 * TODO: Change class description
 *
 * @author oleksandr.zhytariuk (ozhytari)
 * @since 0.18.0
 */
@Mapper(
        config = MapperConfiguration.class
)
public interface ImageMapper {

    @Mapping(target = "hotel", ignore = true)
    @Mapping(target = "room", ignore = true)
    Image toDomain(final ImageApi imageApi);

    ImageApi toApi(final Image image);

}
