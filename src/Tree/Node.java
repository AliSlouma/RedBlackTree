package Tree;

import Tree.INode;

public class Node implements INode {
    INode leftChild=null,rightChild=null,parent=null;
    Object value;
    Comparable key;
    boolean color=BLACK;
    @Override
    public void setParent(INode parent) {
    this.parent=parent;

    }

    @Override
    public INode getParent() {
        return this.parent;
    }

    @Override
    public void setLeftChild(INode leftChild) {
    this.leftChild=leftChild;
    }

    @Override
    public INode getLeftChild() {
        return this.leftChild;
    }

    @Override
    public void setRightChild(INode rightChild) {
    this.rightChild=rightChild;
    }

    @Override
    public INode getRightChild() {
        return this.rightChild;
    }

    @Override
    public Comparable getKey() {
        return this.key;
    }

    @Override
    public void setKey(Comparable key) {
        this.key=key;
    }

    @Override
    public Object getValue() {
        return this.value;
    }

    @Override
    public void setValue(Object value) {
    this.value=value;
    }

    @Override
    public boolean getColor() {
        return this.color;

    }

    @Override
    public void setColor(boolean color) {
    if (color=true){
        this.color=RED;
    }
    else{
        this.color=BLACK;
    }
    }

    @Override
    public boolean isNull() {
        return false;
    }

    public static void main(String[] args) {

        IRedBlackTree y=new RedBlackTree();


        System.out.printf("%s %s  ",y,y.getRoot());
    }
}
