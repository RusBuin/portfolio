//vzor singleton
public class Vlastnik {
    private String meno_priezvisko;

        private static Vlastnik vlastnik = null;
        private Vlastnik() {
            meno_priezvisko = "Peter Pant";
        }
        public static Vlastnik getVlastnik() {
            if (vlastnik == null) {
                vlastnik = new Vlastnik();
            }
            return vlastnik;
        }
    public String getMeno_priezvisko() { // získanie hodnoty súkromnej premennej "meno_priezvisko"
        return meno_priezvisko;
    }

    public void setMeno_priezvisko(String meno_priezvisko) { //nastavenie hodnoty súkromnej premennej
        this.meno_priezvisko = meno_priezvisko;
    }
}
