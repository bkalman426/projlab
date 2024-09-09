import java.io.FileDescriptor;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.Random;

public class Room implements Serializable {
    public String name;
    private ArrayList<Room> neighbours = new ArrayList<Room>();
    private ArrayList<Character> characters = new ArrayList<Character>();
    private ArrayList<Item> items = new ArrayList<Item>();
    private ArrayList<Room> cursedNeighbours = new ArrayList<Room>();
    private ArrayList<ModelObserver> observers = new ArrayList<>();
    private int curseTimer;
    private boolean gassed;
    private boolean cursed;
    private boolean sticky = false;
    private int charactersSinceCleaning = -1;
    private int capacity;
    private Random rnd = new Random(System.currentTimeMillis());


    public Room(boolean gas, boolean curse, int c, String n){
        this.gassed = gas;
        this.cursed = curse;
        this.capacity = c;
        this.name = n;
        System.out.println(name + " created");
    }

    /**
     * Ellenorzi, hogy egy adott karakter befer-e meg a szobaba. Amennyiben igen, vegrehajtja a mozgast.
     * Amennyiben a szoba gazos, kifejti hatasat a belepo karakteren
     * Megnezi, hogy van-e rongy a belepo karakternel, es ha van, kifejti hatasat a szobaban levo tobbi karakteren.
     * Ertesit minden mas a szobaban levo karaktert a belepeserol
     * @param c A belependo karakter.
     */
    public boolean MoveAccept(Character c){

        if(characters.size() < capacity){
            characters.add(c);
            c.Leave();
            c.SetRoom(this);
            notifyObservers();
            System.out.println(c.name + " move " + this.name);
            if(charactersSinceCleaning >= 0 && charactersSinceCleaning < 5){
                ++charactersSinceCleaning;
                if(charactersSinceCleaning == 5)
                    this.sticky = true;
            }
            if(gassed){
                System.out.println(name + " knockout " + c.name);
                c.GasRoomEffect();
            }
            RagVisitor v = new RagVisitor();
            boolean ragFound = false;
            for(Item i : c.getItems()){
                if(i.Accept(v)) {
                    ragFound = true;
                    break;
                }
            }
            if(ragFound){
                for(Character ch: characters)
                    if(ch != c)
                        ch.RagEffect();
            }
            ArrayList<Character> characterPool = new ArrayList<>(characters);
            for(Character ch: characterPool){
                if(ch != c)
                    ch.Notify(c);
            }
            return true;
        }
        System.out.println(c.name + " move " + this.name + " fail");
        return false;
    }

    /**
     * Betesz egy targyat a szobaba
     * @param i A beteendo targy
     */
    public void addItem(Item i){
        this.items.add(i);
        notifyObservers();
        System.out.println(i.name + " added to " + name);
    }

    /**
     * Kiirja a szoba teljes allapotat
     */
    public void WriteRoom(){
        System.out.println("name " + name);
        System.out.println("neighbours:");
        for(Room r : neighbours)
            System.out.println(r.name);
        System.out.println("characters:");
        for(Character c : characters)
            System.out.println(c.name);
        System.out.println("items:");
        for(Item i : items)
            System.out.println(i.name);
        System.out.println("capacity " + capacity);
        System.out.println("gassed " + gassed);
        System.out.println("cursed " + cursed);
        System.out.println("sticky " + sticky);
    }


    /**
     * Betesz egy karakter a szobaba.
     * @param c A karakter.
     */
    public void Add(Character c){
        this.characters.add(c);
        c.SetRoom(this);
        System.out.println(c.name + " added to " + name);
    }
    /**
     * Kiszed egy karaktert a szoba karakterlistajabol.
     * @param c A kiszedendo karakter.
     */
    public void LeaveRoom(Character c){
        this.characters.remove(c);
        notifyObservers();
    }

    /**
     * Beallitja a szoba gazossagat
     * @param b A gazossag erteke
     */
    public void SetGas(boolean b){
        this.gassed = b;
        notifyObservers();
        System.out.println(name + " gas: " + b);
    }

    /**
     * Beallitja a szoba elatkozott allapotat
     * @param b Az elatkozottsag allapota
     */
    public void SetCurse(boolean b){
        this.cursed = b;
        notifyObservers();
        System.out.println(name + " curse: " + b);
    }

    /**
     * Beallitja a szoba ragados allapotat
     * @param b A szoba ragados allapota
     */
    public void SetSticky(boolean b) {
        this.sticky = b;
        notifyObservers();
        System.out.println(name + " sticky: " + b);
    }

    /**
     * Visszaadja hogy ragados-e a szoba
     * @return A ragadossag allapota
     */
    public boolean isSticky(){
        return this.sticky;
    }

    /**
     * Atallitja a szobat kapacitasat
     * @param c Az uj kapacitas
     */
    public void SetCapacity(int c){
        this.capacity = c;
    }

    /**
     * Visszaadja a szobaval szomszedos szobakat. Elatkozott szobanal az elatkozott ajtokat nem adja vissza.
     * @return A szomszedos szobak.
     */
    public ArrayList<Room> getNeighbours() {
        ArrayList<Room> availableNeighbours = new ArrayList<Room>();
        for(Room neighbour : neighbours){
            if(!cursedNeighbours.contains(neighbour))
                availableNeighbours.add(neighbour);
        }
        return availableNeighbours;

    }

    /**
     * Takarito kitakaritja a szobat. Ezutan 5 karakter belepese utan a szoba ragacsossa valik
     */
    public void Clean(){
        this.SetGas(false);
        notifyObservers();
        if(charactersSinceCleaning == -1)
            charactersSinceCleaning = 0;
    }

    /**
     * Hozzaad egy szomszedot a szobahoz.
     * @param r Az uj szomszed.
     */
    public void AddNeighbour(Room r){
        this.neighbours.add(r);
        System.out.println(r.name + " neighbour added to " + name);
    }

    /**
     * Eltavolit egy szomszedot a szobatol
     * @param r Az eltavolitando szomszed
     */
    public void RemoveNeighbour(Room r){
        this.neighbours.remove(r);
    }

    /**
     * Visszaadja a szoba kapacitasat.
     * @return A kapacitas.
     */
    public int GetCapacity(){
        return this.capacity;
    }

    /**
     * Visszaadja hogy gazos-e a szoba.
     * @return A szoba gazos allapota.
     */
    public boolean isGassed(){
        return this.gassed;
    }

    /**
     * Visszaadja hogy elatkozott-e a szoba.
     * @return A szoba elatkozott allapota.
     */
    public boolean isCursed(){
        return this.cursed;
    }

    /**
     *Gazos szoba eseten kifejti hatasat a szobaban tartozkodo karakterekre.
     *Elatkozott szoba eseten random eltuntet ajtokat.
     */
    public void Update(){
        if(gassed){
            for(Character c : characters)
                c.GasRoomEffect();
        }
        if(cursed){
            if(curseTimer > 0){
                --curseTimer;
                if(curseTimer == 0)
                    cursedNeighbours.clear();
            }
            else if(rnd.nextInt(5) == 4 && curseTimer == 0){
                for(Room r : neighbours){
                    if(rnd.nextBoolean())
                        cursedNeighbours.add(r);
                }
                curseTimer = 3;
                System.out.println(name + " cursed");
            }
        }
    }

    /**
     * Visszaadja a szobaban talalhato targyakat.
     * @return A szobaban talalhato targyak.
     */
    public ArrayList<Item> getItems() {
        if(sticky)
            return new ArrayList<Item>();
        else
            return items;
    }

    /**
     * Visszaadja a szobaban tartozkodo karaktereket
     * @return A szobaban tartozkodo karakterek
     */
    public ArrayList<Character> getCharacters() {
        return characters;
    }

    /**
     * Eltavolit egy targyat a szobabol.
     * @param i Az eltavolitando targy.
     */
    public void Remove(Item i){
        this.items.remove(i);
        notifyObservers();
    }

    private void notifyObservers() {
        for (ModelObserver observer : observers) {
            observer.onModelUpdate();
        }
    }

    public void addObserver(ModelObserver o){
        this.observers.add(o);
    }
}
