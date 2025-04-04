package throunhugbunadar.hotelbokanir.vinnsla;

import java.util.List;
import java.util.ArrayList;

public class NoHotelsFoundMock implements HotelDatabase {

    public List<Hotel> findAvailableHotels(String location, String checkIn, String checkOut, boolean pool, boolean gym, boolean bar, String nameOfHotel) {
        return new ArrayList<>();
    }
}
