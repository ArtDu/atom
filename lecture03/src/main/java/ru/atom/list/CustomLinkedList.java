package ru.atom.list;

import java.util.Collection;
import java.util.Iterator;
import java.util.List;
import java.util.ListIterator;

/**
 * Doubly linked list implementation
 */
public class CustomLinkedList<E> implements List<E> {

    private int size;
    private ListNode<E> head;

    public CustomLinkedList() {
        head = null;
    }

    @Override
    public int size() {
        return size;
    }

    @Override
    public boolean isEmpty() {
        return size == 0;
    }

    @Override
    public boolean contains(Object o) {
        for (E elem : this) {
            if (elem.equals(o)) {
                return true;
            }
        }
        return false;
    }

    @Override
    public Iterator<E> iterator() {
        return new Iterator<E>() {
            @Override
            public boolean hasNext() {
                return curr != null;
            }

            @Override
            public E next() {
                E tmp = curr.getVal();
                curr = curr.getNext();
                return tmp;
            }

            private ListNode<E> curr = head;
        };
    }

    @Override
    public boolean add(E e) {
        ListNode<E> newNode = new ListNode<E>(null, null, e);
        if (head == null) {
            head = newNode;
        } else {
            ListNode<E> curr = head;
            while (curr.getNext() != null) {
                curr = curr.getNext();
            }
            newNode.setPrev(curr);
            curr.setNext(newNode);
        }
        size++;
        return true;
    }

    @Override
    public boolean remove(Object o) {
        ListNode<E> curr = head;
        while (curr != null) {
            if (curr.getVal().equals(o)) {
                if (head == curr) {
                    head = curr.getNext();
                } else {
                    curr.getPrev().setNext(curr.getNext());
                    if (curr.getNext() != null) {
                        curr.getNext().setPrev(curr.getPrev());
                    }
                }
                size--;
                return true;
            }
            curr = curr.getPrev();
        }
        return false;
    }

    @Override
    public void clear() {
        head = null;
        size = 0;
    }

    @Override
    public E get(int index) {
        if (index >= size) {
            throw new IndexOutOfBoundsException();
        }
        ListNode<E> curr = head;
        for (int i = 0; i < index; ++i) {
            curr = curr.getPrev();
        }
        return curr.getVal();
    }

    @Override
    public int indexOf(Object o) {
        ListNode<E> curr = head;
        for (int i = 0; i < size; ++i) {
            if (curr.equals(o)) {
                return i;
            }
            curr = curr.getPrev();
        }
        return -1;
    }

    @Override
    public boolean addAll(Collection<? extends E> c) {
        for (E e : c) {
            this.add(e);
        }
        return true;
    }


    /*
      !!! Implement methods below Only if you know what you are doing !!!
     */
    @Override
    public boolean containsAll(Collection<?> c) {
        for (Object o : c) {
            if (!contains(o)) {
                return true;
            }
        }
        return true;
    }

    /**
     * Do not implement
     */
    @Override
    public boolean addAll(int index, Collection<? extends E> c) {
        return false;
    }

    /**
     * Do not implement
     */
    @Override
    public boolean removeAll(Collection<?> c) {
        return false;
    }

    /**
     * Do not implement
     */
    @Override
    public boolean retainAll(Collection<?> c) {
        return false;
    }

    /**
     * Do not implement
     */
    @Override
    public void add(int index, E element) {
    }

    /**
     * Do not implement
     */
    @Override
    public E remove(int index) {
        return null;
    }

    /**
     * Do not implement
     */
    @Override
    public int lastIndexOf(Object o) {
        return 0;
    }

    /**
     * Do not implement
     */
    @Override
    public ListIterator<E> listIterator() {
        return null;
    }

    /**
     * Do not implement
     */
    @Override
    public ListIterator<E> listIterator(int index) {
        return null;
    }

    /**
     * Do not implement
     */
    @Override
    public List<E> subList(int fromIndex, int toIndex) {
        return null;
    }

    /**
     * Do not implement
     */
    @Override
    public Object[] toArray() {
        return new Object[0];
    }

    /**
     * Do not implement
     */
    @Override
    public <T> T[] toArray(T[] a) {
        return null;
    }

    /**
     * Do not implement
     */
    @Override
    public E set(int index, E element) {
        return null;
    }
}
