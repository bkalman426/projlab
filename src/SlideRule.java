public class SlideRule extends Item{
    GameController gc;
    private final boolean valid;
    public SlideRule(String n, boolean b, GameController g) {
        super(n);
        this.gc = g;
        this.valid = b;
    }

    @Override
    public void PickedUp(Character c){
        this.owner = c;
        if(c.Accept(new CharacterTypeVisitor())){
            c.DropItem(this);
            return;
        }
        if(valid){
            gc.StudentsWon();
        }
    }

    public boolean Accept(ItemVisitor v) {
        return v.visit(this);
    }

    @Override
    public String GetInfo() {
        return "";
    }
}
