package company.service.impl;

import company.model.TripBooking;
import company.model.fly.FlyBooking;
import company.model.hotel.HotelBooking;
import company.service.FlyBookingService;
import company.service.HotelBookingService;
import company.service.TripBookingService;
import company.service.tm.MyTM;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import javax.annotation.PostConstruct;
import java.time.LocalDate;

@Slf4j
@Service
@RequiredArgsConstructor
public class TripBookingServiceImpl implements TripBookingService {
    private final MyTM transactionManager;
    private final FlyBookingService flyBookingService;
    private final HotelBookingService hotelBookingService;

    @Override
    public TripBooking book(TripBooking booking) {
        transactionManager.twoPhaseCommitTransaction(booking);
        return booking;
    }


    @Override
    public TripBooking find(Long bookingId) {
        return null;
    }

    @PostConstruct
    public void makeTransaktion(){
        FlyBooking flight = new FlyBooking();
        flight.setDate(LocalDate.now());
        flight.setFrom("From");
        flight.setTo("To");
        flight.setFlyNumber("NuMBer");
        flight.setClientName("Person1");
        HotelBooking hotelBooking = new HotelBooking("HotelLab",LocalDate.now(),LocalDate.of(2020,10,1));
        hotelBooking.setClientName("Person1");
        transactionManager.twoPhaseCommitTransaction(new TripBooking(flight,hotelBooking,false,1000l));
    }

}
