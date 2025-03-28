package throunhugbunadar.hotelbokanir.vinnsla;

/******************************************************************************
 * @author Róbert A. Jack
 * Tölvupóstur: ral9@hi.is
 * Lýsing : 
 *
 *****************************************************************************/
public class Hotel {
private String nafn;
private String stadsetning;
private int fjoldiHerbergja;
private int id;
private boolean pool;
private boolean gym;
private boolean bar;

public Hotel(int id, String nafn, String stadsetning, int fjoldiHerbergja, boolean pool, boolean gym, boolean bar){
    this.id = id;
    this.nafn = nafn;
    this.stadsetning = stadsetning;
    this.fjoldiHerbergja = fjoldiHerbergja;
    this.pool = pool;
    this.gym = gym;
    this.bar = bar;
}

    public String getNafn() {
        return nafn;
    }

    public String getStadsetning() {
        return stadsetning;
    }

    public int getFjoldiHerbergja() {
        return fjoldiHerbergja;
    }

    public int getId() {
        return id;
    }

    public boolean hasPool() {return pool;}
    public boolean hasGym() {return gym;}
    public boolean hasBar() {return bar;}


    public String toString(){
        return nafn + " (" + stadsetning + ") - Herbergi: " + fjoldiHerbergja;
    }
}
