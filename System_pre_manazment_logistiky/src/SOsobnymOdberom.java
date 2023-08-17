import Dorucenie.Dorucenie;

import java.util.List;
    public class SOsobnymOdberom implements Objednavka{
        private int cislo_obj;
        private List<String> zlozenie;
        private Stav_Obj stav;
        protected Dorucenie dorucenie;


        public SOsobnymOdberom(int cislo_obj, List<String> zlozenie, Stav_Obj stav, Dorucenie dorucenie) throws IllegalArgumentException{
            if (cislo_obj <= 0) {
                throw new IllegalArgumentException("Cislo_obj musi byt kladne cislo.");
            }
            this.cislo_obj = cislo_obj;
            this.zlozenie = zlozenie;
            this.stav = stav;
            this.dorucenie = dorucenie;
        }
        @Override
        public int getCislo_obj() {
            return cislo_obj;
        }

        @Override
        public List<String> getZlozenie() {
            return zlozenie;
        }

        @Override
        public Stav_Obj getStav() {
            return stav;
        }

        @Override
        public Stav_Obj setStav(Stav_Obj novyStav){
            this.stav = novyStav;
            return novyStav;
        }
        @Override
        public String toString() {
            return "Objednavka{" +
                    ", cislo=" + cislo_obj +
                    ", zlozenie=" + zlozenie+
                    ", stav=" + stav +
                    '}';
        }
        @Override
        public void dorucaj() {
            if (stav==Stav_Obj.pripravena) {
                System.out.println("Objednavka uz dokoncena a caka v prevadzke");
                dorucenie.doconci_objednavku();
            }

            else{
                System.out.println("Objednavka nie je dokoncena. Stav: " + stav );
            }
        }
        //predefinovanie metodu equals
        @Override
        public boolean equals(Object obj) {
            if (this == obj) {
                return true;
            }

            if (obj == null || getClass() != obj.getClass()) {
                return false;
            }

            SOsobnymOdberom other = (SOsobnymOdberom) obj;
            return cislo_obj == other.cislo_obj;
        }
    }