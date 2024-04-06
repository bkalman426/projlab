import java.util.ArrayList;
import java.util.HashSet;
import java.util.Random;

public class Room {
    public String name; //Csak a Skeleton osztály miatt, getter/setterrel emiatt nem foglalkozunk.
    private ArrayList<Room> neighbours = new ArrayList<Room>();
    private ArrayList<Character> characters = new ArrayList<Character>();
    private ArrayList<Item> items = new ArrayList<Item>();
    private ArrayList<Room> cursedNeighbours = new ArrayList<Room>();
    private int curseTimer;
    private boolean gassed;
    private boolean cursed;
    private int capacity;

    private Random rnd = new Random(System.currentTimeMillis());
    //Csak fuggvenykiirashoz
    private void WriteIndents(int n){
        for(int i = 0; i < n; ++i){
            System.out.print('\t');
        }
    }

    public Room(boolean gas, boolean curse, int c, String n){
        this.gassed = gas;
        this.cursed = curse;
        this.capacity = c;
        this.name = n;
        System.out.println(name + " constructed");
    }

    /**
     * Ellenorzi, hogy egy adott karakter befer-e meg a szobaba. Amennyiben igen, vegrehajtja a mozgast.
     * Amennyiben a szoba gazos, kifejti hatasat a belepo karakteren
     * Megnezi, hogy van-e rongy a belepo karakternel, es ha van, kifejti hatasat a szobaban levo tobbi karakteren.
     * Ertesit minden mas a szobaban levo karaktert a belepeserol
     * @param c A belependo karakter.
     */
    public void MoveAccept(Character c){
        int indents = Thread.currentThread().getStackTrace().length - 4;
        WriteIndents(indents);
        System.out.println(name + ".MoveAccept(" + c.name + ")");

        if(characters.size() < capacity){
            characters.add(c);
            c.Leave();
            c.SetRoom(this);
            if(gassed)
                c.GasRoomEffect();
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
            for(Character ch: characters){
                if(ch != c)
                    ch.Notify(c);
            }
        }
        WriteIndents(indents);
        System.out.println("Visszateres:" + name + ", void");
    }

    /**
     * Betesz egy targyat a szobaba
     * @param i A beteendo targy
     */
    public void addItem(Item i){
        int indents = Thread.currentThread().getStackTrace().length - 4;
        WriteIndents(indents);
        System.out.println(name + ".addItem(" + i.name + ")");

        this.items.add(i);

        WriteIndents(indents);
        System.out.println("Visszateres:" + name + ", void");
    }



    /**
     * Betesz egy karakter a szobaba.
     * @param c A karakter.
     */
    public void Add(Character c){
        this.characters.add(c);
    }
    /**
     * Kiszed egy karaktert a szoba karakterlistajabol.
     * @param c A kiszedendo karakter.
     */
    public void LeaveRoom(Character c){
        int indents = Thread.currentThread().getStackTrace().length - 4;
        WriteIndents(indents);
        System.out.println(name + ".LeaveRoom(" + c.name + ")");

        this.characters.remove(c);

        WriteIndents(indents);
        System.out.println("Visszateres:" + name + ", void");
    }

    /**
     * Beallitja a szoba gazossagat
     * @param b A gazossag erteke
     */
    public void SetGas(Boolean b){
        int indents = Thread.currentThread().getStackTrace().length - 4;
        WriteIndents(indents);
        System.out.println(name + ".SetGas(" + b + ")");

        this.gassed = b;

        WriteIndents(indents);
        System.out.println("Visszateres:" + name + ", void");
    }

    /**
     * Beallitja a szoba elatkozott allapotat
     * @param b Az elatkozottsag allapota
     */
    public void SetCurse(Boolean b){
        int indents = Thread.currentThread().getStackTrace().length - 4;
        WriteIndents(indents);
        System.out.println(name + ".SetCurse(" + b + ")");

        this.cursed = b;

        WriteIndents(indents);
        System.out.println("Visszateres:" + name + ", void");
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
        int indents = Thread.currentThread().getStackTrace().length - 4;
        WriteIndents(indents);
        System.out.println(name + ".getNeighbours()");

        ArrayList<Room> availableNeighbours = new ArrayList<Room>();
        for(Room neighbour : neighbours){
            if(!cursedNeighbours.contains(neighbour))
                availableNeighbours.add(neighbour);
        }

        WriteIndents(indents);
        System.out.print("Visszateres:" + name + ", Room: ");
        for(Room r: availableNeighbours)
            System.out.print(r.name + " ");
        System.out.println();

        return availableNeighbours;

    }

    /**
     * Hozzaad egy szomszedot a szobahoz.
     * @param r Az uj szomszed.
     */
    public void AddNeighbour(Room r){
        this.neighbours.add(r);
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
        int indents = Thread.currentThread().getStackTrace().length - 4;
        WriteIndents(indents);
        System.out.println(name + ".GetCapacity()");

        WriteIndents(indents);
        System.out.println("Visszateres:" + name + ", int: " + this.capacity );

        return this.capacity;
    }

    /**
     * Visszaadja hogy gazos-e a szoba.
     * @return A szoba gazos allapota.
     */
    public boolean isGassed(){
        int indents = Thread.currentThread().getStackTrace().length - 4;
        WriteIndents(indents);
        System.out.println(name + ".isGassed()");

        WriteIndents(indents);
        System.out.println("Visszateres:" + name + ", bool: " + this.gassed );
        return this.gassed;
    }

    /**
     * Visszaadja hogy elatkozott-e a szoba.
     * @return A szoba elatkozott allapota.
     */
    public boolean isCursed(){
        int indents = Thread.currentThread().getStackTrace().length - 4;
        WriteIndents(indents);
        System.out.println(name + ".isCursed()");

        WriteIndents(indents);
        System.out.println("Visszateres:" + name + ", bool: " + this.cursed );

        return this.cursed;
    }

    /**
     *Gazos szoba eseten kifejti hatasat a szobaban tartozkodo karakterekre.
     *Elatkozott szoba eseten random eltuntet ajtokat.
     */
    public void Update(){
        int indents = Thread.currentThread().getStackTrace().length - 4;
        WriteIndents(indents);
        System.out.println(name + ".Update()");

        if(gassed){
            for(Character c : characters)
                c.GasRoomEffect();
        }
        if(cursed){
            if(true){                       //ez meg csak placeholder, curse szoba nem minden korben atkoz el ajtokat, itt meg lesz egy random feltetel
                for(Room r : neighbours){
                    if(rnd.nextBoolean())
                        cursedNeighbours.add(r);
                }
                curseTimer = 3;
            }
            if(curseTimer == 0)
                cursedNeighbours.clear();
            if(curseTimer > 0)
                --curseTimer;
        }

        WriteIndents(indents);
        System.out.println("Visszateres:" + name + ", void");
    }

    /**
     * Visszaadja a szobaban talalhato targyakat.
     * @return A szobaban talalhato targyak.
     */
    public ArrayList<Item> getItems() {
        int indents = Thread.currentThread().getStackTrace().length - 4;
        WriteIndents(indents);
        System.out.println(name + ".getItems()");

        WriteIndents(indents);
        System.out.print("Visszateres:" + name + ", Item: ");
        for(Item i: items)
            System.out.print(i.name + " ");
        System.out.println();

        return items;
    }

    /**
     * Visszaadja a szobaban tartozkodo karaktereket
     * @return A szobaban tartozkodo karakterek
     */
    public ArrayList<Character> getCharacters() {
        int indents = Thread.currentThread().getStackTrace().length - 4;
        WriteIndents(indents);
        System.out.println(name + ".getCharacters()");

        WriteIndents(indents);
        System.out.print("Visszateres:" + name + ", Character: ");
        for(Character c: characters)
            System.out.print(c.name + " ");
        System.out.println();

        return characters;
    }

    /**
     * Eltavolit egy targyat a szobabol.
     * @param i Az eltavolitando targy.
     */
    public void Remove(Item i){
        int indents = Thread.currentThread().getStackTrace().length - 4;
        WriteIndents(indents);
        System.out.println(name + ".Remove(" + i.name + ")");

        this.items.remove(i);

        WriteIndents(indents);
        System.out.println("Visszateres:" + name + ", void");
    }
}
