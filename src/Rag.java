public class Rag extends Item{
    private int duration = 3;

    public Rag(String n) {
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
    public void Update(){
        duration--;
        notifyObservers();
        if(duration == 0){
            owner.DeleteExpiredItem(this);
        }
    }
}
