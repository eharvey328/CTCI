import java.util.ArrayList;
import java.util.Arrays;

public class Sorting {
    public static void main(String[] args) {
        int[][] arr = new int[][]{{15, 20, 40, 85}, {20, 35, 80, 95}};

        matrixSort(arr, 40);
    }

    //mergesort O(nlogn)
    public static void mergeSort(int[] array) {
        int[] helper = new int[array.length];
        mergeSort(array, helper, 0, array.length - 1);
    }

    public static void mergeSort(int[] array, int[] helper, int low, int high) {
        if (low < high) {
            int middle = low + (high - low) / 2;

            mergeSort(array, helper, low, middle);
            mergeSort(array, helper, middle + 1, high);
            merge(array, helper, low, middle, high);
        }
    }

    private static void merge(int[] array, int[] tempArray, int low, int middle, int high) {
        for (int i = low; i <= high; i++) {
            tempArray[i] = array[i];
        }

        int left = low;
        int right = middle + 1;
        int current = low;

        while (left <= middle && right <= high) {
            if (tempArray[left] <= tempArray[right]) {
                array[current] = tempArray[left];
                left++;
            } else {
                array[current] = tempArray[right];
                right++;
            }
            current++;
        }

        while (left <= middle) {
            array[current] = tempArray[left];
            current++;
            left++;
        }
    }

    //mergesort O(nlogn)
    public static void quickSort(int[] array) {
        quickSort(array, 0, array.length - 1);
    }

    public static void quickSort(int[] array, int low, int high) {
        if (low > high) return;
        int index = partition(array, low, high);

        if (low < index - 1) quickSort(array, low, index - 1);
        if (index < high) quickSort(array, index, high);
    }

    public static int partition(int[] array, int low, int high) {
        int pivot = array[(low + high) / 2];

        while (low <= high) {
            while (array[high] > pivot) high--;
            while (array[low] < pivot) low++;

            if (low <= high) {
                swap(array, low, high);
                low++;
                high--;
            }
        }

        return low;
    }

    public static void swap(int[] array, int low, int high) {
        int temp = array[low];
        array[low] = array[high];
        array[high] = temp;
    }

    //binary search (but just use Arrays.binarySearch)
    public static int binarySearch(int[] array, int x) {
        int low = 0;
        int high = array.length - 1;
        int mid;

        while (low <= high) {
            mid = (low + high) / 2;
            if (array[mid] < x) low = mid + 1;
            else if (array[mid] > x) high = mid - 1;
            else return mid;
        }
        return -1; //error
    }

    public static int binarySearch_rec(int[] array, int x) {
        return binarySearch_rec(array, x, 0, array.length - 1);
    }

    public static int binarySearch_rec(int[] array, int x, int low, int high) {
        if (low > high) return -1;

        int mid = (low + high) / 2;

        if (x < array[mid]) return binarySearch_rec(array, x, low, mid - 1);
        else if (x > array[mid]) return binarySearch_rec(array, x, mid + 1, high);
        else return mid;

    }

    //11.1
    //merge 2 arrays where first has room for second (sorted)
    public static void mergeArrays(int[] arr, int[] arr2) {
        mergeArrays(arr, arr2, arr.length - arr2.length, arr2.length);
    }

    private static void mergeArrays(int[] arr, int[] arr2, int last, int last2) {
        int index = last - 1; //last element in arr
        int index2 = last2 - 1; //last element in arr2
        int indexMerged = last2 + last - 1; //end of merged array

        //merge arr and arr2 starting from the last element in each
        while (index >= 0 && index2 >= 0) {
            //end of arr is > end of arr2
            if (arr[index] > arr2[index2]) {
                arr[indexMerged] = arr[index]; //copy
                indexMerged--; //move indices
                index--;
            } else {
                arr[indexMerged] = arr2[index2];
                indexMerged--;
                index2--;
            }
        }

        //copy remaining elements from b into place
        while (index2 >= 0) {
            arr[indexMerged] = arr2[index2];
            indexMerged--;
            index2--;
        }
    }



    //merge two unsroted arrays
    void mergeUnsorted(int mPlusN[], int N[], int m, int n) {
        int i = n;
        /* Current index of i/p part of mPlusN[]*/
        int j = 0;
        /* Current index of N[]*/
        int k = 0;
        /* Current index of of output mPlusN[]*/
        while (k < (m + n)) {
            /* Take an element from mPlusN[] if
            a) value of the picked element is smaller and we have
                not reached end of it
            b) We have reached end of N[] */
            if ((i < (m + n) && mPlusN[i] <= N[j]) || (j == n)) {
                mPlusN[k] = mPlusN[i];
                k++;
                i++;
            } else // Otherwise take element from N[]
            {
                mPlusN[k] = N[j];
                k++;
                j++;
            }
        }
    }

    //move nulls (-1) to end of array
    void moveToEnd(int mPlusN[], int size) {
        int i, j = size - 1;
        for (i = size - 1; i >= 0; i--) {
            if (mPlusN[i] != -1) {
                mPlusN[j] = mPlusN[i];
                j--;
            }
        }
    }


    //11.3
    //search a sorted rotated array for a value
    //use binary search but altered
    //O(logn) time if all elements are unique, O(N) if duplicates
    public static int rotatedBinarySearch(int[] arr, int left, int right, int x) {
        int mid = (left + right) / 2;
        if (x == arr[mid]) return mid;
        if (right < left) return -1;

        if (arr[left] < arr[mid]) { //left normally ordered
            if (x >= arr[left] && x <= arr[mid])
                return rotatedBinarySearch(arr, left, mid - 1, x); //search left;
            else
                return rotatedBinarySearch(arr, mid + 1, right, x); //search right;
        } else if (arr[mid] < arr[left]) { //right normally ordered
            if (x >= arr[mid] && x <= arr[right])
                return rotatedBinarySearch(arr, mid + 1, right, x); //search right;
            else
                return rotatedBinarySearch(arr, left, mid - 1, x); //search left;
        } else if (arr[left] == arr[mid]) { //left is all repeats
            if (arr[mid] != arr[right]) //if right is diff. search it
                return rotatedBinarySearch(arr, mid + 1, right, x); //search right;
            else {
                int result = rotatedBinarySearch(arr, left, mid - 1, x); //search left;
                if (result == -1)
                    return rotatedBinarySearch(arr, mid + 1, right, x); //search right;
                else return result;
            }
        }
        return -1;
    }

    //11.6
    //sort a matrix with columns and rows sorted
    //O(n)
    public static boolean matrixSort(int[][] matrix, int x) {
        int row = 0;
        int col = matrix[0].length - 1;

        while (row < matrix[0].length && col >= 0) {
            if (matrix[row][col] == x) {
                System.out.println(row + ", " + col);
                return true;
            } else if (matrix[row][col] > x)
                col--;
            else
                row++;
        }

        return false;
    }

    //O(nlogm)
    public static boolean matrixSortSimple(int[][] matrix, int x) {
        for (int i = 0; i < matrix.length; i++) {
            int index = Arrays.binarySearch(matrix[i], x);

            if (index > 0) {
                System.out.println(i + " " + index);
                return true;
            }
        }
        return false;
    }


}

