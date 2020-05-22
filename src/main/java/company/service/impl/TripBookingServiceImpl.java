package company.service.impl;

import company.TimeUtils;
import company.model.TripBooking;
import company.service.FlyBookingService;
import company.service.HotelBookingService;
import company.service.TripBookingService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;

import static java.util.Objects.isNull;

@Slf4j
@Service
@RequiredArgsConstructor
public class TripBookingServiceImpl implements TripBookingService {

    private final FlyBookingService flyBookingService;
    private final HotelBookingService hotelBookingService;

    @Override
    @Transactional
    public TripBooking book(TripBooking booking) {

        var flyBooking = flyBookingService.book(booking.getFlight());
        log.debug("Fly booking precommit successful");

        Long timeToSleep = booking.getWait();

        if (! isNull(timeToSleep) && timeToSleep > 0) {
            TimeUtils.sleep(timeToSleep);
        }

        if (booking.getFail()) {
            throw new RuntimeException();
        }

        var hotelBooking = hotelBookingService.book(booking.getHotel());

        return new TripBooking(flyBooking, hotelBooking, null, null);
    }

    @Override
    public TripBooking find(Long bookingId) {
        return null;
    }

}
