package Tree;

import javax.management.RuntimeErrorException;
import java.awt.*;

public class RedBlackTree implements  IRedBlackTree {
    INode root = null;
    INode refNode = null;
    INode tempRoot=null;
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
        if(key == null)
            throw new RuntimeErrorException(new Error());
        if(!contains(key)){
            return false;
        }
        INode temp=findNode(root,key);
        boolean originalColor = temp.getColor();
        root=deleteRec(root,key);
        if(tempRoot!=null){
            root=tempRoot;
            tempRoot=null;
        }
        return false;
    }
    INode deleteRec(INode root, Comparable key)
    {
        //   /Base Case: If the tree is empty
        if (root.isNull())  return root;

        //     Otherwise, recur down the tree
        if (key.compareTo(root.getKey())<0)
            root.setLeftChild( deleteRec(root.getLeftChild(), key));
        else if (key.compareTo(root.getKey())>0)
            root.setRightChild( deleteRec(root.getRightChild(), key));

            // if key is same as root's key, then This is the node
            // to be deleted
        else
        {
            // node with only one child or no child
            if (root.getLeftChild().isNull() || root.getRightChild().isNull()){
                  // a red note to be deleted or black that has a red child
                    if( root.getLeftChild().isNull()&&(root.getRightChild().getColor() || root.getColor()) ){
                        root.getRightChild().setColor(false);
                        return root.getRightChild();
                    }
                    // a red note to be deleted or black that has a red child
                    else if (root.getRightChild().isNull()&&(root.getLeftChild().getColor() || root.getColor())){
                        root.getLeftChild().setColor(false);
                        return root.getLeftChild();
                    }
                    else{
                        if(root.getLeftChild().isNull()){
                            ((Node)root.getRightChild()).setDoubleBlack(false);}
                        else{
                            ((Node)root.getLeftChild()).setDoubleBlack(false);}
                        if(root==root.getParent().getLeftChild()){
                          if(!root.getParent().getRightChild().getColor()){
                              //right sibling black-right child red
                             if(root.getParent().getRightChild().getRightChild().getColor()   ){
                                root.getParent().getRightChild().getRightChild().setColor(false);
                                leftRotate(root.getParent());

                                }
                             //right sibling black-left child red
                             else if(root.getParent().getRightChild().getLeftChild().getColor()){
                                 root.getParent().getRightChild().getLeftChild().setColor(false);
                                    rightRotate(root.getParent().getRightChild());
                                    leftRotate(root.getParent());
                             }

                            }
                          //right sibling red
                          else if (root.getParent().getRightChild().getColor()){
                              root.getParent().getRightChild().setColor(false);
                              root.getParent().getRightChild().getLeftChild().setColor(true);
                              leftRotate(root.getParent());

                          }
                          else {
                              //right sibling black with 2 black children (nigga tree)
                              root.getParent().getRightChild().setColor(true);

                          }


                        }
                        else if(root==root.getParent().getRightChild()){
                            if(!root.getParent().getLeftChild().getColor()){
                                //left sibling black -left child red
                                if(root.getParent().getLeftChild().getLeftChild().getColor()   ){
                                    root.getParent().getLeftChild().getLeftChild().setColor(false);
                                    rightRotate(root.getParent());

                                }
                                //left sibling black-right child red
                                else if(root.getParent().getLeftChild().getRightChild().getColor()){
                                    root.getParent().getLeftChild().getRightChild().setColor(false);
                                    leftRotate(root.getParent().getLeftChild());
                                    rightRotate(root.getParent());
                                }

                            }
                            //left sibling red
                            else if (root.getParent().getLeftChild().getColor()){
                                root.getParent().getLeftChild().setColor(false);
                                root.getParent().getLeftChild().getRightChild().setColor(true);
                                rightRotate(root.getParent());

                            }
                            else {
                                //right sibling black with 2 black children (nigga tree)
                                root.getParent().getLeftChild().setColor(true);

                            }
                        }
                             if(root.getLeftChild().isNull()){
                                  ((Node)root.getRightChild()).setDoubleBlack(false);}
                                else{
                                 ((Node)root.getLeftChild()).setDoubleBlack(false);}
                        }
                return root.getRightChild();
            }


            // node with two children: Get the inorder successor (smallest
            // in the right subtree)
            root.setKey(minKey(root.getRightChild()));
            root.setValue(minValue(root.getRightChild()));
            // Delete the inorder successor
           root.setRightChild(deleteRec(root.getRightChild(), root.getKey()));
        }


        return root;
    }

    INode findNode(INode root,Comparable key){

        if (root == null)  return root;
        INode temp=root;
        while (key.compareTo(temp.getKey())!=0) {
            if (key.compareTo(temp.getKey()) < 0){
                temp=temp.getLeftChild();
            }

            else if (key.compareTo(temp.getKey()) > 0){
                temp=temp.getRightChild();
            }

        }
        return temp;
    }

    Comparable minKey(INode root)
    {
        Comparable minv = root.getKey();
        while (!root.getLeftChild().isNull())
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
        while (!root.getLeftChild().isNull())
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
            tempRoot=x;
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
            tempRoot=y;
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
        tree.insert(50,50);
        tree.insert(10,10);
      tree.delete(10);
      tree.insert(39,39);
        tree.delete(50);






        INode x=tree.getRoot();







    }
}
/*
if(temp.getColor()==true){
            root=deleteRec(root,key);
            return true;
        }
        else if(temp.getColor()==temp.getRightChild().isNull() && (temp.getLeftChild().isNull())){
            temp.getRightChild().setColor(false);
            root=deleteRec(root,key);
            return true;
        }
        else if(temp.getColor()==temp.getLeftChild().isNull() && (temp.getRightChild().isNull())){
            temp.getLeftChild().setColor(false);
            root=deleteRec(root,key);
            return true;
        }

     //   root = deleteRec(root, key);
        System.out.println("special case");
 */

/*
        tree.insert(90,90);
        tree.insert(30,30);
        tree.insert(20,20);
        tree.insert(40,40);
        tree.insert(70,70);
        tree.insert(60,60);
        tree.insert(80,80);
        tree.insert(75,75);
 */