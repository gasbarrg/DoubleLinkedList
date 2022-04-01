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
        //System.out.println("next links = " + total + " prev links = " + totalBack);
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
        printList();
        DNode temp = top;
        //System.out.println("r = " + r.toString());
        //If no list, add to top
        if (top == null) {
            top = new DNode(r, null, null);
            return;
        }

        //Add Games to top of list if closest due date
        if (r instanceof Game && top.getData().getDueBack().after(r.dueBack)) {
            top = new DNode(r, top, null);
            top.getNext().setPrev(top);
            return;
        }

        //Add games to list in order of the closest due date
        else if(r instanceof Game){
            //Search through list until the end, or when new rental due date is after value currently in list
            while(temp.getNext() != null && r.dueBack.after(temp.getData().dueBack)){
                temp = temp.getNext();
            }
            //Set next value to r Node
            temp.setNext(new DNode(r, temp.getNext(), temp));
            //Select new node
            temp = temp.getNext();
            //If node ahead, set prev
            if(temp.getNext() != null)
                temp.getNext().setPrev(temp);
            //If node before, set next
            if(temp.getPrev() != null)
                temp.getPrev().setNext(temp);
        }

        //Add consoles after all games:
        if(r instanceof Console) {
            //Start at the beginning of console list
            while (!(temp.getData() instanceof Console) && temp.getNext() != null) {
                temp = temp.getNext();
            }
            //If no consoles, add to end of list
            if (!(temp.getData() instanceof Console) ) {
                //Set next value to r Node
                temp.setNext(new DNode(r, temp.getNext(), temp));
                //Select new node
                temp = temp.getNext();
                //If node before, set next
                if (temp.getPrev() != null)
                    temp.getPrev().setNext(temp);}
            //If console due before first console, add to top of list
            else if(temp.getData().dueBack.after(r.dueBack)){
                temp = temp.getPrev();
                //Set next value to r Node
                temp.setNext(new DNode(r, temp.getNext(), temp));
                //Select new node
                temp = temp.getNext();
                //If node ahead, set prev
                if (temp.getNext() != null)
                    temp.getNext().setPrev(temp);
                //If node before, set next
                if (temp.getPrev() != null)
                    temp.getPrev().setNext(temp);
            }
            else {
                //Search through list until the end, or when new rental due date is after value currently in list
                while (temp.getNext() != null && r.dueBack.after(temp.getData().dueBack)) {
                    temp = temp.getNext();
                }
                //Set next value to r Node
                temp.setNext(new DNode(r, temp.getNext(), temp));
                //Select new node
                temp = temp.getNext();
                //If node ahead, set prev
                if (temp.getNext() != null)
                    temp.getNext().setPrev(temp);
                //If node before, set next
                if (temp.getPrev() != null)
                    temp.getPrev().setNext(temp);
            }
        }


    }

    public Rental remove(int index) {
        if (top == null || get(index) == null)
            return null;
        //Temp node
        DNode temp = top;
        //Get node @ index
        for (int i = 0; i < index; i++) {
            temp = temp.getNext();
        }
        //If head is node to be deleted:
        if (temp == top) {
            top = top.getNext();
        }
        //If node @ index is not the last, change next
        if(temp.getNext() != null)
            temp.getNext().setPrev(temp.getPrev());
        //if node @ index is not the first, change prev
        if(temp.getPrev() != null)
            temp.getPrev().setNext(temp.getNext());


        return temp.getData();
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


    public void printList() {
        DNode temp = top;
        if(temp != null && temp.getNext() != null)
            do{
                System.out.print(temp.getNext() + "\n");
                temp = temp.getNext();
            } while(temp.getNext() != null);

        System.out.println();
    }
}



/*
         REMOVE METHOD PREVIOUS CODE
        //in you are removing the head of the linked list
        if (index == 0) {
            if(size() > 1) {
                temp = top.getNext();
                top.getNext().setPrev(null);
                top.getNext().setNext(null);
                top = temp;
                top.setPrev(null);
            }
            else if(size() == 1) {
                System.out.println("Size == 1");
                top = null;
            }
            else
                top = temp.getNext();

            return temp.getData();
        }

        for (int i = 0; i < index; i++) {
            temp = temp.getNext();
        }

        if (index < size() - 1 ) {
            System.out.println("HERE");
            System.out.println(temp.toString());
            temp.getNext().setPrev(temp.getPrev());
            temp.getPrev().setNext(temp.getNext());
            return temp.getData();
        }
        return null;
 */