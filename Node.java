class Node {
    Node next;
    int data;
    int length;

    Node() {
        data = 0;
        length = 0;
        next = null;
    }

    Node(int d) {
        data = d;
        next = null;
        length = 1;
    }

    void append(int d) {
        Node newNode = new Node(d);
        Node n = this;
        while (n.next != null) {
            n = n.next;
        }
        n.next = newNode;
        length++;
    }

    void display() {
        System.out.print(data + "->");
        if (next != null) {
            next.display();
        } else System.out.print("null\n");
    }

    public Node reverse() {
        Node n = this;
        Node prev = null;
        Node current = n;

        while (current != null) {
            Node next = current.next;
            current.next = prev;
            prev = current;
            current = next;
        }
        n = prev;
        return n;
    }
}