import java.util.Arrays;

public class HMap {
    EntryList[] t;
    int nbEntries;

    HMap(int n){
        this.t = new EntryList[n];
        this.nbEntries = 0;
    }

    HMap(){
        this.t = new EntryList[20];
        this.nbEntries = 0;
    }

    WordList find(Prefix key){
        EntryList list = this.t[key.hashCode(this.t.length)];
        for(EntryList cur = list; cur != null; cur = cur.next){
            if(Prefix.eq(cur.head.key, key)) return cur.head.value;
        }
        return null;
    }

    void addSimple(Prefix key, String w){
        EntryList list = this.t[key.hashCode(this.t.length)];
        this.nbEntries++;
        if(list == null){
            this.t[key.hashCode(this.t.length)] = new EntryList(new Entry(key, new WordList(new String[]{w})), null);
            return;
        }
        EntryList goal = list;
        boolean found = false;
        for(EntryList cur = list; cur != null && !found; cur = cur.next){
            goal = cur;
            if(Prefix.eq(cur.head.key, key)){
                found = true;
                this.nbEntries--;
            }
        }
        if(found){
            goal.head.value.addLast(w);
            //goal.head.key = key.addShift(w);
        }
        else goal.next = new EntryList(new Entry(key, new WordList(new String[]{w})), null);
    }

    void add(Prefix key, String w){
        this.addSimple(key, w);
        if(this.nbEntries >= 0.75*this.t.length) this.rehash(2*this.t.length);
    }

    void rehash(int n){
        EntryList[] newT = new EntryList[n];
        int hash;
        for(EntryList list : this.t){
            for(EntryList cur=list; cur != null; cur=cur.next){
                hash = cur.head.key.hashCode(n);
                if(newT[hash] == null) newT[hash] = new EntryList(cur.head, null);
                else newT[hash] = new EntryList(cur.head, newT[hash]);
            }
        }
        this.t = newT;
    }

    void print(){
        System.out.println(this.nbEntries);
        for(int i=0; i<t.length; i++){
            System.out.printf("%d: ", i);
            for(EntryList cur = t[i]; cur != null; cur = cur.next){
                System.out.print(Arrays.toString(cur.head.key.t));
                System.out.print(cur.head.value);
            }
            System.out.print("\n");
        }
    }
}


class Test{
    public static void main(String[] args) {
        HMap hmap = new HMap(6);
        Prefix key = new Prefix(5);
        hmap.addSimple(key, "New");
        hmap.addSimple(key, "New");
        hmap.addSimple(key, "New2");
        Prefix key2 = new Prefix(11);
        hmap.addSimple(key2, "Old");
        hmap.print();
        hmap.rehash(5);
        hmap.print();
    }
}