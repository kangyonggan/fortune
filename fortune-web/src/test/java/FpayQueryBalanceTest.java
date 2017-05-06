import com.kangyonggan.app.fortune.common.util.DateUtil;
import com.kangyonggan.app.fortune.common.util.HttpUtil;

/**
 * @author kangyonggan
 * @since 5/4/17
 */
public class FpayQueryBalanceTest {

    private static String xml = "<?xml version=\"1.0\" encoding=\"UTF-8\"?>\n" +
            "<fpay>\n" +
            "   <header>\n" +
            "      <merchCo>201705050000001</merchCo>\n" +
            "      <tranCo>K006</tranCo>\n" +
            "      <serialNo>" + DateUtil.getFullDateTime() + "</serialNo>\n" +
            "      <reqDate>20170502</reqDate>\n" +
            "      <reqTime>211851</reqTime>\n" +
            "      <signature>...</signature>\n" +
            "   </header>\n" +
            "   <body>\n" +
            "      <acctNo>6214831212408888</acctNo>\n" +
            "   </body>\n" +
            "</fpay>";

    public static void main(String[] args) throws Exception {
        String respXml = HttpUtil.sendPost("http://localhost:8080/fpay", xml);
        System.out.println(respXml);
    }

}
