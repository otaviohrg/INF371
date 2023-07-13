import javax.print.attribute.standard.Finishings;
import java.util.Arrays;

public class Percolation {
    static final int size = 100;
    static final int length = size * size;
    static boolean[] grid =  new boolean[length];
    static int[] order;
    static int noirCounter = 0;

    public static void main(String[] args) {
        long time = System.currentTimeMillis();
        System.out.println(monteCarlo(Integer.parseInt(args[0])));
        time = System.currentTimeMillis() - time;
        System.out.println(time);
        System.out.println(percolation());
    }

    static void init(){
        Arrays.fill(grid, false);
        order = new int[length];
        for(int i = 0; i < length; i++) order[i] = i;
        for(int i = 0; i < length-1; i++){
            int j = (int) (Math.random()*(length-i)) + i;
            int temp = order[i];
            order[i] = order[j];
            order[j] = temp;
        }
        UnionFind.init(length+2);
    }

    static void print(){
        for(int i = 0; i<length; i++) {
            if (grid[i]) System.out.print("*");
            else System.out.print("-");
            if (i % 10 == 9) System.out.print("\n");
        }
    }

    static double percolation(){
        init();
        int amount = 0;
        boolean perc_reached = false;
        while(!perc_reached){
            int noirCell = randomShadow();
            perc_reached = isPercolation(noirCell);
            amount++;
        }
        return (double) amount / (double) length;
    }

    static int randomShadow(){
        int index = noirCounter % length;
        grid[order[index]] = true;
        noirCounter++;
        propagateUnion(order[index]);
        return order[index];
    }

    static boolean isPercolation(int n){ return isLogPercolation();}

    static boolean isFastPercolation(int n) {
        int equivClass = UnionFind.find(n);
        boolean found = grid[n];
        if(found){
            boolean foundBegin = false;
            for(int i=0; i<10; i++) if(UnionFind.find(i) == equivClass) foundBegin = true;
            found = foundBegin;
        }
        if(found){
            boolean foundEnd = false;
            for(int i=0; i<10; i++) if(UnionFind.find(90+i) == equivClass) foundEnd = true;
            found = foundEnd;
        }
        return found;
    }

    static boolean isNaivePercolation(int n){
        boolean[] visited = new boolean[length];
        boolean found = grid[n];
        if(found) found = findDemiPath(visited, 0, n);
        if(found) found = findDemiPath(visited, 9, n);
        return found;
    }

    static boolean isLogPercolation(){
        return UnionFind.find(length) == UnionFind.find(length+1);
    }

    static boolean findDemiPath(boolean[] visited, int goal_line, int goal_cell){
        boolean foundPath = false;
        Arrays.fill(visited, false);
        Queue visitQueue = new Queue(length);
        for (int i = 0; i<size; i++){
            int cell = goal_line * size + i;
            if(grid[cell]) visitQueue.enqueue(cell);
        }
        while(!visitQueue.isEmpty() && !foundPath){
            int cell = visitQueue.dequeue();
            visited[cell] = true;
            if(cell==goal_cell) foundPath = true;
            else{
                if(cell % size != 0) if(!visited[cell-1] && grid[cell-1]) visitQueue.enqueue(cell-1);
                if(cell % size != size-1) if(!visited[cell+1] && grid[cell+1]) visitQueue.enqueue(cell+1);
                if(cell / size != 0) if(!visited[cell-size] && grid[cell-size]) visitQueue.enqueue(cell-size);
                if(cell / size != size-1) if(!visited[cell+size] && grid[cell+size]) visitQueue.enqueue(cell+size);
            }
        }
        return foundPath;
    }

    static double monteCarlo(int n){
        double total = 0;
        for(int i=0; i<n; i++){
            total += percolation();
        }
        return total / n;
    }

    static void propagateUnion(int x){
        if(grid[x]){
            if(x % size != 0) if(grid[x-1]) UnionFind.union(x, x-1);
            if(x % size != size-1) if(grid[x+1]) UnionFind.union(x, x+1);
            if(x / size != 0) if(grid[x-size]) UnionFind.union(x, x-size);
            if(x / size != size-1) if(grid[x+size]) UnionFind.union(x, x+size);
            if(x / size == 0) UnionFind.union(x, length);
            if(x / size == size-1) UnionFind.union(x, length+1);
        }
    }
}

class Queue {
    static private int[] q;
    static private int first, last;
    static private int size;

    Queue(int length){
        first = last = 0;
        size = length;
        q = new int[length];
        Arrays.fill(q, -1);
    }

    public void enqueue(int cell){
        if(last<size){
            q[last] = cell;
            last++;
        }
    }

    public int dequeue(){
        int cell = q[0];
        if(first != last){
            for (int i=0; i<last-1; i++){
                q[i] = q[i+1];
            }
            q[last-1] = -1;
            last--;
        }
        return cell;
    }

    public boolean isEmpty(){
        return first==last;
    }
}