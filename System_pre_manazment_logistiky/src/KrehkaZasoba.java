public class KrehkaZasoba extends  Zasoba{

    public KrehkaZasoba( String nazov) {
        super(nazov);
    }
    @Override
    public void upozornenie() {
        System.out.println("Musite sledovatb postupu");
    }
}
