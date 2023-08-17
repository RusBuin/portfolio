
public class RychloPouzit  extends  Zasoba{
    private  int date;
    public RychloPouzit(String nazov,int date) {
        super(nazov);
        this.date = date;
    }


    @Override
    public void upozornenie() {
System.out.println("Moze byt na sklade ne dlhsie ak " + date);
    }
}
