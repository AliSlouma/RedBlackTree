package Tree;

import javax.management.RuntimeErrorException;
import java.nio.MappedByteBuffer;
import java.util.*;

public class TreeMap implements ITreeMap {

    RedBlackTree treemap ;
    int size=0;
    Set <Map.Entry>set = new LinkedHashSet<>();
    Set <Comparable>keySet = new LinkedHashSet<>();
    ArrayList<Map.Entry> arr = new ArrayList<>();
    Map.Entry lastEntry= null;


    public TreeMap() {
        this.treemap = new RedBlackTree();
    }

    public Map.Entry ceilingEntry(Comparable key) {
        Object found = treemap.search(key);
        if(found!=null){

            Map.Entry<Comparable , Object> entry =  new AbstractMap.SimpleEntry<Comparable, Object>(key, found);
            return entry;
        }
        treemap.Pre_Succ(treemap.getRoot(),key);
        INode suc = treemap.getSuc();
        Map.Entry<Comparable , Object> entry =  new AbstractMap.SimpleEntry<Comparable, Object>(key, suc.getValue());
        return entry;
    }

    @Override
    public Comparable ceilingKey(Comparable key) {
        Map.Entry entry =ceilingEntry(key);
        return (Comparable) entry.getKey();

    }

    @Override
    public void clear() {
        size=0;
        this.treemap.clear();
    }

    @Override
    public boolean containsKey(Comparable key) {
        return treemap.contains(key);
    }

    @Override
    public boolean containsValue(Object value) {
        if(value ==null)
            throw new RuntimeErrorException(new Error());
        ArrayList <Object> values = (ArrayList<Object>) values();

       return values.contains(value);

    }

    private void inorder(INode node)
    {
        if (node.isNull())
            return;

        inorder(node.getLeftChild());
        set.add(new AbstractMap.SimpleEntry(node.getKey(),node.getValue()));
        keySet.add(node.getKey());
        lastEntry = new AbstractMap.SimpleEntry(node.getKey(),node.getValue());
        inorder(node.getRightChild());
    }

    public Set<Map.Entry> entrySet()
    {
        set.clear();
        keySet.clear();
        inorder(treemap.getRoot());
        return this.set;
    }

    @Override
    public Map.Entry firstEntry() {
        if(size == 0)
            return null;
        entrySet();
        Iterator<Map.Entry> itr1 = this.set.iterator();
        return itr1.next();
    }

    @Override
    public Comparable firstKey() {
        if(size ==0){
            return null;
        }
        return (Comparable) firstEntry().getKey();
    }

    @Override
    public Map.Entry floorEntry(Comparable key) {
        Object found = treemap.search(key);
        if(found!=null){

            Map.Entry<Comparable , Object> entry =  new AbstractMap.SimpleEntry<Comparable, Object>(key, found);
            return entry;
        }
        treemap.Pre_Succ(treemap.getRoot(),key);
        INode pre = treemap.getPre();
        Map.Entry<Comparable , Object> entry =  new AbstractMap.SimpleEntry<Comparable, Object>(key, pre.getValue());
        return entry;
    }

    @Override
    public Comparable floorKey(Comparable key) {
        Map.Entry entry =floorEntry(key);
        return (Comparable) entry.getKey();
    }

    @Override
    public Object get(Comparable key) {
        Object object =treemap.search(key);
        return object;
    }

    @Override
    public ArrayList<Map.Entry> headMap(Comparable toKey) {
        return headMap(toKey,false);
    }

    @Override
    public ArrayList<Map.Entry> headMap(Comparable toKey, boolean inclusive) {
        if(size ==0){
            return null;
        }
        set.clear();
        keySet.clear();
        inorder(treemap.getRoot());
        ArrayList<Map.Entry> arrayList = new ArrayList<>();
        Iterator<Map.Entry> itr1 = this.set.iterator();
        while (itr1.hasNext()) {
            Map.Entry entry = itr1.next();
            Comparable k = (Comparable) entry.getKey();
            if (k.compareTo(toKey) < 0) {
                arrayList.add(new AbstractMap.SimpleEntry<Comparable, Object>((Comparable) entry.getKey(), entry.getValue()));
            }else if (inclusive && itr1.hasNext()) {
                Map.Entry entry2 = itr1.next();
                Comparable k2 = (Comparable) entry.getKey();
                if (k.compareTo(toKey) == 0) {
                    arrayList.add(new AbstractMap.SimpleEntry<Comparable, Object>((Comparable) entry.getKey(), entry.getValue()));
                }
            }else break;
        }
        return arrayList;
    }

    @Override
    public Set keySet() {
        if(size ==0){
            return null;
        }
        set.clear();
        keySet.clear();
        inorder(treemap.getRoot());
        return this.keySet;
    }

    @Override
    public Map.Entry lastEntry() {
        if(size ==0){
            return null;
        }
        set.clear();
        keySet.clear();
        inorder(treemap.getRoot());
        return this.lastEntry;
    }

    @Override
    public Comparable lastKey() {
        if(size ==0){
            return null;
        }
        return (Comparable) lastEntry().getKey();
    }

    @Override
    public Map.Entry pollFirstEntry(){
        Map.Entry entry = firstEntry();
        if(entry == null){
            return null;
        }
        this.set.remove(entry);
      //  treemap.delete((Comparable) entry.getKey());
        remove((Comparable) entry.getKey());

        return entry;
    }

    @Override
    public Map.Entry pollLastEntry() {
        Map.Entry entry = lastEntry();

        if(lastEntry == null){
            return null;
        }
        lastEntry = null;
       // treemap.delete((Comparable) entry.getKey());
        remove((Comparable) entry.getKey());

        return entry;
    }

    @Override
    public void put(Comparable key, Object value) {
        if(!containsKey(key)){
            size++;
        }
        treemap.insert(key,value);

    }

    @Override
    public void putAll(Map map) {
        if(map ==null)
            throw new RuntimeErrorException(new Error());
        map.forEach((k,v) -> treemap.insert((Comparable)k,v));
        size+= map.size();
    }

    @Override
    public boolean remove(Comparable key) {
        this.size--;
       return treemap.delete(key);

       // return false;
    }

    @Override
    public int size() {
        return this.size;
    }

    @Override
    public Collection values() {
        if(size ==0){
            return null;
        }
        set.clear();
        keySet.clear();
        inorder(treemap.getRoot());
        ArrayList<Object> values = new ArrayList<>();
        Iterator<Map.Entry> itr1 = this.set.iterator();
        while (itr1.hasNext()) {
            Map.Entry entry = itr1.next();
                values.add(entry.getValue());
        }
        return values;
    }
}
