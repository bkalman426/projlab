import java.util.Iterator;

public class Student extends Character{
    public Student(String n) {
        super(n);
    }

    @Override
    public void RagEffect() {
        int indents = Thread.currentThread().getStackTrace().length - 4;
        WriteIndents(indents);
        System.out.println(name + ".RagEffect()");

        WriteIndents(indents);
        System.out.println("Visszateres:" + name + ", void");
        return;
    }

    @Override
    public void Notify(Character c) {
        int indents = Thread.currentThread().getStackTrace().length - 4;
        WriteIndents(indents);
        System.out.println(name + ".Notify(" + c.name + ")");

        c.See(this);

        WriteIndents(indents);
        System.out.println("Visszateres:" + name + ", void");
    }

    @Override
    public void See(Student s) {
        int indents = Thread.currentThread().getStackTrace().length - 4;
        WriteIndents(indents);
        System.out.println(name + ".See(" + s.name + ")");

        WriteIndents(indents);
        System.out.println("Visszateres:" + name + ", void");

        return;
    }

    @Override
    public void See(Lecturer l) {
        int indents = Thread.currentThread().getStackTrace().length - 4;
        WriteIndents(indents);
        System.out.println(name + ".See(" + l.name + ")");


        LecturerProtectionVisitor v = new LecturerProtectionVisitor();
        boolean found = false;
        for(int i = 0; i < this.inventory.size(); ++i){
            if(inventory.get(i).Accept(v)){
                found = true;
                break;
            }
        }

        if(!found){
            for(Item i : this.inventory)
                DropItem(i);
            this.Leave();
        }


        WriteIndents(indents);
        System.out.println("Visszateres:" + name + ", void");
    }
}
