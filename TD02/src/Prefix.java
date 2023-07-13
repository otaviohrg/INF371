import java.util.Arrays;

class Prefix {
    String[] t;

    final static String start = "<START>", end = "<END>", par = "<PAR>";

    Prefix(int n) {
        t = new String[n];
        Arrays.fill(t, start);
    }

    static boolean eq(Prefix p1, Prefix p2){
        boolean equal = p1.t.length == p2.t.length;
        for(int i = 0; i<p1.t.length && equal; i++){
            equal = p1.t[i].equals(p2.t[i]);
        }
        return equal;
    }

    Prefix addShift(String w){
        Prefix shifted = new Prefix(this.t.length);
        if(this.t.length>=1){
            System.arraycopy(this.t, 1, shifted.t, 0, this.t.length - 1);
            shifted.t[this.t.length-1] = w;
        }
        return shifted;
    }

    public int hashCode(){
        int h = 0;
        for (String s : this.t) h = 37 * h + s.hashCode();
        return h;
    }

    int hashCode(int n){
        int codeMod = this.hashCode() % n;
        if(codeMod < 0) codeMod += n;
        return codeMod;
    }

}
