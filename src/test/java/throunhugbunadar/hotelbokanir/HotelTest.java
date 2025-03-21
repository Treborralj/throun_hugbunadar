package throunhugbunadar.hotelbokanir;

import org.junit.jupiter.api.Test;
import throunhugbunadar.hotelbokanir.vinnsla.prufa;

import static org.junit.jupiter.api.Assertions.assertEquals;

/******************************************************************************
 * @author Róbert A. Jack
 * Tölvupóstur: ral9@hi.is
 * Lýsing : 
 *
 *****************************************************************************/
public class HotelTest {
    @Test
    void testSearchHotel(){
        prufa prufa = new prufa();
        assertEquals(2,prufa.plus(1,1));
    }
}
