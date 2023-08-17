package Distribucia;

import java.util.Scanner;
// pouziva sa štrukturálny vzor MVC
public class ConsoleView {
    private Scanner scanner;

    public ConsoleView() {
        scanner = new Scanner(System.in);
    }

    public void showMenu() {
        System.out.println("Akcii:");
        System.out.println("1. Pridat product");
        System.out.println("2. Vymazat product");
        System.out.println("3. Zobraziť obsah skladu");
        System.out.println("0. Exit");

    }
    // jednotlive prikazy, zodpoveda za info v konzole
public int getCisloObj(){
        System.out.println("Ukaz cislo objednavky:");
        return scanner.nextInt();
}
    public int getCommand() {
        System.out.print("Zadajte príkaz: ");
        return scanner.nextInt();
    }

    public String getProductName() {
        System.out.print("Zadajte názov produktu: ");
        return scanner.next();
    }

    public int getQuantity() {
        System.out.print("Zadajte množstvo:");
        return scanner.nextInt();
    }

    public void showMessage(String message) {
        System.out.println(message);
    }
}
