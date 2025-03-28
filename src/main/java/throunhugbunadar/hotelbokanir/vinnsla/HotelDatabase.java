package throunhugbunadar.hotelbokanir.vinnsla;

import java.util.List;

public interface HotelDatabase {

    public List<Hotel> findAvailableHotels(String location, String checkIn, String checkOut, boolean pool, boolean gym, boolean bar, String nameOfHotel);

}