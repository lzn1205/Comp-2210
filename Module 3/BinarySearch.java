import java.util.Arrays;
import java.util.Comparator;

/**
 * Binary search.
 */
public class BinarySearch {

    /**
     * Returns the index of the first key in a[] that equals the search key, 
     * or -1 if no such key exists. This method throws a NullPointerException
     * if any parameter is null.
     */
   public static <Key> int firstIndexOf(Key[] a, Key key, Comparator<Key> comparator) {
      
      if (a == null || key == null || comparator == null) {
         throw new NullPointerException();
      }
      
      int first = 0;
      int last = a.length - 1;
      
      if (comparator.compare(key, a[0]) == 0) {
         return 0;
      }

      while (first <= last) {
      
         int mid = first + (last - first) / 2;
         
         if (comparator.compare(key, a[mid]) < 0) {
            last = mid - 1;
         }    
         
         else if (comparator.compare(key, a[mid]) > 0) {
            first = mid + 1; 
         }
         
         else if (comparator.compare(a[mid - 1], a[mid]) == 0) {
             last = mid - 1;
         }
         
         else {
            
            return mid;
         }
         
      }
      
      return -1; 
   }

    /**
     * Returns the index of the last key in a[] that equals the search key, 
     * or -1 if no such key exists. This method throws a NullPointerException
     * if any parameter is null.
     */
   public static <Key> int lastIndexOf(Key[] a, Key key, Comparator<Key> comparator) {
      
      if (a == null || key == null || comparator == null) {
         throw new NullPointerException();
      }
     
      int first = 0;
      int last = a.length - 1;
      
      if (comparator.compare(key, a[last]) == 0) {
         return last;
      }
      
      while (first <= last) {
      
         int mid = first + (last - first) / 2;
         
         if (comparator.compare(key, a[mid]) < 0) {
            last = mid - 1;
         }    
         
         else if (comparator.compare(key, a[mid]) > 0) {
            first = mid + 1; 
         }
          
         else if (comparator.compare(a[mid + 1], a[mid]) == 0) {
             first = mid + 1;
         }
         
         else {
            
            return mid;
         }
         
      }
      
      return -1;
   }

}