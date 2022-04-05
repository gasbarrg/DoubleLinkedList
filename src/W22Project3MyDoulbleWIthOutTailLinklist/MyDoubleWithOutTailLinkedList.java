package W22Project3MyDoulbleWIthOutTailLinklist;
import java.io.Serializable;
import java.util.Random;


public class MyDoubleWithOutTailLinkedList implements Serializable {

    /** Top node object of the list*/
    private DNode top;

    /** */
    public MyDoubleWithOutTailLinkedList() {
        top = null;
    }

    /******************************************************************
     * Calculates the size of the current list
     *
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
            while(temp.getNext() != null
                    && r.dueBack.after(temp.getData().dueBack)
                    && !r.dueBack.before(temp.getNext().getData().dueBack)) {
                if (r.dueBack.equals(temp.getData().dueBack)){
                    break;}
                else
                    temp = temp.getNext();
            }
            int output = r.nameOfRenter.compareTo(temp.getData().nameOfRenter);
            if (output < 0 && temp.getPrev()!= null) {
                temp = temp.getPrev();
            }
            else if(output > 0 && temp.getNext()!= null)
                while(output > 0 && r.dueBack.equals(temp.getData().dueBack) && temp.getNext() != null){
                    temp = temp.getNext();
                    output = r.nameOfRenter.compareTo(temp.getData().nameOfRenter);
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

        //Add consoles after all games:
        if(r instanceof Console) {
            //Start at the beginning of console list
            while (!(temp.getData() instanceof Console) && temp.getNext() != null) {
                temp = temp.getNext();
            }
            //If no consoles, add to end of list
            if (!(temp.getData() instanceof Console) ) {
                //Set next value to r Node
                temp.setNext(new DNode(r, null, temp));
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
                while (temp.getNext() != null && r.dueBack.after(temp.getData().dueBack) && !r.dueBack.before(temp.getNext().getData().dueBack)) {
                    temp = temp.getNext();
                }
                //Set next node to r value
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
//Handle Equals method:
            String tempName = temp.getData().nameOfRenter;
            String newName = r.nameOfRenter;
            int output = tempName.compareTo(newName);
            if(size() < 2){
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
                return;
            }
            else if(output < 0 ){
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
            else{
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

 */

/*
                if(r.dueBack.equals(temp.getData().dueBack)) {
                    String tempName = temp.getData().nameOfRenter;
                    String newName = r.nameOfRenter;
                    int output = tempName.compareTo(newName);
                    if (output > 0 && temp != top) {
                        System.out.println(r + "\n due before\n" + temp.getData());
                        temp = temp.getPrev();
                    }
                    break;
 */