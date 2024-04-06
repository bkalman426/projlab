import java.util.Scanner;

public class Skeleton {

    public static void main(String[] args) {
        Skeleton sk = new Skeleton();
        Scanner sc = new Scanner(System.in);

        while (true){
            System.out.println("---------------------");
            System.out.println("Lehetőségek:");
            System.out.println("1. Item felvétel teszt");
            System.out.println("2. Karakter mozgatása olyan szobába amibe be tud menni");
            System.out.println("3. Tárgy eldobása");
            System.out.println("4. Karakter mozgatása teli szobába");
            System.out.println("5. Camembert elhasznalása");
            System.out.println("6. Rongy elhasznalása");
            System.out.println("7. Tranzisztorok párosítása");
            System.out.println("8. Párosított transistor céljának beállítása");
            System.out.println("9. Céllal rendelkező tranzisztor használata");
            System.out.println("10. Gázos szobába lépés védelem nélkül");
            System.out.println("11. Gázos szobába lépés védelemmel");
            System.out.println("12. Oktatóval rendelkező szobába lépés védelemmel");
            System.out.println("13. Oktatóval rendelkező szobába lépés védelem nélkül");
            System.out.println("14. Tárgy felvétele teljes eszköztárral");
            System.out.println("15. Elbénított oktatóval rendelkező szobába lépés");
            System.out.println("16. Oktató elbénítása ronggyal");
            System.out.println("17. Oktatóval rendelkező szobába lépés éppen elhasználódó tvsz-el");
            System.out.println("18. SlideRule felvétele");
            System.out.println("19. Szobák összeolvasztása");
            System.out.println("20. Szoba szétválasztása");
            System.out.println("21. Egyirányú ajtón való átmenés próbálás");
            System.out.println("22. Elatkozott szoba ajtoinak elrejtese");
            switch (sc.nextInt()){
                case 1:
                    sk.PickUpItemTest();
                    break;
                case 2:
                    sk.MoveTest();
                    break;
                case 3:
                    sk.DropTest();
                    break;
                case 4:
                    sk.MoveToFullRoomTest();
                    break;
                case 5:
                    sk.UseCamembertTest();
                    break;
                case 6:
                    sk.UseRagTest();
                    break;
                case 7:
                    sk.PairTransistorTest();
                    break;
                case 8:
                    sk.SetTransistorDestinationTest();
                    break;
                case 9:
                    sk.TeleportWithTransistorTest();
                    break;
                case 10:
                    sk.MoveToGasRoomNoDefenseTest();
                    break;
                case 11:
                    sk.MoveToGasRoomDefenseTest();
                    break;
                case 12:
                    sk.MoveToRoomWithLecturerDefenseTest();
                    break;
                case 13:
                    sk.MoveToRoomWithLecturerNoDefenseTest();
                    break;
                case 14:
                    sk.PickUpItemFullInventoryTest();
                    break;
                case 15:
                    sk.MoveToRoomWithStunnedLecturerTest();
                    break;
                case 16:
                    sk.StunLecturerWithRagTest();
                    break;
                case 17:
                    sk.MoveToRoomWithLecturerDefenseExpireTest();
                    break;
                case 18:
                    sk.PickUpSlideRuleTest();
                    break;
                case 19:
                    sk.MergeRoomsTest();
                    break;
                case 20:
                    sk.SplitRoomTest();
                    break;
                case 21:
                    sk.MoveOneWayDoorTest();
                    break;
                case 22:
                    sk.CurseRoomTest();
                    break;
                default:
                    System.out.println("Rossz szám!");
                    break;
            }
        }

    }

    public void PickUpItemTest(){
        Room r = new Room(false,false, 5 , "r");
        Transistor t = new Transistor("t");
        Student s = new Student("s");
        r.addItem(t);
        s.SetRoom(r);
        r.Add(s);

        System.out.println("-----------------");
        s.PickUpItem();
    }

    public void MoveTest(){
        Room r1 = new Room(false, false, 5, "r1");
        Room r2 = new Room(false, false, 5, "r2");
        Student s = new Student("s");
        r1.AddNeighbour(r2);
        r2.AddNeighbour(r1);
        s.SetRoom(r1);
        r1.Add(s);

        System.out.println("-----------------");
        s.Move();
    }

    public void DropTest(){
        Student s = new Student("s");
        Room r = new Room(false,false, 5, "r");
        Mask m = new Mask("m");
        r.Add(s);
        s.SetRoom(r);
        s.Add(m);

        System.out.println("-----------------");
        s.DropItem(m);
    }


    public void MoveToFullRoomTest(){
        Room r1 = new Room(false,false, 4, "r1");
        Room r2 = new Room(false,false, 5, "r2");
        Student s1 = new Student("s1");
        Student s2 = new Student("s2");
        Student s3 = new Student("s3");
        Student s4 = new Student("s4");
        Student s5 = new Student("s5");

        s1.SetRoom(r1);
        s2.SetRoom(r1);
        s3.SetRoom(r1);
        s4.SetRoom(r1);

        r1.Add(s1);
        r1.Add(s2);
        r1.Add(s3);
        r1.Add(s4);

        s5.SetRoom(r2);
        r2.Add(s5);

        r2.AddNeighbour(r1);

        System.out.println("-----------------");
        s5.Move();
    }

    public void UseCamembertTest(){
        Room r = new Room(false, false, 5, "r");
        Student s = new Student("s");
        Camembert c = new Camembert("c");

        r.Add(s);
        s.SetRoom(r);
        s.Add(c);
        c.SetOwner(s);

        System.out.println("-----------------");
        s.UseItem(c);
    }

    public void UseRagTest(){
        Student s = new Student("s");
        Rag r = new Rag("r");

        s.Add(r);
        r.SetOwner(s);

        System.out.println("-----------------");
        s.UseItem(r);
    }

    public void PairTransistorTest(){
        Student s = new Student("s");
        Transistor t1 = new Transistor("t1");
        Transistor t2 = new Transistor("t2");

        s.Add(t1);
        s.Add(t2);
        t1.SetOwner(s);
        t2.SetOwner(s);

        System.out.println("-----------------");
        s.UseItem(t1);
    }

    public void SetTransistorDestinationTest(){
        Room r = new Room(false, false, 5, "r");
        Student s = new Student("s");
        Transistor t1 = new Transistor("t1");
        Transistor t2 = new Transistor("t2");
        t1.setPair(t2);
        t2.setPair(t1);

        r.Add(s);
        s.SetRoom(r);
        s.Add(t1);
        s.Add(t2);
        t1.SetOwner(s);
        t2.SetOwner(s);

        System.out.println("-----------------");
        t1.Use();
    }

    public void TeleportWithTransistorTest(){
        Room r1 = new Room(false, false, 5, "r1");
        Room r2 = new Room(false, false, 5, "r2");
        Student s = new Student("s");
        Transistor t1 = new Transistor("t1");
        Transistor t2 = new Transistor("t2");

        r1.Add(s);
        s.SetRoom(r1);

        t1.setPair(t2);
        t2.setPair(t1);

        t1.SetRoom(r2);
        r2.addItem(t1);

        t2.SetOwner(s);
        s.Add(t2);

        System.out.println("-----------------");
        s.UseItem(t2);
    }

    public void MoveToGasRoomNoDefenseTest(){
        Room r1 = new Room(false,false,5, "r1");
        Room r2 = new Room(false, false, 5, "r2");

        Student s = new Student("s");
        r1.AddNeighbour(r2);
        r2.SetGas(true);

        r1.Add(s);
        s.SetRoom(r1);

        System.out.println("-----------------");
        s.Move();
    }

    public void MoveToGasRoomDefenseTest(){
        Room r1 = new Room(false,false,5, "r1");
        Room r2 = new Room(false, false, 5, "r2");
        Student s = new Student("s");
        Mask m = new Mask("m");

        r1.AddNeighbour(r2);
        r2.SetGas(true);

        r1.Add(s);
        s.SetRoom(r1);
        s.Add(m);

        System.out.println("-----------------");
        s.Move();
    }

    public void MoveToRoomWithLecturerDefenseTest(){
        Room r1 = new Room(false,false,5, "r1");
        Room r2 = new Room(false, false, 5, "r2");
        Student s = new Student("s");
        Lecturer l = new Lecturer("l");
        Tvsz tv = new Tvsz("tv");

        r1.AddNeighbour(r2);
        s.Add(tv);

        r1.Add(s);
        s.SetRoom(r1);

        r2.Add(l);
        l.SetRoom(r2);

        System.out.println("-----------------");
        s.Move();
    }

    public void MoveToRoomWithLecturerNoDefenseTest(){
        Room r1 = new Room(false,false,5, "r1");
        Room r2 = new Room(false, false, 5, "r2");
        Student s = new Student("s");
        Lecturer l = new Lecturer("l");

        r1.AddNeighbour(r2);

        r1.Add(s);
        s.SetRoom(r1);

        r2.Add(l);
        l.SetRoom(r2);

        System.out.println("-----------------");
        s.Move();
    }

    public void PickUpItemFullInventoryTest(){
        Room r = new Room(false,false,5, "r");
        Student s = new Student("s");

        Tvsz tv = new Tvsz("tv");
        Rag rg = new Rag("rg");
        Beer b = new Beer("b");
        Mask m = new Mask("m");
        Transistor t = new Transistor("t");
        Camembert c = new Camembert("c");

        r.Add(s);
        s.SetRoom(r);

        r.addItem(c);

        s.Add(tv);
        s.Add(rg);
        s.Add(b);
        s.Add(m);
        s.Add(t);

        System.out.println("-----------------");
        s.PickUpItem();
    }

    public void MoveToRoomWithStunnedLecturerTest(){
        Room r1 = new Room(false,false,5, "r1");
        Room r2 = new Room(false, false, 5, "r2");
        Student s = new Student("s");
        Lecturer l = new Lecturer("l");
        l.RagEffect();
        r1.AddNeighbour(r2);

        r1.Add(s);
        s.SetRoom(r1);

        r2.Add(l);
        l.SetRoom(r2);

        System.out.println("-----------------");
        s.Move();
    }

    public void StunLecturerWithRagTest(){
        Room r1 = new Room(false, false, 5, "r1");
        Room r2 = new Room(false, false, 5, "r2");
        Student s = new Student("s");
        Lecturer l = new Lecturer("l");
        Rag r = new Rag("r");

        r1.AddNeighbour(r2);
        r2.Add(l);
        l.SetRoom(r2);

        r1.Add(s);
        s.SetRoom(r1);

        s.Add(r);
        r.SetOwner(s);

        System.out.println("-----------------");
        s.Move();
    }

    public void MoveToRoomWithLecturerDefenseExpireTest(){
        Room r1 = new Room(false,false,5, "r1");
        Room r2 = new Room(false, false, 5, "r2");
        Student s = new Student("s");
        Lecturer l = new Lecturer("l");
        Tvsz tv = new Tvsz("tv");
        tv.ReduceDurability();
        tv.ReduceDurability();

        r1.AddNeighbour(r2);
        s.Add(tv);
        tv.SetOwner(s);

        r1.Add(s);
        s.SetRoom(r1);

        r2.Add(l);
        l.SetRoom(r2);

        System.out.println("-----------------");
        s.Move();
    }

    public void PickUpSlideRuleTest(){
        Room r = new Room(false,false, 5 , "r");
        GameController gc = new GameController();
        SlideRule sr = new SlideRule("sr", gc);
        Student s = new Student("s");
        r.addItem(sr);
        s.SetRoom(r);
        r.Add(s);

        System.out.println("-----------------");
        s.PickUpItem();
    }

    public void MergeRoomsTest(){
        GameController gc = new GameController();

        Room r1 = new Room(true,false,9, "r1");
        Room r2 = new Room(false, true, 4, "r2");

        Student s = new Student("s");
        r2.Add(s);
        s.SetRoom(r2);

        gc.addRoom(r1);
        gc.addRoom(r2);

        System.out.println("-----------------");
        gc.MergeRooms(r1, r2);
    }

    public void SplitRoomTest(){
        GameController gc = new GameController();

        Room r1 = new Room(true,false,9, "r1");
        Student s1 = new Student("s1");
        Student s2 = new Student("s2");

        r1.Add(s1);
        s1.SetRoom(r1);
        r1.Add(s2);
        s2.SetRoom(r1);

        gc.addRoom(r1);

        System.out.println("-----------------");
        gc.SplitRoom(r1);
    }

    public void MoveOneWayDoorTest(){
        Room r1 = new Room(false,false,5, "r1");
        Room r2 = new Room(false,false,5, "r2");
        Student s = new Student("s");

        r2.AddNeighbour(r1);

        r1.Add(s);
        s.SetRoom(r1);

        System.out.println("-----------------");
        s.Move();
    }

    public void CurseRoomTest(){
        Room r1 = new Room(false,true,5, "r1");
        Room r2 = new Room(false,false,5, "r2");
        Room r3 = new Room(false,false,5, "r3");
        Room r4 = new Room(false,false,5, "r4");
        Room r5 = new Room(false,false,5, "r5");

        r1.AddNeighbour(r2);
        r1.AddNeighbour(r3);
        r1.AddNeighbour(r4);
        r1.AddNeighbour(r5);

        System.out.println("-----------------");
        r1.Update();
        r1.getNeighbours();
    }
}
