import java.util.Comparator;

/**
 * Autocomplete term representing a (query, weight) pair.
 * 
 */
public class Term implements Comparable<Term> {
   
   String query;
   long weight;
   
    /**
     * Initialize a term with the given query and weight.
     * This method throws a NullPointerException if query is null,
     * and an IllegalArgumentException if weight is negative.
     */
   public Term(String query, long weight) {
      
      if (query == null) {
         throw new NullPointerException();
      }   
     
      if (weight < 0) {
         throw new IllegalArgumentException();
      }   
      
      this.query = query;
      this.weight = weight;
   }

    /**
     * Compares the two terms in descending order of weight.
     */
   public static Comparator<Term> byDescendingWeightOrder() {
      return new Comparator<Term>() {
         public int compare(Term a1, Term a2) {     
            
            return Long.valueOf(a2.weight).compareTo(Long.valueOf(a1.weight));
         
         }
      
      };
   
    }

    /**
     * Compares the two terms in ascending lexicographic order of query,
     * but using only the first length characters of query. This method
     * throws an IllegalArgumentException if length is less than or equal
     * to zero.
     */
   public static Comparator<Term> byPrefixOrder(int length) {
     
      if (length <= 0) {
         throw new IllegalArgumentException();
      }
      
      return new Comparator<Term>() {
         
         public int compare(Term a1, Term a2) {
            String t1, t2;
            
             if (a1.query.length() < length) {
                 t1 = a1.query;
             }
             else {
                 t1 = a1.query.substring(0, length);
             }
             
             if (a2.query.length() < length) {
                 t2 = a2.query;
             }
             else {
                 t2 = a2.query.substring(0, length);
             }
             
             return t1.compareTo(t2);
         }
         
      };
      
   }

    /**
     * Compares this term with the other term in ascending lexicographic order
     * of query.
     */
   @Override
   public int compareTo(Term other) {
      
      return this.query.compareTo(other.query);
   }

    /**
     * Returns a string representation of this term in the following format:
     * query followed by a tab followed by weight
     */
   @Override
   public String toString(){
      
      return query + "\t" + weight; 
   }

}
