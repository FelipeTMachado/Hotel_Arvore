package model;

class RedBlackTree<K extends Comparable<K>, V> {
    private static final boolean RED = true;
    private static final boolean BLACK = false;

    public class Node {
        K key;
        V value;
        Node left, right;
        boolean color;

        Node(K key, V value, boolean color) {
            this.key = key;
            this.value = value;
            this.color = color;
        }
    }

    private Node root;

    private boolean isRed(Node node) {
        return node != null && node.color == RED;
    }

    private Node rotateLeft(Node h) {
        Node x = h.right;
        h.right = x.left;
        x.left = h;
        x.color = h.color;
        h.color = RED;
        return x;
    }

    private Node rotateRight(Node h) {
        Node x = h.left;
        h.left = x.right;
        x.right = h;
        x.color = h.color;
        h.color = RED;
        return x;
    }

    private void flipColors(Node h) {
        h.color = RED;
        h.left.color = BLACK;
        h.right.color = BLACK;
    }

    private Node put(Node h, K key, V value) {
        if (h == null) return new Node(key, value, RED);

        int cmp = key.compareTo(h.key);
        if (cmp < 0) h.left = put(h.left, key, value);
        else if (cmp > 0) h.right = put(h.right, key, value);
        else h.value = value;

        if (isRed(h.right) && !isRed(h.left)) h = rotateLeft(h);
        if (isRed(h.left) && isRed(h.left.left)) h = rotateRight(h);
        if (isRed(h.left) && isRed(h.right)) flipColors(h);

        return h;
    }

    public void put(K key, V value) {
        root = put(root, key, value);
        root.color = BLACK;
    }

    private Node min(Node h) {
        return h.left == null ? h : min(h.left);
    }

    private Node deleteMin(Node h) {
        if (h.left == null) return null;

        h.left = deleteMin(h.left);
        return balance(h);
    }

    private Node balance(Node h) {
        if (isRed(h.right)) h = rotateLeft(h);
        if (isRed(h.left) && isRed(h.left.left)) h = rotateRight(h);
        if (isRed(h.left) && isRed(h.right)) flipColors(h);
        return h;
    }

    public void delete(K key) {
        if (root == null) return;

        root = delete(root, key);
        if (root != null) root.color = BLACK;
    }

    private Node delete(Node h, K key) {
        if (key.compareTo(h.key) < 0) {
            h.left = delete(h.left, key);
        } else if (key.compareTo(h.key) > 0) {
            h.right = delete(h.right, key);
        } else {
            if (h.right == null) return h.left;
            if (h.left == null) return h.right;

            Node x = h;
            h = min(x.right);
            h.right = deleteMin(x.right);
            h.left = x.left;
        }
        return balance(h);
    }

    public V get(K key) {
        Node x = root;
        while (x != null) {
            int cmp = key.compareTo(x.key);
            if (cmp < 0) x = x.left;
            else if (cmp > 0) x = x.right;
            else return x.value;
        }
        return null;
    }

	public Node getRoot() {
		return root;
	}

	public void setRoot(Node root) {
		this.root = root;
	}

	public static boolean isRed() {
		return RED;
	}

	public static boolean isBlack() {
		return BLACK;
	}
}

