package throunhugbunadar.hotelbokanir;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import throunhugbunadar.hotelbokanir.vinnsla.Hotel;
import throunhugbunadar.hotelbokanir.vinnsla.HotelVinnsla;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

/******************************************************************************
 * @author Róbert A. Jack
 * Tölvupóstur: ral9@hi.is
 * Lýsing : 
 *
 *****************************************************************************/
public class HotelTest {
    private SimulHotelDBMock hotels;
    private HotelController controller;

    @BeforeEach
    void setup(){
        controller = new HotelController;
    }
    @AfterEach
    void teardown(){
        controller = null;
        hotels = null;
    }
    @Test
    void testSearchHotelWithNoParameters(){
        List<Hotel >hotelList = new ArrayList<>(List.of(
                new Hotel(1, "Hotel a", "Reykjavík", 100, true, false, true),
                new Hotel(2, "Hotel b", "Akureyri", 75, false, true, true),
                new Hotel(3, "Hotel c", "Selfoss", 125, true, false, false),
                new Hotel(4, "Hotel d", "Vík", 50, true, true, true),
                new Hotel(5, "Hotel e", "Eigilsstaðir", 75, false, false, false),
                new Hotel(6, "Hotel f", "Borgarnes", 80, false, true, false)
        ));
        hotels = new SumulHotelDBMock(hotelList);
        List<Hotel> results = controller.searchHotel(hotels, "", "", "", false, false, false, "");
        assertTrue(results.isEmpty());
    }
    @Test
    void testSearchByBar() {
        List<Hotel >hotelList = new ArrayList<>(List.of(
                new Hotel(1, "Hotel a", "Reykjavík", 100, true, false, true),
                new Hotel(2, "Hotel b", "Akureyri", 75, false, true, true),
                new Hotel(3, "Hotel c", "Selfoss", 125, true, false, false),
                new Hotel(4, "Hotel d", "Vík", 50, true, true, true),
                new Hotel(5, "Hotel e", "Eigilsstaðir", 75, false, false, false),
                new Hotel(6, "Hotel f", "Borgarnes", 80, false, true, false)
        ));
        hotels = new SumulHotelDBMock(hotelList);
        List<Hotel> results = controller.searchHotel(hotels, "", "31-03-2025", "02-04-2025", false, false, true, "");
        for(Hotel r: results){
            assertTrue(r.hasBar());
        }
    }
    @Test
    void testSearchByName() {
        List<Hotel> results = HotelVinnsla.finnaLausHotel("", "", false, false, false, "Hotel Rey");
        assertTrue(results.stream().anyMatch(h -> h.getNafn().toLowerCase().contains("hotel rey")));
    }
    @Test
    void testSearchWithPartialName(){

    }
    @Test
    void testSearchWithWrongName(){

    }
    @Test
    void testSearchByLocation(){

    }
    @Test
    void testSearchByAllFilters(){

    }
    @Test
    void testSearchWithInvalidInput(){

    }

}
