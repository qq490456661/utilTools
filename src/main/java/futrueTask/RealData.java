package futrueTask;

import java.util.concurrent.Callable;

/**
 * Created by Administrator on 2017/10/17 0017.
 */
public class RealData implements Callable<String> {
    @Override
    public String call() throws Exception {

        Thread.sleep(5000);

        return "KO";
    }
}
