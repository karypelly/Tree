
public class TreeSort{
	/** Sorts an array using TreeSort with a balanced BST implementation 
	 * @param a an array to sort
	 */
	public static <E extends Comparable <? super E>> void sort( E[] a){
		Tree <E> tree = new A3AVLTree<>();
		sort(tree, a);
	}
	
	/**
	 * Sorts an array using TreeSort with a specified BST
	 * @param tree tree to use
	 * @param a an array to sort
	 */
	@SuppressWarnings("unchecked")
	public static <E extends Comparable <? super E>> void sort(Tree <E> tree, E[] a){
		A3AVLTree <Integer> t = new A3AVLTree<>();
		for (int i = 0; i < a.length ; i++) {
			t.add(i);
		}
		for (int i = 0; i < a.length; i++) {
			a[i] = (E) t.inOrder().get(i);
		}
	}
	
	
}
