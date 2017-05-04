import com.kangyonggan.app.fortune.common.util.HttpUtil;

/**
 * @author kangyonggan
 * @since 5/4/17
 */
public class FpayTest {

    private static String xml = "<?xml version=\"1.0\" encoding=\"UTF-8\"?>\n" +
            "<fpay>\n" +
            "   <header>\n" +
            "      <merchCo>319879465156486</merchCo>\n" +
            "      <tranCo>K001</tranCo>\n" +
            "      <serialNo>20170502211851000001</serialNo>\n" +
            "      <reqDate>20170502</reqDate>\n" +
            "      <reqTime>211851</reqTime>\n" +
            "      <signature>...</signature>\n" +
            "   </header>\n" +
            "   <body>\n" +
            "      <acctNo>436742330123456789</acctNo>\n" +
            "      <acctNm>康永敢</acctNm>\n" +
            "\t  <idNo>340321199112273095</idNo>\n" +
            "\t  <mobile>15121149571</mobile>\n" +
            "   </body>\n" +
            "</fpay>";

    public static void main(String[] args) throws Exception {
        String respXml = HttpUtil.sendPost("http://localhost:8080/fpay", xml);
        System.out.println(respXml);
    }

}
