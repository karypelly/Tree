import static org.junit.Assert.*;

import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.lang.reflect.Modifier;
import java.util.Arrays;
import java.util.Iterator;
import java.util.NoSuchElementException;
import java.util.Queue;
import java.util.Random;

import org.junit.FixMethodOrder;
import org.junit.runners.MethodSorters;
import org.junit.Test;

@FixMethodOrder(MethodSorters.NAME_ASCENDING)
public class TreeTest2011 {
	private static Random rng = new Random();

	@Test (timeout = 500)
	public void addRemoveContainsTest1BST_10pts() {
		final int N = 47;
		Tree <Integer>t = new A3BSTree<>();

		assertTrue("contains failed on empty", !t.contains(0));
		for (int i = 0; i < N; i++) {t.add(i);}

		for (int i = 0; i < N; i++)
			assertTrue("contains failed after add: i", t.contains(i));
		
		for (int i = 0; i < N; i+=3) {t.remove(i);} //remove every 3rd
		
		for (int i = 0; i < N; i++) {
			if (i % 3 == 0) assertTrue("contains failed after remove: removed", !t.contains(i));
			else			assertTrue("contains failed after remove: remained", t.contains(i));
		}
	}

	@Test (timeout = 500)
	public void addRemoveContainsTest2AVL_10pts() {
		final int N = 131;
		Tree <Integer>t = new A3AVLTree<>();

		assertTrue("contains failed on empty", !t.contains(0));
		for (int i = 0; i < N; i++) {t.add(i);}

//		Iterator <Integer> iter = t.iterator();
//		int value = 0;
//		while(iter.hasNext()) {
//			System.out.println(iter.next());
//		}

		for (int i = 0; i < N; i++)
			assertTrue("contains failed after add: i = " + i + "; size = " + t.size(), t.contains(i));
		
		for (int i = 0; i < N; i+=3) {t.remove(i);} //remove every 3rd
		
		for (int i = 0; i < N; i++) {
			if (i % 3 == 0) assertTrue("contains failed after remove: removed", !t.contains(i));
			else			assertTrue("contains failed after remove: remained", t.contains(i));
		}
	}

	@Test (timeout = 500)
	public void iteratorTest1_10pts() {
		final int n = 299;
		//Tree <Integer>t = new A3BSTree<>();
		Tree <Integer>t = new A3BSTree<>();
		for (int i = 0; i < n; i++) {t.add(i);}

		Iterator <Integer> iter = t.iterator();
		int value = 0;
		while(iter.hasNext()) {
			assertTrue("BST Iterator: wrong value", iter.next() == value++);
		}

		t = new A3AVLTree<>();
		for (int i = 0; i < n; i++) {t.add(i);}
		iter = t.iterator();
		value = 0;
		while(iter.hasNext()) {
			assertTrue("AVL Iterator: wrong value", iter.next() == value++);
		}
	}

	@Test (timeout = 5000) //should not generate stack oveflows and should not time out
	public void iteratorTest2_10pts() {
		final int n = 1_000_000;
		Tree <Integer>t = new A3AVLTree<>();
		for (int i = 0; i < n; i++) {t.add(i);}

		Iterator <Integer> iter = t.iterator();
		int value = 0;
		while(iter.hasNext()) {
			assertTrue("AVL Iterator: wrong value", iter.next() == value++);
		}
	}

	@Test (timeout = 500)
	public void heightTest_5_pts() {
		Tree <Integer>t = new A3BSTree<>();
		assertTrue("Height wrong", t.height() == -1 || t.height() == 0);
		for (int i = 0; i < 5; i++) {t.add(i);}
		assertTrue("Height wrong", t.height() == 4);

		t = new A3AVLTree<>();
		assertTrue("Height wrong", t.height() == -1 || t.height() == 0);
		for (int i = 0; i < 3; i++) {t.add(i);}
		assertTrue("Height wrong", t.height() == 1 || t.height() == 2);
	}

	@Test (timeout = 500)
	public void sizeTest_5_pts() {
		Tree <Integer>t = new A3BSTree<>();
		assertTrue("Size wrong", t.size() == 0);
		for (int i = 0; i < 5; i++) {t.add(i);}
		assertTrue("Size wrong", t.size() == 5);

		t = new A3AVLTree<>();
		assertTrue("Size wrong", t.size() == 0);
		for (int i = 0; i < 5; i++) {t.add(i);}
		assertTrue("Size wrong", t.size() == 5);
	}

	@Test (timeout = 2000)
	public void sorting_TreeSort1TestDEF_10pts() {
		final int n = 89;
		for (int trial = 0; trial < 100; trial++) {
			Integer[] array = createRandomArrayNoDups(n);
			TreeSort.sort(array);
			assertTrue("Not sorted!", isSorted(array));
		}
	}

	@Test (timeout = 2000)
	public void sorting_TreeSort2TestBST_10pts() {
		final int n = 89;
		for (int trial = 0; trial < 100; trial++) {
			Integer[] array = createRandomArrayNoDups(n);
			Tree <Integer>t = new A3BSTree<>();
			TreeSort.sort(t, array);
			assertTrue("Not sorted!", isSorted(array));
		}
	}

	@Test (timeout = 2000)
	public void sorting_TreeSort3TestAVL_10pts() {
		final int n = 89;
		for (int trial = 0; trial < 100; trial++) {
			Integer[] array = createRandomArrayNoDups(n);
			Tree <Integer>t = new A3AVLTree<>();
			TreeSort.sort(t, array);
			assertTrue("Not sorted!", isSorted(array));
		}
	}

	@Test (timeout = 10000) //should take up to 1s for an AVL tree 
	public void treeBalanceTest1_10pts() {
		final int n = 30000;
		for (int trial = 0; trial < 100; trial++) {
			Integer[] array = createSequentialArrayNoDups(n);
			Tree <Integer>t = new A3AVLTree<>();
			TreeSort.sort(t, array);
			assertTrue("Not sorted!", isSorted(array));
		}
	}

	@Test (timeout = 10000) //should take up to 1s for an AVL tree; 0.5x of the test above
	public void treeBalanceTest2_10pts() {
		final int n = 3_000_000;
		Tree <Integer>t = new A3AVLTree<>();
		for (int i = 0; i < n; i++) {t.add(i);}
	}


	private static Integer [] createRandomArray (int size){
		rng.setSeed(8); //each time, the random sequence will be the same
		Integer [] array = new Integer [size];

		for (int i = 0; i < size; i++) array[i] = rng.nextInt();

		return array;
	}

	private static Integer [] createRandomSortedArray (int size){
		rng.setSeed(8); //each time, the random sequence will be the same
		Integer [] array = new Integer [size];

		for (int i = 0; i < size; i++) array[i] = rng.nextInt();

		Arrays.sort(array);
		return array;
	}

	private static Integer [] createSequentialArrayNoDups (int size){
		Integer [] array = new Integer[size];
		for (int i = 0; i < array.length; i++){
			array[i] = i;
		}
		return array;
	}

	private static Integer [] createRandomArrayNoDups (int size){
		Integer [] array = new Integer[size];
		for (int i = 0; i < array.length; i++){
			array[i] = i;
		}
		shuffleArray(array);
		return array;
	}

	private static void shuffleArray (Integer [] array){
		Random rand = new Random();
		for (int i = 0; i < array.length; i++) {
			int randomIndexToSwap = rand.nextInt(array.length);
			int temp = array[randomIndexToSwap];
			array[randomIndexToSwap] = array[i];
			array[i] = temp;
		}
	}


	private static boolean isSorted(Integer [] array){
		if (array.length <= 1) return true;
		for (int i = 1; i < array.length; i++){
			if (array[i] < array [i-1]) return false;
		}
		return true;
	}

}