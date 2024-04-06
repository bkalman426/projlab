public abstract class Item {
    protected Character owner;

    public String name; //Csak a Skeleton osztály miatt, getter/setterrel emiatt nem foglalkozunk.

    public Item(String n){
        this.name = n;
        System.out.println(name + " constructed");
    }

    protected void WriteIndents(int n){
        for(int i = 0; i < n; ++i){
            System.out.print('\t');
        }
    }

    /**
     * Ha lehet hasznalni a targyat, akkor kifejti a hatasat, kulonben siman visszater.
     */
    public void Use(){
        int indents = Thread.currentThread().getStackTrace().length - 4;
        WriteIndents(indents);
        System.out.println(name + ".Use()");

        WriteIndents(indents);
        System.out.println("Visszateres:" + name + ", void");
        return;
    }


    /**
     * A targynak a felveteli esemenye. Emellett beallitja a targynak a tulajdonosat arra a kerakterre, aki felvette.
     */
    public void PickedUp(Character c){
        int indents = Thread.currentThread().getStackTrace().length - 4;
        WriteIndents(indents);
        System.out.println(name + ".PickedUp(" + c.name + ")");

        this.SetOwner(c);

        WriteIndents(indents);
        System.out.println("Visszateres:" + name + ", void");
    }

    /**
     * Beallitja a targy tulajdonosat.
     * @param c A tulajdonos.
     */
    public void SetOwner(Character c){
        this.owner = c;
    }

    /**
     * Ha van a targynak koronkent vegrehajtando esemenye, ebben megtortenik.
     */
    public void Update(){
        return;
    }

    /**
     *A targy fogad egy transistort.
     * @param tr A transistor.
     */
    public void Accept(Transistor tr) { return; }


    /**
     * Eltavolitja a targy tulajdonosat (=null)
     */
    public void RemoveOwner(){
        int indents = Thread.currentThread().getStackTrace().length - 4;
        WriteIndents(indents);
        System.out.println(name + ".Use()");

        this.owner = null;

        WriteIndents(indents);
        System.out.println("Visszateres:" + name + "void");
    }

    /**
     * A targyat fogad egy visitort.
     * @param v A visitor.
     * @return A visitor visit() fuggvenyenek visszaterese.
     */
    public abstract boolean Accept(ItemVisitor v);
}
