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

public Hotel(int id, String nafn, String stadsetning, int fjoldiHerbergja){
    this.id = id;
    this.nafn = nafn;
    this.stadsetning = stadsetning;
    this.fjoldiHerbergja = fjoldiHerbergja;
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
    public String toString(){
        return nafn + " (" + stadsetning + ") - Herbergi: " + fjoldiHerbergja;
    }
}
