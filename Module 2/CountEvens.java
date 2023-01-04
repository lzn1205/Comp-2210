/**
 * Count the number of even values in an array.
 *
 */
public class CountEvens {

    //  C O M P L E T E   T H I S    M E T H O D 

    /**
     * Returns the number of even values in the paramter.
     */
    public static int countEvens(int[] values) {
        int count = 0;
        for (int a : values) {
            if (a % 2 == 0) {
                count++;
            }
        }
        return count;
    }
    
    public static int negativeCount(int[] val) {
      int count = 0;
      for (int element: val) {
         if (element < 0) {
            count++;
         }
      }
      return count;
    }

}
