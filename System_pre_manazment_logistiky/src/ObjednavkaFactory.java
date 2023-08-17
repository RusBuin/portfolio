import Dorucenie.Kurier;
import Dorucenie.OsobnyOdber;

import java.util.List;
import java.util.ArrayList;

// vzor factory
public class ObjednavkaFactory {
    private List<Objednavka> objednavky = new ArrayList<>();
// V závislosti na hodnote argumentu "type" metóda vytvára rôzne typy objednávok.
    public Objednavka createObjednavka(String type, int cislo_obj, List<String> zlozenie, Stav_Obj stav) {
        Objednavka objednavka = null;

        if (type.equalsIgnoreCase("SDopravou")) { // equalsIgnoreCase ignoruje veľkosť písmen
            //vzor bridge
            objednavka = new SDopravou(cislo_obj, zlozenie, stav, new Kurier());

        } else if (type.equalsIgnoreCase("SOsobnymOdberom")) {
            objednavka = new SOsobnymOdberom(cislo_obj, zlozenie, stav, new OsobnyOdber());

        } else {
            throw new IllegalStateException("nepodporovaný systém");
        }
        objednavky.add(objednavka);
        return objednavka;
    }

    public List<Objednavka> getObjednavky() {
        return objednavky;
    }
}
