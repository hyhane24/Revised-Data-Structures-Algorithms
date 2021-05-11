import java.util.Comparator;
import java.util.LinkedList;
import java.util.List;
import java.util.PriorityQueue;
import java.util.Random;

/**
 * Your implementation of various sorting algorithms.
 *
 * @author Hane Yie
 * @version 1.0
 * @userid hyie3
 * @GTID 903441211
 * <p>
 * Collaborators: I worked on this assignment alone
 * <p>
 * Resources: Course modules, pseudo-codes, student shared JUnits
 */
public class Sorting {

    /**
     * Implement selection sort.
     * <p>
     * It should be:
     * in-place
     * unstable
     * not adaptive
     * <p>
     * Have a worst case running time of:
     * O(n^2)
     * <p>
     * And a best case running time of:
     * O(n^2)
     *
     * @param <T>        data type to sort
     * @param arr        the array that must be sorted after the method runs
     * @param comparator the Comparator used to compare the data in arr
     * @throws java.lang.IllegalArgumentException if the array or comparator is
     *                                            null
     */
    public static <T> void selectionSort(T[] arr, Comparator<T> comparator) {
        if (arr == null || comparator == null) {
            throw new IllegalArgumentException("Array or comparator null");
        } else {
            for (int i = 0; i < arr.length; i++) {
                int index = i;
                for (int j = i + 1; j < arr.length; j++) {
                    if (comparator.compare(arr[index], arr[j]) > 0) {
                        index = j;
                    }
                }
                T array = arr[i];
                arr[i] = arr[index];
                arr[index] = array;
            }
        }
    }

    /**
     * Implement cocktail sort.
     * <p>
     * It should be:
     * in-place
     * stable
     * adaptive
     * <p>
     * Have a worst case running time of:
     * O(n^2)
     * <p>
     * And a best case running time of:
     * O(n)
     * <p>
     * NOTE: See pdf for last swapped optimization for cocktail sort. You
     * MUST implement cocktail sort with this optimization
     *
     * @param <T>        data type to sort
     * @param arr        the array that must be sorted after the method runs
     * @param comparator the Comparator used to compare the data in arr
     * @throws java.lang.IllegalArgumentException if the array or comparator is
     *                                            null
     */
    public static <T> void cocktailSort(T[] arr, Comparator<T> comparator) {
        if (arr == null || comparator == null) {
            throw new IllegalArgumentException("Array or comparator null");
        } else {
            int f = 0;
            int l = arr.length - 1;
            boolean swapped = true;
            while (swapped && l >= f) {
                int index = 0;
                swapped = false;
                if (!swapped) {
                    for (int i = f; i < l; i++) {
                        if (comparator.compare(arr[i], arr[i + 1]) > 0) {
                            T array = arr[i];
                            arr[i] = arr[i + 1];
                            arr[i + 1] = array;
                            swapped = true;
                            index = i;
                        }
                    }
                }
                l = index;
                if (swapped) {
                    swapped = false;
                    for (int i = l; i > f; i--) {
                        if (comparator.compare(arr[i], arr[i - 1]) < 0) {
                            T array = arr[i];
                            arr[i] = arr[i - 1];
                            arr[i - 1] = array;
                            swapped = true;
                            index = i;
                        }
                    }
                    f = index;
                } else {
                    return;
                }
            }
        }
    }

    /**
     * Implement merge sort.
     * <p>
     * It should be:
     * out-of-place
     * stable
     * not adaptive
     * <p>
     * Have a worst case running time of:
     * O(n log n)
     * <p>
     * And a best case running time of:
     * O(n log n)
     * <p>
     * You can create more arrays to run merge sort, but at the end, everything
     * should be merged back into the original T[] which was passed in.
     * <p>
     * When splitting the array, if there is an odd number of elements, put the
     * extra data on the right side.
     * <p>
     * Hint: If two data are equal when merging, think about which subarray
     * you should pull from first
     *
     * @param <T>        data type to sort
     * @param arr        the array to be sorted
     * @param comparator the Comparator used to compare the data in arr
     * @throws java.lang.IllegalArgumentException if the array or comparator is
     *                                            null
     */
    public static <T> void mergeSort(T[] arr, Comparator<T> comparator) {
        if (arr == null || comparator == null) {
            throw new IllegalArgumentException("Array or comparator null");
        } else {
            if (arr.length <= 1) {
                return;
            } else {
                T[] arrL = (T[]) new Object[arr.length / 2];
                T[] arrR = (T[]) new Object[arr.length - (arr.length / 2)];
                for (int i = 0; i < arr.length / 2; i++) {
                    arrL[i] = arr[i];
                }
                for (int i = arr.length / 2; i < arr.length; i++) {
                    arrR[i - (arr.length / 2)] = arr[i];
                }
                mergeSort(arrL, comparator);
                mergeSort(arrR, comparator);
                int left = 0;
                int right = 0;
                while (left != arr.length / 2 && right
                        != arr.length - (arr.length / 2)) {
                    if (comparator.compare(arrL[left], arrR[right]) <= 0) {
                        arr[left + right] = arrL[left];
                        left++;
                    } else {
                        arr[left + right] = arrR[right];
                        right++;
                    }
                }
                while (left < arr.length / 2) {
                    arr[left + right] = arrL[left];
                    left++;
                }
                while (right < arr.length - arr.length / 2) {
                    arr[left + right] = arrR[right];
                    right++;
                }
            }
        }
    }

    /**
     * Implement quick sort.
     * <p>
     * Use the provided random object to select your pivots. For example if you
     * need a pivot between a (inclusive) and b (exclusive) where b > a, use
     * the following code:
     * <p>
     * int pivotIndex = rand.nextInt(b - a) + a;
     * <p>
     * If your recursion uses an inclusive b instead of an exclusive one,
     * the formula changes by adding 1 to the nextInt() call:
     * <p>
     * int pivotIndex = rand.nextInt(b - a + 1) + a;
     * <p>
     * It should be:
     * in-place
     * unstable
     * not adaptive
     * <p>
     * Have a worst case running time of:
     * O(n^2)
     * <p>
     * And a best case running time of:
     * O(n log n)
     * <p>
     * Make sure you code the algorithm as you have been taught it in class.
     * There are several versions of this algorithm and you may not receive
     * credit if you do not use the one we have taught you!
     *
     * @param <T>        data type to sort
     * @param arr        the array that must be sorted after the method runs
     * @param comparator the Comparator used to compare the data in arr
     * @param rand       the Random object used to select pivots
     * @throws java.lang.IllegalArgumentException if the array or comparator or
     *                                            rand is null
     */
    public static <T> void quickSort(T[] arr, Comparator<T> comparator,
                                     Random rand) {
        if (arr == null || comparator == null || rand == null) {
            throw new IllegalArgumentException("Array or "
                    + "comparator or rand null");
        } else {
            quickHelper(arr, comparator, rand, 0, arr.length - 1);
        }
    }

    /**
     * Quick sort Helper method
     *
     * @param arr        T array
     * @param comparator comparator T comparator
     * @param rand       random
     * @param l          left integer
     * @param r          right integer
     * @param <T>        method type
     */

    private static <T> void quickHelper(T[] arr,
                                        Comparator<T> comparator, Random rand,
                                        int l, int r) {
        if (l < r) {
            int pivotInd = rand.nextInt(r - l + 1) + l;
            T array = arr[pivotInd];
            arr[pivotInd] = arr[l];
            arr[l] = array;
            int a = l + 1;
            int b = r;
            while (a <= b) {
                while (a <= b && comparator.compare(arr[a], array) <= 0) {
                    a++;
                }
                while (a <= b && comparator.compare(arr[b], array) >= 0) {
                    b--;
                }
                if (a < b) {
                    T ar = arr[a];
                    arr[a] = arr[b];
                    arr[b] = ar;
                    a++;
                    b--;
                } else {
                    break;
                }
            }
            T t = arr[b];
            arr[b] = arr[l];
            arr[l] = t;
            quickHelper(arr, comparator, rand, l, b - 1);
            quickHelper(arr, comparator, rand, b + 1, r);
        }
    }

    /**
     * Implement LSD (least significant digit) radix sort.
     * <p>
     * Make sure you code the algorithm as you have been taught it in class.
     * There are several versions of this algorithm and you may not get full
     * credit if you do not implement the one we have taught you!
     * <p>
     * Remember you CANNOT convert the ints to strings at any point in your
     * code! Doing so may result in a 0 for the implementation.
     * <p>
     * It should be:
     * out-of-place
     * stable
     * not adaptive
     * <p>
     * Have a worst case running time of:
     * O(kn)
     * <p>
     * And a best case running time of:
     * O(kn)
     * <p>
     * You are allowed to make an initial O(n) passthrough of the array to
     * determine the number of iterations you need. The number of iterations
     * can be determined using the number with the largest magnitude.
     * <p>
     * At no point should you find yourself needing a way to exponentiate a
     * number; any such method would be non-O(1). Think about how how you can
     * get each power of BASE naturally and efficiently as the algorithm
     * progresses through each digit.
     * <p>
     * Refer to the PDF for more information on LSD Radix Sort.
     * <p>
     * You may use ArrayList or LinkedList if you wish, but it may only be
     * used inside radix sort and any radix sort helpers. Do NOT use these
     * classes with other sorts. However, be sure the List implementation you
     * choose allows for stability while being as efficient as possible.
     * <p>
     * Do NOT use anything from the Math class except Math.abs().
     *
     * @param arr the array to be sorted
     * @throws java.lang.IllegalArgumentException if the array is null
     */
    public static void lsdRadixSort(int[] arr) {
        if (arr == null) {
            throw new IllegalArgumentException("Array is null");
        } else {
            LinkedList<Integer>[] list =
                    (LinkedList<Integer>[]) new LinkedList[19];
            int max = arr[0];
            int size = 0;
            for (int i = 0; i < arr.length; i++) {
                if (arr[i] > max) {
                    max = Math.abs(arr[i]);
                }
            }
            while (max > 0) {
                size++;
                max = max / 10;
            }
            for (int i = 0; i < 19; i++) {
                list[i] = new LinkedList<>();
            }
            int num = 1;
            for (int i = 0; i < size; i++) {
                for (int j = 0; j < arr.length; j++) {
                    if (list[(arr[j] / num) % 10 + 9] == null) {
                        list[(arr[j] / num) % 10 + 9] = new LinkedList<>();
                    }
                    list[(arr[j] / num) % 10 + 9].add(arr[j]);
                }
                int index = 0;
                for (int k = 0; k < list.length; k++) {
                    if (list[k] != null) {
                        for (int a : list[k]) {
                            arr[index++] = a;
                        }
                        list[k].clear();
                    }
                }
                num = num * 10;
            }

        }
    }

    /**
     * Implement heap sort.
     * <p>
     * It should be:
     * out-of-place
     * unstable
     * not adaptive
     * <p>
     * Have a worst case running time of:
     * O(n log n)
     * <p>
     * And a best case running time of:
     * O(n log n)
     * <p>
     * Use java.util.PriorityQueue as the heap. Note that in this
     * PriorityQueue implementation, elements are removed from smallest
     * element to largest element.
     * <p>
     * Initialize the PriorityQueue using its build heap constructor (look at
     * the different constructors of java.util.PriorityQueue).
     * <p>
     * Return an int array with a capacity equal to the size of the list. The
     * returned array should have the elements in the list in sorted order.
     *
     * @param data the data to sort
     * @return the array with length equal to the size of the input list that
     * holds the elements from the list is sorted order
     * @throws java.lang.IllegalArgumentException if the data is null
     */
    public static int[] heapSort(List<Integer> data) {
        if (data == null) {
            throw new IllegalArgumentException("Data is null");
        } else {
            PriorityQueue<Integer> q = new PriorityQueue<>(data);
            int[] array = new int[data.size()];
            int index = 0;
            boolean full = true;
            while (full) {
                if (q.isEmpty()) {
                    break;
                } else {
                    array[index++] = q.peek();
                    q.remove();
                }
            }
            return array;
        }
    }
}
