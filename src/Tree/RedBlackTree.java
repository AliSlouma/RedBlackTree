package Tree;

import javax.management.RuntimeErrorException;
import java.awt.*;

public class RedBlackTree implements  IRedBlackTree {
    INode root = null;
    INode refNode = null;
    public INode getRoot()
    {
        return this.root;
    }

    @Override
    public boolean isEmpty() {
        if(root == null)
            return true;
        return false;
    }

    @Override
    public void clear() {
        root=null;
    }


    private Object searchRec(INode root, Comparable key) {
        if( !root.isNull() ){
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

        if(key == null)
            throw new RuntimeErrorException(new Error());
        if(!isEmpty())
            return searchRec(this.root,key);
        return null;
    }

    @Override
    public boolean contains(Comparable key) {
        if(key == null)
        throw new RuntimeErrorException(new Error());
        if(search(key) != null)
            return true;
        return false;
    }

    private boolean redUncle(INode x){
        if(x == this.root){
            x.setColor(false);
            return true;
        }
        x.getParent().getParent().getLeftChild().setColor(false);
        x.getParent().getParent().getRightChild().setColor(false);
        x.getParent().getParent().setColor(true);
        return false;
    }

    private boolean checkIsLeftChild(INode parent){
        if(parent == parent.getParent().getLeftChild()){
            return true;
        }
        return false;
    }
    private void R_B_Proberties(INode root){
        // check the place of the uncle
        INode uncle=null;
        if(root.getParent()!=this.root ) {
            if (checkIsLeftChild(root.getParent())) {
                uncle = root.getParent().getParent().getRightChild();
            } else
                uncle = root.getParent().getParent().getLeftChild();

            //if uncle is red and parent is not black
            INode alt = root;
            while (alt.getParent().getColor() == true && !uncle.isNull() && uncle.getColor() == true) {
                redUncle(alt);

                alt = alt.getParent().getParent();
                if (alt == this.root){
                    alt.setColor(false);
                    return;
                }
                if (alt .getParent() != this.root && checkIsLeftChild(alt.getParent())) {
                    uncle = alt.getParent().getParent().getRightChild();
                } else if(alt .getParent() != this.root && !checkIsLeftChild(alt.getParent()))
                    uncle = alt.getParent().getParent().getLeftChild();
            }
            // uncle is black and parent is not black ==> null is black
            if (alt.getParent().getColor() == true && (uncle.isNull() || uncle.getColor() == false)) {
                //LLC
                if (alt.getParent().getParent().getLeftChild() == alt.getParent() && alt == alt.getParent().getLeftChild()) {
                    rightRotate(alt.getParent().getParent());
                    alt.getParent().setColor(false);
                    alt.getParent().getRightChild().setColor(true);
                }
                //LRC
                else if (alt.getParent().getParent().getLeftChild() == alt.getParent() && alt == alt.getParent().getRightChild()) {
                    leftRotate(alt.getParent());
                    rightRotate(alt.getParent());
                    alt.setColor(false);
                    alt.getRightChild().setColor(true);
                }
                //RRC
                else if (alt.getParent().getParent().getRightChild() == alt.getParent() && alt == alt.getParent().getRightChild()) {
                    leftRotate(alt.getParent().getParent());
                    alt.getParent().setColor(false);
                    alt.getParent().getLeftChild().setColor(true);
                }
                //RLC
                else if (alt.getParent().getParent().getRightChild() == alt.getParent() && alt == alt.getParent().getLeftChild()) {
                    rightRotate(alt.getParent());
                    leftRotate(alt.getParent());
                    alt.setColor(false);
                    alt.getLeftChild().setColor(true);
                }
            }
        }
    }

    private INode insertRec(INode root , Comparable key , Object value){
        if(root== null || root.isNull()){
            root = new Node();
            root.setKey(key);
            root.setValue(value);
            root.setColor(INode.RED);
            root.setRightChild(new Node());
            root.setLeftChild(new Node());
            if(this.root == null){
                root.setColor(false);
            }

            return root;
        }
        //  INode alt;
        if(root.getKey().compareTo(key)==0){
            root.setValue(value);
        }
        else if(root.getKey().compareTo(key)<0){
            root.setRightChild(insertRec(root.getRightChild(),key,value));
            if(root.getRightChild().getParent() == null){
                root.getRightChild().setParent(root);
                //R_B_Proberties(root.getRightChild());
                this.refNode = root.getRightChild();
            }

        }
        else if(root.getKey().compareTo(key)>0){
            root.setLeftChild(insertRec(root.getLeftChild(),key,value));
            if(root.getLeftChild().getParent()==null){
                root.getLeftChild().setParent(root);
                // R_B_Proberties(root.getLeftChild());
                this.refNode = root.getLeftChild();
            }

        }

        return root;


    }

    public void insert(Comparable key, Object value) {


        if(key !=null  && value !=null)
            this.root=insertRec(this.root,key,value);
        else
            throw new RuntimeErrorException(new Error());

        if(refNode != null && key !=null  && value !=null)
            R_B_Proberties(this.refNode);
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
        if(x.getParent()==null){
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

        tree.insert(11,2221);
        tree.insert(1,2222);
        tree.insert(14,2322);
        tree.insert(2,32222);
        tree.insert(7,2332);
        tree.insert(15,2333);
        //tree.insert(80,23333);

        System.out.println(tree.getRoot().getValue());
        System.out.println(tree.search(70));
    }
}
