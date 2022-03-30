package W22Project3MyDoulbleWIthOutTailLinklist;

import java.io.Serializable;
import java.util.Random;

public class MyDoubleWithOutTailLinkedList implements Serializable {

    /** Top node object of the list*/
    private DNode top;

    //NODE REFERENCE
/*    public DNode(Rental data, DNode next, DNode prev) {
        super();
        this.data = data;
        this.next = next;
        this.prev = prev;
    }*/


    public MyDoubleWithOutTailLinkedList() {
        top = null;
    }

    /******************************************************************
     * Calculates the size of the current list
     * DO NOT MODIFY
     * @return the size of the list.
     *******************************************************************/
    public int size() {
        if (top == null)
            return 0;

        int total = 0;
        DNode temp = top;
        while (temp != null) {
            total++;
            temp = temp.getNext();
        }

        int totalBack = 0;
        temp = top;
        while (temp.getNext() != null) {
            temp = temp.getNext();
        }

        while (temp != null) {
            totalBack++;
            temp = temp.getPrev();
        }

        if (total != totalBack) {
            System.out.println("next links " + total + " do not match prev links " + totalBack);
            throw new RuntimeException();
        }

        return total;

    }

    /******************************************************************
     * Clears the current list
     * DO NOT MODIFY
     *******************************************************************/
    public void clear() {
        Random rand = new Random(13);
        while (size() > 0) {
            int number = rand.nextInt(size());
            remove(number);
        }
    }

    public void add(Rental s) {
        DNode temp = top;

        // no list
        if (top == null) {
            top = new DNode(s, null, null);
            return;
        }

        // s is a Game, and s goes on top
        if (s instanceof Game && top.getData().getDueBack().after(s.dueBack)) {
            top = new DNode(s, top, null);
            top.getNext().setPrev(top);
            return;
        }

    }

    public Rental remove(int index) {

        if (top == null)
            return null;

        DNode temp = top;

    // more code here

            return null;

    }

    public Rental get(int index) {

        if (top == null)
            return null;

        return top.getData();  // this line will need to be changed
    }

    public void display() {
        DNode temp = top;
        while (temp != null) {
            System.out.println(temp.getData());
            temp = temp.getNext();
        }
    }

    public String toString() {
        return "LL {" +
                "top=" + top +
                ", size=" + size() +
                '}';
    }

}