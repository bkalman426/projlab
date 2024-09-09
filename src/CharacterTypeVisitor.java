public class CharacterTypeVisitor {
    public boolean visit(Cleaner c){
        return true;
    }

    public boolean visit(Lecturer l){
        return true;
    }

    public boolean visit(Student s){
        return false;
    }
}
