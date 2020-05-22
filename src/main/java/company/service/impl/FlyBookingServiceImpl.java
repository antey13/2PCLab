package company.service.impl;

import company.model.fly.FlyBooking;
import company.repository.fly.FlyBookingRepository;
import company.service.FlyBookingService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

@Slf4j
@Service
@RequiredArgsConstructor
public class FlyBookingServiceImpl implements FlyBookingService {

    private final FlyBookingRepository repository;

    @Override
    public FlyBooking book(FlyBooking booking) {
        log.info("Starting booking fly: client [{}], fly [{}]", booking.getClientName(), booking.getFlyNumber());
        return repository.save(booking);
    }

    @Override
    public FlyBooking find(Long bookingId) {
        return repository.findById(bookingId)
                .orElseThrow();
    }

}
