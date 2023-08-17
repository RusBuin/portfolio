package Distribucia;

import java.util.HashMap;
import java.util.Map;

public class Sklad {
    private Map<String, Integer> products;

    public Sklad() {
        products = new HashMap<>();
    }
// pouziva sa na pracu so skladom
    public void addProduct(String productName, int pocet) {
        if (products.containsKey(productName)) {
            int currentQuantity = products.get(productName);
            products.put(productName, currentQuantity + pocet);
        } else {
            products.put(productName, pocet);
        }
    }

    public void removeProduct(String productName, int pocet) throws Exception {
        if (!products.containsKey(productName)) {
            throw new Exception("Tovar sa nenašiel na sklade"); //Ak produkt nie je na sklade, metóda vyhodí výnimku s chybovou správou "Product not found on sklad".
        }
        int currentQuantity = products.get(productName);
        if (pocet > currentQuantity) {
            throw new Exception("\n" +
                    "Na sklade nie je dostatočné množstvo");
        }
        products.put(productName, currentQuantity - pocet);
    }

    public void printSklad() {
        System.out.println("Sklad contents:");
        for (String productName : products.keySet()) {
            int pocet = products.get(productName);
            System.out.println(productName + ": " + pocet);
        }
    }
}
