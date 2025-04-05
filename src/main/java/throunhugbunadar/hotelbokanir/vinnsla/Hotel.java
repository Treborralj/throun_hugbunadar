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


public Hotel(int id, String name, String location, int numberOfRooms, boolean pool, boolean gym, boolean bar){

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
        return name + " (" + location + ") - Herbergi: " + numberOfRooms;
    }
}
