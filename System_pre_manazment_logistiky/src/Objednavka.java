import java.util.List;

public interface Objednavka {
    int getCislo_obj();
    List<String> getZlozenie();
     Stav_Obj getStav();
     Stav_Obj setStav(Stav_Obj novyStav);
     void dorucaj();
     String toString();

    @Override
    boolean equals(Object obj);

}
