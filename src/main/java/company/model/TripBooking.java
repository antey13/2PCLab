package company.model;

import company.model.fly.FlyBooking;
import company.model.hotel.HotelBooking;
import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class TripBooking {
    private FlyBooking flight;
    private HotelBooking hotel;
    private Boolean fail;
    private Long wait;
}
