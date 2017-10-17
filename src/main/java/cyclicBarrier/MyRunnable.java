package cyclicBarrier;

import java.util.concurrent.BrokenBarrierException;
import java.util.concurrent.CyclicBarrier;

/**
 * Created by Administrator on 2017/10/17 0017.
 */
public class MyRunnable implements Runnable {

    private CyclicBarrier barrier;

    private int i;

    public MyRunnable(CyclicBarrier cyclicBarrier,int i){
        this.barrier = cyclicBarrier;
        this.i = i;
    }

    @Override
    public void run() {


        try {
            System.out.println("前：执行"+i+"线程");
            //执行之前方法
            barrier.await();//阻塞，等待其他线程都完成
            //执行后续方法
            System.out.println("后：执行"+i+"线程");
            barrier.await();//阻塞，等待其他线程都完成
            System.out.println("结束：执行"+i+"线程");
        } catch (InterruptedException e) {
            e.printStackTrace();
        } catch (BrokenBarrierException e) {
            e.printStackTrace();
        }


    }
}
