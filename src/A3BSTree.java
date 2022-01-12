import java.util.Collection;
import java.util.Iterator;
import java.util.NoSuchElementException;
import java.util.ArrayList;

public class A3BSTree <E extends Comparable<? super E>> implements Tree<E>{
	
	static class Node<E extends Comparable<? super E>>{
		private E item;
		private Node<E> left;
		private Node<E> right;
		
		private Node(E item) {
			this.item = item;
		}
	}
	
	private Node<E> root;
	private int size;
	
	public A3BSTree(){
		this.size = 0;
	}

	
	/**
	 * Logic used from:
	 * 	https://github.com/nishantroy/Data-Structures-and-Algorithms/blob/master/Binary%20Search%20Tree/BST.java
	 */
	@Override
	public boolean add(E e) {
		if (e == null) {
			throw new NullPointerException();
		}
		if (contains(e)) {
	        return false;
	    }
		
		if (this.root == null) {
			Node<E> newNode = new Node<E>(e);
			this.root = newNode;
			this.size++;
			return true;
		}
		Node<E> newNode = new Node<E>(e);
		add(newNode, this.root);
		return true;
	}
	
	/**
	 * Logic used from:
	 * 	https://github.com/nishantroy/Data-Structures-and-Algorithms/blob/master/Binary%20Search%20Tree/BST.java
	 */
	private void add(Node<E> node, Node<E> root) {
		int comp = root.item.compareTo(node.item);
		if (comp == 0) {
			return;
		} else if (comp < 0) {
			if (root.right == null) {
				root.right = node;
				this.size++;
				return;
			}else {
				add(node, root.right);
			}
		}else {
			if (root.left == null) {
				root.left = node;
				this.size++;
				return;
			}else {
				add(node, root.left);
			}
		}
	}

	@Override
	public boolean addAll(Collection<? extends E> c) {
		if (c == null) {
			throw new NullPointerException();
		}
		for (E item : c) {
			add(item);
		}
		return true;
	}

	/**
	 * Logic used from:
	 * 	https://github.com/nishantroy/Data-Structures-and-Algorithms/blob/master/Binary%20Search%20Tree/BST.java
	 */
	@SuppressWarnings("unchecked")
	@Override
	public boolean remove(Object o) {
		if (o == null) {
            throw new NullPointerException();
        } else if (this.root == null) {
            return false;
        }
		int comp = this.root.item.compareTo((E) o);
        if (comp == 0) {
            Node<E> node = this.root;
            if (node.left != null) {
                this.root = this.root.left;
                if (node.right != null) {
                    add(node.right, root);
                    this.size--;
                }
            } else if (node.right != null) {
                root = node.right;
            } else {
                root = null;
            }
            this.size--;
            return true;
        }
        remove(o, this.root);
        return true;
	}
	
	/**
	 * Logic used from:
	 * 	https://github.com/nishantroy/Data-Structures-and-Algorithms/blob/master/Binary%20Search%20Tree/BST.java
	 */
	@SuppressWarnings("unchecked")
	private E remove(Object item, Node<E> root) {
		int comp = root.item.compareTo((E) item);
	    Node<E> leaf;
        if (comp < 0) {
        		leaf = root.right;
	    } else {
	        leaf = root.left;
	    }
        if (leaf == null) {
    	 		throw new NullPointerException();
	    } else if (leaf.item.compareTo((E) item) == 0) {
	         if (leaf.left != null) {
	        	 	if (comp > 0) {
	        	 		root.left = leaf.left;
	            } else {
	                root.right = leaf.left;
	            }
	            if (leaf.right != null) {
                    add(leaf.right, leaf.left);
                    this.size--;
	            }
	        } else {
	            if (comp > 0) {
	                root.left = leaf.right;
	            } else {
	                root.right = leaf.right;
	            }
	        }
	        this.size--;
	        return leaf.item;
	    }
        return remove(item, leaf);
    }

	/**
	 * Logic used from:
	 * 	https://github.com/nishantroy/Data-Structures-and-Algorithms/blob/master/Binary%20Search%20Tree/BST.java
	 */
	@SuppressWarnings("unchecked")
	@Override
	public boolean contains(Object o) {
		if (o == null) {
			throw new NullPointerException();
	    }
	    try {
	        get((E) o);
        } catch (NoSuchElementException exception) {
        		return false;
	    }
	        return true;
	}
	
	/**
	 * Logic used from:
	 * 	https://github.com/nishantroy/Data-Structures-and-Algorithms/blob/master/Binary%20Search%20Tree/BST.java
	 */
	public E get(E item) {
		if (item == null) {
			throw new IllegalArgumentException("item cannot be null");
		}
		Node<E> node = this.root;
		while (node != null) {
			int comp = node.item.compareTo(item);
			Node<E> leaf;
			if (comp > 0) {
				leaf = node.left;
			} else {
				leaf = node.right;
			}
			
			if (node.item.equals(item)) {
				return node.item;
			} else if (leaf == null) {
				throw new NoSuchElementException();
			} else {
				node = leaf;
			}
		}
		throw new NullPointerException();
	}

	@Override
	public Iterator<E> iterator() {
		return new InOrderIterator();
	}
	
	/**
	 * Logic used from:
	 * 	https://liveexample.pearsoncmg.com/html/BST.html
	 */
	private class InOrderIterator implements java.util.Iterator<E>{
		private ArrayList<E> list = new ArrayList<>();
		private int current = 0;
		
		private InOrderIterator() {
			inOrder();
		}
		
		private void inOrder() {
			inOrder(root);
		}
		
		/**
		 * Logic used from:
		 * 	https://liveexample.pearsoncmg.com/html/BST.html
		 */
		private void inOrder(Node<E> node) {
			if (node == null) {
				return;
			}
			inOrder(node.left);
			this.list.add(node.item);
			inOrder(node.right);
		}

		/**
		 * Logic used from:
		 * 	https://liveexample.pearsoncmg.com/html/BST.html
		 */
		@Override
		public boolean hasNext() {
			if (this.current < list.size()) {
				return true;
			}
			return false;
		}

		/**
		 * Logic used from:
		 * 	https://liveexample.pearsoncmg.com/html/BST.html
		 */
		@Override
		public E next() {
			return list.get(current++);
		}
		
	}

	/**
	 * Logic used from:
	 * 	https://github.com/nishantroy/Data-Structures-and-Algorithms/blob/master/Binary%20Search%20Tree/BST.java
	 */
	@Override
	public int height() {
		return height(this.root);
	}
	
	/**
	 * Logic used from:
	 * 	https://github.com/nishantroy/Data-Structures-and-Algorithms/blob/master/Binary%20Search%20Tree/BST.java
	 */
	private int height(Node<E> node) {
		if (node == null) {
			return -1;
		}
		return Math.max(height(node.left), height(node.right)) + 1;
	}

	@Override
	public int size() {
		return this.size;
	}
}
