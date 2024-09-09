public interface ItemVisitor {
    /**
     * A visitor sort latogat meg
     * @param b A sor
     * @return
     */
    public boolean visit(Beer b);
    /**
     * A visitor tvszt latogat meg
     * @param t A tvsz
     * @return
     */
    public boolean visit(Tvsz t);
    /**
     * A visitor camembertet latogat meg
     * @param c A camembert
     * @return
     */
    public boolean visit(Camembert c);
    /**
     * A visitor rongyot latogat meg
     * @param r A rongy
     * @return
     */
    public boolean visit(Rag r);
    /**
     * A visitor maszkot latogat meg
     * @param m A maszk
     * @return
     */
    public boolean visit(Mask m);
    /**
     * A visitor tranzisztort latogat meg
     * @param t A tranzisztor
     * @return
     */
    public boolean visit(Transistor t);
    /**
     * A visitor logaerlecet latogat meg
     * @param s A logarlec
     * @return
     */
    public boolean visit(SlideRule s);

    /**
     * A visitor legfrissitot latogat
     * @param a A legfrissito
     * @return
     */
    public boolean visit(AirFreshener a);
}
