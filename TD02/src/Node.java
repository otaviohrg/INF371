public class Node {
    String head;
    Node next;

    Node(String head, Node next) {
        this.head = head;
        this.next = next;
    }

    public static void main(String[] args) {
        Node foobar = new Node("foo", new Node("baz", new Node("bar", null)));
        Node klow = new Node("a", new Node("beta", new Node("dumb", null)));
        Node copied = copy(foobar);
        addLast("qux", foobar);
        insert("car", foobar);
        insert("zed", foobar);
        insertionSort(foobar);
        System.out.println(makeString(foobar));
        System.out.println(makeString(klow));
        System.out.println(makeString(merge(foobar,klow)));
        System.out.println(makeString(copied));
    }

    static int lengthRec(Node l) {
        int length = 0;
        if(l != null) length += lengthRec(l.next) + 1;
        return length;
    }

    static int length(Node l) {
        int len = 0;
        for (Node cur = l; cur != null; cur = cur.next) {
            len++;
        }
        return len;
    }

    static String makeString(Node l) {
        StringBuilder list = new StringBuilder();
        list.append("[");
        for (Node cur = l; cur != null; cur = cur.next) {
            list.append(cur.head);
            if(cur.next != null) list.append(", ");
        }
        list.append("]");
        return list.toString();
    }

    static void addLast(String s, Node l){
        boolean insert = false;
        for (Node cur = l; cur != null && !insert; cur = cur.next) {
            if(cur.next == null){
                cur.next = new Node(s, null);
                insert = true;
            }
        }
    }

    static Node copy(Node the){
        Node copiedList = null;
        if(the != null) {
            copiedList = new Node(the.head, null);
            for (Node cur = the; cur.next != null; cur = cur.next) {
                addLast(cur.next.head, copiedList);
            }
        }
        return copiedList;
    }

    static Node insert(String s, Node l){
        boolean insert = false;
        if(l==null){
            l = new Node(s, null);
            insert = true;
        }
        if(!insert && s.compareTo(l.head)<=0){
            l.next = new Node(l.head, l.next);
            l.head = s;
            insert = true;
        }
        for (Node cur = l; !insert; cur = cur.next) {
            if(cur.next == null){
                cur.next = new Node(s, null);
                insert = true;
            }
            else if(s.compareTo(cur.next.head)<=0){
                cur.next = new Node(s, cur.next);
                insert = true;
            }
        }
        return l;
    }

    static Node insertionSort(Node l){
        Node sorted = null;
        if(l!=null){
            sorted = new Node(l.head, null);
            for (Node cur = l; cur.next != null; cur = cur.next) {
                insert(cur.next.head, sorted);
            }
            l.next = sorted.next;
            l.head = sorted.head;
        }
        return sorted;
    }

    static Node merge(Node l1, Node l2){
        Node merged;
        Node cur1 = l1, cur2 = l2;
        if(l1 == null) return l2;
        if(l2 == null) return l1;
        if(l1.head.compareTo(l2.head) <= 0){
            merged = new Node(l1.head, null);
            cur1 = cur1.next;
        }
        else{
            merged = new Node(l2.head, null);
            cur2 = cur2.next;
        }
        while( cur1 != null || cur2 != null){
            if(cur2 == null){
                Node.addLast(cur1.head, merged);
                cur1 = cur1.next;
                continue;
            }
            if(cur1 == null){
                Node.addLast(cur2.head, merged);
                cur2 = cur2.next;
                continue;
            }
            if(cur1.head.compareTo(cur2.head) <= 0){
                Node.addLast(cur1.head, merged);
                cur1 = cur1.next;
            }
            else{
                Node.addLast(cur2.head, merged);
                cur2 = cur2.next;
            }
        }
        return merged;
    }
}
