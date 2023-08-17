import Distribucia.Controller;
import java.util.ArrayList;
import java.util.InputMismatchException;
import java.util.List;
import java.util.Scanner;

public class Main {
    public static void main(String[] args) throws InterruptedException{ //vynimka pre Thread.sleep()
        KrehkaZasoba[] krehkaZasoba = new KrehkaZasoba[10];
        krehkaZasoba[0] = new KrehkaZasoba("Vazocka");

        krehkaZasoba[1] = new KrehkaZasoba("Okno");

        krehkaZasoba[2] = new KrehkaZasoba("Dzerkalo");

        RychloPouzit[] rychloPouzit = new RychloPouzit[10];
        rychloPouzit[0] = new RychloPouzit("Jedlo", 5);

        Vybusna[] vybusna = new Vybusna[10];
        vybusna[0] = new Vybusna("Lak na vlasy", 40.5);



        ObjednavkaFactory factory = new ObjednavkaFactory();
        List<Objednavka> objednavky = new ArrayList<>();// List uchováva zoznam objektov triedy Objednavka
        objednavky.add(factory.createObjednavka("SDopravou", 10, List.of("состав1", "состав22"), Stav_Obj.pripravena));
        objednavky.add(factory.createObjednavka("SOsobnymOdberom", 5, List.of("состав3"), Stav_Obj.pripravena));
        objednavky.add(factory.createObjednavka("SDopravou", 7, List.of("состав3"), Stav_Obj.pripravena));

        objednavky.add(factory.createObjednavka("SOsobnymOdberom", 1, List.of("состав3"), Stav_Obj.vytvorena));


// heslo a login, ktore bude sa pouzivat pri pristupe ku systemu
        String userLogin = "user";
        String userPassword = "userPassword";
        String adminLogin = "admin";
        String adminPassword = "adminPassword";
        Vlastnik vlastnik = Vlastnik.getVlastnik();
        vlastnik.setMeno_priezvisko("Michal Bondar");
        System.out.println("Meno a priezvisko vlastnika: " + vlastnik.getMeno_priezvisko());

        Scanner scanner = new Scanner(System.in);
        Scanner scanner2 = new Scanner(System.in); //nacitavanie vstupu pre heslo a login

        int attempts = 3; // mozne pokusy

        while (attempts > 0) {
            System.out.print("Login:");
            String userInputLogin = scanner2.nextLine();

            System.out.print("Heslo:");
            String userInputPassword = scanner2.nextLine();

// moznost pre user
            if (userInputLogin.equals(userLogin) && userInputPassword.equals(userPassword)) {
                int choice = 0;

                while (choice != 5) {
                    //bezacij string
                    String text = "Vitajte, pouzivatel! ";
                    for (int i = 0; i < text.length(); i++) {
                        System.out.print("\r" + text);

                        // vytvorí sa nový reťazec, ktorý obsahuje všetky znaky
                        // pôvodného reťazca text okrem prvého znaku, ktorý sa
                        // nahradí za posledný znak pôvodného reťazca text.
                        text = text.substring(1) + text.charAt(0);
                        Thread.sleep(100);//aby pozostavit program
                    }

                    //Menu pre pouzivatela
                    System.out.println("\nMenu");
                    System.out.println("1.Kontrola zlozenia");
                    System.out.println("2.Kontrola stavu");
                    System.out.println("3.Dorucenie");
                    System.out.println("4.Stav zasob");
                    System.out.println("5.Exit");
                    System.out.print("Vyberte akciu:");

                    choice = scanner.nextInt();//hodnota vyberu usera
                    switch (choice) {
                        case 1:
                            //blok pre kontrolu zlozenia
                            System.out.println("Ukaz cislo objednavku:");
                            int cislo = scanner.nextInt();
                            boolean found = false; // pomocna premenna

                            for (Objednavka objednavka : objednavky) {
                                if (Integer.valueOf(objednavka.getCislo_obj()).equals(cislo)) { // porovnava hodnotu pre cislo objednavky, ktorá sa nachádza v objekte typu Objednavka
                                    System.out.println("Zlozenie:" + objednavka.getZlozenie());
                                    found = true; //ak akcia bola splnena tak nastavime hodnotu
                                    break;
                                }
                            }
                            //ak akcia nebola vykonana tak vyvolame upozornenie
                            if (!found) {
                                System.out.println("Objednavka z cislom " + cislo + " nebola najdena.");
                            }
                            break; // aby po spleniu sa vratilo ku menu

                        // cast pre kontrolu stavu
                        case 2:
                            System.out.println("Ukaz cislo objednavky:");
                            cislo = scanner.nextInt();
                            found = false;
                            for (Objednavka objednavka : objednavky) {
                                if (Integer.valueOf(objednavka.getCislo_obj()).equals(cislo)) {
                                    System.out.println("Stav:" + objednavka.getStav());
                                    found = true;
                                    break;
                                }
                            }
                            if (!found) {
                                System.out.println("Objednavka z cislom" + cislo + " nebola najdena.");
                            }
                            break;
                            //cast pre ukazanie sposobu dorucenia
                        case 3:
                            System.out.println("Ukaz cislo objednavky:");
                            cislo = scanner.nextInt();
                            found = false;
                            for (Objednavka objednavka : objednavky) {
                                if (Integer.valueOf(objednavka.getCislo_obj()).equals(cislo)) {
                                    objednavka.dorucaj();
                                    found = true;
                                    break;
                                }
                            }
                            if (!found) {
                                System.out.println("Objednavka z cislom" + cislo + " nebola najdena.");
                            }
                            break;
                            //cast pre stav zasob
                        case 4:
                            for (int i = 0; i < krehkaZasoba.length; i++) {
                                if (krehkaZasoba[i] != null) {
                                    System.out.println("Krehka zasoba " + (i+1) + ": ");
                                    krehkaZasoba[i].getNazov();
                                    krehkaZasoba[i].upozornenie();
                                }
                            }

                            for (int i = 0; i < rychloPouzit.length; i++) {
                                if (rychloPouzit[i] != null) {
                                    System.out.println("Rychlo pouzit " + (i+1) + ": ");
                                    rychloPouzit[i].getNazov();
                                    rychloPouzit[i].upozornenie();
                                }
                            }

                            for (int i = 0; i < vybusna.length; i++) {
                                if (vybusna[i] != null) {
                                    System.out.println("Vybusna " + (i+1) + ": ");
                                    vybusna[i].getNazov();
                                    vybusna[i].upozornenie();
                                }
                            }

                            break;
                        default:
                            System.out.println("Nespravny vyber cisla!");
                            break;
                    }

                }
                System.out.println("Sluzba ukoncena");


                break;
            } else if (userInputLogin.equals(adminLogin) && userInputPassword.equals(adminPassword)) { //cast pre administrator
                int choice2 = 0;
                while (choice2 !=4 ) {
                    String text = "Vitajte,  administrator! ";
                    int textLength = text.length();
                    for (int i = 0; i < textLength; i++) {
                        System.out.print("\r" + text);
                        text = text.substring(1) + text.charAt(0);
                        Thread.sleep(100);
                    }

                    System.out.println("\n Menu");
                    System.out.println("1.Distribucia");
                    System.out.println("2.Zmena stavu objednavku");
                    System.out.println("3.Editor objednávok");
                    System.out.println("4.Exit");
                    System.out.print("Vyberte akciu:");
                    choice2 = scanner.nextInt();
                    switch (choice2) {
                        //cast na planovanie distribucie
                        case 1:
                            System.out.println("Prostredie na planovanie distribucie");
                            Controller controller = new Controller();
                            controller.start();
                            break;

                        case 2:
                            Scanner scanner3 = new Scanner(System.in);
                            System.out.print("Zadajte cislo objednavky:");
                            int cislo = scanner3.nextInt();
                            boolean found = false;

                            for (Objednavka objednavka : objednavky) {
                                if (Integer.valueOf(objednavka.getCislo_obj()).equals(cislo)) { // hlada objednavku ktora zodpoveda danemu cislu
                                    System.out.println(objednavka.getStav());
                                    do {
                                        System.out.print("Zadajte novy stav objednavky (vytvorena, spracovana, pripravena): ");

                                        try {
                                            String novyStavStr = scanner.next();

                                            Stav_Obj novyStav = Stav_Obj.valueOf(novyStavStr);

                                            objednavka.setStav(novyStav);
                                            System.out.println("Stav objednavky s cislom " + cislo + " bol zmeneny na " + novyStav + ".");

                                            found = true;
                                            break;
                                        } catch (IllegalArgumentException e) {
                                            System.out.println("Neplatný stav objednávky. Platné hodnoty: vytvorena, spracovana, pripravena.");

                                        } catch (Exception e) {
                                            System.out.println("Chyba " + e.getMessage());

                                        }
                                    }while (!found);
                                    break;
                                }
                            }
                            if (!found) {
                                System.out.println("Objednavka s cislom " + cislo + " nebola najdena.");
                                return;
                            }

                            break;
                            //cast na editaciu objednavok
                        case 3:
                            Scanner scanner4 = new Scanner(System.in);
                            boolean run = true;
                            System.out.println("objednávky");
                            for (Objednavka objednavka : objednavky) {
                                System.out.println(objednavka);
                            }
                            while (run) {
                                System.out.println("1 - pridat objednavku");
                                System.out.println("2 - vymazat obkednavku");
                                System.out.println("3 - exit");
                                try {
                                    int choice = scanner4.nextInt();
                                    switch (choice) {
                                        case 1:
                                            System.out.println("Zadajte typ objednávky (SDopravou или SOsobnymOdberom):");
                                            String typ = scanner.next();
                                            System.out.println("Zadajte cislo:");
                                            int cislo1 = scanner.nextInt();
                                            System.out.println("Zadajte zlozenie objednavku:");
                                            String[] zloz = scanner.next().split(",");
                                            List<String> zoznam = new ArrayList<>();
                                            for (String z: zloz) {
                                                zoznam.add(z);
                                            }
                                            System.out.println("Zadajte stav objednavku (vytvorena, spracovana, pripravena):");
                                            String stav = scanner.next();
                                            Stav_Obj stavObj = Stav_Obj.valueOf(stav);
                                            objednavky.add(factory.createObjednavka(typ, cislo1, zoznam, stavObj));
                                            System.out.println("Objednávka bola úspešne pridaná.");
                                            System.out.println("objednávky");
                                            for (Objednavka objednavka : objednavky) {
                                                System.out.println(objednavka);
                                            }
                                            break;
                                        case 2:
                                            System.out.println("Zadajte číslo objednávky, ktorú chcete vymazať:");
                                            int index = scanner.nextInt();
                                            if (index < 0 ) {
                                                System.out.println("Neplatné číslo objednávky.");
                                                break;
                                            }
                                            for (Objednavka objednavka : objednavky) {
                                                if (Integer.valueOf(objednavka.getCislo_obj()).equals(index)) {
                                                    objednavky.remove(objednavka);
                                                    break;
                                                }
                                            }

                                            System.out.println("Objednávka bola úspešne vymazaná.");

                                            for (Objednavka objednavka : objednavky) {
                                                System.out.println(objednavka);
                                            }
                                            break;
                                        case 3:
                                            run = false;
                                            break;
                                        default:
                                            System.out.println("Zlý výber. Prosím skúste znova.");
                                    }
                                } catch (InputMismatchException e ) { //vynimka na zachytenie chyby o cislo objednavky
                                    System.out.println("Neplatný vstupný formát. Zadajte celé číslo.");
                                    scanner.next();
                                } catch (IllegalArgumentException e) { //vynimka na zachytenie chyby o neplatnom stave
                                    System.out.println("Neplatný stav objednávky. Platné hodnoty: vytvorena, spracovana, pripravena.");
                                    scanner.next();
                                } catch (Exception e) { //zachytáva všetky ostatné výnimky, ktoré neboli spracované
                                    System.out.println("Chyba " + e.getMessage());
                                    scanner.next();
                                }
                            }
                            break;
                        case 4:
                            break;

                    }

                }
                System.out.println("Sluzba ukoncena");
                break;
            } else {
                attempts--;
                System.out.println("Nesprávne prihlasovacie meno alebo heslo, skúste to znova");
                System.out.println("Zostavaju vam " + attempts + " pokusy.");
            }
        }

        if (attempts == 0) {
            System.out.println("Vycerpali ste vsetky pokusy o zadanie hesla.");
        }

    }

}