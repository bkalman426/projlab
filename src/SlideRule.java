public class SlideRule extends Item{
    GameController gc;

    public SlideRule(String n, GameController g) {
        super(n);
        this.gc = g;
    }

    @Override
    public void PickedUp(Character c){
        int indents = Thread.currentThread().getStackTrace().length - 4;
        WriteIndents(indents);
        System.out.println(name + ".PickedUp(" + c.name + ")");

        this.owner = c;
        gc.StudentsWon();;

        WriteIndents(indents);
        System.out.println("Visszateres:" + name + ", void");
    }

    public boolean Accept(ItemVisitor v) {
        return v.visit(this);
    }
}
