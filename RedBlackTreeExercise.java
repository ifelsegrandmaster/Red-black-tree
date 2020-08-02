public class RedBlackTreeExercise<T extends Comparable<T>> {
    public class Node {
        T data; // holds the key
        Node parent; // pointer to the parent
        Node left; // pointer to the left child
        Node right; // pointer to the right child
        int color; // 1. Red, O. Black
        int size; // size of the current node
    }

    private Node TNULL;
    private Node root;
    private int rankIndex;

    // ctor for initialization
    public RedBlackTreeExercise() {
        TNULL = new Node();
        TNULL.color = 0;
        TNULL.left = null;
        TNULL.right = null;
        TNULL.size = 0;
        root = TNULL;
    }

    //Get the root node
    public Node getRootNode(){
        return this.root;
    }

    // Add a new item to the tree
    public void add(T s) {
        // 1. Insert
        Node newNode = insertNode(root, s);

        //if node is a root node, simply return
        if(newNode.parent == null){
           newNode.color = 0; //color it black
           return;
        }
        //if grandparent is null, simply return
        if(newNode.parent.parent == null){
            return;
        }
        // Fix the tree
        fixInsert(newNode);
    }

    // The number of items that have been added to the tree
    public int size() {
        return root.size;
    }

    // Find an item by it's rank according to the natural
    // comparable order
    public T get(int rank) {
        return null;
    }

    // Search for an item that was previously added to the tree
    public int rank(T s) {
        // search for the node
        return 0;
    }

    // Helper function to insert an item
    private Node insertNode(Node node, T s) {
        // If the tree is empty, return a new node
        // Ordinary Binary Search Insertion
        Node newNode = new Node();
        newNode.parent = null;
        newNode.data = s;
        newNode.left = TNULL;
        newNode.right = TNULL;
        newNode.color = 1; // new node must be red
        newNode.size = 1; //new node must have size of 1

        if (node == TNULL){
            newNode.parent = node;
            return newNode;
        }
        else if (newNode.data.compareTo(node.data) < 0) {
            node.left = insertNode(node.left, s);
            // Update the size of the tree

        } else if (newNode.data.compareTo(node.data) > 0) {
            node.right = insertNode(node.right, s);
            // Update the size of the tree
        }
        node.size = Math.max(node.left.size,node.right.size) + 1;
        
        //Return the unchanged node
        return node;
    }
    //Inorder helper function
    public void inOrderHelper(Node node) {
		if (node != TNULL) {
			inOrderHelper(node.left);
			System.out.print(node.data + " ");
			inOrderHelper(node.right);
        } 
        
    }
    
    public static void main(String [] args){
        RedBlackTreeExercise<Integer> rb = new RedBlackTreeExercise<>();
        rb.add(12);
        rb.add(23);
        rb.add(25);
        rb.add(2);
        rb.add(2);
        rb.add(10);
        rb.inOrderHelper(rb.getRootNode());
    }

    // rotate right at node x
    public void rightRotate(Node x) {
        Node y = x.left;
        x.left = y.right;
        if (y.right != TNULL) {
            y.right.parent = x;
        }
        y.parent = x.parent;
        if (x.parent == null) {
            this.root = y;
        } else if (x == x.parent.right) {
            x.parent.right = y;
        } else {
            x.parent.left = y;
        }
        y.right = x;
        x.parent = y;
    }

    // rotate left at node x
    public void leftRotate(Node x) {
        Node y = x.right;
        x.right = y.left;
        if (y.left != TNULL) {
            y.left.parent = x;
        }
        y.parent = x.parent;
        if (x.parent == null) {
            this.root = y;
        } else if (x == x.parent.left) {
            x.parent.left = y;
        } else {
            x.parent.right = y;
        }
        y.left = x;
        x.parent = y;
    }

    // fix the red-black tree
    private void fixInsert(Node k) {
        Node u;
        while (k.parent.color == 1) {
            if (k.parent == k.parent.parent.right) {
                u = k.parent.parent.left; // uncle
                if (u.color == 1) {
                    // case 3.1
                    u.color = 0;
                    k.parent.color = 0;
                    k.parent.parent.color = 1;
                    k = k.parent.parent;
                } else {
                    if (k == k.parent.left) {
                        // case 3.2.2
                        k = k.parent;
                        rightRotate(k);
                    }
                    // case 3.2.1
                    k.parent.color = 0;
                    k.parent.parent.color = 1;
                    leftRotate(k.parent.parent);
                }
            } else {
                u = k.parent.parent.right; // uncle

                if (u.color == 1) {
                    // mirror case 3.1
                    u.color = 0;
                    k.parent.color = 0;
                    k.parent.parent.color = 1;
                    k = k.parent.parent;
                } else {
                    if (k == k.parent.right) {
                        // mirror case 3.2.2
                        k = k.parent;
                        leftRotate(k);
                    }
                    // mirror case 3.2.1
                    k.parent.color = 0;
                    k.parent.parent.color = 1;
                    rightRotate(k.parent.parent);
                }
            }
            if (k == root) {
                break;
            }
        }
        root.color = 0;
    }

}
