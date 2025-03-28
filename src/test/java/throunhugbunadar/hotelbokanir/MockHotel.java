package throunhugbunadar.hotelbokanir;

import throunhugbunadar.hotelbokanir.vinnsla.Hotel;

import java.util.ArrayList;
import java.util.List;

/******************************************************************************
 * @author Róbert A. Jack
 * Tölvupóstur: ral9@hi.is
 * Lýsing : 
 *
 *****************************************************************************/
public class MockHotel {
    //int id, String nafn, String stadsetning, int fjoldiHerbergja, boolean bar, boolean spa, boolean parking
    private List<Hotel> hotels;

    public MockHotel(){
        hotels = new ArrayList<>(List.of(
                new Hotel(1, "Hotel a", "Reykjavík", 100, true, false, true),
                new Hotel(2, "Hotel b", "Akureyri", 75, false, true, true),
                new Hotel(3, "Hotel c", "Selfoss", 125, true, false, false),
                new Hotel(4, "Hotel d", "Vík", 50, true, true, true),
                new Hotel(5, "Hotel e", "Eigilsstaðir", 75, false, false, false),
                new Hotel(6, "Hotel f", "Borgarnes", 80, false, true, false)
        ));
    }
    public List<Hotel> getHotels(){
        return hotels;
    }
}
