public class Cleaner extends Character{
    public Cleaner(String n) {
        super(n);
    }

    @Override
    public void GasRoomEffect(){
        System.out.println(name + " clean " + room.name);
        this.room.Clean();
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
        if(!s.isStunned()){
            for (Room r : this.room.getNeighbours()){
                if(r.MoveAccept(s)){
                    break;
                }
            }
        }
    }

    @Override
    public void See(Lecturer l) {
        if(!l.isStunned()){
            for (Room r : this.room.getNeighbours()){
                if(r.MoveAccept(l)){
                    break;
                }
            }
        }
    }

    public void See(Cleaner c){
        return;
    }
}
