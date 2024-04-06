public class Lecturer extends Character{
    public Lecturer(String n) {
        super(n);
    }

    @Override
    public void RagEffect() {
        int indents = Thread.currentThread().getStackTrace().length - 4;
        WriteIndents(indents);
        System.out.println(name + ".RagEffect()");

        this.knockoutTimer = 1;

        WriteIndents(indents);
        System.out.println("Visszateres:" + name + ", void");

    }


    @Override
    public void Notify(Character c) {
        int indents = Thread.currentThread().getStackTrace().length - 4;
        WriteIndents(indents);
        System.out.println(name + ".Notify(" + c.name + ")");

        if(this.knockoutTimer == 0)
            c.See(this);

        WriteIndents(indents);
        System.out.println("Visszateres:" + name + ", void");
    }

    @Override
    public void See(Student s) {
        s.See(this);
    }

    @Override
    public void See(Lecturer l) {
        return;
    }
}
