package ua.zhytariuk.nure.booking.common.utility;

import java.time.Instant;

import org.springframework.stereotype.Component;

/**
 * TODO: Change class description
 *
 * @author oleksandr.zhytariuk (ozhytari)
 * @since 0.18.0
 */
@Component
public class TimeUtility {

    public Instant convertStringToTime(final String stringToConvert) {
        return Instant.parse(stringToConvert);
    }

}
