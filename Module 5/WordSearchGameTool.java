import java.util.List;
import java.util.LinkedList;
import java.util.SortedSet;
import java.util.TreeSet;
import java.util.Scanner;
import java.util.Iterator;
import java.io.File;
import java.io.FileNotFoundException;

public class WordSearchGameTool implements WordSearchGame {
   private TreeSet<String> lexicon;
   private String[][] board;
   private int boardSize;
   private SortedSet<String> set;
   
   public WordSearchGameTool() {
      lexicon = null;
      boardSize = 4;
      board = new String[boardSize][boardSize];
      board[0][0] = "E"; board[0][1] = "E"; 
      board[0][2] = "C"; board[0][3] = "A"; 
      board[1][0] = "A"; board[1][1] = "L"; 
      board[1][2] = "E"; board[1][3] = "P"; 
      board[2][0] = "H"; board[2][1] = "N"; 
      board[2][2] = "B"; board[2][3] = "O"; 
      board[3][0] = "Q"; board[3][1] = "T"; 
      board[3][2] = "T"; board[3][3] = "Y";
   }
   /**
     * Loads the lexicon into a data structure for later use. 
     * 
     * @param fileName A string containing the name of the file to be opened.
     * @throws IllegalArgumentException if fileName is null
     * @throws IllegalArgumentException if fileName cannot be opened.
     */
   public void loadLexicon(String fileName) {
      if (fileName == null) {
         throw new IllegalArgumentException();
      }
      try {
         lexicon = new TreeSet<String>();
         File file = new File(fileName);
         Scanner scanFile = new Scanner(file);
         while (scanFile.hasNext()) {
            lexicon.add(scanFile.next().toUpperCase());
         }
         scanFile.close();
      }
      catch (FileNotFoundException e) {
         throw new IllegalArgumentException();
      }
   }
   
   /**
     * Stores the incoming array of Strings in a data structure that will make
     * it convenient to find words.
     * 
     * @param letterArray This array of length N^2 stores the contents of the
     *     game board in row-major order. Thus, index 0 stores the contents of board
     *     position (0,0) and index length-1 stores the contents of board position
     *     (N-1,N-1). Note that the board must be square and that the strings inside
     *     may be longer than one character.
     * @throws IllegalArgumentException if letterArray is null, or is  not
     *     square.
     */
     
   public void setBoard(String[] letterArray) {
      if (letterArray == null) {
         throw new IllegalArgumentException();
      }
      int a = (int) Math.sqrt(letterArray.length);
      if ((a * a) != letterArray.length) {
         throw new IllegalArgumentException();
      }
      board = new String[a][a];
      int index = 0;
      for (int a1 = 0; a1 < a; a1++) {
         for (int a2 = 0; a2 < a; a2++) {
            board[a1][a2] = letterArray[index++];
         }
      }
      boardSize = a;
   }
   
   /**
     * Creates a String representation of the board, suitable for printing to
     *   standard out. Note that this method can always be called since
     *   implementing classes should have a default board.
     */
     
   public String getBoard() {
      String strBoard = "";
      for (int b1 = 1; b1 < boardSize; b1++) {
         strBoard += "\n";
         for (int b2 = 0; b2 < boardSize; b2++) {
            strBoard += board[b1][b2] + " ";
         }
      }
      return strBoard;
   }
   
   /**
     * Retrieves all scorable words on the game board, according to the stated game
     * rules.
     * 
     * @param minimumWordLength The minimum allowed length (i.e., number of
     *     characters) for any word found on the board.
     * @return java.util.SortedSet which contains all the words of minimum length
     *     found on the game board and in the lexicon.
     * @throws IllegalArgumentException if minimumWordLength < 1
     * @throws IllegalStateException if loadLexicon has not been called.
     */
     
   public SortedSet<String> getAllScorableWords(int minimumWordLength) {
      if (minimumWordLength < 1) {
         throw new IllegalArgumentException();
      }
      if (lexicon == null) {
         throw new IllegalStateException();
      }
      set = new TreeSet<String>();
      LinkedList<Integer> linkedSet = new LinkedList<Integer>();
      for (int i = 0; i < (boardSize * boardSize); i++) {
         linkedSet.add(i);
         if (isValidWord(toWord(linkedSet))
            && toWord(linkedSet).length() 
            >= minimumWordLength) {
            set.add(toWord(linkedSet));
         }
         if (isValidPrefix(toWord(linkedSet))) {
            wordBoardSearch(linkedSet, minimumWordLength);
         }
         linkedSet.clear();
      }
      return set;
   }
   
   private LinkedList<Integer> wordBoardSearch(LinkedList<Integer> linkedSet,
      int min) {
      Position[] adjArray = new Position(linkedSet.getLast()).adjacent(linkedSet);
      for (Position p : adjArray) {
         if (p == null) {
            break;
         }
         linkedSet.add(p.getIndex());
         if (isValidPrefix(toWord(linkedSet))) {
            if (isValidWord(toWord(linkedSet))
               && toWord(linkedSet).length() >= min) {
               set.add(toWord(linkedSet));
            }
            wordBoardSearch(linkedSet, min);
         }
         else {
            linkedSet.removeLast();
         }
      }
      linkedSet.removeLast();
      return linkedSet;
   }
   
   /**
    * Computes the cummulative score for the scorable words in the given set.
    * To be scorable, a word must (1) have at least the minimum number of characters,
    * (2) be in the lexicon, and (3) be on the board. Each scorable word is
    * awarded one point for the minimum number of characters, and one point for 
    * each character beyond the minimum number.
    *
    * @param words The set of words that are to be scored.
    * @param minimumWordLength The minimum number of characters required per word
    * @return the cummulative score of all scorable words in the set
    * @throws IllegalArgumentException if minimumWordLength < 1
    * @throws IllegalStateException if loadLexicon has not been called.
    */ 
    
   public int getScoreForWords(SortedSet<String> words, int minimumWordLength) {
      if (minimumWordLength < 1) {
         throw new IllegalArgumentException();
      }
      if (lexicon == null) {
         throw new IllegalStateException();
      }
      int score = 0;
      Iterator<String> itr = words.iterator();
      while (itr.hasNext()) {
         String word = itr.next();
         if (word.length() >= minimumWordLength && isValidWord(word)
            && !isOnBoard(word).isEmpty()) {
            score += (word.length() - minimumWordLength) + 1;
         }
      }
      return score;
   }
   
   /**
     * Determines if the given word is in the lexicon.
     * 
     * @param wordToCheck The word to validate
     * @return true if wordToCheck appears in lexicon, false otherwise.
     * @throws IllegalArgumentException if wordToCheck is null.
     * @throws IllegalStateException if loadLexicon has not been called.
     */
     
   public boolean isValidWord(String wordToCheck) {
      if (wordToCheck == null) {
         throw new IllegalArgumentException();
      }
      if (lexicon == null) {
         throw new IllegalStateException();
      }
      return lexicon.contains(wordToCheck);
   }
   
    /**
     * Determines if there is at least one word in the lexicon with the 
     * given prefix.
     * 
     * @param prefixToCheck The prefix to validate
     * @return true if prefixToCheck appears in lexicon, false otherwise.
     * @throws IllegalArgumentException if prefixToCheck is null.
     * @throws IllegalStateException if loadLexicon has not been called.
     */
   
   public boolean isValidPrefix(String prefixToCheck) {
      if (prefixToCheck == null) {
         throw new IllegalArgumentException();
      }
      if (lexicon == null) {
         throw new IllegalStateException();
      }
      String aux = lexicon.ceiling(prefixToCheck);
      if (aux != null) {
         return aux.startsWith(prefixToCheck);
      }
      return false;
   }
   
   /**
     * Determines if the given word is in on the game board. If so, it returns
     * the path that makes up the word.
     * @param wordToCheck The word to validate
     * @return java.util.List containing java.lang.Integer objects with  the path
     *     that makes up the word on the game board. If word is not on the game
     *     board, return an empty list. Positions on the board are numbered from zero
     *     top to bottom, left to right (i.e., in row-major order). Thus, on an NxN
     *     board, the upper left position is numbered 0 and the lower right position
     *     is numbered N^2 - 1.
     * @throws IllegalArgumentException if wordToCheck is null.
     * @throws IllegalStateException if loadLexicon has not been called.
     */
     
    public List<Integer> isOnBoard(String wordToCheck) {
      if (wordToCheck == null) {
         throw new IllegalArgumentException();
      }
      if (lexicon == null) {
         throw new IllegalStateException();
      }
      LinkedList<Integer> list = new LinkedList<Integer>();
      List<Integer> path = boardSearch(wordToCheck, list, 0);
      return path;
   }
   
   /**
    * @param wordToCheck Word to check if found in the board
    * @param listProg Integer list representing the positions of the word in
    * @param posIndex Index of the last letter in the word
    * @return the updated word being built
    */
   
   private LinkedList<Integer> boardSearch(String wordToCheck,
      LinkedList<Integer> listProg, int posIndex) {
      if (listProg.size() > 0 && !wordToCheck.equals(toWord(listProg))) {
         Position[] adjArray = 
            new Position(posIndex).adjacent(listProg);
         for (Position p : adjArray) {
            if (p == null) {
               break;
            }
            listProg.add(p.getIndex());
            if (wordToCheck.equals(toWord(listProg))) {
               break;
            }
            if (wordToCheck.startsWith(toWord(listProg))) {
               boardSearch(wordToCheck, listProg, p.getIndex());
            }
            else {
               listProg.removeLast();
            }
         }
      }
      if (listProg.size() == 0) {
         while (posIndex < (boardSize * boardSize)) {
            if (wordToCheck.startsWith(new Position(posIndex).getLetter())) {
               listProg.add(posIndex);
               boardSearch(wordToCheck, listProg, posIndex);
            }
            posIndex++;
         }
         return listProg;
      }
      if (toWord(listProg).equals(wordToCheck)) {
         return listProg;
      }
      else {
         listProg.removeLast();
         return listProg;
      }
   }
   
   /**
    * Takes an integer list and creates its corresponding word.
    *
    * @param listIn Integer list representing word
    * @return word in string form
    */
   
   public String toWord(LinkedList<Integer> listIn) {
      String word = "";
      for (int i : listIn) {
         word += new Position(i).getLetter();
      }
      return word;
   }
   
   private class Position {
      private int x;
      private int y;
      private int index;
      private String letter;
      private static final int MAX_ADJ = 8;
   
      /**
       * Position constructor.
       * 
       * @param index The index of the position
       */
      Position(int indexIn) {
         this.index = indexIn;
         if (index == 0) {
            this.x = 0;
            this.y = 0;
         }
         else {
            this.x = index % boardSize;
            this.y = index / boardSize;
         }
         this.letter = board[y][x];
      }
      
      /**
       * Position constructor.
       * 
       * @param x The x coordinate of the position
       * @param y The y coordinate of the position
       */
      Position(int xIn, int yIn) {
         this.x = xIn;
         this.y = yIn;
         this.index = (y * boardSize) + x;
         this.letter = board[y][x];
      }
      
      public Position[] adjacent(LinkedList<Integer> visited) {
         Position[] adj = new Position[MAX_ADJ];
         int k = 0;
         for (int i = this.x - 1; i <= this.x + 1; i++) {
            for (int j = this.y - 1; j <= this.y + 1; j++) {
               if (!(i == this.x && j == this.y)) {
                  if (isValid(i, j) && !visited.contains((j * boardSize) + i)) {
                     Position aux = new Position(i, j);
                     adj[k++] = aux;
                  }
               }
            }
         }
         return adj;
      }
      
      public boolean isValid(int xTest, int yTest) {
         return xTest >= 0 && xTest < boardSize && yTest >= 0 && yTest < boardSize;
      }
   
      /**
       * Get position letter.
       * 
       * @return letter
       */
      public String getLetter() {
         return letter;
      }
   
      /**
       * Get position index.
       * 
       * @return index
       */
      public int getIndex() {
         return index;
      }
   }
}