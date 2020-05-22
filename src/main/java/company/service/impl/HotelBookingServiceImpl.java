package company.service.impl;

import company.model.hotel.HotelBooking;
import company.repository.hotel.HotelBookingRepository;
import company.service.HotelBookingService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

@Slf4j
@Service
@RequiredArgsConstructor
public class HotelBookingServiceImpl implements HotelBookingService {

    private final HotelBookingRepository repository;

    @Override
    public HotelBooking book(HotelBooking booking) {
        log.info("Starting booking hotel: client [{}], fly [{}]", booking.getClientName(), booking.getHotelName());
        return repository.save(booking);
    }

    @Override
    public HotelBooking find(Long bookingId) {
        return repository.findById(bookingId)
                .orElseThrow();
    }

}
