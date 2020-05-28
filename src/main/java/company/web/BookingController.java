package company.web;

import company.model.TripBooking;
import company.model.fly.FlyBooking;
import company.model.hotel.HotelBooking;
import company.service.TripBookingService;
import company.service.tm.MyTM;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import javax.annotation.PostConstruct;
import java.time.LocalDate;

@RestController
@RequestMapping("/booking")
@RequiredArgsConstructor
public class BookingController {
    private final MyTM transactionManager;
    private final TripBookingService tripBookingService;

    @GetMapping("/{id}")
    public TripBooking getBook(@PathVariable Long id) {
        return tripBookingService.find(id);
    }

    @PostMapping
    public void twoPC(@RequestBody TripBooking booking){
        transactionManager.twoPhaseCommitTransaction(booking);
    }

    @PostConstruct
    public void makeTransaktion(){
        FlyBooking flight = new FlyBooking();
        flight.setDate(LocalDate.now());
        flight.setFrom("From");
        flight.setTo("To");
        flight.setFlyNumber("NuMBer");
        flight.setClientName("MeClient");
        HotelBooking hotelBooking = new HotelBooking("HotelLab",LocalDate.now(),LocalDate.of(2020,10,1));
        hotelBooking.setClientName("MeClient");
        transactionManager.twoPhaseCommitTransaction(new TripBooking(flight,hotelBooking,false,1000l));
    }

}
