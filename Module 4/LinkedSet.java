import java.util.Iterator;
import java.util.NoSuchElementException;

/**
 * Provides an implementation of the Set interface.
 * A doubly-linked list is used as the underlying data structure.
 * Although not required by the interface, this linked list is
 * maintained in ascending natural order. In those methods that
 * take a LinkedSet as a parameter, this order is used to increase
 * efficiency.
 *
 * @author Dean Hendrix (dh@auburn.edu)
 * @author Long Nguyen (lzn0025@auburn.edu)
 *
 */
public class LinkedSet<T extends Comparable<T>> implements Set<T> {

   //////////////////////////////////////////////////////////
   // Do not change the following three fields in any way. //
   //////////////////////////////////////////////////////////

   /** References to the first and last node of the list. */
   Node front;
   Node rear;

   /** The number of nodes in the list. */
   int size;

   /////////////////////////////////////////////////////////
   // Do not change the following constructor in any way. //
   /////////////////////////////////////////////////////////

   /**
   * Instantiates an empty LinkedSet.
   */
   public LinkedSet() {
      front = null;
      rear = null;
      size = 0;
   }


   //////////////////////////////////////////////////
   // Public interface and class-specific methods. //
   //////////////////////////////////////////////////

   ///////////////////////////////////////
   // DO NOT CHANGE THE TOSTRING METHOD //
   ///////////////////////////////////////
   /**
   * Return a string representation of this LinkedSet.
   *
   * @return a string representation of this LinkedSet
   */
   @Override
   public String toString() {
      if (isEmpty()) {
         return "[]";
      }
      StringBuilder result = new StringBuilder();
      result.append("[");
      for (T element : this) {
         result.append(element + ", ");
      }
      result.delete(result.length() - 2, result.length());
      result.append("]");
      return result.toString();
   }


   ///////////////////////////////////
   // DO NOT CHANGE THE SIZE METHOD //
   ///////////////////////////////////
   /**
    * Returns the current size of this collection.
    *
    * @return  the number of elements in this collection.
    */
   public int size() {
      return size;
   }

    //////////////////////////////////////
    // DO NOT CHANGE THE ISEMPTY METHOD //
    //////////////////////////////////////
    /**
     * Tests to see if this collection is empty.
     *
     * @return  true if this collection contains no elements, false otherwise.
     */
   public boolean isEmpty() {
      return (size == 0);
   }


   /**
    * Ensures the collection contains the specified element. Neither duplicate
    * nor null values are allowed. This method ensures that the elements in the
    * linked list are maintained in ascending natural order.
    *
    * @param  element  The element whose presence is to be ensured.
    * @return true if collection is changed, false otherwise.
    */
   public boolean add(T element) {
      Node n = new Node(element);
      Node current = front;
      
      if (element == null) {
         return false;
      }
      
      if (this.contains(element)) {
         return false;
      }
      
      if (isEmpty()) {
         front = n;
         rear = n;
         rear.next = null;
         size++;
         return true;
      }
      else if (front.element.compareTo(element) > 0) {
         n.next = front;
         front.prev = n;
         front = n;
         size++;
         return true;
      }
      else if (element.compareTo(rear.element) > 0) {
         rear.next = n;
         n.prev = rear;
         rear = n;
         rear.next = null;
         size++;
         return true;
      }
      else {
         Node a = prevNode(element);
         a.next.prev = n;
         n.next = a.next;
         a.next = n;
         n.prev = a;
         size++;
         return true;
      }      
   }

   /**
    * Ensures the collection does not contain the specified element.
    * If the specified element is present, this method removes it
    * from the collection. This method, consistent with add, ensures
    * that the elements in the linked lists are maintained in ascending
    * natural order.
    *
    * @param   element  The element to be removed.
    * @return  true if collection is changed, false otherwise.
    */
   public boolean remove(T element) {
      if (element == null) {
         return false;
      }
      
      if (isEmpty()) {
         return false;
      }
      
      if (!contains(element)) {
         return false;
      }
      
      if (size == 1) {
         front = null;
         rear = null;
         size = 0;
         return true;
      }
      
      Node b = store(element);
      if (b == front) {
         front = front.next;
         front.prev = null;
      }
      else {
         if (b.next != null) {
            b.prev.next = b.next;
            b.next.prev = b.prev;
         }
         else {
            b.prev.next = null;
            rear = b.prev;
         }
      }
      size--;
      return true;   
   }


   /**
    * Searches for specified element in this collection.
    *
    * @param   element  The element whose presence in this collection is to be tested.
    * @return  true if this collection contains the specified element, false otherwise.
    */
   public boolean contains(T element) {
      return store(element) != null;
   }


   /**
    * Tests for equality between this set and the parameter set.
    * Returns true if this set contains exactly the same elements
    * as the parameter set, regardless of order.
    *
    * @return  true if this set contains exactly the same elements as
    *               the parameter set, false otherwise
    */
   public boolean equals(Set<T> s) {
      if (s.size() == size && complement(s).size() == 0) {
         return true;
      }
      return false;
   }


    /**
     * Tests for equality between this set and the parameter set.
     * Returns true if this set contains exactly the same elements
     * as the parameter set, regardless of order.
     *
     * @return  true if this set contains exactly the same elements as
     *               the parameter set, false otherwise
     */
   public boolean equals(LinkedSet<T> s) {
      if (s.size() == size && complement(s).size() == 0) {
         return true;
      }
      return false;
   }


   /**
   * Returns a set that is the union of this set and the parameter set.
   *
   * @return  a set that contains all the elements of this set and the parameter set
   */
   public Set<T> union(Set<T> s){
      
      LinkedSet<T> t = new LinkedSet<T>();
   
      Node d = front;
      while (d != null) {
         t.LinkedAdd(d.element);
         d = d.next;
      }
      
      if (s == null) {
         return t;
      }
      
      Iterator<T> itr = s.iterator();
      
      while (itr.hasNext()) {
         t.add(itr.next());
      }
      return t;
   }


    /**
     * Returns a set that is the union of this set and the parameter set.
     *
     * @return  a set that contains all the elements of this set and the parameter set
     */
   public Set<T> union(LinkedSet<T> s){
      LinkedSet<T> t = new LinkedSet<T>();
      Node e1 = front;
      if (s.isEmpty()) {
         while (e1 != null) {
            t.LinkedAdd(e1.element);
            e1 = e1.next;
         }
         return t;
      }
      
      Node e2 = s.front;
      if (this.isEmpty()) {
         while (e2 != null) {
            t.LinkedAdd(e2.element);
            e2 = e2.next;
         }
         return t;
      }
      
      while (e1 != null && e2 != null) {
         if (e1.element.compareTo(e2.element) > 0) {
            t.LinkedAdd(e2.element);
            e2 = e2.next;
         }
         else if (e1.element.compareTo(e2.element) < 0) {
            t.LinkedAdd(e1.element);
            e1 = e1.next;
         }
         else if (e1.element.compareTo(e2.element) == 0) {
            t.LinkedAdd(e1.element);
            e1 = e1.next;
            e2 = e2.next;      
         }
      }
      
      if (e1 == null) {
         while (e2 != null) {
            t.LinkedAdd(e2.element);
            e2 = e2.next;
         }
      }
      else if (e2 == null) {
         while (e1 != null) {
            t.LinkedAdd(e1.element);
            e1 = e1.next;
         }
      }
      
      return t;
   }


   /**
    * Returns a set that is the intersection of this set and the parameter set.
    *
    * @return  a set that contains elements that are in both this set and the parameter set
    */
   public Set<T> intersection(Set<T> s) {
      LinkedSet<T> t = new LinkedSet<T>();
      
      if (s == null) {
         return t;
      }

      Node f = front;
      while (f != null) {
         if (s.contains((T)f.element)) {
            t.add((T)f.element);
         }
         f = f.next;
      }
      return t;
   }

    /**
     * Returns a set that is the intersection of this set and
     * the parameter set.
     *
     * @return  a set that contains elements that are in both
     *            this set and the parameter set
     */
   public Set<T> intersection(LinkedSet<T> s) {
      LinkedSet<T> t = new LinkedSet<T>();
      if (s.isEmpty()) {
         return t;
      }
      if (this.isEmpty()) {
         return t;
      }
      Node g1 = front;
      Node g2 = s.front;
      while (g1 != null && g2 != null) {
         if (g1.element.compareTo(g2.element) > 0) {
            g2 = g2.next;
         }
         else if (g1.element.compareTo(g2.element) < 0) {
            g1 = g1.next;
         }
         else if (g1.element.compareTo(g2.element) == 0) {
            t.LinkedAdd(g1.element);
            g1 = g1.next;
            g2 = g2.next;      
         }
      }
      return t;
   }


   /**
    * Returns a set that is the complement of this set and the parameter set.
    *
    * @return  a set that contains elements that are in this set but not the parameter set
    */
   public Set<T> complement(Set<T> s) {
      LinkedSet<T> t = new LinkedSet<T>();
      Node h = front;
      if (isEmpty()) {
         return t;
      }
      if (s == null) {
         while (h != null) {
            t.LinkedAdd(h.element);
            h = h.next;
         }
         return t;
      }
      while (h != null) {
         if (!s.contains((T)h.element)) {
            t.add((T)h.element);
         }
         h = h.next;
      }
      return t;
   }

   /**
    * Returns a set that is the complement of this set and
    * the parameter set.
    *
    * @return  a set that contains elements that are in this
    *            set but not the parameter set
    */
   public Set<T> complement(LinkedSet<T> s) {
      Node i1 = front;
      Node i2 = s.front;
      LinkedSet<T> t = new LinkedSet<T>();
      if (s.isEmpty()) {
         while (i1 != null) {
            t.LinkedAdd(i1.element);
            i1 = i1.next;
         }
         return t;
      }
      if (this.isEmpty()) {
         return t;
      }
      while (i1 != null && i2 != null) {
         if (i1.element.compareTo(i2.element) > 0) {
            t.LinkedAdd(i2.element);
            i2 = i2.next;
         }
         else if (i1.element.compareTo(i2.element) < 0) {
            t.LinkedAdd(i1.element);
            i1 = i1.next;
         }
         else if (i1.element.compareTo(i2.element) == 0) {
            i1 = i1.next;
            i2 = i2.next;      
         }
      }
      return t;

   }


    /**
     * Returns an iterator over the elements in this LinkedSet.
     * Elements are returned in ascending natural order.
     *
     * @return  an iterator over the elements in this LinkedSet
     */
   public Iterator<T> iterator() {
      return new LinkedIterator();
   }


   /**
    * Returns an iterator over the elements in this LinkedSet.
    * Elements are returned in descending natural order.
    *
    * @return  an iterator over the elements in this LinkedSet
    */
   public Iterator<T> descendingIterator() {
      return new LinkedDescendingIterator(rear);
   }


    /**
     * Returns an iterator over the members of the power set
     * of this LinkedSet. No specific order can be assumed.
     *
     * @return  an iterator over members of the power set
     */
   public Iterator<Set<T>> powerSetIterator() {
      return new LinkedPowerIterator(this.size());
   }



    //////////////////////////////
    // Private utility methods. //
    //////////////////////////////
   
    // Feel free to add as many private methods as you need.
   private Node store(T element) {
      Node n = front;
      while (n != null) {
         if (n.element.equals(element)) {
            return n;
         }
         n = n.next;
      }
      return null;
   }
   
   private Node prevNode(T element) {
      Node n = front;
      while (n != null) {
         if (n.element.compareTo(element) > 0) {
            return n.prev;
         }
         n = n.next;
      }
      return n;
   }
   
   private boolean LinkedAdd(T element) {
      if (element == null) {
         return false;
      }
      
      Node current = front;
      Node n = new Node(element);
      if (isEmpty()) {
         front = n;
         rear = n;
         rear.next = null;
         size++;
         return true;
      }
      else if (front.element.compareTo(element) > 0) {
         n.next = front;
         front.prev = n;
         front = n;
         size++;
         return true;
      }
      else if (front.element.compareTo(element) < 0) {
         rear.next = n;
         n.prev = rear;
         rear = n;
         rear.next = null;
         size++;
         return true;
      }
      return false;
   }
   
   private class LinkedIterator implements Iterator<T> {
      private Node cur = front;
      
      public boolean hasNext() {
         return cur != null;
      }
      
      public void remove() {
         throw new UnsupportedOperationException();
      }
      
      public T next() {
         if (!hasNext()) {
            throw new NoSuchElementException();
         }
         T a = cur.element;
         cur = cur.next;
         return a;
      }
         
   }
   
   private class LinkedDescendingIterator implements Iterator<T> {
      Node j;
      public LinkedDescendingIterator (Node rear) {
         j = rear;
      }
      
      public boolean hasNext() {
         return j != null && j.element != null;
      }
      
      public void remove() {
         throw new UnsupportedOperationException();
      }
      
      public T next() {
         if (j != null) {
            T a = j.element;
            j = j.prev;
            return a;
         }
         return null;
      }
   }
   
   private class LinkedPowerIterator implements Iterator<Set<T>> {
      Node curNode;
      int num;
      int numSet;
      int cur;
      
      public LinkedPowerIterator (int numElement) {
         curNode = front;
         num = numElement;
         numSet = (int) Math.pow(2,num);
         cur = 0;
      }
      
      public boolean hasNext() {
         return cur < numSet;
      }
      
      public Set<T> next() {
         Set<T> t = new LinkedSet<T>();
         String result = Integer.toBinaryString(cur);
         while (result.length() < num) {
            result = "0" + result;
         }
         for (int i = 0; i < num; i++) {
            if (result.charAt(i) == '1') {
               T element = curNode.element;
               t.add(element);
            }
            curNode = curNode.next;
         }
         curNode = front;
         cur++;
         return t;
      }         
      
      public void remove() {
         throw new UnsupportedOperationException();
      }
   }
    ////////////////////
    // Nested classes //
    ////////////////////
   
    //////////////////////////////////////////////
    // DO NOT CHANGE THE NODE CLASS IN ANY WAY. //
    //////////////////////////////////////////////

    /**
     * Defines a node class for a doubly-linked list.
     */
   class Node {
      /** the value stored in this node. */
      T element;
      /** a reference to the node after this node. */
      Node next;
      /** a reference to the node before this node. */
      Node prev;

      /**
       * Instantiate an empty node.
       */
      public Node() {
         element = null;
         next = null;
         prev = null;
      }

      /**
       * Instantiate a node that containts element
       * and with no node before or after it.
       */
      public Node(T e) {
          element = e;
          next = null;
          prev = null;
      }
   }

}