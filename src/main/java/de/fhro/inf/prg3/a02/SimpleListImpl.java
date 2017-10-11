package de.fhro.inf.prg3.a02;

import java.util.Iterator;

/**
 * @author Peter Kurfer
 * Created on 10/6/17.
 */
public class SimpleListImpl implements SimpleList, Iterable {

    private Element head;

    private static class Element { //static so the inner class can't access the outer one
       Object item;
       Element next;

       Element(Object item) { this.item = item; }
    }

    private class SimpleIteratorImpl implements Iterator { //non-static cause has to access head
        private Element element = head;

        @Override
        public boolean hasNext() {
            return element != null;
        }
        @Override
        public Object next(){
            Element tmp = element;

            element = element.next;
            return tmp.item;
        }
    }

    @Override
    public Iterator iterator(){
        return new SimpleIteratorImpl();
    }

    @Override
    public void add(Object item){
        Element tmp = head;

        if (tmp == null) head = new Element(item);
        else {
            while (tmp.next != null) tmp = tmp.next;
            tmp.next = new Element(item);
        }
    }

    @Override
    public int size(){
        Element tmp = head;
        int counter = 1;

        if (tmp == null) return 0;
        while (tmp.next != null) {
            tmp = tmp.next;
            counter ++;
        }
        return counter;
    }

    @Override
    public SimpleList filter(SimpleFilter filter) {
        Iterator iterator = iterator();
        SimpleListImpl retlist = new SimpleListImpl();

       while (iterator.hasNext()) {
           Object tmp = iterator.next();
           if (filter.include(tmp)) retlist.add(tmp);
       }
       return retlist;
    }
}
