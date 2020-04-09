package Tree;

import Tree.INode;

public class Node implements INode {
    INode leftChild=null,rightChild=null,parent=null;
    Object value;
    boolean color=BLACK;
    @Override
    public void setParent(INode parent) {
    this.parent=parent;
    if(parent.getColor()==RED){
        this.setColor(BLACK);
    }
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
        return null;
    }

    @Override
    public void setKey(Comparable key) {

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
        INode x=new Node();
        INode y=new Node();
        x.setParent(y);
        System.out.printf("%s %s %s ",y.getLeftChild(),y.getRightChild(),x.getParent());
    }
}
