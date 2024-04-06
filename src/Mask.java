public class Mask extends Item{
    private int durability = 3;

    public Mask(String n) {
        super(n);
    }

    /**
     * Csokkenti a maszk tartossagat (durability). Ha 0-ra csokken, akkor eldobatja magat.
     */
    public void ReduceDurability(){
        int indents = Thread.currentThread().getStackTrace().length - 4;
        WriteIndents(indents);
        System.out.println(name + ".ReduceDurability()");

        this.durability--;
        if(this.durability == 0){
            this.owner.DeleteExpiredItem(this);
        }

        WriteIndents(indents);
        System.out.println("Visszateres:" + name + ", void");
    }
    public boolean Accept(ItemVisitor v) {
        int indents = Thread.currentThread().getStackTrace().length - 4;
        WriteIndents(indents);
        System.out.println(name + ".Accept(v)");

        boolean b = v.visit(this);

        WriteIndents(indents);
        System.out.println("Visszateres:" + name + ", bool: " + b);

        return b;
    }
}
