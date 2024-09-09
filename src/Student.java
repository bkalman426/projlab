import java.util.ArrayList;
import java.util.Iterator;

public class Student extends Character{

    GameController gc;
    public Student(String n, GameController c) {
        super(n);
        gc = c;
    }

    @Override
    public void RagEffect() {
        return;
    }

    @Override
    public void Notify(Character c) {
        c.See(this);
    }

    @Override
    public boolean Accept(CharacterTypeVisitor v) {
        return v.visit(this);
    }

    @Override
    public void See(Student s) {
        return;
    }

    @Override
    public void See(Lecturer l) {
        System.out.println(l.name + " attack " + name);
        LecturerProtectionVisitor v = new LecturerProtectionVisitor();
        boolean found = false;
        for(int i = 0; i < this.inventory.size(); ++i){
            if(inventory.get(i).Accept(v)){
                found = true;
                break;
            }
        }

        if(!found){
            System.out.println(l.name + " kill " + name);
            ArrayList<Item> items = new ArrayList<>(inventory);
            for(Item i : items)
                DropItem(i);
            this.Leave();
            this.SetRoom(null);
            if(gc.getCurrentCharacter() == this)
                gc.nextCharacter();
        }
        else {
            System.out.println(name + " defend");
        }
    }

    public void See(Cleaner c){
        c.See(this);
    }
}
