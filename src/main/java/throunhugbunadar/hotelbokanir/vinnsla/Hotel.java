package throunhugbunadar.hotelbokanir.vinnsla;

/******************************************************************************
 * @author Róbert A. Jack
 * Tölvupóstur: ral9@hi.is
 * Lýsing :
 *****************************************************************************/
public class Hotel {
private String name;
private String location;
private int numberOfRooms;
private int id;
private boolean pool;
private boolean gym;
private boolean bar;


public Hotel(int id, String nafn, String stadsetning, int fjoldiHerbergja, boolean pool, boolean gym, boolean bar){

    this.id = id;
    this.name = name;
    this.location = location;
    this.numberOfRooms = numberOfRooms;
    this.pool = pool;
    this.gym = gym;
    this.bar = bar;
}

    public String getName() {
        return name;
    }

    public String getLocation() {
        return location;
    }

    public int getNumberOfRooms() {
        return numberOfRooms;
    }

    public int getId() {
        return id;
    }

    public boolean hasPool() {return pool;}
    public boolean hasGym() {return gym;}
    public boolean hasBar() {return bar;}

    public String toString(){
        String hotelString = nafn + " (" + stadsetning + ")" + " \n";
        if (pool) {
            hotelString = hotelString + "Pool";
            if (gym || bar) hotelString = hotelString + ", ";}
        if (gym) {
            hotelString = hotelString + "Gym";
            if (bar) hotelString = hotelString + ", ";
        }
        if (bar) hotelString = hotelString + "Bar";
        return hotelString;
    }
}
