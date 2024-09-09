import java.util.Random;

public class Beer extends Item{
    private int duration = 3;

    public Beer(String n) {
        super(n);
    }

    public boolean Accept(ItemVisitor v) {
        return v.visit(this);
    }

    @Override
    public String GetInfo() {
        return String.valueOf(this.duration);
    }

    @Override
    public void Use(){
        if(owner.getItems().size() == 1)   //ez az egyetlen targy, onmagat nem dobja el
            return;
        Random rnd = new Random(System.currentTimeMillis());
        int choice = rnd.nextInt(owner.getItems().size());
        while(owner.getItems().get(choice) == this)
            choice = rnd.nextInt(owner.getItems().size());
        owner.DropItem(owner.getItems().get(choice));
    }

    @Override
    public void Update(){
        duration--;
        notifyObservers();
        if(duration == 0){
            owner.DeleteExpiredItem(this);
        }
    }
}
