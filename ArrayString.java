import java.util.ArrayList;
import java.util.Objects;

public class ArrayString {

    public static void main(String[] args) {
        char[][] matrix = {
                {'f', 'f', 'f', 'f', 'a'},
                {'f', 'f', 'f', 'p', 'f'},
                {'f', 'f', 'p', 'f', 'f'},
                {'f', 'l', 'f', 'f', 'f'},
                {'e', 'f', 'f', 'f', 'f'}};

        ArrayList<String> words = new ArrayList<>();
        words.add("apple");

        ArrayList<String> matches = wordSearch(matrix, words);
        matches.forEach(System.out::println);

    }

    public static ArrayList<String> wordSearch(char[][] matrix, ArrayList<String> words) {
        ArrayList<String> result = new ArrayList<>();
        ArrayList<String> diagonals = traverseDiagonals(matrix);

        for(String word : words) {
            for(String diagonal : diagonals) {
                if(word.equals(diagonal) || diagonal.contains(word) )
                    result.add(word);
            }
        }

        return result;
    }

    private static ArrayList<String> traverseDiagonals(char[][] matrix) {
        ArrayList<String> diagonals = new ArrayList<>();
        StringBuilder diagonalUp, diagonalDown;

        int iteration = 0;
        int maxCount = matrix.length + matrix[0].length - 1;
        int topRow = 0, column = 0;
        int bottomRow = matrix.length - 1;


        while (iteration < maxCount) {
            diagonalUp = new StringBuilder(getDiagonalUp(matrix, topRow, column));
            diagonalDown = new StringBuilder(getDiagonalDown(matrix, bottomRow, column));

            diagonals.add(diagonalUp.toString());
            diagonals.add(diagonalUp.reverse().toString());
            diagonals.add(diagonalDown.toString());
            diagonals.add(diagonalDown.reverse().toString());

            if (topRow < matrix.length - 1 || bottomRow > 0) {
                topRow++;
                bottomRow--;
            } else if (column < matrix[0].length - 1) {
                column++;
            }
            iteration++;
        }

        return diagonals;
    }

    private static String getDiagonalUp(char[][] matrix, int row, int column) {
        StringBuilder sb = new StringBuilder();
        while (row >= 0 && column < matrix[0].length) {
            sb.append(matrix[row][column]);
            row--;
            column++;
        }
        return sb.toString();
    }

    private static String getDiagonalDown(char[][] matrix, int row, int column) {
        StringBuilder sb = new StringBuilder();
        while (row < matrix.length && column < matrix[0].length) {
            sb.append(matrix[row][column]);
            row++;
            column++;
        }
        return sb.toString();
    }

    public static int balencedPar(String str) {
        final Character L_PAR = '(';
        final Character R_PAR = ')';
        Character next;
        int count = 0;

        java.util.Stack<Character> store = new java.util.Stack<>();
        for (int i = 0; i < str.length(); ++i) {
            next = str.charAt(i);
            if (next == L_PAR) store.push(next);
            else if ((next == R_PAR) && (!store.empty())) {
                store.pop();
                count++;
            } else if ((next == R_PAR) && (store.empty())) {
                return -1;
            }
        }
        return count;
    }

    //1.1
    //check using hashset
    public static boolean uniqueString(String s) {
        java.util.HashSet<Character> hash = new java.util.HashSet<>();

        for (Character c : s.toCharArray()) {
            if (!hash.add(c)) return false;
        }
        return true;
    }

    //1.1
    //fill array length of all ASCII chars with false
    //check each char in the string convert to ascii int val
    //check if val in boolArray is true (already match)
    public static boolean uniqueStringUsingArray(String str) {
        boolean[] boolArray = new boolean[256];
        for (int i = 0; i < str.length(); i++) {
            int val = str.charAt(i);
            if (boolArray[val]) {
                return false;
            }
            boolArray[val] = true;
        }
        return true;
    }

    //1.2
    //stringbuilder used since faster than stringbuffer (via async) and no need for threadsafe
    public static String reverseString(String str) {
        StringBuilder reverse = new StringBuilder();
        for (int i = str.length() - 1; i >= 0; i--) {
            reverse.append(str.charAt(i));
        }
        return reverse.toString();
    }

    //1.3 solution #1
    //Anagrams have same characters in different order
    //Check if same after sorting
    private static String sortString(String str) {
        char[] charArray = str.toCharArray();
        java.util.Arrays.sort(charArray);
        return new String(charArray);
    }

    public static boolean permCheck(String s1, String s2) {
        return s1.length() == s2.length() && sortString(s1).equals(sortString(s2));
    }

    //1.3 solution #2
    //Count how many times each char appears
    //compare the two arrays
    public static boolean permCheck2(String s1, String s2) {
        if (s1.length() != s2.length()) return false;

        int letters[] = new int[256];
        char charArray[] = s1.toCharArray();
        for (char c : charArray) {
            letters[c]++;
        }

        for (int i = 0; i < s2.length(); i++) {
            int charVal = (int) s2.charAt(i);
            if (--letters[charVal] < 0) {
                return false;
            }
        }

        return true;
    }

    //1.5
    //copy each character to a new string and count the repeats
    //O(N) size and O(N) space
    public static String compressString(String str) {
        int size = countCompression(str);
        if (size >= str.length()) {
            return str;
        }
        StringBuilder compressed = new StringBuilder();
        char temp = str.charAt(0);
        int count = 1;

        for (int i = 1; i < str.length(); i++) {
            if (str.charAt(i) == temp) {
                count++;
            } else {
                compressed.append(temp);
                compressed.append(count);
                temp = str.charAt(i);
                count = 1;
            }
        }

        compressed.append(temp);
        compressed.append(count);
        return compressed.toString();
    }

    //check if compressed is smaller than orig
    public static int countCompression(String str) {
        if (str == null || str.isEmpty()) return 0;
        char last = str.charAt(0);
        int size = 0;
        int count = 1;

        for (int i = 1; i < str.length(); i++) {
            if (str.charAt(i) == last) count++;
            else {
                last = str.charAt(i);
                size += 1 + String.valueOf(count).length(); //digit count
                count = 1;
            }
        }
        size += 1 + String.valueOf(count).length();
        return size;
    }

    public static int[][] rotateMatrix(int[][] matrix, int flag) {
        int m = matrix.length;
        int n = matrix[0].length;
        int[][] ret = new int[n][m];

        for (int r = 0; r < m; r++) {
            for (int c = 0; c < n; c++) {
                if (flag == 0) ret[m - 1 - c][r] = matrix[r][c];
                else if (flag == 1) ret[c][m - 1 - r] = matrix[r][c];
                else throw new Error("Invalid");
            }
        }
        return ret;
    }


    //1.7
    public static void setToZero(int[][] matrix) {
        int M = matrix.length;
        int N = matrix[0].length;

        boolean row[] = new boolean[M];
        boolean col[] = new boolean[N];

        for (int i = 0; i < M; i++) {
            for (int j = 0; j < N; j++) {
                if (matrix[i][j] == 0) {
                    row[i] = true;
                    col[j] = true;
                }
            }
        }

        for (int i = 0; i < M; i++) {
            for (int j = 0; j < N; j++) {
                if (row[i] || col[j]) {
                    matrix[i][j] = 0;
                }
            }
        }
    }

    //1.8
    public static boolean isRotation(String s1, String s2) {
        if (s1.length() != s2.length()) return false;

        int length = s1.length();
        int offset = 0;
        for (int i = 0; i < length; i++) {
            if (s1.charAt(0) == s2.charAt(i)) {
                offset = i;
                break;
            }
        }

        for (int i = 0; i < length; i++) {
            if (s1.charAt(i) == s2.charAt(offset)) {
                offset++;
                offset %= length; //don't go past size limit
            } else return false;
        }

        return true;
    }

    public static boolean isRotationBook(String s1, String s2) {
        if (s1.length() != s2.length()) return false;

        String s1Double = s1 + s1;
        return s1Double.contains(s2);
    }


    public static void findFirst(String str) {
        java.util.HashMap<Character, Integer> map = new java.util.HashMap<>();

        for (char c : str.toCharArray()) {
            if (map.containsKey(c)) {
                map.put(c, map.get(c) + 1);
            } else {
                map.put(c, 1);
            }
        }

        for (java.util.Map.Entry<Character, Integer> entry : map.entrySet()) {
            if (entry.getValue() == 1) {
                System.out.println(entry.getKey());
                break;
            }
        }
    }

    public static void oddOccurence(int[] arr) {
        java.util.HashMap<Integer, Integer> map = new java.util.HashMap<>();

        for (int i : arr) {
            if (map.containsKey(i)) {
                map.put(i, map.get(i) + 1);
            } else {
                map.put(i, 1);
            }
        }

        for (java.util.Map.Entry<Integer, Integer> item : map.entrySet()) {
            if (item.getValue() % 2 != 0) System.out.print(item.getKey());
        }
    }

    //17.1
    //swap values in place
    public static void inplaceSwap(int a, int b) {
        a = a - b;
        b = a + b;
        a = b - a;
    }

    //17.2
    //NxN tic tac toe winner check
    public char TTTWon(char[][] board) {
        int N = board.length;
        int row;
        int col;

        //check rows
        for (row = 0; row < N; row++) {
            if (board[row][0] != ' ') {
                for (col = 1; col < N; col++) {
                    if (board[row][col] != board[row][col - 1]) break;
                }
                if (col == N) return board[row][0];
            }
        }

        //check cols
        for (col = 0; col < N; col++) {
            if (board[0][col] != ' ') {
                for (row = 1; row < N; row++) {
                    if (board[row][col] != board[row - 1][col]) break;
                }
                if (row == N) return board[0][col];
            }
        }

        //check diagonal top left to bottom right
        if (board[0][0] != ' ') {
            for (row = 1; row < N; row++) {
                if (board[row][row] != board[row - 1][row - 1]) break;
            }
            if (row == N) return board[0][0];
        }

        //check diagonal top left to bottom right
        if (board[N - 1][0] != ' ') {
            for (row = 1; row < N; row++) {
                if (board[N - row - 1][row] != board[N - row][row - 1]) break;
            }
            if (row == N) return board[N - 1][0];
        }

        return ' ';
    }

    //17.6
    //get max sum of values in an array
    public static int getMaxSum(int[] arr) {
        int maxSum = 0;
        int sum = 0;
        for (int i : arr) {
            sum += i;
            if (maxSum < sum) maxSum = sum;
            else if (sum < 0) sum = 0;
        }
        return maxSum;
    }

    public static String reverseWords(String sentence) {
        String[] parts = sentence.split(" ");

        StringBuilder builder = new StringBuilder();
        builder.append(parts[parts.length - 1]);

        for (int i = parts.length - 2; i >= 0; --i) {
            builder.append(" ").append(parts[i]);
        }

        return builder.toString();
    }
}