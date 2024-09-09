public class Transistor extends Item{
    private Transistor pair = null;
    private Room dest = null;

    public Transistor(String n) {
        super(n);
    }

    /**
     * Osszeparositja a transistort egy masikkal.
     * @param t A masik transistor.
     */
    public void setPair(Transistor t){
        this.pair = t;
    }
    @Override
    public void Accept(Transistor tr){
        if(this.pair == null && tr != this){
            tr.setPair(this);
            this.setPair(tr);
            System.out.println(tr.name + " connected to " + name);
        }
    }

    @Override
    public boolean Accept(ItemVisitor v) {
        return v.visit(this);
    }

    @Override
    public String GetInfo() {
        if(this.pair == null)
            return "";
        else if(this.pair.dest == null)
            return "paired";
        else
            return "usable";
    }

    /**
     * Visszaadja a tranzisztor celszobajat
     * @return A celszoba
     */
    public Room GetRoom(){
        return this.dest;
    }

    @Override
    public void Use(){
        if(this.pair == null){
            for(Item i : owner.getItems())
                i.Accept(this);
        }
        else if(pair.dest == null){
            this.SetRoom(owner.getRoom());
            System.out.println(name + " destination " + owner.getRoom().name);
            owner.DropItem(this);
        }
        else {
            Room r = owner.getRoom();
            pair.GetRoom().MoveAccept(owner);
            owner.DeleteExpiredItem(this);
            r.addItem(this);
            this.SetRoom(r);
            this.RemoveOwner();
        }
    }
    /**
     * Beallitja a tranzisztor celszobajat
     * @param r A celszoba
     */
    public void SetRoom(Room r){
        this.dest = r;
    }

    @Override
    public void PickedUp(Character c){
        this.SetOwner(c);
        this.SetRoom(null);
    }
}
