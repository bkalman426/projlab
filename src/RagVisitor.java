public class RagVisitor implements ItemVisitor{
    public RagVisitor(){
        int indents = Thread.currentThread().getStackTrace().length - 4;
        WriteIndents(indents);
        System.out.println("RagVisitor v consturected");
    }
    @Override
    public boolean visit(Beer b) {
        return false;
    }

    @Override
    public boolean visit(Tvsz t) {
        int indents = Thread.currentThread().getStackTrace().length - 4;
        WriteIndents(indents);
        System.out.println("v" + ".visit(" + t.name + ")");

        WriteIndents(indents);
        System.out.println("Visszateres:" + "v" + ", bool: false");
        return false;
    }

    @Override
    public boolean visit(Camembert c) {
        return false;
    }

    @Override
    public boolean visit(Rag r) {
        int indents = Thread.currentThread().getStackTrace().length - 4;
        WriteIndents(indents);
        System.out.println("v" + ".visit(" + r.name + ")");

        WriteIndents(indents);
        System.out.println("Visszateres:" + "v" + ", bool: true");
        return true;
    }

    @Override
    public boolean visit(Mask m) {
        int indents = Thread.currentThread().getStackTrace().length - 4;
        WriteIndents(indents);
        System.out.println("v" + ".visit(" + m.name + ")");

        WriteIndents(indents);
        System.out.println("Visszateres:" + "v" + ", bool: false");

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
