class WordList {
    Node content;

    static WordList foobar = initialize_foobar();;

    public static void main(String[] args) {
    }

    WordList() {
        content = null;
    }

    WordList(String[] t){
        content = null;
        if(t.length > 0) addFirst(t[0]);
        for(int i = 1; i<t.length; i++){
            addLast(t[i]);
        }
    }

    int length() {return Node.length(content);}

    static WordList initialize_foobar() {
        String[] list = new String[]{"foo", "bar", "baz"};
        return new WordList(list);
    }

    public String toString() {return Node.makeString(content);}

    void addFirst(String w) {content = new Node(w, content);}

    void addLast(String w) {
        if(content == null) content = new Node(w, null);
        else Node.addLast(w, content);
    }

    String removeFirst() {
        if (content != null){
            String out = content.head;
            content = content.next;
            return out;
        }
        return null;
    }

    String removeLast() {
        String out = null;
        if(content == null) return out;
        if(content.next == null){
            out = content.head;
            content = null;
        }
        for (Node cur = content; cur!= null && cur.next != null; cur = cur.next) {
            if(cur.next.next == null){
                out = cur.next.head;
                cur.next = null;
            }
        }
        return out;
    }

    void insert(String s) {
        if(content == null) content = new Node(s, null);
        else Node.insert(s, content);
    }

    void insertionSort() {Node.insertionSort(content);}

    void mergeSort() {
        int len = length();
        if(len <= 1) return;
        WordList left = new WordList();
        WordList right = new WordList();
        for(int i=0; i<len/2; i++){
            left.addLast(this.removeFirst());
        }
        while(this.content != null){
            right.addLast(this.removeFirst());
        }
        left.mergeSort();
        right.mergeSort();
        this.content = Node.merge(left.content, right.content);
    }

    String[] toArray() {
        String[] arrayList = new String[length()];
        int i = 0;
        for (Node cur = content; cur != null; cur = cur.next) {
            arrayList[i] = cur.head;
            i++;
        }
        return arrayList;
    }

}
