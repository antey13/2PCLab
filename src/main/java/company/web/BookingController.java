package company.web;

import company.model.TripBooking;
import company.service.TripBookingService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/booking")
@RequiredArgsConstructor
public class BookingController {

    private final TripBookingService tripBookingService;

    @GetMapping("/{id}")
    public TripBooking getBook(@PathVariable Long id) {
        return tripBookingService.find(id);
    }

    @PostMapping
    public void twoPC(@RequestBody TripBooking booking){
        tripBookingService.book(booking);
    }



}
