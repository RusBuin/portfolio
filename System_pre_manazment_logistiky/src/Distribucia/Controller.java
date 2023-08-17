package Distribucia;
public class Controller{
    private Sklad sklad;
    private ConsoleView view;

    public Controller() {
        sklad = new Sklad();
        view = new ConsoleView();
    }
    // slúži na prácu s údajmi od používateľa
    public void start() {
        int command;
        do {
            view.showMenu();
            command = view.getCommand();

            switch (command) {
                case 1:
                    String productName = view.getProductName();
                    int quantity = view.getQuantity();
                    sklad.addProduct(productName, quantity);
                    view.showMessage("Položka pridaná do skladu");
                    break;

                case 2:
                    productName = view.getProductName();
                    quantity = view.getQuantity();
                    try {
                        sklad.removeProduct(productName, quantity);
                        view.showMessage("Produkt stiahnutý zo skladu");
                    } catch (Exception e) {
                        view.showMessage(e.getMessage());
                    }
                    break;
                case 3:
                    sklad.printSklad();
                    break;
                case 0:
                    view.showMessage("Ukončenie programu...");
                    break;

                default:
                    view.showMessage("Neplatný príkaz");
                    break;
            }
        } while (command != 0);
    }
}
