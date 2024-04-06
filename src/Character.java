import java.util.ArrayList;
import java.util.Scanner;

public abstract class Character {
    protected int actions;
    public String name; //Csak a Skeleton osztály miatt, getter/setterrel emiatt nem foglalkozunk.

    Scanner sc = new Scanner(System.in); //Skeleton miatt
    protected final int capacity = 5;
    protected int knockoutTimer;
    protected ArrayList<Item> inventory = new ArrayList<Item>();
    protected Room room;

    //Csak fuggvenykiirashoz
    protected void WriteIndents(int n){
        for(int i = 0; i < n; ++i){
            System.out.print('\t');
        }
    }

    public Character(String n){
        this.name = n;
        System.out.println(name + " constructed");
    }

    /**
     * A karakter kifejezi a mozgasra valo szandekat. Ezutan atmehet egy szomszedos szobaba
     */
    public void Move(){
        int indents = Thread.currentThread().getStackTrace().length - 4;
        WriteIndents(indents);
        System.out.println(name + ".Move()");

        ArrayList<Room> choices = room.getNeighbours();
        int selection = 0;
        if(!choices.isEmpty())
            choices.get(selection).MoveAccept(this);

        WriteIndents(indents);
        System.out.println("Visszateres:" + name + ", void");
    }

    /**
     * Elhagyja a karakter szobajat
     */
    public void Leave(){
        int indents = Thread.currentThread().getStackTrace().length - 4;
        WriteIndents(indents);
        System.out.println(name + ".Leave()");

        this.room.LeaveRoom(this);

        WriteIndents(indents);
        System.out.println("Visszateres:" + name + ", void");
    }

    /**
     * Beallitja a karakter szobajat
     * @param r AZ uj szoba
     */
    public void SetRoom(Room r){
        int indents = Thread.currentThread().getStackTrace().length - 4;
        WriteIndents(indents);
        System.out.println(name + ".SetRoom(" + r.name + ")");

        this.room = r;

        WriteIndents(indents);
        System.out.println("Visszateres:" + name + ", void");
    }

    /**
     * A karakter megkiserel hasznalni egy targyat
     * @param i A hasznalando targy
     */
    public void UseItem(Item i){
        int indents = Thread.currentThread().getStackTrace().length - 4;
        WriteIndents(indents);
        System.out.println(name + ".UseItem(" + i.name + ")");

        i.Use();

        WriteIndents(indents);
        System.out.println("Visszateres:" + name + ", void");
    }

    /**
     * Beletesz a jatekos eszkoztaraba egy targyat
     * @param i A targy
     */
    public void Add(Item i){
        this.inventory.add(i);
    }

    /**
     * Jelzi a jatekosnak a szandekat a targyfelvetelre. Ezutan felvehet egy targyat
     */
    public void PickUpItem(){
        int indents = Thread.currentThread().getStackTrace().length - 4;
        WriteIndents(indents);
        System.out.println(name + ".PickUpItem()");

        ArrayList<Item> choices = this.room.getItems();
        int selection = 0;
        if(this.inventory.size() < this.capacity && !choices.isEmpty()){
            this.inventory.add(choices.get(selection));
            choices.get(selection).PickedUp(this);
            this.room.Remove(choices.get(selection));
        }

        WriteIndents(indents);
        System.out.println("Visszateres:" + name + ", void");
    }

    /**
     * Eltavolit egy targyat a jatekos eszkoztarabol
     * @param i Az eltavolitando targy
     */
    public void DeleteExpiredItem(Item i){
        int indents = Thread.currentThread().getStackTrace().length - 4;
        WriteIndents(indents);
        System.out.println(name + ".DeleteExpiredItem(" + i.name + ")");

        this.inventory.remove(i);

        WriteIndents(indents);
        System.out.println("Visszateres:" + name + ", void");
    }

    /**
     * Visszaadja a karakter targyait
      * @return A karakter eszkoztara
     */
    public ArrayList<Item> getItems(){
        int indents = Thread.currentThread().getStackTrace().length - 4;
        WriteIndents(indents);
        System.out.println(name + ".getItems()");

        WriteIndents(indents);
        System.out.print("Visszateres:" + name + ", Item: ");
        for(Item i: this.inventory)
            System.out.print(i.name + " ");
        System.out.println();

        return this.inventory;
    }

    /**
     * Visszaadja a karakter jelenlegi szobajat
     * @return A jelenlegi szoba
     */
    public Room getRoom(){
        int indents = Thread.currentThread().getStackTrace().length - 4;
        WriteIndents(indents);
        System.out.println(name + ".getRoom()");

        WriteIndents(indents);
        System.out.println("Visszateres:" + name + ", Room: " + this.room.name);

        return this.room;
    }

    /**
     * A karakter eldob egy targyat, eltavolitva azt az eszköztarabol es elhelyezi a jelenlegi szobajaban
     * @param i Az eldobando targy
     */
    public void DropItem(Item i){
        int indents = Thread.currentThread().getStackTrace().length - 4;
        WriteIndents(indents);
        System.out.println(name + ".DropItem(" + i.name + ")");

        this.room.addItem(i);
        i.RemoveOwner();
        this.inventory.remove(i);

        WriteIndents(indents);
        System.out.println("Visszateres:" + name + ", void");
    }

    /**
     * Kifejti egy gazos szoba hatasat a karakteren. Ha nincs vedelme akkor elkabul es eldob minden targyat
     */
    public void GasRoomEffect(){
        int indents = Thread.currentThread().getStackTrace().length - 4;
        WriteIndents(indents);
        System.out.println(name + ".GasRoomEffect()");


        GasImmunityVisitor v = new GasImmunityVisitor();
        boolean found = false;
        for(Item i: this.inventory){
            if(i.Accept(v)){
                found = true;
                break;
            }
        }
        if(!found){
            this.knockoutTimer = 3;
            for(Item i : this.inventory)
                DropItem(i);
        }

        WriteIndents(indents);
        System.out.println("Visszateres:" + name + ", void");
    }

    /**
     * Rongy hatasa a karakteren
     */
    public abstract void RagEffect();

    /**
     * Ertesiti a karaktert egy masik karakterrel valo egy szobaba keruleserol
     * @param c A masik karakter
     */
    public abstract void Notify(Character c);

    /**
     * A karakter reakcioja egy Studentel valo egy szobaba kerulesre
     * @param s A Student akivel egy szobaba kerult
     */
    public abstract void See(Student s);

    /**
     * A karakter reakcioja egy Lecturerrel valo egy szobaba kerulesre
     * @param l A Lecturer akivel egy szobaba kerult
     */
    public abstract void See(Lecturer l);

}

