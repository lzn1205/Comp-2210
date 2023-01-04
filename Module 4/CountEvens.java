/**
 * Count the number of even values in a chain of linked nodes.
 *
 */
public class CountEvens {

    //  C O M P L E T E   T H I S    M E T H O D 

    /**
     * Returns the number of even values in the paramter.
     */
    public int countEvens(Node firstNode) {
      int count = 0;
      Node a = firstNode;
      while (a != null) {
         if (a.value % 2 == 0) {
            count++;
         }
         a = a.next;
      }
      return count;
   }
      

    class Node {
        int value;
        Node prev;
        Node next;

        public Node(int val) {
            value = val;
            prev = null;
            next = null;
        }
    }

}

