package Tree;

import Tree.INode;

public class Node implements INode {
    INode leftChild=null,rightChild=null,parent=null;
    Object value = null;
    Comparable key =null;
    boolean color;
    boolean doubleBlack=false;



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
        if(this==null){
            return false;
        }
        return this.color;

    }

    @Override
    public void setColor(boolean color) {
    if (color==true){
        this.color=RED;
    }
    else{
        this.color=BLACK;
    }
    }

    @Override
    public boolean isNull() {
        if(this.key == null && this.value == null)
            return true;
        return false;
    }
    public boolean isDoubleBlack() {
        return doubleBlack;
    }

    public void setDoubleBlack(boolean doubleBlack) {
        this.doubleBlack = doubleBlack;
    }

}
