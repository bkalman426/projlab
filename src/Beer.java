public class Beer extends Item{
    private int duration = 3;

    public Beer(String n) {
        super(n);
    }

    public boolean Accept(ItemVisitor v) {
        return v.visit(this);
    }

    @Override
    public void Update(){
        duration--;
        if(duration == 0){
            owner.DeleteExpiredItem(this);
        }
    }
}
