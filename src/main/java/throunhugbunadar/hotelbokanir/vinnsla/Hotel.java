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
private int price;


public Hotel(int id, String name, String location, int numberOfRooms, boolean pool, boolean gym, boolean bar, int price){

    this.id = id;
    this.name = name;
    this.location = location;
    this.numberOfRooms = numberOfRooms;
    this.pool = pool;
    this.gym = gym;
    this.bar = bar;
    this.price = price;
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

    public int getPrice() {
    return price;
    }

    public boolean hasPool() {return pool;}
    public boolean hasGym() {return gym;}
    public boolean hasBar() {return bar;}

    public String toString(){
        String hotelString = name + " (" + location + ")" + "\nPrice per night " + price + "\n";
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
