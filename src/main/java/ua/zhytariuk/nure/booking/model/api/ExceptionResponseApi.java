package ua.zhytariuk.nure.booking.model.api;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * TODO: Change class description
 *
 * @author oleksandr.zhytariuk (ozhytari)
 * @since 0.1
 */
@NoArgsConstructor
@AllArgsConstructor
@Data
@Builder
public class ExceptionResponseApi {

    private String message;
    private int code;
}
