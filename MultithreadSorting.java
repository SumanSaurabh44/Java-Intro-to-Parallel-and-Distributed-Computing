//Main Class

import java.util.Arrays;
import java.util.Random;
import java.lang.Thread;

public class Main {
  public static void main(String[] args) throws InterruptedException {
    Random r = new Random(System.currentTimeMillis());
    int n = r.nextInt(101) + 50;
    int[] a = new int[n];
    for(int i = 0; i < n; i++)
    a[i] = r.nextInt(100);
    n = r.nextInt(101) + 50;
    int[] b = new int[n];
    for(int i = 0; i < n; i++)
    b[i] = r.nextInt(100);
    SortThread s1 = new SortThread(a);  
    SortThread s2 = new SortThread(b);  
    Thread t1= new Thread(s1);
    Thread t2= new Thread(s2);
    t1.start();
    t2.start();
    t1.join();
    t2.join();
    MergeThread m1= new MergeThread(a,b);
    Thread t3 =new Thread(m1);
    t3.start();
    System.out.println(Arrays.toString(m1.get()));
  }
}

//SortThread Class

public class SortThread extends Thread{
  int[] x; 
  
  public SortThread(int[] x){
    this.x = x;
    run();    
  }
    public void run(){
      sort(x);
  }
    
  private void sort(int[] x){
    for(int i = 0; i < x.length ; i++){
      int indexOfSmallest = findIndexOfSmallest(x, i);
      int t = x[i];
      x[i] = x[indexOfSmallest];
      x[indexOfSmallest] = t;
    }
  }
      
  private int findIndexOfSmallest(int[] a, int from){
    int indexOfSmallest = from;

    for(int i = from; i < a.length; i++)
      if(a[i] < a[indexOfSmallest])
        indexOfSmallest = i;
    return indexOfSmallest;
  }
  
  
  public int[] get(){
    return x;
  }
}


//MergeThread Class


public class MergeThread extends Thread {
  int[] a;
  int[] b;
  int[] c;

  public MergeThread(int[] a, int[] b){
    this.a = a;
    this.b = b;
    c = new int[a.length + b.length];
    run();      
  }
  public void run(){
    merge();
  }

  private void merge(){
    int aIndex = 0, bIndex = 0, cIndex = 0;
    while(aIndex < a.length && bIndex < b.length)
      if(a[aIndex] < b[bIndex])
        c[cIndex++] = a[aIndex++];
      else
        c[cIndex++] = b[bIndex++];

    while(aIndex < a.length)
      c[cIndex++] = a[aIndex++];
    
    while(bIndex < b.length)
      c[cIndex++] = b[bIndex++];
  }
  
  public int[] get(){
    return c;
  }
}
