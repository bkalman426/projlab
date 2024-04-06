public class LecturerProtectionVisitor implements ItemVisitor{
    public LecturerProtectionVisitor(){
        int indents = Thread.currentThread().getStackTrace().length - 4;
        WriteIndents(indents);
        System.out.println("LecturerProtectionVisitor v consturected");
    }

    @Override
    public boolean visit(Beer b) {
        return true;
    }
    @Override
    public boolean visit(Tvsz t) {
        int indents = Thread.currentThread().getStackTrace().length - 4;
        WriteIndents(indents);
        System.out.println("v" + ".visit(" + t.name + ")");


        t.ReduceDurability();

        WriteIndents(indents);
        System.out.println("Visszateres:" + "v" + ", bool: true");
        return true;
    }

    @Override
    public boolean visit(Camembert c) {
        return false;
    }

    @Override
    public boolean visit(Rag r) {
        return false;
    }

    @Override
    public boolean visit(Mask m) {
        return false;
    }

    @Override
    public boolean visit(Transistor t) {
        return false;
    }

    @Override
    public boolean visit(SlideRule s) {
        return false;
    }
}
