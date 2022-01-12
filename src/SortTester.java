import java.util.Arrays;
import java.util.Random;

public class SortTester {
	private static Random rng = new Random();

	public static void main(String[] args) {
		test(10);
		test(100);
		test(1000);
		test(10000);
//		test(30000);
		test(100000);
		test(1000000);
	}

	/**
	 * This method tests the efficiency of heapsort by comparing it to quicksort
	 * 
	 * @param N is the number of elements
	 */
	public static void test(int N) {
		System.out.println("n = " + N + ":");
		Integer[] arraysMergeSort = createRandomArrayNoDups(N);
		Integer[] arrayHeapSort = createRandomArrayNoDups(N);
		Integer[] arrayHeapSort2011 = createRandomArrayNoDups(N);
		Integer[] arrayTreeSort2011 = createRandomArrayNoDups(N);
		int[] arraysQuickSort = new int[N];
		for (int i = 0; i < N; i++) {arraysQuickSort[i] = arraysMergeSort[i];}

		//System.out.printf("Shuffled%n%s%n",Arrays.toString(arraysQuickSort));

		long begin = System.currentTimeMillis();
		Arrays.sort(arraysQuickSort);
		System.out.print("Arrays.Qsort\t" + (System.currentTimeMillis() - begin) + " ms\n");
		
		begin = System.currentTimeMillis();
		Arrays.sort(arraysMergeSort);
		System.out.print("Arrays.Msort\t" + (System.currentTimeMillis() - begin) + " ms\n");

		begin = System.currentTimeMillis();
		A2PQSort.heapSort(arrayHeapSort);
		System.out.print("heapsort\t" + (System.currentTimeMillis() - begin) + " ms\n");

		begin = System.currentTimeMillis();
		A2PQSort.heapSort2011(arrayHeapSort2011);
		System.out.print("heapsort2011\t" + (System.currentTimeMillis() - begin) + " ms\n");

		begin = System.currentTimeMillis();
		TreeSort.sort(arrayTreeSort2011);
		System.out.print("treeSort2011\t" + (System.currentTimeMillis() - begin) + " ms\n");
	}
	
	
	private static Integer [] createRandomArray (int size){
		rng.setSeed(8); //each time, the random sequence will be the same
		Integer [] array = new Integer [size];

		for (int i = 0; i < size; i++) array[i] = rng.nextInt();

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


}