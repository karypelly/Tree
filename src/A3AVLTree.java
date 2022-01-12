import java.util.ArrayList;
import java.util.Collection;
import java.util.Iterator;
import java.util.List;
import java.util.NoSuchElementException;


public class A3AVLTree <E extends Comparable<? super E>> implements Tree<E>{ 

	static class Node<E extends Comparable<? super E>>{
		private E item;
 	    private Node<E> left;
 	    private Node<E> right;
 	    private int height;
 	    private int balanceFactor;

 	    public Node(E item) {
 	        this.item = item;
 	    }
	}
	
	private Node<E> root;
    private int size;
	
	public A3AVLTree(){
		this.size = 0;
	}

	/**
	 * Logic used from:
	 * 	https://github.com/nishantroy/Data-Structures-and-Algorithms/blob/master/AVL%20Trees/AVL.java
	 */
	@Override
	public boolean add(E e) {
		if (e == null) {
            throw new NullPointerException();
        }
        Node<E> newNode = new Node<>(e);
        if (contains(e)) {
            return false;
        }
        if (this.size == 0) {
            this.root = newNode;
            this.size++;
            findHtBf(this.root);
        } else {
            add(this.root, newNode);
            this.size++;
        }
        return true;
	}
	
	/**
	 * Logic used from:
	 * 	https://github.com/nishantroy/Data-Structures-and-Algorithms/blob/master/AVL%20Trees/AVL.java
	 */
	private void add(Node<E> root, Node<E> node) {
        if (root == null) {
            return;
        }
        E rootItem = root.item;
        E nodeItem = node.item;
        int comp = rootItem.compareTo(nodeItem);
        if (comp < 0) {
            if (root.right == null) {
                root.right = node;
            } else {
                add(root.right, node);
            }
        } else if (comp > 0) {
            if (root.left == null) {
                root.left = node;
            } else {
                add(root.left, node);
            }
        }
        findHtBf(root);
        trinodeRotate(root);
    }
	
	/**
	 * Logic used from:
	 * 	https://github.com/nishantroy/Data-Structures-and-Algorithms/blob/master/AVL%20Trees/AVL.java
	 */
	private void findHtBf(Node<E> node) {
	    int lh;
	    int rh;
	    if (node.left != null) {
	    		lh = node.left.height;
	    } else {
	    		lh = -1;
	    }
	    if (node.right != null) {
	    		rh = node.right.height;
	    } else {
	    		rh = -1;
	    }
	    int nodeHeight = (Math.max(lh, rh) + 1);
	    int balanceFactor = (lh - rh);
	    node.height = nodeHeight;
	    node.balanceFactor = balanceFactor;
	}
	
	/**
	 * Logic used from:
	 * 	https://github.com/nishantroy/Data-Structures-and-Algorithms/blob/master/AVL%20Trees/AVL.java
	 */
	private void trinodeRotate(Node<E> node) {
		int bf = node.balanceFactor;
		if (bf > 1) {
			if (node.left != null) {
				int lBalanceFactor = node.left.balanceFactor;
				if (lBalanceFactor >= 0) {
	                rightRotate(node);
	            } else if (lBalanceFactor < 0) {
	                leftRightRotate(node);
	            }
			}
	    } else if (bf < -1) {
	        if (node.right != null) {
	        		int rBalancefactor = node.right.balanceFactor;
	        		if (rBalancefactor > 0) {
	                rightLeftRotate(node);
	            } else if (rBalancefactor <= 0) {
	            		leftRotate(node);	                
                }
	        }
	    }
	}
	
	/**
	 * Logic used from:
	 * 	https://github.com/nishantroy/Data-Structures-and-Algorithms/blob/master/AVL%20Trees/AVL.java
	 */
	private void rightRotate(Node<E> node) {
		E e = node.item;
		Node<E> newNode = new Node<>(e);
	    Node<E> left = node.left;
	    newNode.right = node.right;
	    newNode.left = left.right;
        node.right = newNode;
        node.item = left.item;
	    node.left = left.left;
        left.left = null;
        findHtBfSub(node);
	}
	
	/**
	 * Logic used from:
	 * 	https://github.com/nishantroy/Data-Structures-and-Algorithms/blob/master/AVL%20Trees/AVL.java
	 */
	private void leftRotate(Node<E> node) {
	    E e = node.item;
	    Node<E> right = node.right;
	    Node<E> newNode = new Node<>(e);
        newNode.left = node.left;
        newNode.right = right.left;
	    node.left = newNode;
	    node.item = right.item;
	    node.right = right.right;
	    right.right = null;
        findHtBfSub(node);
    }
	
	/**
	 * Logic used from:
	 * 	https://github.com/nishantroy/Data-Structures-and-Algorithms/blob/master/AVL%20Trees/AVL.java
	 */
    private void leftRightRotate(Node<E> node) {
        leftRotate(node.left);
        rightRotate(node);
    }

    /**
	 * Logic used from:
	 * 	https://github.com/nishantroy/Data-Structures-and-Algorithms/blob/master/AVL%20Trees/AVL.java
	 */
    private void rightLeftRotate(Node<E> node) {
        rightRotate(node.right);
        leftRotate(node);
    }
    
    /**
	 * Logic used from:
	 * 	https://github.com/nishantroy/Data-Structures-and-Algorithms/blob/master/AVL%20Trees/AVL.java
	 */
    private void findHtBfSub(Node<E> root) {
        if (root.left != null) {
            findHtBfSub(root.left);
        }

        if (root.right != null) {
            findHtBfSub(root.right);
        }
        findHtBf(root);
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
	 * 	https://github.com/nishantroy/Data-Structures-and-Algorithms/blob/master/AVL%20Trees/AVL.java
	 */
	@SuppressWarnings("unchecked")
	@Override
	public boolean remove(Object o) {
		if (o == null) {
			throw new IllegalArgumentException();
	    }
	    if (!this.contains(o)) {
            throw new NoSuchElementException();
        }
	    int comp = root.item.compareTo((E) o);
	    if (size == 1 && comp == 0) {
	        this.root = null;
            this.size--;
            return true;

	    } else {
	        this.root = remove(root, (E) o);
	    }
	    this.size--;
        return true;
	}
	
	/**
	 * Logic used from:
	 * 	https://github.com/nishantroy/Data-Structures-and-Algorithms/blob/master/AVL%20Trees/AVL.java
	 */
	private Node<E> remove(Node<E> node, E item) {
		if (node == null) {
			return null;
	    }
		int comp = item.compareTo(node.item);
	    if (comp == 0) {
            if (node.left == null && node.right == null) {
            		return null;
	        } else if (node.left == null) {
	            return node.right;
	        } else if (node.right == null) {
                return node.left;
            } else {
	            E e = successor(node);
	            node.item = e;
	            node.right = remove(node.right, e);
	        }
	    } else if (comp < 0) {
	        node.left = remove(node.left, item);
        } else {
        	    node.right = remove(node.right, item);
	    }
	    findHtBf(node);
        trinodeRotate(node);
        return node;
    }
	
	/**
	 * Logic used from:
	 * 	https://github.com/nishantroy/Data-Structures-and-Algorithms/blob/master/AVL%20Trees/AVL.java
	 */
	private E successor(Node<E> node) {
        if (node.right != null) {
            node = node.right;
        }
        if (node == null) {
            return null;
        }
        Node<E> left = node.left;
        if (left == null) {
            return node.item;
        } else {
            return successor(left);
        }
    }

	/**
	 * Logic used from:
	 * 	https://github.com/nishantroy/Data-Structures-and-Algorithms/blob/master/AVL%20Trees/AVL.java
	 */
	@SuppressWarnings("unchecked")
	@Override
	public boolean contains(Object o) {
		if (o == null) {
            throw new IllegalArgumentException("item cannot be null");
        }
        try {
            get((E) o);
        } catch (NoSuchElementException | IllegalArgumentException e) {
            return false;
        }
        return true;
	}
	
	/**
	 * Logic used from:
	 * 	https://github.com/nishantroy/Data-Structures-and-Algorithms/blob/master/AVL%20Trees/AVL.java
	 */
	private E get(E item) {
		if (item == null) {
			throw new IllegalArgumentException();
	    }
	    return get(this.root, item);
	}

	/**
	 * Logic used from:
	 * 	https://github.com/nishantroy/Data-Structures-and-Algorithms/blob/master/AVL%20Trees/AVL.java
	 */
	private E get(Node<E> root, E item) {
		if (root == null) {
			throw new NoSuchElementException();
	    }
		int comp = root.item.compareTo(item);
	    if (comp > 0) {
	   	 	return get(root.left, item);
	    } else if (comp < 0) {
	        return get(root.right, item);
	    }
	    return root.item;
	}

	@Override
	public Iterator<E> iterator() {
		return new InorderIterator();
	}
	
	/**
	 * Logic used from:
	 * 	https://liveexample.pearsoncmg.com/html/BST.html
	 */
	private class InorderIterator implements Iterator<E> {
		private ArrayList<E> list = new ArrayList<>();
		private int current = 0; 

		private InorderIterator() {
			inorder();
		}

		private void inorder() {
			inorder(root);
		}

		/**
		 * Logic used from:
		 * 	https://liveexample.pearsoncmg.com/html/BST.html
		 */
		private void inorder(Node<E> root) {
			if (root == null) return;
			inorder(root.left);
			this.list.add(root.item);
			inorder(root.right);
		}

		/**
		 * Logic used from:
		 * 	https://liveexample.pearsoncmg.com/html/BST.html
		 */
		@Override
		public boolean hasNext() {
			if (this.current < this.list.size())
				return true;

			return false;
		}

		/**
		 * Logic used from:
		 * 	https://liveexample.pearsoncmg.com/html/BST.html
		 */
		@Override 
		public E next() {
			return this.list.get(this.current++);
		}
	}

	/**
	 * Logic used from:
	 * 	https://github.com/nishantroy/Data-Structures-and-Algorithms/blob/master/AVL%20Trees/AVL.java
	 */
	@Override
	public int height() {
		if (this.root == null) {
            return -1;
        }
        return this.root.height;
    }

	@Override
	public int size() {
		return this.size;
	}
	
	/**
	 * Logic used from:
	 * 	https://github.com/nishantroy/Data-Structures-and-Algorithms/blob/master/AVL%20Trees/AVL.java
	 */
	protected List<E> inOrder() {
		return inOrder(this.root);
	}

	/**
	 * Logic used from:
	 * 	https://github.com/nishantroy/Data-Structures-and-Algorithms/blob/master/AVL%20Trees/AVL.java
	 */
	private List<E> inOrder(Node<E> root) {
		if (root == null) {
	        return new ArrayList<E>();
	    }
	    List<E> inList = inOrder(root.left);
        inList.add(root.item);
        inList.addAll(inOrder(root.right));
        return inList;
	}
}
