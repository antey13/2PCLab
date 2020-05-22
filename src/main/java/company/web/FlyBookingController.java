package company.web;

import company.model.fly.FlyBooking;
import company.service.FlyBookingService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/booking/fly")
@RequiredArgsConstructor
public class FlyBookingController {

    private final FlyBookingService flyBookingService;

    @GetMapping("/{id}")
    public FlyBooking getBook(@PathVariable Long id) {
        return flyBookingService.find(id);
    }

}
