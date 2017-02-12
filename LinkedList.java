import java.util.HashSet;

public class LinkedList {
    public static void main(String[] args) {
        Node list = new Node(1);
        list.append(2);
        list.append(3);
        list.append(4);

        Node list2 = new Node(3);
        list2.append(4);
        list2.append(6);


        System.out.println(getIntersection(list, list2).data);
    }

    //2.1
    // O(N): N = number of nodes in list
    // iterate through the list, adding each element to a hash set
    // if we discover a duplicate, we remove the element (list assigns previous list the next node)
    public static void removeDupes(Node list) {
        java.util.HashSet<Integer> set = new java.util.HashSet<>();
        Node previous = null;
        while (list != null) {
            if (set.add(list.data)) {
                previous = list;
            } else {
                previous.next = list.next;
            }
            list = list.next;
        }
    }

    //O(1) Space, O(n^2) time
    public static void removeDupesRunner(Node head) {
        Node current = head;
        while (current != null) {
            Node runner = current;
            while (runner.next != null) {
                if (runner.next.data == current.data) {
                    runner.next = runner.next.next;
                } else {
                    runner = runner.next;
                }
            }
            current = current.next;
        }
    }


    //2.2
    // recursive O(N)
    // recurse until last element (list == null) then unwind
    // during that unwinding, if i == given index print it
    // else increment i and return it
    public static int nthToLastRecursive(Node list, int index) {
        if (list == null) {
            return 0;
        }
        int i = nthToLastRecursive(list.next, index) + 1;
        if (i == index) {
            System.out.println(list.data);
        }

        return i;
    }

    //2.2
    //iterative O(N) time and O(1) space
    //using two pointers p1 is at head, p2 is {{index}} away from head
    //once p2 reaches the end p1 will be {{index}} from the tail.
    public static int nthToLastIterative(Node list, int index) {
        if (index < 0) return -1;

        Node p1 = list;
        Node p2 = list;

        for (int i = 0; i < index - 1; i++) {
            if (p2 == null) return -1;
            p2 = p2.next;
        }

        while (p2.next != null) {
            p1 = p1.next;
            p2 = p2.next;
        }

        return p1.data;
    }

    //2.2 my own simple solution
    public static int kthToLast(Node list, int k) {
        int length = 0;
        Node temp = list;
        while (temp != null) {
            length++;
            temp = temp.next;
        }

        for (int i = 0; i < (length - k); i++) {
            list = list.next;
        }

        return list.data;
    }

    //2.3
    //Only given node to delete
    //copy next to current
    public static boolean deleteNodeAt(Node n) {
        if (n == null || n.next == null) return false;

        Node next = n.next;
        n.next = next.next;
        n.data = next.data;
        return true;
    }


    //2.4
    //iterator through the list and insert into appropriate new list
    //once finished, merge
    public static Node partitionList(Node list, int value) {
        Node beforeList = null;
        Node afterList = null;

        //partition list
        while (list != null) {
            Node next = list.next; //store temp since modifying list as we iter
            if (list.data < value) {
                // insert node into end of before list
                list.next = beforeList;
                beforeList = list;
            } else {
                list.next = afterList;
                afterList = list;
            }
            list = next;
        }

        if (beforeList == null) return afterList;

        Node head = beforeList;
        while (beforeList.next != null) {
            beforeList = beforeList.next;
        }
        beforeList.next = afterList;

        return head;
    }

    //2.5 my own
    public static Node listAdd(Node num1, Node num2) {
        checkLargerLength(num1, num2);
        Node sum = new Node(-1);

        while (num1 != null) {
            int value = num1.data + num2.data;

            if (value >= 10) {
                int remainder = value % 10;
                int carry = (value - remainder) / 10;
                num1.next.data += carry;
                value = remainder;
            }

            if (sum.data == -1) {
                sum.data = value;
            } else {
                sum.append(value);
            }

            num1 = num1.next;
            num2 = num2.next;
        }

        return sum;
    }

    public static void checkLargerLength(Node list1, Node list2) {
        if (list1.length > list2.length)
            appendZeros(list2, (list1.length - list2.length));
        else if (list2.length > list1.length)
            appendZeros(list1, (list2.length - list1.length));
    }

    public static void appendZeros(Node list, int difference) {
        for (int i = 0; i < difference; i++) {
            list.append(0);
        }
    }

    //2.6 - detected beginning of loop in a list
    //move fastPointer at 2k and slowPointer at k pace
    //when they collide, move slowPointer to the head. keep fastPointer
    //move pointers at k each, new collide is loop start.
    public static Node findFirstInLoop(Node head) {
        Node slow = head;
        Node fast = head;

        while (fast != null && fast.next != null) {
            slow = slow.next;
            fast = fast.next.next;
            if (slow == fast) {
                //collision
                break;
            }
        }

        //check if no meeting point (no loop)
        if (fast == null || fast.next == null) {
            return null;
        }

        //move slow back to head
        slow = head;
        //loop until collide taking 1 step each
        while (slow != fast) {
            slow = slow.next;
            fast = fast.next;
        }

        return fast;
    }

    //2.7 - check if list is a palindrome
    //use fast/slow runner technique to find middle
    //check if first half matches second half
    public static boolean isPalindrome(Node head) {
        Node fast = head;
        Node slow = head;

        java.util.Stack<Integer> stack = new java.util.Stack<>();

        while (fast != null && fast.next != null) {
            stack.push(slow.data);
            slow = slow.next;
            fast = fast.next.next;
        }

        //has odd number of elements
        if (fast != null) {
            slow = slow.next;
        }

        while (slow != null) {
            int top = stack.pop();
            if (top != slow.data) {
                return false;
            }
            slow = slow.next;
        }
        return true;
    }

    public static boolean isPal(Node head) {
        Node rev = head.reverse();
        while (head != null) {
            if (head.data != rev.data) return false;
            head = head.next;
            rev = rev.next;
        }
        return true;
    }

    public static Node getIntersection(Node list1, Node list2) {
        HashSet<Integer> set = new HashSet<>();

        while (list1 != null) {
            set.add(list1.data);
            list1 = list1.next;
        }

        while (list2 != null) {
            if (!set.add(list2.data)) return list2;
            else list2 = list2.next;
        }

        return null;
    }

    //delete node
    public boolean delete(Node head, int searchValue) {
        Node tmpNode = head;
        Node prevNode = null;
        boolean deletedANode = false;


        while (tmpNode != null) {
            if (tmpNode.data == searchValue) {
                if (tmpNode == head) {
                    head = head.next;
                } else { // fixed indenting/newline
                    prevNode.next = tmpNode.next;
                }
                // fixed indenting
                deletedANode = true;
            } else {
                // only advance the prevNode when there's no match.
                prevNode = tmpNode;
            }
            tmpNode = tmpNode.next;
        }

        return deletedANode;
    }
}
