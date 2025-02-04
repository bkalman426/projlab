public class Camembert extends Item{
    public Camembert(String n) {
        super(n);
    }

    public boolean Accept(ItemVisitor v) {
        return v.visit(this);
    }

    @Override
    public String GetInfo() {
        return "";
    }

    @Override
    public void Use(){
        owner.getRoom().SetGas(true);
        owner.DeleteExpiredItem(this);
    }
}
