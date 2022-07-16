package ua.zhytariuk.nure.booking.model.mapper;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import ua.zhytariuk.nure.booking.model.api.DiscountApi;
import ua.zhytariuk.nure.booking.model.domain.Discount;

@Mapper(config = MapperConfiguration.class)
public interface DiscountMapper {

    @Mapping(target = "rooms", ignore = true)
    Discount toDomain(final DiscountApi discountApi);

    DiscountApi toApi(final Discount discount);

}
