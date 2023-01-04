import java.util.Comparator;
public class Provider {
   public static int search(Object[] a, Object target) {
      for (int i = 0; i < a.length; i++) {
         if (a[i].equals(target)) {
            return i;
         }
      }
      return -1;
   }
   
   public static <T> int search1(T[] a, T target) {
      for (int i = 0; i < a.length; i++) {
         if (a[i].equals(target)) {
            return i;
         }
      }
      return -1;
   }
   
   public static <T extends Comparable<T>> T min(T[] a) {
      T min = a[0];
      for (T element: a) {
         if (element.compareTo(min) < 0) {
            min = element;
         }
      }
      return min;
   }
   
   public static <T> T min(T[] a, Comparator<T> comp) {
      T min = a[0];
      for (T element: a) {
         if (comp.compare(element, min) < 0) {
            min = element;
         }
      }
      return min;
   }
   
   
}