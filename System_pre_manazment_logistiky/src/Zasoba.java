//abstraktna trieda
public abstract class Zasoba {
    private String nazov;
    public Zasoba( String nazov){
        this.nazov = nazov;
    }
    public void getNazov(){
        System.out.println("Nazov daneho typy zasoby:" + nazov);
    }
    public abstract void upozornenie();
}
