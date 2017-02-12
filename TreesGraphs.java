import java.util.*;
import java.util.LinkedList;

public class TreesGraphs {
    public static void main(String[] args) {
        BST tree = new BST(20);
        tree.insert(8);
        tree.insert(22);
        tree.insert(4);
        tree.insert(12);
        tree.insert(10);
        tree.insert(14);

        System.out.println(tree.getHeight(tree.root));

    }

    public static class BST {
        private TreeNode root;

        public BST() {
            root = null;
        }

        public BST(int value) {
            root = new TreeNode(value);
        }

        public void insert(int value) {
            TreeNode newNode = new TreeNode(value);
            if (root == null) root = newNode;
            else {
                root.insert(value);
            }
        }

        public boolean search(int id) {
            TreeNode current = root;
            while (current != null) {
                if (current.data == id) {
                    return true;
                } else if (current.data > id) {
                    current = current.left;
                } else {
                    current = current.right;
                }
            }
            return false;
        }

        public void inOrderTraversal(TreeNode node) {
            if (node != null) {
                inOrderTraversal(node.left);
                System.out.print(node.data + " ");
                inOrderTraversal(node.right);
            }
        }

        public void preOrderTraversal(TreeNode node) {
            if (node != null) {
                System.out.print(node.data + " ");
                preOrderTraversal(node.left);
                preOrderTraversal(node.right);
            }
        }

        public void postOrderTraversal(TreeNode node) {
            if (node != null) {
                postOrderTraversal(node.left);
                postOrderTraversal(node.right);
                System.out.print(node.data + " ");
            }
        }

        public int getHeight(TreeNode node) {
            if (node == null) return 0;
            return Math.max(getHeight(node.left), getHeight(node.right)) + 1;
        }

        //lowest common ancestor for binary tree
        public static TreeNode LCA(TreeNode n1, TreeNode n2) {
            HashMap<TreeNode, Boolean> ancestors = new HashMap<>();

            while (n1 != null) {
                ancestors.put(n2, Boolean.TRUE);
                n1 = n1.parent;
            }
            while (n2 != null) {
                if (ancestors.containsKey(n2) != ancestors.isEmpty()) return n2;
                n2 = n2.parent;
            }

            return null;
        }

        public static int findLevel(TreeNode root, TreeNode node) {
            if (root == null) return -1;
            if (root.data == node.data) return 0;

            int level = findLevel(root.left, node);

            if (level == -1)
                level = findLevel(root.right, node);
            if (level != -1)
                return level + 1;

            return -1;
        }

        public int distance(TreeNode n1, TreeNode n2) {
            TreeNode node = LCA(n1, n2);
            int distLCA = findLevel(this.root, node);
            int dist1 = findLevel(this.root, n1);
            int dist2 = findLevel(this.root, n2);

            return dist1 + dist2 - 2 * distLCA;
        }

        //lowest common ancestor for binary search tree
        public TreeNode LCA_BST(TreeNode node, int val1, int val2) {
            if (node == null) return null;

            if (node.data > val1 && node.data > val2) {
                return LCA_BST(node.left, val1, val2);
            }

            if (node.data < val1 && node.data < val2) {
                return LCA_BST(node.right, val1, val2);
            }

            return node;
        }

        //4.1 Check if tree is balanced
        //O(N) time and O(H) space, H = height
        public int checkHeight(TreeNode root) {
            if (root == null) return 0; //base case

            //check if left is balanced
            int lHeight = checkHeight(root.left);
            if (lHeight == -1) return -1;

            //check if right is balanced
            int rHeight = checkHeight(root.right);
            if (rHeight == -1) return -1;

            //check if current node is balanced
            int diff = lHeight - rHeight;
            if (Math.abs(diff) > 1) return -1;
            else return Math.max(lHeight, rHeight) + 1; //return height
        }

        public boolean isBalanced() {
            return checkHeight(this.root) != -1;
        }

        //4.3
        //insert middle
        //insert into the left tree the left sub array
        //insert into the right ...
        //recurse
        public TreeNode createMinBST(int arr[], int start, int end) {
            if (end < start) return null;

            int mid = (start + end) / 2;

            TreeNode newNode = new TreeNode(arr[mid]);
            newNode.left = createMinBST(arr, start, mid - 1);
            newNode.right = createMinBST(arr, mid + 1, end);

            return newNode;
        }

        public TreeNode createMinBST(int arr[]) {
            return createMinBST(arr, 0, arr.length - 1);
        }

        //4.4
        //DFS: recursive O(N) time O(logn) space
        public void createLevelLinkedList(TreeNode root, ArrayList<LinkedList<TreeNode>> lists, int level) {
            if (root == null) return;

            LinkedList<TreeNode> list;
            if (lists.size() == level) {
                list = new LinkedList<>();
                lists.add(list);
            } else list = lists.get(level);

            list.add(root);
            createLevelLinkedList(root.left, lists, level + 1);
            createLevelLinkedList(root.right, lists, level + 1);
        }

        public ArrayList<LinkedList<TreeNode>> createLevelLinkedList() {
            ArrayList<LinkedList<TreeNode>> lists = new ArrayList<>();
            createLevelLinkedList(this.root, lists, 0);
            return lists;
        }

        //4.4
        //BSF iterative O(N) time O(1) space
        public ArrayList<LinkedList<TreeNode>> createLevelLinkedList_BFS(TreeNode root) {
            ArrayList<LinkedList<TreeNode>> result = new ArrayList<>();
            LinkedList<TreeNode> current = new LinkedList<>();
            if (root != null) current.add(root);

            while (current.size() > 0) {
                result.add(current);
                LinkedList<TreeNode> parents = current;
                current = new LinkedList<>();
                for (TreeNode node : parents) {
                    if (node.left != null) current.add(node.left);
                    if (node.right != null) current.add(node.right);
                }
            }

            return result;
        }

        //4.5
        //validate BST O(N) time O(logN) space
        public boolean validateBST() {
            return validateBST(this.root, Integer.MIN_VALUE, Integer.MAX_VALUE);
        }

        private boolean validateBST(TreeNode n, int min, int max) {
            if (n == null) return true;
            if (n.data <= min || n.data > max) return false;
            return (validateBST(n.left, min, n.data) || validateBST(n.right, n.data, max));
        }

        //4.6
        //In order successor
        public TreeNode inOrderSucc(TreeNode n) {
            if (n == null) return null;

            if (n.right != null) return leftMostChild(n.right);
            else {
                TreeNode q = n;
                TreeNode x = q.parent;

                while (x != null && x.left != q) {
                    q = x;
                    x = x.parent;
                }
                return x;
            }
        }

        public TreeNode leftMostChild(TreeNode n) {
            if (n == null) return null;

            while (n.left != null) {
                n = n.left;
            }
            return n;
        }

        //4.8
        //search t1 tree for node that matches t2 root
        //compare from that node to see if t1 matches t2
        //O(mn) m & n are the number of nodes in each tree
        //or O(m + kn) where k is the number of matching nodes
        //O(logm + logn) space
        public boolean containsTree(TreeNode t1, TreeNode t2) {
            if (t2 == null) return true;
            return subTree(t1, t2);
        }

        private boolean subTree(TreeNode r1, TreeNode r2) {
            if (r1 == null) return false;
            if (r1.data == r2.data) {
                if (matchTree(r1, r2)) return true;
            }
            return (subTree(r1.left, r2) || subTree(r1.right, r2));
        }

        private boolean matchTree(TreeNode r1, TreeNode r2) {
            if (r2 == null && r1 == null) return true;
            if (r1 == null || r2 == null) return false;
            if (r1.data != r2.data) return false;

            return (matchTree(r1.left, r2.left) && matchTree(r1.right, r2.right));

        }

        //4.9
        //O(nlogn) time O(logn) space
        public void findSum(TreeNode node, int sum) {
            int height = getHeight(node);
            int[] path = new int[height];
            findSum(node, sum, path, 0);
        }

        public void findSum(TreeNode node, int sum, int[] path, int level) {
            if (node == null) return;

            //insert current node into path
            path[level] = node.data;

            //look for paths with a sum that ends at this node
            int t = 0;
            for (int i = level; i >= 0; i--) {
                t += path[i];
                if (t == sum) {
                    printPath(path, i, level);
                }
            }

            //search nodes beneath this one
            findSum(node.left, sum, path, level + 1);
            findSum(node.right, sum, path, level + 1);

            //remove current node from path
            path[level] = Integer.MIN_VALUE;
        }

        public static void printPath(int[] path, int start, int end) {
            for (int i = start; i <= end; i++) {
                System.out.print(path[i] + " ");
            }
            System.out.println();
        }

        public int getDepth(int key)
        {
            TreeNode node = this.root;
            int depth = 0;
            while (node != null)
            {
                if (node.data == key) return depth;
                if (node.data > key) node = node.left;
                else node = node.right;
                depth++;
            }

            return -1;
        }
    }

    public static void BFS(TreeNode root) {
        LinkedList<TreeNode> queue = new LinkedList<>();
        queue.add(root);
        while (!queue.isEmpty()) {
            TreeNode current = queue.remove();
            System.out.println(current.data);
            if (current.left != null) queue.add(current.left);
            if (current.right != null) queue.add(current.right);
        }
    }

    //just in order traversal
    public static void DFS(TreeNode current) {
        if (current.left != null) DFS(current.left);
        System.out.println(current.data);
        if (current.right != null) DFS(current.right);

    }


    /* 4.2

     public enum State {
        UNVISITED, VISITED, VISITING;
    }

    public static boolean BFS(Graph graph, Node start, Node end) {
        LinkedList<Node> queue = new LinkedList<>();

        for (Node v : graph.getNodes()) {
            v.state = State.UNVISITED;
        }

        start.state = State.VISITING;
        queue.add(start);
        Node v;

        while (!queue.isEmpty()) {
            v = queue.removeFirst(); //dequeue
            if (v != null) {
                for (Node u : v.getAdjacent()) {
                    if (u.state == State.UNVISITED) {
                        if (u == end) return true;
                        else {
                            u.state = State.VISITING;
                            queue.add(u);
                        }
                    }
                }
                v.state = State.VISITED;
            }
        }
        return false;
    }
    */
}
