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
        int indents = Thread.currentThread().getStackTrace().length - 4;
        WriteIndents(indents);
        System.out.println(name + ".setPair(" + t.name + ")");

        this.pair = t;

        WriteIndents(indents);
        System.out.println("Visszateres: " + name + ", void");
    }
    @Override
    public void Accept(Transistor tr){
        int indents = Thread.currentThread().getStackTrace().length - 4;
        WriteIndents(indents);
        System.out.println(name + ".Accept(" + tr.name + ")");
        if(this.pair == null && tr != this){
            tr.setPair(this);
            this.setPair(tr);
        }
        WriteIndents(indents);
        System.out.println("Visszateres: " + name + ", void");

    }

    @Override
    public boolean Accept(ItemVisitor v) {
        return v.visit(this);
    }

    /**
     * Visszaadja a tranzisztor celszobajat
     * @return A celszoba
     */
    public Room GetRoom(){
        int indents = Thread.currentThread().getStackTrace().length - 4;
        WriteIndents(indents);
        System.out.println(name + ".GetRoom()");

        WriteIndents(indents);
        System.out.println("Visszateres: " + name + ", Room: " + this.dest.name);
        return this.dest;
    }

    @Override
    public void Use(){
        int indents = Thread.currentThread().getStackTrace().length - 4;
        WriteIndents(indents);
        System.out.println(name + ".Use()");

        if(this.pair == null){
            for(Item i : owner.getItems())
                i.Accept(this);
        }
        else if(pair.dest == null){
            this.SetRoom(owner.getRoom());
            owner.DropItem(this);
        }
        else {
            Room r = owner.getRoom();
            pair.GetRoom().MoveAccept(owner);
            owner.DeleteExpiredItem(this);
            r.addItem(this);
            this.RemoveOwner();
        }

        WriteIndents(indents);
        System.out.println("Visszateres: " + name + ", void");
    }


    /**
     * Beallitja a tranzisztor celszobajat
     * @param r A celszoba
     */
    public void SetRoom(Room r){
        int indents = Thread.currentThread().getStackTrace().length - 4;
        WriteIndents(indents);
        System.out.println(name + ".SetRoom(" + r.name + ")");

        this.dest = r;

        WriteIndents(indents);
        System.out.println("Visszateres: " + name + ", void");
    }
}
