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
        TNULL.parent = null;
        root = TNULL;
    }

    // Get the root node
    public Node getRootNode() {
        return this.root;
    }

    // Add a new item to the tree
    public void add(T s) {
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
            } else {
                x = x.right;
            }
        }

        // y is parent of x
        Node z;
        node.parent = y;
        z = node;
        if (y == null) {
            root = node;
        } else if (node.data.compareTo(y.data) < 0) {
            y.left = node;
            // Update the size
            while (z != null) {
                z.size++;
                z = z.parent;
            }
        } else if (node.data.compareTo(y.data) > 0) {
            y.right = node;
            // Update the size;
            while (z != null) {
                z.size++;
                z = z.parent;
            }
        }
        // If new node has been inserted update the size
        // if new node is a root node, simply return
        if (node.parent == null) {
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

    // The number of items that have been added to the tree
    public int size() {
        return root.size;
    }

    // Find an item by it's rank according to the natural
    // comparable order
    public T get(int rank) {
        Node find;
        if ((rank >= 0) && (rank < root.size)) {
            find = select(this.root, rank);
            return find.data;
        }
        throw new IndexOutOfBoundsException();
    }

    // Search for an item that was previously added to the tree
    public int rank(T s) {
        // search for the node
        Node x = search(s);

        if(x == TNULL)
           return -1; //Doesn't exist

        int r = (x.left.size) + 1;
        Node y = x;

        while(y != root){
            if(y == y.parent.right){
                r = r + (y.parent.left.size) + 1;
            }
            y = y.parent;
        }
        return r;
    }

    // Inorder helper function
    public void inOrderHelper(Node node) {
        if (node != TNULL) {
            inOrderHelper(node.left);
            System.out.println(node.data + " ");
            inOrderHelper(node.right);
        }

    }
    //Select helper function
    private Node select(Node node, int i) {
      
        int r = ((node.left.size) + 1);
        if (i == r)
            return node;
        else if (i < r)
            return select(node.left, i);
        else 
            return select(node.right, i - r);

    }

    //Seaarch helper function
    private Node search(T key){
        Node x = this.root;
        while(x != null){
            //compare if the key is the one we searching for
            if(key.compareTo(x.data) < 0){
                //move left
                x = x.left;
            } else if(key.compareTo(x.data) > 0){
               //move right
               x = x.right;
            } else if(key.compareTo(x.data) == 0){
                return x;
            }
        }

        return TNULL;
    }



    private void printHelper(Node root, String indent, boolean last) {
        // print the tree structure on the screen
        if (root != TNULL) {
            System.out.print(indent);
            if (last) {
                System.out.print("R----");
                indent += "     ";
            } else {
                System.out.print("L----");
                indent += "|    ";
            }

            String sColor = root.color == 1 ? "RED" : "BLACK";
            System.out.println(root.data + "(" + sColor + ") (" + root.size + ")");
            printHelper(root.left, indent, false);
            printHelper(root.right, indent, true);
        }
    }

    // print the tree structure on the screen
    public void prettyPrint() {
        printHelper(this.root, "", true);
    }

    public static void main(String[] args) {
        RedBlackTreeExercise<Integer> rb = new RedBlackTreeExercise<>();
        rb.add(12);
        rb.add(23);
        rb.add(25);
        rb.add(1);
        rb.add(13);
        rb.add(34);
        rb.add(40);
        rb.add(56);
        rb.add(45);
        rb.add(78);

        rb.inOrderHelper(rb.getRootNode());
        System.out.println();
        rb.prettyPrint();
        System.out.println(rb.getRootNode().size);
        // Find the node in a tree
        //int k = rb.get(6);
        //System.out.println("Found " + k);
        int rank = rb.rank(78);
        System.out.println("Rank of k " + rank);

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
                    k.parent.size += 1;
                    k.parent.parent.color = 1;
                    k.parent.parent.size -= 2;
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
                    k.parent.size += 1;
                    k.parent.parent.color = 1;
                    k.parent.parent.size -= 2;
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
