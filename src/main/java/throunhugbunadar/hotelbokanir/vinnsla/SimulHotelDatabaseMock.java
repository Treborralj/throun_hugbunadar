package throunhugbunadar.hotelbokanir.vinnsla;

import java.util.ArrayList;
import java.util.List;

public class SimulHotelDatabaseMock implements HotelDatabase {
    private ArrayList<Hotel> hotels;

    SimulHotelDatabaseMock(ArrayList<Hotel> htls) {
        this.hotels = htls;
    }

    public List<Hotel> findAvailableHotels(String location, String checkIn, String checkOut, boolean pool, boolean gym, boolean bar, String nameOfHotel) {
        List<Hotel> found = new ArrayList<>();
        for (Hotel h : hotels) {
            if(!h.getStadsetning().isEmpty() && !h.getStadsetning().equals(location)) {continue;}
            if(!h.getNafn().isEmpty() && !h.getNafn().equals(nameOfHotel)) {continue;}
            if(pool && !h.hasPool()) {continue;}
            if(gym && !h.hasGym()) {continue;}
            if(bar && !h.hasBar()) {continue;}
            found.add(h);
        }
        return found;
    }
}
