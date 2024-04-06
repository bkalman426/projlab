public class GasImmunityVisitor implements ItemVisitor{
    public GasImmunityVisitor(){
        int indents = Thread.currentThread().getStackTrace().length - 4;
        WriteIndents(indents);
        System.out.println("GasImmunityVisitor v consturected");
    }

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
        return false;
    }

    @Override
    public boolean visit(Mask m) {
        int indents = Thread.currentThread().getStackTrace().length - 4;
        WriteIndents(indents);
        System.out.println("v" + ".visit(" + m.name + ")");

        m.ReduceDurability();

        WriteIndents(indents);
        System.out.println("Visszateres:" + "v" + ", bool: true");
        return true;
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
