public class LecturerProtectionVisitor implements ItemVisitor{


    @Override
    public boolean visit(Beer b) {
        System.out.println(b.name + " activated");
        return true;
    }
    @Override
    public boolean visit(Tvsz t) {
        System.out.println(t.name + " activated");
        t.ReduceDurability();
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

    @Override
    public boolean visit(AirFreshener a) { return false; }
}
