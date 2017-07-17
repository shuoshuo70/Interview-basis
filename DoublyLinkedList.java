package DataStructure;


public class DoublyLinkedList {
	class Node {
		Node next;
		Node prev;
		int val;
		Node(int val) {
			this.val = val;
		}
	}
	
	Node head, tail;
	DoublyLinkedList() {
		head = new Node(-1);
		tail = new Node(-1);
	}
	
	public void addToHead(int v) {
		Node newNode = new Node(v);
		if(head.next == null) {
			head.next = newNode;
			tail.prev = newNode;
		} else {
			newNode.next = head.next;
			head.next.prev = newNode;
			head.next = newNode;
		}
	}
	
	public void addToTail(int v) {
		Node newNode = new Node(v);
		if(tail.prev == null) {
			tail.prev = newNode;
			head.next = newNode;
		} else {
			newNode.prev = tail.prev;
			tail.prev.next = newNode;
			tail.prev = newNode;
		}
	}
	
	public boolean insertNode(int index, int v) {
		Node newNode = new Node(v);
		Node cur = head.next;
		int i=0; 
		for(;i<index && cur != null; i++) {
			cur = cur.next;
		}
		if(i < index) return false;
		newNode.next= cur.next;
		cur.next.prev = newNode;
		cur.next = newNode;
		newNode.prev = cur;
		return true;
	}
}
