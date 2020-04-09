package Tree;

public class RedBlackTree implements  IRedBlackTree {
    INode root=null;
    public void RedBlackTree(){

        root.setColor(INode.BLACK);
    }
    @Override
    public INode getRoot() {
        return this.root;
    }

    @Override
    public boolean isEmpty() {
        return false;
    }

    @Override
    public void clear() {

    }

    @Override
    public Object search(Comparable key) {
        return null;
    }

    @Override
    public boolean contains(Comparable key) {
        return false;
    }

    @Override
    public void insert(Comparable key, Object value) {

    }

    @Override
    public boolean delete(Comparable key) {
        root = deleteRec(root, key);

        return false;
    }
    INode deleteRec(INode root, Comparable key)
    {
        /* Base Case: If the tree is empty */
        if (root == null)  return root;

        /* Otherwise, recur down the tree */
        if (key.compareTo(root.getKey())<0)
            root.setLeftChild( deleteRec(root.getLeftChild(), key));
        else if (key.compareTo(root.getKey())>0)
            root.setRightChild( deleteRec(root.getRightChild(), key));

            // if key is same as root's key, then This is the node
            // to be deleted
        else
        {
            // node with only one child or no child
            if (root.getLeftChild() == null)
                return root.getRightChild();
            else if (root.getRightChild() == null)
                return root.getLeftChild();

            // node with two children: Get the inorder successor (smallest
            // in the right subtree)
            root.setKey(minValue(root.getRightChild()));

            // Delete the inorder successor
            root.setRightChild(deleteRec(root.getRightChild(), root.getKey()));
        }


        return root;
    }

    Comparable minValue(INode root)
    {
        Comparable minv = root.getKey();
        while (root.getLeftChild() != null)
        {
            minv = root.getLeftChild().getKey();
            root = root.getLeftChild();
        }
        return minv;
    }



    public void rightRotate(IRedBlackTree tree, INode y){
        INode x=y.getLeftChild();
        y.setLeftChild(x.getRightChild());
        if(x.getRightChild()!=null){
            x.getRightChild().setParent(y);
        }
        x.setParent(y.getParent());
        if(y.getParent()==null) {
            root=x;
        }
        else if(y==y.getParent().getRightChild()){
            y.getParent().setRightChild(x);
        }
        else{
            y.getParent().setLeftChild(x);
        }
        x.setRightChild(y);
        y.setParent(x);
    }
    public void leftRotate(IRedBlackTree tree,INode x){
        INode y = x.getRightChild();
        x.setRightChild(y.getLeftChild());
        if(y.getLeftChild()!=null){
            y.getLeftChild().setParent(x);
        }
        y.setParent(x.getParent());
        if(x.getParent()!=null){
            root=y;
        }
        else if (x==x.getParent().getLeftChild()){
            x.getParent().setLeftChild(y);
        }
        else {
            x.getParent().setRightChild(y);
        }
        y.setLeftChild(x);
        x.setParent(y);
    }
}

