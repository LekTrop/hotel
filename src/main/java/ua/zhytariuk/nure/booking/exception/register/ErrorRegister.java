package ua.zhytariuk.nure.booking.exception.register;

public enum ErrorRegister implements BookingError {

    ENTITY_NOT_FOUND_EXCEPTION("Entity with id: %s was not found", 404),
    USER_WITH_USERNAME_ALREADY_EXIST("User with username: %s already exist", 2),
    USER_WITH_EMAIL_ALREADY_EXIST("User with email: %s already exist", 3),
    USER_CANNOT_BE_NULL("User cannot be null", 4),
    ROLE_CANNOT_BE_NULL("Role cannot be null", 5),
    ROLE_WITH_NAME_ALREADY_EXIST("Role with name: %s already exist", 6),
    ROLE_NAME_DOES_NOT_EQUAL("Updated role name: %s does not equal with rest call name: %s", 7),
    REQUIRED_FIELD_DOES_NOT_EXIST("Required fields: [%s] does not exist", 8),
    USER_CONTAIN_ASSIGNED_ROLE("Cannot assign role, user with username %s already have role with name: %s", 9),
    USER_DONT_CONTAIN_UN_ASSIGNED_ROLE("Cannot un assign role, user with username %s do not have role with name: %s", 10),
    USER_PASSWORD_IS_LESS_THEN_MINIMUM_LENGTH("Password length is less then %s", 11),
    USER_USERNAME_IS_LESS_THEN_MINIMUM_LENGTH("Username length is less then %s", 12),
    ROLE_WITH_NAME_DOES_NOT_EXIST("Roles with names: [%s] does not exist", 13),
    RESERVATION_CANNOT_BE_NULL("Reservation cannot be null", 14),
    ROOM_CANNOT_BE_NULL("Room cannot be null", 15),
    INVALID_RESERVATION_DATE("Invalid reservation date", 16),
    ROOM_IS_RESERVED_FOR_DATE_RANGE("Cannot create reservation, because room is reserved for date range %s - %s", 17),
    HOTEL_CANNOT_BE_NULL("Hotel cannot be null", 18),
    AGE_IS_LESS_THAT_MINIMUM("Age: [%d] is less then minimum: [%d]", 19);

    private String message;
    private int errorCode;

    ErrorRegister(final String message, final int errorCode) {
        this.message = message;
        this.errorCode = errorCode;
    }

    @Override
    public String getMessage() {
        return message;
    }

    @Override
    public int getErrorCode() {
        return errorCode;
    }

    @Override
    public String getFormattedMessage(final Object... args) {
        return String.format(getMessage(), args);
    }
}
