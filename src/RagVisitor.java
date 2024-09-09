public class RagVisitor implements ItemVisitor{

    @Override
    public boolean visit(Beer b) {
        return false;
    }

    @Override
    public boolean visit(Tvsz t) {
        return false;
    }

    @Override
    public boolean visit(Camembert c) {
        return false;
    }

    @Override
    public boolean visit(Rag r) {
        return true;
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
