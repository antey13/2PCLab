package company.service;

public interface BookingService<T> {

    T book(T booking);

    T find(Long bookingId);

}
