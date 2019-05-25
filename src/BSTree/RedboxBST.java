// RAISAAT ATIFA RASHID
// Net ID: rar150430
// CS 2336.003 - Project #4

package BSTree;

public class RedboxBST
{
    // Member
    private Node root;
    
    // Mutator
    public void setRoot (Node root) { this.root = root; }
    
    // Accessor
    public Node getRoot () { return root; }
    
    // Function insertNode creates a new node from its parameters and inserts it to the bst
    public void insertNode (String MovieTitle, int numAvailable, int numRented)
    {
        // Call the recursive helper function, and assign the node that it returns to root
        root = insertNode(root, new Node(MovieTitle, numAvailable, numRented));
    }
    
    // Recursive helper function of function insertNode
    public Node insertNode (Node currParent, Node newNode)
    {
        // if the current node is equal to null, return the new node
        if (currParent == null)
            return newNode;
        
        // Else if the new node's title is greater than the current node's title,
        // call the function itself with the current node's right node and the new node as arguments
        else if (newNode.getTitle().compareTo(currParent.getTitle()) > 0)
            currParent.setRight(insertNode(currParent.getRight(), newNode));
        
        // Else if the new node's title is less than the current node's title,
        // call the function itself with the current node's left node and the new node as arguments
        else if (newNode.getTitle().compareTo(currParent.getTitle()) < 0)
            currParent.setLeft(insertNode(currParent.getLeft(), newNode));
        
        // Return the current node
        return currParent;
    }
    
    // function searchTitle recursively searches for a movie title and returns the node where the title is found
    public Node searchTitle(Node curr, String title)
    {
        // Return null, if the title is not found
        if (curr == null)
            return null;
        
        // Return the current node, if the title is equal to the current node's title
        if (curr.getTitle().equals(title))
            return curr;
        
        // If the title is less than the current node's title, call the function itself with the current node's
        // left node and return the node that is returned by the function call
        if (curr.getTitle().compareTo(title) > 0)
            return searchTitle(curr.getLeft(), title);
        
        // call the function itself with the current node's right
        // node and return the node that is returned by the function call
        return searchTitle(curr.getRight(), title);
    }
    
    // function delete recursively deletes a node from the bst
    public void delete (String str)
    {
        root = delete(str, root);
    }
    
    // recursive helper function of function delete
    public Node delete(String str, Node curr)
    {
        // Return null if the current node is equal to null
        if (curr == null)
            return curr;
        
        // If the title to be removed is less than the current node's title, call the function itself with
        // the current node's left node and set the node it returns as the current node's left node
        if (str.compareTo(curr.getTitle()) < 0)
            curr.setLeft(delete(str, curr.getLeft()));
        
        // If the title to be removed is greater than the current node's title, call the function itself with
        // the current node's right node and set the node it returns as the current node's right node
        else if (str.compareTo(curr.getTitle()) > 0)
            curr.setRight(delete(str, curr.getRight()));
        
        // Else if the current node has both a left and a right subtree
        else if (curr.getLeft() != null && curr.getRight() != null)
        {
            // Find the title of the leftmost node in the current node's right subtree, and set it
            // as the current node's title
            curr.setTitle(findMin(curr.getRight()).getTitle());
            
            // call the function itself with the current node's right node, and set the node 
            // that is returned as the current node's right node
            curr.setRight(delete(curr.getTitle(), curr.getRight()));
        }
        
        // Else, set the current node to its right node, if the left node is null.
        // If the left node is not null, set it to its left node.
        else
            curr = (curr.getLeft() != null) ? curr.getLeft() : curr.getRight();
        
        // Return the current node
        return curr;
    }
    
    // function findMin finds the leftmost node in a bst
    private Node findMin (Node curr)
    {
        // If the current node's left node is equal to null,
        // return the current node
        if (curr.getLeft() == null)
            return curr;
        
        // Call the function itself with the current node's left node as the argument
        return findMin (curr.getLeft());
    }
    
    // function add adds copies of a movie if it already exists in the inventory
    // or creates a new node for it if it doesn't already exist
    public void add (String movieTitle, int quantity)
    {
        // Search for the movie title
        Node node = searchTitle(root, movieTitle);
        
        // If the movie title is found, update its no. available with the desired quantity
        if (node != null)
            node.setAvailable(node.getAvailable() + quantity);
        
        // Else, insert it into the bst
        else
            insertNode(movieTitle, quantity, 0);
    }
    
    // function rent allows a movie from the inventory to be rented
    public void rent (String movieTitle)
    {
        // Search for the movie that is to be rented
        Node node = searchTitle(root, movieTitle);
        
        // If the movie is found
        if (node != null)
        {
            // Decrement its no. available and increment its no. rented
            node.setAvailable(node.getAvailable() - 1);
            node.setRented(node.getRented() + 1);
        }
    }
    
    // function Return allows a movie to be returned to the inventory
    public void Return (String movieTitle)
    {
        // search for the movie that is to be returned
        Node node = searchTitle(root, movieTitle);
        
        // if the movie is found
        if (node != null)
        {
            // Increment its no. available and decrement its no. rented
            node.setAvailable(node.getAvailable() + 1);
            node.setRented(node.getRented() - 1);
        }
    }
    
    // Function remove allows a movie to be removed from the inventory
    public void remove (String movieTitle, int quantity)
    {
        // Search for the movie that is to be removed
        Node node = searchTitle(root, movieTitle);
        
        // If the movie is found
        if (node != null)
        {
            // If the no. available is greater than the quantity to be removed,
            // decrement the no. available by the desired quantity
            if (quantity < node.getAvailable())
                node.setAvailable(node.getAvailable() - quantity);
            
            else
            {
                // If the no. rented is equal to 0, delete the node from the bst
                if (node.getRented() == 0)
                    delete(movieTitle);
                
                // Else if rented is not equal to zero, set the no. available to 0
                else
                    node.setAvailable(0); 
            }
        }
    }
}
