import java.util.Arrays;

/**
 * Autocomplete.
 */
public class Autocomplete {
   
   private Term[] terms;

	/**
	 * Initializes a data structure from the given array of terms.
	 * This method throws a NullPointerException if terms is null.
	 */
   public Autocomplete(Term[] terms) {
	   
      if (terms == null) {
         throw new NullPointerException();
      }
     
      this.terms = new Term[terms.length];
      
      for (int i = 0; i < terms.length; i++) {
         this.terms[i] = terms[i];
      }
     
      Arrays.sort(this.terms);
   }

	/** 
	 * Returns all terms that start with the given prefix, in descending order of weight. 
	 * This method throws a NullPointerException if prefix is null.
	 */
   public Term[] allMatches(String prefix) {
	  
      if (prefix == null) {
         throw new NullPointerException();
      }
      
      int a1 = BinarySearch.firstIndexOf(terms, new Term(prefix, -1), Term.byPrefixOrder(prefix.length())); 
      int a2 = BinarySearch.lastIndexOf(terms, new Term(prefix, -1), Term.byPrefixOrder(prefix.length()));
      
      if (a1 == -1) {
        
         return new Term[0];
      }
     
      Term[] a = new Term[1 + a2 - a1];
     
      for (int i = 0; i < a.length; i++) {
         a[i] = terms[a1++];
      }
      
      Arrays.sort(a, Term.byDescendingWeightOrder());
     
      return a;
   }
}