public class Camembert extends Item{
    public Camembert(String n) {
        super(n);
    }

    public boolean Accept(ItemVisitor v) {
        return v.visit(this);
    }

    @Override
    public void Use(){
        int indents = Thread.currentThread().getStackTrace().length - 4;
        WriteIndents(indents);
        System.out.println(name + ".Use()");

        owner.getRoom().SetGas(true);
        owner.DeleteExpiredItem(this);

        WriteIndents(indents);
        System.out.println("Visszateres:" + name + ", void");
    }
}
