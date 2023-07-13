public class Bovary {
    static HMap buildTable(String[] files, int n){
        HMap hashTable = new HMap(2);
        for (String file : files){
            Prefix key = new Prefix(n);
            WordList value = new WordList();
            WordReader wr = new WordReader(file);
            for (String w = wr.read(); w != null; w = wr.read()){
                hashTable.add(key, w);
                key = key.addShift(w);
            }
            hashTable.add(key, Prefix.end);
        }
        return hashTable;
    }

    static void generate(HMap t, int n){
        Prefix key = new Prefix(n);

        StringBuilder output = new StringBuilder();
        while(true){
            String[] wordlist = t.find(key).toArray();
            String w = wordlist[(int)(Math.random()* wordlist.length)];
            if(w.equals(Prefix.end)) break;
            if(w.equals(Prefix.par)) output.append("\n");
            else output.append(w).append(" ");
            key = key.addShift(w);
        }
        System.out.println(output);
    }

    public static void main(String[] args) {
        HMap hashtable = Bovary.buildTable(new String[]{"bovary/01.txt", "bovary/02.txt", "bovary/03.txt", "bovary/04.txt", "bovary/05.txt", "bovary/06.txt"}, 5);
        Bovary.generate(hashtable, 5);
    }

}
