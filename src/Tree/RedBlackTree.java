package Tree;

import java.awt.*;

public class RedBlackTree implements  IRedBlackTree {
    INode root = null;
    public INode getRoot()
    {
        return this.root;
    }

    @Override
    public boolean isEmpty() {
        return false;
    }

    @Override
    public void clear() {

    }


    public Object searchRec(INode root, Comparable key) {
        if(root != null){
            if(root.getKey().compareTo(key)==0){
                return  root.getValue();
            }else if(root.getKey().compareTo(key) <0){
                return searchRec(root.getRightChild(),key);
            }else if(root.getKey().compareTo(key) > 0){
                return searchRec(root.getLeftChild(),key);
            }
        }
        return null;
    }

    public Object search(Comparable key) {
        return searchRec(this.root,key);
    }

    @Override
    public boolean contains(Comparable key) {
        return false;
    }

    public INode insertRec(INode root , Comparable key , Object value){
        if(root== null){
            root = new Node();
            root.setKey(key);
            root.setValue(value);
            root.setColor(INode.RED);

            if(this.root == null){
                root.setColor(INode.BLACK);
            }
            return root;
        }
        if(root.getKey().compareTo(key)<0){
            root.setRightChild(insertRec(root.getRightChild(),key,value));
            if(root.getRightChild().getParent() == null)
                root.getRightChild().setParent(root);
        }
        else if(root.getKey().compareTo(key)>0){
            root.setLeftChild(insertRec(root.getLeftChild(),key,value));
            if(root.getLeftChild().getParent()==null)
                root.getLeftChild().setParent(root);
        }

        return root;


    }

    public void insert(Comparable key, Object value) {

        root = insertRec(this.root,key,value);

    }
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
            root.setKey(minKey(root.getRightChild()));
            root.setValue(minValue(root.getRightChild()));
            // Delete the inorder successor
            root.setRightChild(deleteRec(root.getRightChild(), root.getKey()));
        }


        return root;
    }

    Comparable minKey(INode root)
    {
        Comparable minv = root.getKey();
        while (root.getLeftChild() != null)
        {
            minv = root.getLeftChild().getKey();
            root = root.getLeftChild();
        }
        return minv;
    }
    Object minValue(INode root)
    {
        Comparable minv = root.getKey();
        Object val=root.getValue();
        while (root.getLeftChild() != null)
        {
            minv = root.getLeftChild().getKey();
            val=root.getLeftChild().getValue();
            root = root.getLeftChild();
        }
        return val;
    }


    public void rightRotate( INode y){
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
    public void leftRotate(INode x){
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


    public static void main(String[] args) {

        RedBlackTree tree = new RedBlackTree();

        tree.insert(90,90);
        tree.insert(30,30);
        tree.insert(20,20);
        tree.insert(40,40);
        tree.insert(70,70);
        tree.insert(60,60);
        tree.insert(80,80);
        tree.insert(75,75);

        INode x=tree.getRoot();
        System.out.println(x.getValue());
        x=x.getLeftChild();
        System.out.println(x.getValue());
        x=x.getRightChild();
        System.out.println(x.getValue());
        System.out.println(tree.search(70));
        tree.delete(70);
        System.out.println(tree.search(80));
        x=x.getRightChild();
        System.out.println(x.getValue());
        System.out.println(tree.search(70));
        System.out.println(tree.search(75));



    }
}
