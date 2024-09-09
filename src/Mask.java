public class Mask extends Item{
    private int durability = 3;
    private final boolean valid;

    public Mask(String n, boolean b) {
        super(n);
        this.valid = b;
    }

    /**
     * Csokkenti a maszk tartossagat (durability). Ha 0-ra csokken, akkor eldobatja magat.
     */
    public void ReduceDurability(){
        this.durability--;
        notifyObservers();
        System.out.println(name + " durability -= 1");
        if(this.durability == 0){
            this.owner.DeleteExpiredItem(this);
        }
    }
    public boolean Accept(ItemVisitor v) {
        if(valid)
            return v.visit(this);
        else
            return false;
    }

    @Override
    public String GetInfo() {
        return String.valueOf(this.durability);
    }
}
