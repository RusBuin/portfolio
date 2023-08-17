package Dorucenie;

public class Kurier implements Dorucenie { //implementuje rozhranie Dorucenie

    @Override
    public void doconci_objednavku() {
        System.out.println("Kurier doniese objednavku");
    }
}