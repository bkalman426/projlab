public class Lecturer extends Character{
    public Lecturer(String n) {
        super(n);
    }

    @Override
    public void RagEffect() {
        System.out.println(name + " knocked out");
        this.knockoutTimer = 1;
    }


    @Override
    public void Notify(Character c) {
        if(this.knockoutTimer == 0)
            c.See(this);
    }

    @Override
    public boolean Accept(CharacterTypeVisitor v) {
        return v.visit(this);
    }

    @Override
    public void See(Student s) {
        s.See(this);
    }

    @Override
    public void See(Lecturer l) {
        return;
    }

    public void See(Cleaner c){
        c.See(this);
    }
}
