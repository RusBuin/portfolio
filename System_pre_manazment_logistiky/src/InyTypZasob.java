public class InyTypZasob  extends Zasoba{
    String diskr;
    public InyTypZasob(String nazov) {
        super(nazov);
    }

    @Override
    public void upozornenie() {
        System.out.println("Description:" + diskr);
    }
}
