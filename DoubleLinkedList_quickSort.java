/**
 * Created by shuoshuo on 2017/10/9.
 */
public class DoubleLinkedList_quickSort {
    public static void main(String[] args) {
        int[] vals = {9, 8, 5, 3, 2, 4, 1};
        DoubleNode head = buildDoubleLinkedList(vals);
        print(head);
        System.out.println();
        quickSort(head);
        print(head);
    }

    private static void quickSort(DoubleNode head) {
        DoubleNode tail = getTail(head);
        quickSort(head, tail);
    }

    private static DoubleNode getTail(DoubleNode head) {
        if (head == null) return head;

        while (head != null && head.next != null) {
            head = head.next;
        }
        return head;
    }

    private static void quickSort(DoubleNode head, DoubleNode tail) {
        if (head != null && tail != null && tail != head && tail.next != head) {
            DoubleNode pivot = partition(head, tail);
            quickSort(head, pivot.pre);
            quickSort(pivot.next, tail);
        }
    }

    private static DoubleNode partition(DoubleNode head, DoubleNode tail) {
        int val = tail.val;
        DoubleNode preNode = head.pre;

        for (DoubleNode cur = head; cur != tail; cur = cur.next) {
            if (cur.val <= val) {
                preNode = (preNode == null) ? head : preNode.next;
                int temp = preNode.val;
                preNode.val = cur.val;
                cur.val = temp;
            }
        }

        preNode = (preNode == null) ? head : preNode.next;
        int temp = preNode.val;
        preNode.val = tail.val;
        tail.val = temp;

        return preNode;
    }

    private static void print(DoubleNode head) {
        while (head != null) {
            System.out.print(head.val + " ");
            head = head.next;
        }
    }

    private static DoubleNode buildDoubleLinkedList(int[] vals) {
        DoubleNode head = null, tail = null;

        for (int val : vals) {
            DoubleNode newNode = new DoubleNode(val);
            if (head == null) {
                head = newNode;
                tail = head;
            } else {
                tail.next = newNode;
                newNode.pre = tail;
                tail = tail.next;
            }
        }

        return head;
    }
}

class DoubleNode {
    DoubleNode pre;
    DoubleNode next;
    int val;
    DoubleNode(int val) {
        this.val = val;
    }
}
