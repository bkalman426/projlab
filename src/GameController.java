import java.io.Serializable;
import java.util.ArrayList;
import java.util.Random;

public class GameController implements Serializable {
    private ArrayList<Room> rooms = new ArrayList<Room>();
    private ArrayList<Character> characters = new ArrayList<Character>();
    private ArrayList<Item> items = new ArrayList<Item>();
    private Character currentCharacter;


    private GameFrame game;

    private boolean randomOn = true;

    private boolean useMode = true;

    private Random rnd = new Random(System.currentTimeMillis());

    private int rounds = 10;

    public GameController(){
        new StartFrame(this);
    }


    /**
     * Osszeolvaszt ket szobat eggye, az egyinek beallitja a kapacitasat a ket szoba kapacitasanak maximumara, gazossagat es elatkozott allapotat igazra, ha legalabb az egyik
     * szobanak igaz au a tulajdonsaga, karaktereket atteszi, tagyakat es szomszedokat lemasolja, a masik szobat megsemmisiti.
     * @param r1 A megmarado szoba
     * @param r2 A megsemmisulo szoba
     */
    public void MergeRooms(Room r1, Room r2){
        int capacity = Math.max(r1.GetCapacity(), r2.GetCapacity());
        boolean cursed = r1.isCursed() || r2.isCursed();
        boolean gassed = r1.isGassed() || r2.isGassed();
        boolean sticky = r1.isSticky() || r2.isSticky();

        r1.SetGas(gassed);
        r1.SetCurse(cursed);
        r1.SetCapacity(capacity);
        r1.SetSticky(sticky);

        for(Room neighbour: r2.getNeighbours()){
            if(!r1.getNeighbours().contains(neighbour) && neighbour != r1)
                r1.AddNeighbour(neighbour);
        }

        for(int i = 0; i < r2.getCharacters().size(); ++i){
            r1.Add(r2.getCharacters().get(i));
            r2.getCharacters().get(i).Leave();
        }

        for(Item i: r2.getItems()){
            r1.addItem(i);
        }

        System.out.println(r1.name + " merged " + r2.name);

        for(Room room : rooms){
            if(room.getNeighbours().contains(r2))
                room.RemoveNeighbour(r2);
        }

        rooms.remove(r2);
        game.Draw();
    }

    /**
     * Visszaad egy szobat a neve alapjan
     * @param s A szoba neve
     * @return A keresett szoba
     */
    public Room GetRoomByName(String s){
        for (Room r : rooms)
            if(r.name.equals(s))
                return r;
        return null;
    }

    /**
     * Visszaad egy karaktert a neve alapjan
     * @param s A karakter neve
     * @return A keresett karakter
     */
    public Character GetCharacterByName(String s){
        for (Character c : characters)
            if(c.name.equals(s))
                return c;
        return null;
    }

    /**
     * Visszaad egy targyat a neve alapjan
     * @param s A targy neve
     * @return A keresett targy
     */
    public Item GetItemByName(String s){
        for (Item i : items)
            if(i.name.equals(s))
                return i;
        return null;
    }

    /**
     * Elolrol kezdi a teljes jatekot es a palyafelepitest
     */
    public void Reset(){
        items.clear();
        characters.clear();
        rooms.clear();
    }

    /**
     * Elkezdi a jatekmenetet
     */
    public void Start(){
        if(!characters.isEmpty()){
            currentCharacter = characters.get(0);
            game = new GameFrame(this);
            game.Draw();
            System.out.println("next: " + currentCharacter.name);
            if(currentCharacter.Accept(new CharacterTypeVisitor()) && randomOn)
                RandomActions();
        }
    }

    public void RandomOff(){
        randomOn = false;
    }

    public void RandomOn() { randomOn = true; }
    /**
     * Egy szobat ket szobara oszt. A ket uj szoba megtartja az eredeti kapacitasat, random felosztja egymas kozott a karaktereiket es targyaikat, a szobak kepessegei
     * pedig azonosak lesznek az eredeti szobaeval.
     * @param r1 A felosztando szoba.
     */
    public void SplitRoom(Room r1){
        int id = rnd.nextInt(1000);
        Room r2 = new Room(r1.isGassed(),r1.isCursed(), r1.GetCapacity(), "r" + (rooms.size() + id));
        r2.SetSticky(r1.isSticky());

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

        ArrayList<Item> itempool = new ArrayList<>(r1.getItems());

        for (Item i : itempool)
            r1.Remove(i);

        for(Item i : itempool){
            if(rnd.nextBoolean())
                r1.addItem(i);
            else
                r2.addItem(i);
        }

        this.rooms.add(r2);
        game.Draw();
    }

    /**
     * Koronket lefut, random szobaesemenyekert felel, valamint szobak, karakterek es targyak allapotanak frissiteseert
     */
    public void Round(){
        System.out.println("round");
        int event = rnd.nextInt(5);
        if(event == 1 && rooms.size() > 2 && randomOn){
            Room room1 = this.rooms.get(rnd.nextInt(rooms.size()));
            Room room2 = room1.getNeighbours().get(rnd.nextInt(room1.getNeighbours().size()));
            MergeRooms(room1, room2);
        }
        if(event == 2 && randomOn) {
            SplitRoom(rooms.get(rnd.nextInt(rooms.size())));
        }
        for(Room r : rooms)
            r.Update();

        for(Character c : characters){
            c.Update();
            ArrayList<Item> items = new ArrayList<Item>(c.getItems());
            for(Item i : items)
                i.Update();
            }

        rounds--;
        if(rounds == 0)
            new EndGameFrame("Defeat");
    }

    /**
     * Random cselekvest valosit meg a nem jatekos altal iranyitott karaktereknek
     */
    public void RandomActions(){
        for(int i = 0; i < 5; ++i){
            try {
                Thread.sleep(500);
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            }
            int action = rnd.nextInt(7);
            if(action == 1 || action == 2 || action == 3){
                if(!currentCharacter.getRoom().getNeighbours().isEmpty()){
                    int choice = rnd.nextInt(currentCharacter.getRoom().getNeighbours().size());
                    currentCharacter.Move(currentCharacter.getRoom().getNeighbours().get(choice));
                }
            }
            else if(action == 4){
                int choice;
                if(!currentCharacter.getRoom().getItems().isEmpty()){
                    choice = rnd.nextInt(currentCharacter.getRoom().getItems().size());
                    currentCharacter.PickUpItem(currentCharacter.getRoom().getItems().get(choice));
                }

            }
            else if(action == 5){
                int choice;
                if(!currentCharacter.getItems().isEmpty()){
                    choice = rnd.nextInt(currentCharacter.getItems().size());
                    currentCharacter.UseItem(currentCharacter.getItems().get(choice));
                }
            }
            else if(action == 6){
                int choice;
                if(!currentCharacter.getItems().isEmpty()){
                    choice = rnd.nextInt(currentCharacter.getItems().size());
                    currentCharacter.DropItem(currentCharacter.getItems().get(choice));
                }
            }
        }
        nextCharacter();
    }

    /**
     * Hozzaad egy szobat a jatekhoz
     * @param r A hozzaadott szoba
     */
    public void addRoom(Room r){
        this.rooms.add(r);
    }

    /**
     * A kovetkezo karakternek a kore kovetkezik ezutan
     */
    public void nextCharacter(){
        do{
            if(currentCharacter != characters.get(characters.size()-1)){
                currentCharacter = characters.get(characters.indexOf(currentCharacter) + 1);
            }
            else{
                Round();
                currentCharacter = characters.get(0);
            }
        } while(currentCharacter.getRoom() == null);

        game.Draw();
        System.out.println("next: " + currentCharacter.name);
        if(currentCharacter.Accept(new CharacterTypeVisitor()) && randomOn)
            RandomActions();
    }

    /**
     * Visszaadja a karaktert akinek eppen a kore van
     * @return A koron levo karakter
     */
    public Character getCurrentCharacter(){
        return currentCharacter;
    }

    /**
     * Hozzaad egy karaktert a jatekhoz
     * @param c a karakter
     */
    public void addCharacter(Character c){
        this.characters.add(c);
    }

    /**
     * Hozzaad egy targyat a jatekhoz
     * @param i a targy
     */
    public void addItem(Item i){
        this.items.add(i);
    }
    /**
     * Megnyeri a hallgatoknak a jatekot.
     */
    public void StudentsWon(){
        new EndGameFrame("Victory");
        System.out.println("students won");
    }

    /**
     * Egy szobara valo ranyomas utani esemeny
     * @param r A megnyomott szoba
     */
    public void RoomClicked(Room r){
        currentCharacter.Move(r);
        game.Draw();
    }

    /**
     * Egy targyra valo ranyomas utani esemeny
     * @param i A megnyomott targy
     */
    public void ItemClicked(Item i){
        if(i.getOwner() == null){
            currentCharacter.PickUpItem(i);
        }
        else {
            if(useMode){
                currentCharacter.UseItem(i);
                game.Draw();
            }
            else
                currentCharacter.DropItem(i);
        }
    }

    /**
     * Esemeny ha meg lett nyomva a mod valtas gomb
     */
    public void changeModeClicked(){
        useMode = !useMode;
    }

    public boolean getMode(){
        return this.useMode;
    }

    /**
     * Jatekmenet inicializalasa: szobak, targyak es karakterer letrehozasa es elhelyezese
     * @param n A hallgatok szama
     */
    public void init(int n){
        int numOfRooms = rnd.nextInt(75) + 75;
        ArrayList<Integer> names = new ArrayList<>();
        for(int i = 0; i < numOfRooms;++i){
            names.add(i+1);
        }

        for(int i = 0; i < numOfRooms; ++i){
            int cap = rnd.nextInt(9) + 2;
            int curse = rnd.nextInt(6);
            int gas = rnd.nextInt(6);
            boolean gassed = (gas == 5);
            boolean cursed = (curse == 5);
            int name = rnd.nextInt(names.size());
            Room room = new Room(gassed, cursed, cap, "r" + (names.remove(name)));
            rooms.add(room);
        }

        for(Room room : rooms){
            int index = rooms.indexOf(room);
            rnd = new Random(System.currentTimeMillis() * (index + 1));
            int neighbours = rnd.nextInt(6) + 1;
            System.out.println(neighbours);
            int lower = neighbours - rnd.nextInt(neighbours + 1);
            int higher = neighbours - lower;

            while (!(index - lower >= 0 && index + higher < rooms.size())){
                lower = neighbours - rnd.nextInt(neighbours + 1);
                higher = neighbours - lower;
            }

            for(int i = index; i > index-lower; --i)
                room.AddNeighbour(rooms.get(i - 1));

            for(int i = index; i < index+higher; ++i)
                room.AddNeighbour(rooms.get(i + 1));

            int items = rnd.nextInt(5);
            for(int i = 0; i < items; ++i){
                int selection = rnd.nextInt(8);
                switch (selection){
                    case 0:
                        room.addItem(new AirFreshener("AirFreshener"));
                        break;
                    case 1:
                        room.addItem(new Beer("Beer"));
                        break;
                    case 2:
                        room.addItem(new Camembert("Camembert"));
                        break;
                    case 3:
                        room.addItem(new Mask("Mask", rnd.nextBoolean()));
                        break;
                    case 4:
                        room.addItem(new Rag("Rag"));
                        break;
                    case 5:
                        room.addItem(new SlideRule("Sliderule", false, this));
                        break;
                    case 6:
                        room.addItem(new Transistor("Transistor"));
                        break;
                    case 7:
                        room.addItem(new Tvsz("Tvsz", rnd.nextBoolean()));
                        break;
                }
            }

        }

        for(int i = 0; i < n;  ++i){
            Student s = new Student("s" + (i+1), this);
            addCharacter(s);
            rooms.get(rnd.nextInt(rooms.size())).Add(s);
        }

        for(int i = 0; i < Math.max(n/2,1); ++i){
            Lecturer l = new Lecturer("l" + (i+1));
            addCharacter(l);
            rooms.get(rnd.nextInt(rooms.size())).Add(l);
        }

        for(int i = 0; i < Math.max(n/2,1); ++i){
            Cleaner c = new Cleaner("c" + (i+1));
            addCharacter(c);
            rooms.get(rnd.nextInt(rooms.size())).Add(c);
        }

        rooms.get(rnd.nextInt(rooms.size())).addItem(new SlideRule("Sliderule", true, this));
        Start();
    }
}
