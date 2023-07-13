import java.util.Arrays;

public class UnionFind {
    static int[] equiv;
    static int[] height;

    static void init(int len){
        equiv = new int[len];
        height = new int[len];
        for(int i=0; i<len; i++){
            equiv[i] = i;
        }
        Arrays.fill(height, 1);
    }

    public static void main(String[] args) {
        init(10);
        union(9, 1);
        union(0, 1);
        union(1, 3);
        union(3, 4);
        union(8, 2);
        union(7, 2);
        union(2, 4);
        union(6,5);
        union(5, 4);
        print();
    }

    static int find(int n){
        return logFind(n);
    }

    static int naiveFind(int n){
        return equiv[n];
    }

    static int fastFind(int n){
        int index = n;
        while(equiv[index] != index){ index = equiv[index];}
        return index;
    }

    static int logFind(int n){
        int index = n;
        while(equiv[index] != index){
            int p = equiv[index];
            if(p == equiv[p]) index = p;
            else index = equiv[p];
        }
        return index;
    }

    static int union(int x, int y){
        return logUnion(x, y);
    }

    static int naiveUnion(int x, int y){
        int n = find(y);
        int o = find(x);
        for(int i=0; i<equiv.length; i++){
            if(equiv[i] == o) equiv[i] = n;
        };
        return n;
    }

    static int fastUnion(int x, int y){
        int n = find(y);
        int o = find(x);
        equiv[o] = n;
        return n;
    }

    static int logUnion(int x, int y){
        int branchY = find(y);
        int branchX = find(x);
        int r = branchY;
        int b = branchX;
        if(height[branchX] > height[branchY]){
            r = branchX;
            b = branchY;
        }
        equiv[b] = r;
        if(height[r] == height[b]) updateHeights(r);
        return height[branchX] >= height[branchY] ? branchX : branchY;
    }

    static void updateHeights(int n){
        int index = n;
        height[n] ++;
        while(equiv[index] != index){
            index = equiv[index];
            height[n] ++;
        }
    }

    static void print(){
        for (int i=0; i<equiv.length; i++){
            System.out.print(equiv[i]);
            System.out.print(" ");
        }
        System.out.print("\n");
        for (int i=0; i<height.length; i++){
            System.out.print(height[i]);
            System.out.print(" ");
        }
    }
}
