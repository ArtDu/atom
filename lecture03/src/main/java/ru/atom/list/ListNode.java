package ru.atom.list;

/**
 * Contains ref to next node, prev node and value
 */
public class ListNode<E> {
    ListNode<E> next;
    ListNode<E> prev;
    E val;

    public ListNode<E> getNext() {
        return next;
    }

    public ListNode<E> getPrev() {
        return prev;
    }

    public E getVal() {
        return val;
    }

    public void setNext(ListNode<E> next) {
        this.next = next;
    }

    public void setPrev(ListNode<E> prev) {
        this.prev = prev;
    }

    public void setVal(E val) {
        this.val = val;
    }

    public ListNode() {
        this.prev = null;
        this.next = null;
        this.val  = null;
    }

    public ListNode(ListNode<E> prev, ListNode<E> next, E val) {
        this.prev = prev;
        this.next = next;
        this.val = val;
    }
}
