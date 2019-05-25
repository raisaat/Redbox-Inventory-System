// RAISAAT ATIFA RASHID

package BSTree;

public class Node 
{
    // Members
    private String title;
    private int available;
    private int rented;
    private Node left;
    private Node right;
    
    // Default constructor
    public Node(){}
    
    // Overloaded constructor
    public Node (String title, int available, int rented)
    {
        this.title = title;
        this.available = available;
        this.rented = rented;
    }
    
    // Mutators
    public void setTitle (String title) { this.title = title; }
    public void setAvailable (int available) { this.available = available; }
    public void setRented (int rented) { this.rented = rented; }
    public void setLeft (Node l) { left = l; }
    public void setRight (Node r) { right = r; }
    
    // Accessors
    public String getTitle () { return title; }
    public int getAvailable () { return available; }
    public int getRented () { return rented; }
    public Node getLeft () { return left; }
    public Node getRight () { return right; } 
}
