package throunhugbunadar.hotelbokanir;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import throunhugbunadar.hotelbokanir.vinnsla.NoHotelsFoundMock;
import throunhugbunadar.hotelbokanir.vinnsla.prufa;


import static org.junit.jupiter.api.Assertions.*;

/******************************************************************************
 * @author Róbert A. Jack
 * Tölvupóstur: ral9@hi.is
 * Lýsing : 
 *
 *****************************************************************************/
public class HotelTest {
    private MockHotel hotels;

    @BeforeEach
    void setup(){
        hotels = new MockHotel();
    }
    @AfterEach
    void teardown(){
        hotels = null;

    }
    @Test
    void testSearchHotelWithNoParameters(){
        List<Hotel> results = HotelVinnsla.finnaLausHotel("", "", false, false, false, "");
        assertFalse(results.isEmpty());
    }
    @Test
    void testSearchByBarOnly() {
        List<Hotel> results = HotelVinnsla.finnaLausHotel("", "", true, false, false, "");
        for (Hotel hotel : results) {
            assertTrue(hotel.isBar());
        }
    }
    @Test
    void testSearchByNameOnly() {
        List<Hotel> results = HotelVinnsla.finnaLausHotel("", "", false, false, false, "Hotel Rey");
        assertTrue(results.stream().anyMatch(h -> h.getNafn().toLowerCase().contains("hotel rey")));
    }

}
