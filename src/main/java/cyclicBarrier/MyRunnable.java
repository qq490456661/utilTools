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
            System.out.println("ǰ��ִ��"+i+"�߳�");
            //ִ��֮ǰ����
            barrier.await();//�������ȴ������̶߳����
            //ִ�к�������
            System.out.println("��ִ��"+i+"�߳�");
            barrier.await();//�������ȴ������̶߳����
            System.out.println("������ִ��"+i+"�߳�");
        } catch (InterruptedException e) {
            e.printStackTrace();
        } catch (BrokenBarrierException e) {
            e.printStackTrace();
        }


    }
}
