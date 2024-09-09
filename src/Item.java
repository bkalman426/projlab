import java.io.Serializable;
import java.util.ArrayList;

public abstract class Item implements Serializable {
    protected Character owner;
    protected ArrayList<ModelObserver> observers = new ArrayList<>();
    public String name;

    public Item(String n){
        this.name = n;
        System.out.println(name + " created");
    }

    /**
     * Ha lehet hasznalni a targyat, akkor kifejti a hatasat, kulonben siman visszater.
     */
    public void Use(){
        return;
    }


    /**
     * A targynak a felveteli esemenye. Emellett beallitja a targynak a tulajdonosat arra a karakterre, aki felvette.
     */
    public void PickedUp(Character c){
        this.SetOwner(c);
    }

    /**
     * Beallitja a targy tulajdonosat.
     * @param c A tulajdonos.
     */
    public void SetOwner(Character c){
        this.owner = c;
    }

    /**
     * Ha van a targynak koronkent vegrehajtando esemenye, ebben megtortenik.
     */
    public void Update(){
        return;
    }

    /**
     *A targy fogad egy transistort.
     * @param tr A transistor.
     */
    public void Accept(Transistor tr) { return; }


    /**
     * Eltavolitja a targy tulajdonosat (=null)
     */
    public void RemoveOwner(){
        this.owner = null;
    }

    /**
     * A targyat fogad egy visitort.
     * @param v A visitor.
     * @return A visitor visit() fuggvenyenek visszaterese.
     */
    public abstract boolean Accept(ItemVisitor v);

    protected void notifyObservers() {
        for (ModelObserver observer : observers) {
            observer.onModelUpdate();
        }
    }

    public Character getOwner(){
        return this.owner;
    }

    public abstract String GetInfo();
}
