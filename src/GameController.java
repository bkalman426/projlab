import java.util.ArrayList;
import java.util.Random;

public class GameController {
    public final String name = "gc";

    private void WriteIndents(int n){
        for(int i = 0; i < n; ++i){
            System.out.print('\t');
        }
    }
    private ArrayList<Room> rooms = new ArrayList<Room>();
    private ArrayList<Character> characters = new ArrayList<Character>();

    private final Random rnd = new Random(System.currentTimeMillis());


    /**
     * Osszeolvaszt ket szobat eggye, az egyinek beallitja a kapacitasat a ket szoba kapacitasanak maximumara, gazossagat es elatkozott allapotat igazra, ha legalabb az egyik
     * szobanak igaz au a tulajdonsaga, karaktereket atteszi, tagyakat es szomszedokat lemasolja, a masik szobat megsemmisiti.
     * @param r1 A megmarado szoba
     * @param r2 A megsemmisulo szoba
     */
    public void MergeRooms(Room r1, Room r2){
        int indents = Thread.currentThread().getStackTrace().length - 4;
        WriteIndents(indents);
        System.out.println(name + ".MergeRooms(" + r1.name + ", " + r2.name + ")");

        int capacity = Math.max(r1.GetCapacity(), r2.GetCapacity());
        boolean cursed = r1.isCursed() || r2.isCursed();
        boolean gassed = r1.isGassed() || r2.isGassed();

        r1.SetGas(gassed);
        r1.SetCurse(cursed);
        r1.SetCapacity(capacity);

        for(Room neighbour: r2.getNeighbours()){
            if(!r1.getNeighbours().contains(neighbour))
                r1.AddNeighbour(neighbour);
        }

        for(int i = 0; i < r2.getCharacters().size(); ++i){
            r1.Add(r2.getCharacters().get(i));
            r2.getCharacters().get(i).Leave();
        }

        for(Item i: r2.getItems()){
            r1.addItem(i);
        }

        rooms.remove(r2);

        WriteIndents(indents);
        System.out.println("Visszateres:" + name + ", void");
    }

    /**
     * Egy szobat ket szobara oszt. A ket uj szoba random, felosztja egymas kozott az eredeti kapacitasat, es karaktereit, a szobak kepessegei
     * pedig azonosak lesznek az eredeti szobaeval.
     * @param r1 A felosztando szoba.
     */
    public void SplitRoom(Room r1){
        int indents = Thread.currentThread().getStackTrace().length - 4;
        WriteIndents(indents);
        System.out.println(name + ".SplitRoom(" + r1.name + ")");



        int capacity1 = 1 + rnd.nextInt(r1.GetCapacity());
        int capacity2 = r1.GetCapacity() - capacity1;

        r1.SetCapacity(capacity1);
        Room r2 = new Room(r1.isGassed(),r1.isCursed(), capacity2, "r2");

        ArrayList<Room> neighbourPool = new ArrayList<Room>(r1.getNeighbours());

        for(int i = 0; i < neighbourPool.size(); ++i){
            r1.RemoveNeighbour(r1.getNeighbours().get(0));
        }

        for(Room r : neighbourPool){
            if(rnd.nextBoolean())
                r1.AddNeighbour(r);
            else
                r2.AddNeighbour(r);
        }

        r1.AddNeighbour(r2);
        r2.AddNeighbour(r1);

        ArrayList<Character> characterPool = new ArrayList<Character>(r1.getCharacters());

        for(int i = 0; i < characterPool.size(); ++i){
            r1.LeaveRoom(r1.getCharacters().get(0));
        }

        for(Character c : characterPool){
            if(rnd.nextBoolean()){
                r1.Add(c);
                c.SetRoom(r1);
            }
            else{
                r2.Add(c);
                c.SetRoom(r2);
            }
        }


        this.rooms.add(r2);

        WriteIndents(indents);
        System.out.println("Visszateres:" + name + ", void");
    }

    /**
     * Hozzaad egy szobat a jatekhoz
     * @param r A hozzaadott szoba
     */
    public void addRoom(Room r){
        this.rooms.add(r);
    }

    /**
     * Megnyeri a hallgatoknak a jatekot.
     */
    public void StudentsWon(){
        int indents = Thread.currentThread().getStackTrace().length - 4;
        WriteIndents(indents);
        System.out.println(name + ".StudentsWon()");

        WriteIndents(indents);
        System.out.println("Visszateres:" + name + ", void");

        return;
    }
}
