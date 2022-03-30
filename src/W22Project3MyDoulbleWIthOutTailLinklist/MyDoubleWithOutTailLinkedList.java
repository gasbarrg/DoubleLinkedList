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

    public void add(Rental r) {
        DNode temp = top;
        System.out.println("r = " + r.toString());
        //If no list, add to top
        if (top == null) {
            top = new DNode(r, null, null);
            System.out.println("SETTING TOP:" + r.toString() + "\n");
            return;
        }

        //Add Games to top of list if closest due date
        if (r instanceof Game && top.getData().getDueBack().after(r.dueBack)) {
            top = new DNode(r, top, null);
            top.getNext().setPrev(top);
            System.out.println("SETTING NEW TOP:" + r.toString() + "\n                " + temp.getData().toString());
            return;
        }


        //Add games to list in order of the closest due date
        else if(r instanceof Game){
            System.out.println("ADDING TO LIST" + r.toString() + "\n" + temp.getData().toString());
            //Search through list until the end, or when new rental due date is after value currently in list
            while(temp.getNext() != null && r.dueBack.after(temp.getData().dueBack)){
                temp = temp.getNext();
                System.out.println(temp.getData());
            }
            //Set next value to r Node
            temp.setNext(new DNode(r, temp.getNext(), temp));
            temp.getNext().setPrev(temp);
        }
    }

    public Rental remove(int index) {

        if (top == null)
            return null;

        DNode temp = top;
        //in you are removing the head of the linked list

        // more code here
        if (index == 0) {
            top = top.getNext();
            return temp.getData();
        }
        if (index < size()) {

            for (int i = 0; i < index; i++) {
                temp = temp.getNext();
            }
            temp.getNext().setPrev(temp.getPrev());
            temp.getPrev().setNext(temp.getNext());

            return temp.getData();
        }
        return null;
    }

    public Rental get(int index) {
        //return null for empty list
        if (top == null)
            return null;
        //Temp node
        DNode temp = top;
        //Loop through list index # of times
        for (int i = 0; i < index; i++) {
            temp = temp.getNext();
        }
        //Return value at index
        return temp.getData();
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