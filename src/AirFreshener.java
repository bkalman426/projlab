public class AirFreshener extends Item{

    public AirFreshener(String n) {
        super(n);
    }

    @Override
    public boolean Accept(ItemVisitor v) {
        return v.visit(this);
    }

    @Override
    public String GetInfo() {
        return "";
    }

    @Override
    public void Use(){
        this.owner.getRoom().SetGas(false);
        this.owner.DeleteExpiredItem(this);
    }
}
