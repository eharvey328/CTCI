public class StackQueue {

    public static void main(String[] args) {
        Dog d = new Dog("bill");
        Dog d2 = new Dog("greg");
        Cat c = new Cat("keith");
        Cat c2 = new Cat("meg");

        ShelterQueue sq = new ShelterQueue();

        sq.enqueue(d);
        sq.enqueue(d2);
        sq.enqueue(c);
        sq.enqueue(c2);

        System.out.println(sq.dequeueAny().getName());
        System.out.println(sq.dequeueCats().getName());
    }

    // Last in first out (LIFO)
    public static class Stack {
        Node top;

        Stack() {
            top = null;
        }

        void push(int data) {
            Node newNode = new Node(data);
            newNode.next = top;
            top = newNode;
        }

        Node pop() {
            if (top != null) {
                Node item = new Node(top.data);
                top = top.next;
                return item;
            }
            return null;
        }

        Integer peek() {
            return top.data;
        }

        void display() {
            Node n = top;
            do {
                System.out.print(n.data + "->");
                n = n.next;
            } while (n != null);
            System.out.print("NULL\n");
        }
    }

    //first in first out (FIFO)
    public class Queue {
        Node first, last;

        void enqueue(Node item) {
            if (first == null) {
                last = new Node(item.data);
                first = last;
            } else {
                last.next = new Node(item.data);
                last = last.next;
            }
        }

        Node dequeue() {
            if (first != null) {
                Node item = new Node(first.data);
                first = first.next;
                return item;
            }
            return null;
        }
    }

    //3.2 Stack with min at O(1)
    public static class StackWithMin extends java.util.Stack<Integer> {
        private java.util.Stack<Integer> minStack;

        public StackWithMin() {
            minStack = new java.util.Stack<>();
        }

        public void push(int value) {
            if (value <= min()) {
                minStack.push(value);
            }
            super.push(value);
        }

        public Integer pop() {
            int value = super.pop();
            if (value == min()) {
                minStack.pop();
            }
            return value;
        }

        public int min() {
            if (minStack.isEmpty()) {
                return Integer.MAX_VALUE;
            } else {
                return minStack.peek();
            }
        }

    }

    //3.4 towers of hanoi
    public static void towerMain() {
        int n = 5;
        Tower[] towers = new Tower[n];

        for (int i = 0; i < n; i++) {
            towers[i] = new Tower(i);
        }
        for (int i = n - 1; i >= 0; i--) {
            towers[0].add(i);
        }

        towers[0].moveDiscs(n, towers[2], towers[1]);

    }

    //3.4 towers of hanoi
    public static class Tower {
        private java.util.Stack<Integer> discs;
        private int index;

        public Tower(int i) {
            discs = new java.util.Stack<>();
            index = i;
        }

        public int getIndex() {
            return index;
        }

        public void add(int d) {
            if (!discs.isEmpty() && discs.peek() <= d) {
                System.out.println("Error placing disk " + d);
            } else {
                discs.push(d);
            }
        }

        public void moveTopTo(Tower t) {
            int top = discs.pop();
            t.add(top);
            System.out.println("Move disk " + top + " from " + getIndex() + " to " + t.getIndex());
        }

        public void moveDiscs(int n, Tower destination, Tower buffer) {
            if (n <= 0) return; //base case

            moveDiscs(n - 1, buffer, destination); //move n-1 discs from (this) to buffer (mid)
            moveTopTo(destination); //move disc from (this) to dest
            buffer.moveDiscs(n - 1, destination, this); //move n-1 discs from buffer to dest
        }
    }

    //3.6
    //Ascending sort of a stack using only one stack
    //O(n^2) time and O(n) space
    public static java.util.Stack<Integer> sortStack(java.util.Stack<Integer> stack) {
        java.util.Stack<Integer> sorted = new java.util.Stack<>();

        while (!stack.isEmpty()) {
            int temp = stack.pop();
            while (!sorted.isEmpty() && sorted.peek() > temp) {
                stack.push(sorted.pop());
            }
            sorted.push(temp);
        }

        return sorted;
    }

    public static abstract class Pet {
        private int order;
        String name;

        public Pet(String n) {
            name = n;
        }

        public String getName() {
            return name;
        }

        public void setOrder(int ord) {
            order = ord;
        }

        public int getOrder() {
            return order;
        }

        public boolean isOlderThan(Pet p) {
            return this.order < p.getOrder();
        }
    }

    public static class Dog extends Pet {
        public Dog(String n) {
            super(n);
        }
    }

    public static class Cat extends Pet {
        public Cat(String n) {
            super(n);
        }
    }

    //3.7
    //Queue using linked list
    //using a dequeued by time
    public static class ShelterQueue {
        java.util.LinkedList<Dog> dogs = new java.util.LinkedList<>();
        java.util.LinkedList<Cat> cats = new java.util.LinkedList<>();
        private int order = 0;

        public void enqueue(Pet p) {
            p.setOrder(order);
            order++;

            if (p instanceof Dog) dogs.addLast((Dog) p);
            else if (p instanceof Cat) cats.addLast((Cat) p);
        }

        public Pet dequeueAny() {
            if (dogs.size() == 0) {
                return dequeueCats();
            } else if (cats.size() == 0) {
                return dequeueDogs();
            }

            Dog dog = dogs.peek();
            Cat cat = cats.peek();

            if (dog.isOlderThan(cat)) {
                return dequeueDogs();
            } else {
                return dequeueCats();
            }
        }

        public Pet dequeueDogs() {
            return dogs.poll();
        }

        public Pet dequeueCats() {
            return cats.poll();
        }
    }
}
