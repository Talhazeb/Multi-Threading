package multi_threading;

/**
 * A binary search tree on Strings. Reference:
 * http://www.cs.cornell.edu/courses/cs2110/2012sp/lectures/09/BST.java
 */
public class BST {

   TreeCell<String> root; // The root of the BST

   /**
    * Constructor.
    */
   public BST() {
      root = null;
   }

   /**
    * Insert. Nothing happens if the string is already in the BST.
    */
   public void insert(String string) {
      root = insert(string, root);
   }

   private static TreeCell<String> insert(String string, TreeCell<String> node) {
      if (node == null)
         return new TreeCell<String>(string);
      int compare = string.compareTo(node.datum);
      if (compare < 0)
         node.left = insert(string, node.left);
      else if (compare > 0)
         node.right = insert(string, node.right);
      return node;
   }

   /**
    * Show the contents of the BST in alphabetical order.
    */
   public void show() {
      show(root);
      System.out.println();
   }

   private static void show(TreeCell<String> node) {
      if (node == null)
         return;
      show(node.left);
      System.out.print(node.datum + " ");
      show(node.right);
   }

   public Boolean compare(String token) {

      if (root.datum.compareTo(token) == 0)
         return true;
      else {
         TreeCell<String> node = root;
         while (node != null) {
            if (node.datum.compareTo(token) == 0)
               return true;
            else if (token.compareTo(node.datum) < 0) {
               node = node.left;
            } else if (token.compareTo(node.datum) > 0) {
               node = node.right;
            }
         }
         return false;
      }

   }

   public String toString() {
      return toString("", root);
   }

   private static String toString(String prefix, TreeCell<String> node) {
      if (node == null)
         return "";
      String string = prefix + node.datum.toString();
      if (node.right != null)
         string = toString("    " + prefix, node.right) + "\n" + string;
      if (node.left != null)
         string = string + "\n" + toString("    " + prefix, node.left);
      return string;
   }
}

/**
 * TreeCell
 */
class TreeCell<T> {

   T datum;
   TreeCell<T> left, right;

   /**
    * Constructor
    */
   public TreeCell() {
      left = null;
      right = null;
   }

   public TreeCell(T datum) {
      this.datum = datum;
      left = null;
      right = null;
   }
}