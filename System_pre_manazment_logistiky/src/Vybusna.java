public class Vybusna extends Zasoba{
    double temp;
    public Vybusna(String nazov, double temp) {
        super(nazov); // volanie konštruktora nadradenej triedy
        this.temp = temp;
    }

    @Override
    public void upozornenie() {
        System.out.println("Tuto zasobu mozte udrzovat pri teplote nevyscie ako " + temp + " a je vybusna ");

    }
}
