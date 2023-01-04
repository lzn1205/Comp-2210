/**
 * Implements shift-right behavior in an array.
 *
 */

public class ShiftRight {


   /**
    * Shifts the elements at a[index] through a[a.length - 2] one
    * position to the right. 
    */
   public static void shiftRight(Object[] array, int index) {   
      int arraySize = array.length;
      Object lastElement = array[arraySize - 1];
      for (int i = arraySize - 1; i > index; i--) {
         array[i] = array[i - 1];   
      }
      array[index] = lastElement;
   }

}