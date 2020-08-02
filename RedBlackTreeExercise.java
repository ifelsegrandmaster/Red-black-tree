

public class RedBlackTreeExercise<T extends Comparable<T>> {
    public class Node{
            T data; //holds the key
            Node parent; //pointer to the parent
            Node left; //pointer to the left child
            Node right; //pointer to the right child
            int color; //1. Red, O. Black
	    int size; //size of the current node
    }

    private Node TNULL;
    private Node root;
    private int rankIndex;
    //ctor for initialization
    public RedBlackTreeExercise(){
        TNULL = new Node();
        TNULL.color = 0;
        TNULL.left = null;
        TNULL.right = null;
	TNULL.size = 0;
        root = TNULL;
    }
    //Add a new item to the tree
    public void add(T s){
        // Ordinary Binary Search Insertion
		Node node = new Node();
		node.parent = null;
		node.data = s;
		node.left = TNULL;
		node.right = TNULL;
		node.color = 1; // new node must be red

		Node y = null;
		Node x = this.root;
		
		while (x != TNULL) {
			y = x;
			if (node.data.compareTo(x.data) < 0) {
				x = x.left;
			} else if(node.data.compareTo(x.data) > 0) {
				x = x.right;
			}
		}

		// y is parent of x
		node.parent = y;
		if (y == null) {
			root = node;
		} else if (node.data.compareTo(y.data) < 0) {
            y.left = node;
            //Update the size of the tree
         
	   } else if(node.data.compareTo(y.data) > 0) {
            y.right = node;
            //Update the size of the tree
	       y.size++;
	    }

		// if new node is a root node, simply return
		if (node.parent == null){
			node.color = 0;
			return;
		}

		// if the grandparent is null, simply return
		if (node.parent.parent == null) {
			return;
		}

		// Fix the tree
		fixInsert(node);
    }
    //The number of items that have been added to the tree
    public int size(){
        return size;
    } 
    //Find an item by it's rank according to the natural
    //comparable order
    public T get(int rank){
        return null;
    }
    //Search for an item that was previously added to the tree
    public int rank(T s){
        //search for the node
        return 0;
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
	private void fixInsert(Node k){
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
