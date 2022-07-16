package ua.zhytariuk.nure.booking.service.facade;

import org.springframework.security.core.Authentication;

public interface UserAuthentication {

    Authentication getAuthentication();
}
