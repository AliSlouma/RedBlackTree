package Tree;

import javax.management.RuntimeErrorException;
import java.awt.*;
import java.util.Random;

public class RedBlackTree implements  IRedBlackTree {
   // INode root = null;
   // INode refNode = null;
    INode tempRoot=null;
    private INode root = new Node();
    private INode refNode = null;
    private INode pre;
    private INode suc;
    private boolean existKey=false;
    public INode getRoot()
    {
        return this.root;
    }

    @Override
    public boolean isEmpty() {
        if(root.isNull())
            return true;
        return false;
    }

    @Override
    public void clear() {
        root = new Node();
    }

    public void Pre_Succ(INode root,Comparable key){
        if(root==null || root.isNull()){
            return;
        }
        if(root.getKey().compareTo(key) == 0){
            if(!root.getLeftChild().isNull()) {
                INode temp = root.getLeftChild();
                while (!temp.getRightChild().isNull()) {
                    temp = temp.getRightChild();
                }
                pre = temp;
            }
            if(!root.getRightChild().isNull()) {
                INode temp = root.getRightChild();
                while (!temp.getLeftChild().isNull()) {
                    temp = temp.getLeftChild();
                }
                suc = temp;
            }
            return;
        }
        if(root.getKey().compareTo(key) > 0){
            suc = root;
            Pre_Succ(root.getLeftChild(),key);
        }else {
            pre =root;
            Pre_Succ(root.getRightChild(),key);
        }
    }
    public INode getPre(){
        return this.pre;
    }
    public INode getSuc(){
        return this.suc;
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
        if(root.getParent()!=this.root){
            if (checkIsLeftChild(root.getParent())) {
                uncle = root.getParent().getParent().getRightChild();
            } else
                uncle = root.getParent().getParent().getLeftChild();
            //if uncle is red and parent is not black
            INode alt = root;
            while (alt.getParent().getColor() == true && !uncle.isNull() && uncle.getColor() == true) {
                //System.out.println("red");
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
                else if(alt.getParent() == this.root){
                    break;
                }
            }
            // uncle is black and parent is not black ==> null is black
            if (alt.getParent().getColor() == true && (uncle.isNull() || uncle.getColor() == false)) {
                //LLC
                if (alt.getParent().getParent().getLeftChild() == alt.getParent() && alt == alt.getParent().getLeftChild()) {
                    rightRotate(alt.getParent().getParent());
                    boolean c = alt.getParent().getColor();
                    alt.getParent().setColor(alt.getParent().getRightChild().getColor());
                    alt.getParent().getRightChild().setColor(c);
                   // System.out.println("LLC");
                }
                //LRC
                else if (alt.getParent().getParent().getLeftChild() == alt.getParent() && alt == alt.getParent().getRightChild()) {
                    leftRotate(alt.getParent());
                    rightRotate(alt.getParent());
                    boolean c = alt.getColor();
                    alt.setColor(alt.getRightChild().getColor());
                    alt.getRightChild().setColor(c);
                   // System.out.println("LRC");
                }
                //RRC
                else if (alt.getParent().getParent().getRightChild() == alt.getParent() && alt == alt.getParent().getRightChild()) {
                    leftRotate(alt.getParent().getParent());
                    boolean c = alt.getParent().getColor();
                    alt.getParent().setColor(alt.getParent().getLeftChild().getColor());
                    alt.getParent().getLeftChild().setColor(c);
                   // System.out.println("RRC");
                }
                //RLC
                else if (alt.getParent().getParent().getRightChild() == alt.getParent() && alt == alt.getParent().getLeftChild()) {
                    rightRotate(alt.getParent());
                    leftRotate(alt.getParent());
                    boolean c = alt.getColor();
                    alt.setColor(alt.getLeftChild().getColor());
                    alt.getLeftChild().setColor(c);
                   // System.out.println("RLC");
                }

            }
        }
    }

    private INode insertRec(INode root , Comparable key , Object value){
        if( root.isNull()){
            root.setKey(key);
            root.setValue(value);
            root.setColor(INode.RED);
            root.setRightChild(new Node());
            root.setLeftChild(new Node());
            root.getRightChild().setParent(root);
            root.getLeftChild().setParent(root);
            this.refNode = root;
            if(this.root == root){
                root.setColor(false);
            }
            return root;
        }
        if(root.getKey().compareTo(key)==0){
            root.setValue(value);
            existKey = true;
            return root;
        }
        else if(root.getKey().compareTo(key)<0){
            root.setRightChild(insertRec(root.getRightChild(),key,value));
        }
        else if(root.getKey().compareTo(key)>0){
            root.setLeftChild(insertRec(root.getLeftChild(),key,value));
        }

        return root;
    }

    public void insert(Comparable key, Object value) {


        if(key !=null  && value !=null)
            this.root=insertRec(this.root,key,value);
        else
            throw new RuntimeErrorException(new Error());

        if(refNode != this.root && !existKey)
            R_B_Proberties(this.refNode);
        else
            existKey =false;

    }
    public boolean delete(Comparable key) {
        if(!contains(key)){
            return false;
        }
        if(key == null)
            throw new RuntimeErrorException(new Error());

        INode temp=findNode(root,key);
        boolean originalColor = temp.getColor();
        root=deleteRec(root,key);
        if(tempRoot!=null){
            root=tempRoot;
            tempRoot=null;
        }
        return true;
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
        {               if (root.getParent()==null &&root==this.getRoot()){
            return null;
        }
            // node with only one child or no child
            if (root.getLeftChild().isNull() || root.getRightChild().isNull()){
                  // a red note to be deleted or black that has a red child
                    if( root.getLeftChild().isNull()&&(root.getRightChild().getColor() || root.getColor()) ){
                        root.getRightChild().setColor(false);
                        root.getRightChild().setParent(root.getParent());
                        return root.getRightChild();
                    }
                    // a red note to be deleted or black that has a red child
                    else if (root.getRightChild().isNull()&&(root.getLeftChild().getColor() || root.getColor())){
                        root.getLeftChild().setColor(false);
                        root.getLeftChild().setParent(root.getParent());
                        return root.getLeftChild();
                    }
                    else{
                        if(root.getLeftChild().isNull()){
                            ((Node)root.getRightChild()).setDoubleBlack(true);}
                        else{
                            ((Node)root.getLeftChild()).setDoubleBlack(true);}
                        if(root==root.getParent().getLeftChild()){
                          if(!root.getParent().getRightChild().getColor()){
                              if( (root.getParent().getRightChild().getLeftChild()==null &&
                                      root.getParent().getRightChild().getRightChild()==null) ||
                                      (root.getParent().getRightChild().getLeftChild().isNull() &&
                                              root.getParent().getRightChild().getRightChild().isNull())) {
                                  //right sibling black with 2 black children (nigga tree)
                                  INode temp=root;
                                  while (temp.getParent()!=null ){
                                      if(temp.getParent().getColor()){
                                          temp.getParent().setColor(false);
                                           temp.getParent().getRightChild().setColor(true);
                                          break;
                                      }
                                      else{
                                          temp.getParent().getRightChild().setColor(true);
                                          temp=temp.getParent();
                                      }
                                  }

                              }
                             else  if(root.getParent().getRightChild().getRightChild()!=null) {
                                  //right sibling black-right child red
                                  if (root.getParent().getRightChild().getRightChild().getColor() == true) {
                                      root.getParent().getRightChild().setColor(root.getParent().getColor());
                                      root.getParent().setColor(false);
                                      root.getParent().getRightChild().getRightChild().setColor(false);
                                      leftRotate(root.getParent());

                                  }

                                  //right sibling black-left child red
                                  else if (root.getParent().getRightChild().getLeftChild()!=null){
                                   if (root.getParent().getRightChild().getLeftChild().getColor() == true) {
                                      root.getParent().getRightChild().getLeftChild().setColor(false);
                                      root.getParent().setColor(false);
                                      rightRotate(root.getParent().getRightChild());
                                      leftRotate(root.getParent());
                                    }
                                   else {
                                       //right sibling black with 2 black children (nigga tree)
                                       INode temp=root;
                                       while (temp.getParent()!=null){
                                           if(temp.getParent().getColor()){
                                               temp.getParent().setColor(false);
                                               temp.getParent().getRightChild().setColor(true);
                                               break;
                                           }
                                           else{
                                               temp.getParent().getRightChild().setColor(true);
                                               temp=temp.getParent();
                                           }
                                       }

                                   }
                                  }
                                  else {
                                      //right sibling black with 2 black children (nigga tree)
                                      INode temp=root;
                                      while (temp.getParent()!=null){
                                          if(temp.getParent().getColor()){
                                              temp.getParent().setColor(false);
                                               temp.getParent().getRightChild().setColor(true);
                                              break;
                                          }
                                          else{
                                              temp.getParent().getRightChild().setColor(true);
                                              temp=temp.getParent();
                                          }
                                      }

                                  }
                              }
                              //right sibling black-left child red
                              else if (root.getParent().getRightChild().getLeftChild()!=null){
                                  if (root.getParent().getRightChild().getLeftChild().getColor() == true) {
                                      root.getParent().getRightChild().getLeftChild().setColor(false);
                                      root.getParent().setColor(false);
                                      rightRotate(root.getParent().getRightChild());
                                      leftRotate(root.getParent());
                                  }
                                  else {
                                      //right sibling black with 2 black children (nigga tree)
                                      INode temp=root;
                                      while (temp.getParent()!=null){
                                          if(temp.getParent().getColor()){
                                              temp.getParent().setColor(false);
                                              temp.getParent().getRightChild().setColor(true);
                                              break;
                                          }
                                          else{
                                              temp.getParent().getRightChild().setColor(true);
                                              temp=temp.getParent();
                                          }
                                      }

                                  }
                              }
                              else {
                                  //right sibling black with 2 black children (nigga tree)
                                  INode temp=root;
                                  while (temp.getParent()!=null){
                                      if(temp.getParent().getColor()){
                                          temp.getParent().setColor(false);
                                          temp.getParent().getRightChild().setColor(true);
                                          break;
                                      }
                                      else{
                                          temp.getParent().getRightChild().setColor(true);
                                          temp=temp.getParent();
                                      }
                                  }

                              }
                            }
                          //right sibling red
                          else if (root.getParent().getRightChild().getColor() == true){
                              root.getParent().getRightChild().setColor(false);
                              root.getParent().setColor(true);
                              leftRotate(root.getParent());

                              // now case 3 occurs : find which sub-case
                              if( (root.getParent().getRightChild().getLeftChild()==null &&
                                      root.getParent().getRightChild().getRightChild()==null) ||
                                      (root.getParent().getRightChild().getLeftChild().isNull() &&
                                              root.getParent().getRightChild().getRightChild().isNull())) {
                                  //right sibling black with 2 black children (nigga tree)
                                  INode temp=root;
                                  while (temp.getParent()!=null ){
                                      if(temp.getParent().getColor()){
                                          temp.getParent().setColor(false);
                                          temp.getParent().getRightChild().setColor(true);
                                          break;
                                      }
                                      else{
                                          temp.getParent().getRightChild().setColor(true);
                                          temp=temp.getParent();
                                      }
                                  }

                              }
                              else  if(root.getParent().getRightChild().getRightChild()!=null) {
                                  //right sibling black-right child red
                                  if (root.getParent().getRightChild().getRightChild().getColor() == true) {
                                      root.getParent().getRightChild().setColor(root.getParent().getColor());
                                      root.getParent().setColor(false);
                                      root.getParent().getRightChild().getRightChild().setColor(false);
                                      leftRotate(root.getParent());

                                  }

                                  //right sibling black-left child red
                                  else if (root.getParent().getRightChild().getLeftChild()!=null){
                                      if (root.getParent().getRightChild().getLeftChild().getColor() == true) {
                                          root.getParent().getRightChild().getLeftChild().setColor(false);
                                          root.getParent().setColor(false);
                                          rightRotate(root.getParent().getRightChild());
                                          leftRotate(root.getParent());
                                      }
                                      else {
                                          //right sibling black with 2 black children (nigga tree)
                                          INode temp=root;
                                          while (temp.getParent()!=null){
                                              if(temp.getParent().getColor()){
                                                  temp.getParent().setColor(false);
                                                  temp.getParent().getRightChild().setColor(true);
                                                  break;
                                              }
                                              else{
                                                  temp.getParent().getRightChild().setColor(true);
                                                  temp=temp.getParent();
                                              }
                                          }

                                      }
                                  }
                                  else {
                                      //right sibling black with 2 black children (nigga tree)
                                      INode temp=root;
                                      while (temp.getParent()!=null){
                                          if(temp.getParent().getColor()){
                                              temp.getParent().setColor(false);
                                              temp.getParent().getRightChild().setColor(true);
                                              break;
                                          }
                                          else{
                                              temp.getParent().getRightChild().setColor(true);
                                              temp=temp.getParent();
                                          }
                                      }

                                  }
                              }
                              //right sibling black-left child red
                              else if (root.getParent().getRightChild().getLeftChild()!=null){
                                  if (root.getParent().getRightChild().getLeftChild().getColor() == true) {
                                      root.getParent().getRightChild().getLeftChild().setColor(false);
                                      root.getParent().setColor(false);
                                      rightRotate(root.getParent().getRightChild());
                                      leftRotate(root.getParent());
                                  }
                                  else {
                                      //right sibling black with 2 black children (nigga tree)
                                      INode temp=root;
                                      while (temp.getParent()!=null){
                                          if(temp.getParent().getColor()){
                                              temp.getParent().setColor(false);
                                              temp.getParent().getRightChild().setColor(true);
                                              break;
                                          }
                                          else{
                                              temp.getParent().getRightChild().setColor(true);
                                              temp=temp.getParent();
                                          }
                                      }

                                  }
                              }
                              else {
                                  //right sibling black with 2 black children (nigga tree)
                                  INode temp=root;
                                  while (temp.getParent()!=null){
                                      if(temp.getParent().getColor()){
                                          temp.getParent().setColor(false);
                                          temp.getParent().getRightChild().setColor(true);
                                          break;
                                      }
                                      else{
                                          temp.getParent().getRightChild().setColor(true);
                                          temp=temp.getParent();
                                      }
                                  }

                              }

                          }



                        }
                        else if(root==root.getParent().getRightChild()){
                            if(!root.getParent().getLeftChild().getColor()){
                                if( (root.getParent().getLeftChild().getLeftChild()==null &&
                                        root.getParent().getLeftChild().getRightChild()==null)
                                 || (root.getParent().getLeftChild().getLeftChild()!=null &&
                                        root.getParent().getLeftChild().getRightChild()!=null) ) {
                                    //left sibling black with 2 black children (nigga tree)
                                    INode temp=root;
                                    while (temp.getParent()!=null){
                                        if(temp.getParent().getColor()){
                                            temp.getParent().setColor(false);
                                             temp.getParent().getLeftChild().setColor(true);
                                            break;
                                        }
                                        else{
                                            temp.getParent().getLeftChild().setColor(true);
                                            temp=temp.getParent();
                                        }
                                    }

                                }
                                if(root.getParent().getLeftChild().getLeftChild()!=null) {
                                    //left sibling black -left child red
                                    if (root.getParent().getLeftChild().getLeftChild().getColor() == true) {
                                        root.getParent().getLeftChild().setColor(root.getParent().getColor());
                                        root.getParent().setColor(false);
                                        root.getParent().getLeftChild().getLeftChild().setColor(false);
                                        rightRotate(root.getParent());

                                    }

                                    //left sibling black-right child red
                                    else if (root.getParent().getLeftChild().getRightChild() != null){
                                     if (root.getParent().getLeftChild().getRightChild().getColor() == true) {
                                        root.getParent().getLeftChild().getRightChild().setColor(false);
                                        root.getParent().setColor(false);
                                        leftRotate(root.getParent().getLeftChild());
                                        rightRotate(root.getParent());


                                    }
                                     else {
                                         //left sibling black with 2 black children (nigga tree)
                                         INode temp=root;
                                         while (temp.getParent()!=null){
                                             if(temp.getParent().getColor()){
                                                 temp.getParent().setColor(false);
                                                 temp.getParent().getLeftChild().setColor(true);
                                                 break;
                                             }
                                             else{
                                                 temp.getParent().getLeftChild().setColor(true);
                                                 temp=temp.getParent();
                                             }
                                         }
                                     }
                                }
                                    else {
                                        //left sibling black with 2 black children (nigga tree)
                                        INode temp=root;
                                        while (temp.getParent()!=null){
                                            if(temp.getParent().getColor()){
                                                temp.getParent().setColor(false);
                                                temp.getParent().getLeftChild().setColor(true);
                                                break;
                                            }
                                            else{
                                                temp.getParent().getLeftChild().setColor(true);
                                                temp=temp.getParent();
                                            }
                                        }
                                    }
                                }
                            }
                            //left sibling red
                            else if (root.getParent().getLeftChild().getColor()){
                                root.getParent().getLeftChild().setColor(false);
                                root.getParent().setColor(true);
                                rightRotate(root.getParent());
                                //case 3 occurs : find which sub-case

                                if( (root.getParent().getLeftChild().getLeftChild()==null &&
                                        root.getParent().getLeftChild().getRightChild()==null)
                                || (root.getParent().getLeftChild().getLeftChild().isNull() &&
                                        root.getParent().getLeftChild().getRightChild().isNull() )) {
                                    //left sibling black with 2 black children (nigga tree)
                                    INode temp=root;
                                    while (temp.getParent()!=null){
                                        if(temp.getParent().getColor()){
                                            temp.getParent().setColor(false);
                                             temp.getParent().getLeftChild().setColor(true);
                                            break;
                                        }
                                        else{
                                            temp.getParent().getLeftChild().setColor(true);
                                            temp=temp.getParent();
                                        }
                                    }

                                }
                                if(root.getParent().getLeftChild().getLeftChild()!=null) {
                                    //left sibling black -left child red
                                    if (root.getParent().getLeftChild().getLeftChild().getColor() == true) {
                                        root.getParent().getLeftChild().setColor(root.getParent().getColor());
                                        root.getParent().setColor(false);
                                        root.getParent().getLeftChild().getLeftChild().setColor(false);
                                        rightRotate(root.getParent());

                                    }

                                    //left sibling black-right child red
                                    else if(root.getParent().getLeftChild().getRightChild()!=null) {
                                        if (root.getParent().getLeftChild().getRightChild().getColor() == true) {
                                            root.getParent().getLeftChild().getRightChild().setColor(false);
                                            root.getParent().setColor(false);
                                            leftRotate(root.getParent().getLeftChild());
                                            rightRotate(root.getParent());


                                        }
                                        else {
                                            //left sibling black with 2 black children (nigga tree)
                                            INode temp=root;
                                            while (temp.getParent()!=null){
                                                if(temp.getParent().getColor()){
                                                    temp.getParent().setColor(false);
                                                    temp.getParent().getLeftChild().setColor(true);
                                                    break;
                                                }
                                                else{
                                                    temp.getParent().getLeftChild().setColor(true);
                                                    temp=temp.getParent();
                                                }
                                            }
                                        }
                                    }
                                    else {
                                        //left sibling black with 2 black children (nigga tree)
                                        INode temp=root;
                                        while (temp.getParent()!=null){
                                            if(temp.getParent().getColor()){
                                                temp.getParent().setColor(false);
                                                temp.getParent().getLeftChild().setColor(true);
                                                break;
                                            }
                                            else{
                                                temp.getParent().getLeftChild().setColor(true);
                                                temp=temp.getParent();
                                            }
                                        }
                                    }
                                }
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

        Random r = new Random();
        int[] x =new int[100];
      for(int i=0;i<100;i++){
          int z=r.nextInt(100);
          tree.insert(z,z);
          x[i]=z;

      }
      for (int i=0;i<100;i++){
          tree.delete(x[i]);
        }
        System.out.println(tree.getRoot());














    }
}
