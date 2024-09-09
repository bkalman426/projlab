import java.io.Serializable;
import java.util.ArrayList;
import java.util.Scanner;

public abstract class Character implements Serializable {
    protected int actions = 5;
    public String name;
    protected final int capacity = 5;

    protected int knockoutTimer;
    protected ArrayList<Item> inventory = new ArrayList<Item>();

    private ArrayList<ModelObserver> observers = new ArrayList<>();

    protected Room room;

    public Character(String n){
        this.name = n;
        System.out.println(name + " created");
    }

    /**
     * A karakter megkiserel atmozogni a parameterkent kapott szobaba
     * @param r A szoba amibe atmozogni kivan
     */
    public void Move(Room r){
        if(!isStunned() && actions != 0){
            if(r != null && this.room.getNeighbours().contains(r)){
                    --actions;
                    r.MoveAccept(this);
            }
            else
                System.out.println(name + " move " + r.name + " fail");
        }
    }

    /**
     * Visszatolti a karakter akcioit es csokkenti a kabitottsagi idejet
     */
    public void Update(){
        actions = 5;
        if(knockoutTimer > 0)
            --knockoutTimer;
    }

    /**
     * Elhagyja a karakter szobajat
     */
    public void Leave(){
        this.room.LeaveRoom(this);
    }

    /**
     * Beallitja a karakter szobajat
     * @param r AZ uj szoba
     */
    public void SetRoom(Room r){
        this.room = r;
    }

    /**
     * A karakter megkiserel hasznalni egy targyat
     * @param i A hasznalando targy
     */
    public void UseItem(Item i){
        if(!isStunned() && actions != 0){
            --actions;
            System.out.println(name + " use " + i.name);
            i.Use();
            notifyObservers();
        }
    }

    /**
     * Beletesz a jatekos eszkoztaraba egy targyat
     * @param i A targy
     */
    public void Add(Item i){
        this.inventory.add(i);
        i.SetOwner(this);
        System.out.println(i.name + " added to " + name);
    }

    /**
     * A karakter megkiserli felvenni a parameterkent kapott targyat
     * @param i A felvenni megkiserelt targy
     */
    public void PickUpItem(Item i){
        if(!isStunned() && actions != 0){
            if(i != null &&  this.getRoom().getItems() != null && this.getRoom().getItems().contains(i) && this.inventory.size() < capacity){
                --actions;
                System.out.println(name + " take " + i.name);
                this.inventory.add(i);
                i.PickedUp(this);
                this.getRoom().Remove(i);
                notifyObservers();
            }
            else if(i != null){
                System.out.println(name + " take " + i.name + " fail");
            }
        }
    }

    /**
     * Eltavolit egy targyat a jatekos eszkoztarabol
     * @param i Az eltavolitando targy
     */
    public void DeleteExpiredItem(Item i){
        this.inventory.remove(i);
        notifyObservers();
        System.out.println(i.name + " deleted");
    }

    /**
     * Visszaadja a karakter targyait
      * @return A karakter eszkoztara
     */
    public ArrayList<Item> getItems(){
        return this.inventory;
    }

    /**
     * Visszaadja a karakter jelenlegi szobajat
     * @return A jelenlegi szoba
     */
    public Room getRoom(){
        return this.room;
    }

    /**
     * A karakter eldob egy targyat, eltavolitva azt az eszköztarabol es elhelyezi a jelenlegi szobajaban
     * @param i Az eldobando targy
     */
    public void DropItem(Item i){
        if(!isStunned()){
            System.out.println(name + " drop " + i.name);
            this.room.addItem(i);
            i.RemoveOwner();
            this.inventory.remove(i);
            notifyObservers();
        }
    }

    /**
     * Kifejti egy gazos szoba hatasat a karakteren. Ha nincs vedelme akkor elkabul es eldob minden targyat
     */
    public void GasRoomEffect(){
        if(!isStunned()){
            GasImmunityVisitor v = new GasImmunityVisitor();
            boolean found = false;
            for(Item i: this.inventory){
                if(i.Accept(v)){
                    found = true;
                    break;
                }
            }
            if(!found){
                System.out.println(name + " knocked out");
                ArrayList<Item> items = new ArrayList<>(inventory);
                for(Item i : items)
                    DropItem(i);
                this.knockoutTimer = 3;
            }
        }
    }

    /**
     * Kiirja egy karakter allapotat
     */
    public void WriteCharacter(){
        System.out.println("name " + name);
        System.out.println("inventory:");
        for (Item i : inventory)
            System.out.println(i.name);
        System.out.println("room" + this.room.name);
        System.out.println("stunned " + isStunned());
    }

    public boolean isStunned(){
        return this.knockoutTimer > 0;
    }

    public int getActions() { return this.actions; }

    /**
     * Rongy hatasa a karakteren
     */
    public abstract void RagEffect();

    /**
     * Ertesiti a karaktert egy masik karakterrel valo egy szobaba keruleserol
     * @param c A masik karakter
     */
    public abstract void Notify(Character c);

    public abstract boolean Accept(CharacterTypeVisitor v);

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

    public abstract void See(Cleaner c);

    private void notifyObservers() {
        for (ModelObserver observer : observers) {
            observer.onModelUpdate();
        }
    }

    public void addObserver(ModelObserver o){
        this.observers.add(o);
    }
}

