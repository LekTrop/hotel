package ua.zhytariuk.nure.booking.common.utility;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.Instant;

import javax.swing.text.DateFormatter;

import lombok.SneakyThrows;
import org.springframework.stereotype.Component;

/**
 * TODO: Change class description
 *
 * @author oleksandr.zhytariuk (ozhytari)
 * @since 0.18.0
 */
@Component
public class TimeUtility {
    public static final String DATE_FORMAT = "yyyy-MM-dd";
    public static final SimpleDateFormat simpleDateFormat = new SimpleDateFormat(DATE_FORMAT);

    @SneakyThrows
    public Instant convertStringToTime(final String stringToConvert) {
        return simpleDateFormat.parse(stringToConvert).toInstant();
    }

}
