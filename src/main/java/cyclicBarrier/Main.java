package cyclicBarrier;

import java.util.concurrent.CyclicBarrier;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

/**
 * Created by Administrator on 2017/10/17 0017.
 */
public class Main {


    public static void main(String[] args) {
        CyclicBarrier barrier  = new CyclicBarrier(5);//5个线程一起

        ExecutorService executor = Executors.newFixedThreadPool(5);
        MyRunnable a1 = new MyRunnable(barrier,1);
        MyRunnable a2 = new MyRunnable(barrier,2);
        MyRunnable a3 = new MyRunnable(barrier,3);
        MyRunnable a4 = new MyRunnable(barrier,4);
        MyRunnable a5 = new MyRunnable(barrier,5);
        executor.submit(a1);
        executor.submit(a2);
        executor.submit(a3);
        executor.submit(a4);
        executor.submit(a5);

    }

}
