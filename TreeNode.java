public class TreeNode {
    public int data;
    public TreeNode left;
    public TreeNode right;
    public TreeNode parent;

    public TreeNode() {
        data = 0;
        left = null;
        right = null;
        parent = null;
    }

    public TreeNode(int d) {
        data = d;
        left = null;
        right = null;
        parent = null;
    }

    public void insert(int value) {
        TreeNode newNode = new TreeNode(value);

        if (value < this.data) {
            if (left == null) {
                newNode.parent = this;
                left = newNode;
            } else left.insert(value);
        } else if (value > this.data) {
            if (right == null) {
                newNode.parent = this;
                right = newNode;
            } else right.insert(value);
        }
    }
}