package company.web;

import company.model.hotel.HotelBooking;
import company.service.HotelBookingService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/booking/hotel")
@RequiredArgsConstructor
public class HotelBookingController {

    private final HotelBookingService hotelBookingService;

    @GetMapping("/{id}")
    public HotelBooking getBook(@PathVariable Long id) {
        return hotelBookingService.find(id);
    }

}
