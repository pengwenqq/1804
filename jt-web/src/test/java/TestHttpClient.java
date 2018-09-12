import java.io.IOException;

import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.util.EntityUtils;
import org.junit.Test;
public class TestHttpClient {
//测试get提交
	@Test
	public void testGet() throws IOException, IOException{
		String url="http://cds.3.cn/hotwords/get?callback=jQuery532246&cate=737%2C794%2C870&_=1534991799178";
		//定义请求对象
		HttpGet httpGet = new HttpGet(url);
		HttpPost post = new HttpPost(url);
		//定义httpClient
		CloseableHttpClient httpClient = HttpClients.createDefault();
		//获取response对象
		CloseableHttpResponse httpRespsonse = httpClient.execute(post);
		//判断状态信息
		if(httpRespsonse.getStatusLine().getStatusCode()==200){
			//请求正确,获取正确的响应对象
			String data=EntityUtils.toString(httpRespsonse.getEntity());
			System.out.println(data);
		}
	}
}
