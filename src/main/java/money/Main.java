package money;

import java.math.BigDecimal;
import java.math.MathContext;

/**
 * Created by linjunjie(490456661@qq.com) on 2016/8/16.
 */
public class Main {

    public static void main(String[] args) {
       Money money = new Money(1000000.5558);
        System.out.println(money.toLevelString());
        System.out.println(money.toIntMoney());

        System.out.println(new BigDecimal(-100.5).setScale(0,BigDecimal.ROUND_HALF_DOWN));

        BigDecimal bigDecimal = new BigDecimal(11510.0512,new MathContext(4));
        System.out.println(bigDecimal);
    }

}
