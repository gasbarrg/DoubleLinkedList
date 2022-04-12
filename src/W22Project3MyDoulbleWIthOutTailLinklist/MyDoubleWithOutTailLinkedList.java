/**
 * @Author Gabe Gasbarre & Justin Burch
 * @Verson 4/11/2022
 */
package W22Project3MyDoulbleWIthOutTailLinklist;
import java.io.Serializable;
import java.util.Random;


public class MyDoubleWithOutTailLinkedList implements Serializable {

    /** Top node object of the list*/
    private DNode top;

    /**
     * Description - Is a constructor for the linked list that sets the top node to null.
     */
    public MyDoubleWithOutTailLinkedList() {
        top = null;
    }

    /**
     * Calculates the size of the current list
     *
     * @return the size of the list. If the list has nothing in it returns 0.
     */

    /******************************************************************
     * Description -Calculates the size of the current list
     *
     * @return the size of the list. If the list has nothing in it returns 0.
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


    /**
     * Description - Clears the current list
     */

    /******************************************************************
     * Description - Clears the current list
     *******************************************************************/
    public void clear() {
        Random rand = new Random(13);
        while (size() > 0) {
            int number = rand.nextInt(size());
            remove(number);
        }
    }

    /**
     * Description - This method adds a new rental to the rental in the correct order of this list.
     *               If the rental is the first ever it will just make it the first rental
     *
     * @param r This variable refers to the object being rented so it holds what date it was rented on,
     *                when the rental needs to be returned, what was rented, name of renter.
     */
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
            //Search through list until the end, or when new rental due date is at or after value currently in list
            while(temp.getNext() != null && !r.dueBack.before(temp.getNext().getData().dueBack)) {
                if (r.dueBack.equals(temp.getData().dueBack))
                    break;  //Break Search if dates are equal
                else
                    temp = temp.getNext();
            }
            //If dates are equal, enter loop:
            if (r.dueBack.equals(temp.getData().dueBack)) {
                int output = r.nameOfRenter.compareTo(temp.getData().nameOfRenter);
                //Move forward until name belongs in list.
                while (output > 0 && temp.getNext() != null && r.dueBack.equals(temp.getData().dueBack)) {
                    temp = temp.getNext();
                    if(!r.dueBack.equals(temp.getData().dueBack)){
                        temp = temp.getPrev();
                        break;}
                    output = r.nameOfRenter.compareTo(temp.getData().nameOfRenter);
                }
                //Move backward until name belongs in list
                while(output <= 0 && temp.getPrev() != null && r.dueBack.equals(temp.getData().dueBack)) {
                    temp = temp.getPrev();
                    output = r.nameOfRenter.compareTo(temp.getData().nameOfRenter);
                }
            }
        }

        //Add consoles after all games:
        if(r instanceof Console) {
            //Start at the beginning of console list
            while (!(temp.getData() instanceof Console) && temp.getNext() != null) {
                temp = temp.getNext();
            }

            //If console due before first console, add to top of list
            if(temp.getData().dueBack.after(r.dueBack)){
                temp = temp.getPrev();
            }
            else {
                //Search through list until the end, or when new rental due date is after value currently in list
                while (temp.getNext() != null
                        && r.dueBack.after(temp.getData().dueBack)
                        && !r.dueBack.before(temp.getNext().getData().dueBack)) {
                    //Break Search if dates are equal
                    if (r.dueBack.equals(temp.getData().dueBack))
                        break;
                    else
                        temp = temp.getNext();
                }
                //If dates are equal, enter loop:
                if (r.dueBack.equals(temp.getData().dueBack)) {
                    int output = r.nameOfRenter.compareTo(temp.getData().nameOfRenter);
                    //Move forward until name belongs in list.
                    while (output > 0 && temp.getNext() != null && r.dueBack.equals(temp.getData().dueBack)) {
                        temp = temp.getNext();
                        if(!r.dueBack.equals(temp.getData().dueBack)){
                            temp = temp.getPrev();
                            break;}
                        output = r.nameOfRenter.compareTo(temp.getData().nameOfRenter);
                    }
                    //Move backward until name belongs in list
                    while(output <= 0 && temp.getPrev() != null && r.dueBack.equals(temp.getData().dueBack)) {
                        temp = temp.getPrev();
                        output = r.nameOfRenter.compareTo(temp.getData().nameOfRenter);
                    }
                }
            }
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
    /**
     * Removes a rental and returns it.
     *
     * @param index Location of the rental to be removed.
     *
     * @return  return the removed Rental data. If no data, return null.
     */
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
    /**
     * Get rental data at location "index"
     *
     * @param index The index refers to what location to retrieve data from
     *
     * @return This method will return the Rental data from the index.
     *         If the list has nothing in it then it will return null.
     */
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


    /**
     * Print entire linked list to terminal
     *
     */
    public void display() {
        DNode temp = top;
        while (temp != null) {
            System.out.println(temp.getData());
            temp = temp.getNext();
        }
    }
    /**
     * Print the top and size of list.
     * @return You are returning the string giving you the top and size.
     */
    public String toString() {
        return "LL {" +
                "top=" + top +
                ", size=" + size() +
                '}';
    }
}


/*

            //If dates are equal, enter loop:
            if (r.dueBack.equals(temp.getData().dueBack)) {
                int output = r.nameOfRenter.compareTo(temp.getData().nameOfRenter);
                while(output < 0 && temp.getPrev() != null && r.dueBack.equals(temp.getData().dueBack)) {
                    if(!r.dueBack.equals(temp.getData().dueBack)){
                        temp = temp.getNext();
                        break;}
                    else
                        temp = temp.getPrev();
                    output = r.nameOfRenter.compareTo(temp.getData().nameOfRenter);
                }
                while (output > 0 && r.dueBack.equals(temp.getData().dueBack) && temp.getNext() != null) {
                    if(!r.dueBack.equals(temp.getData().dueBack)){
                        temp = temp.getPrev();
                        break;}
                    else
                        temp = temp.getNext();
                    output = r.nameOfRenter.compareTo(temp.getData().nameOfRenter);
                    }
            }

 */