package futrueTask;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.FutureTask;

/**
 * Created by Administrator on 2017/10/17 0017.
 */
public class Main {

    public static void main(String[] args)throws Exception{
        //����FutureTask
        FutureTask<String> future = new FutureTask<String>(new RealData());
        ExecutorService executor = Executors.newFixedThreadPool(1);
        //ִ��FutureTask���൱�������е� client.request("a") ��������
        //�����￪���߳̽���RealData��call()ִ��
        executor.submit(future);
        System.out.println("�������");
        try {
            //������Ȼ��������������ݲ���������ʹ��sleep��������ҵ���߼��Ĵ���
            Thread.sleep(2000);
            System.out.println("2���ȥ��");
        } catch (InterruptedException e) {
        }
        //�൱�������е�data.getContent()��ȡ��call()�����ķ���ֵ
        //�����ʱcall()����û��ִ����ɣ�����Ȼ��ȴ�
        System.out.println("���� = " + future.get());
    }

}
