import com.google.common.util.concurrent.RateLimiter;
import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.ConnectionPool;
import okhttp3.FormBody;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;
import okhttp3.ResponseBody;
import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.net.Proxy;
import java.util.Date;
import java.util.concurrent.TimeUnit;

public class SimpleTest {
    private static final Logger LOGGER = LoggerFactory.getLogger(SimpleTest.class);
    @Test
    public void testSubmitOrder() throws IOException {

        OkHttpClient client = new OkHttpClient().newBuilder()
                .connectTimeout(1, TimeUnit.SECONDS)
                .connectionPool(new ConnectionPool(50, 5, TimeUnit.SECONDS))
                .readTimeout(1, TimeUnit.SECONDS)
                .writeTimeout(1, TimeUnit.SECONDS)
                .proxy(new Proxy(Proxy.Type.HTTP, new InetSocketAddress("localhost", 8888)))
                .build();

        FormBody.Builder formBody = new FormBody.Builder();
        formBody.add("policyId", "24974055");
        formBody.add("isJjj", "0");
        formBody.add("needPriceCheck", "8LGS");
        formBody.add("logo", "group");
        formBody.add("lineStr", "TSN-DLC/DLC-TSN");
        formBody.add("insurerFlightno", "ALL");
        formBody.add("hotelFlightno", "");
        formBody.add("hotelPrice", "");
        formBody.add("org_cityadded", "TSN");
        formBody.add("dst_cityadded", "DLC");
        formBody.add("flightnoadded", "GS7803");
        formBody.add("depdateadded", "2018-04-30");
        formBody.add("cabinadded", "G");
        formBody.add("priceadded", "300");
        formBody.add("segmentpolicyId", "");
        formBody.add("insurer", "30.0");
        formBody.add("org_cityadded", "DLC");
        formBody.add("dst_cityadded", "TSN");
        formBody.add("flightnoadded", "GS6658");
        formBody.add("depdateadded", "2018-05-09");
        formBody.add("cabinadded", "G");
        formBody.add("priceadded", "300");
        formBody.add("segmentpolicyId", "");
        formBody.add("DLCTSN20180509_3923", "9057");
        formBody.add("seatnum", "4");
        formBody.add("checkForeignGroup", "0");
        formBody.add("tel", "13911550123");
        formBody.add("mobile", "13911550123");
        formBody.add("sendsms", "yes");
        formBody.add("remark", "\t");
        formBody.add("myDept", "0");
        formBody.add("content", "yes");


        Request request = new Request.Builder()
                .url("http://gt.hnair.com/gt/order/frontend/submitorder/submitOrder.do?logo=group")
                .addHeader("Host", "gt.hnair.com")
                .addHeader("Origin", "http://gt.hnair.com")
                .addHeader("Content-Type", "application/x-www-form-urlencoded")
                .addHeader("User-Agent", "Mozilla/5.0 (Windows NT 6.1; Win64; x64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/66.0.3359.117 Safari/537.36")
                .addHeader("Upgrade-Insecure-Requests", "1")
                .addHeader("Accept", "text/html,application/xhtml+xml,application/xml;q=0.9,image/webp,image/apng,*/*;q=0.8")
                .addHeader("Referer", "http://gt.hnair.com/gt/backend/sun/searchPrice.do")
                .addHeader("Connection", "keep-alive")
                .addHeader("Accept-Encoding", "deflate")
                .addHeader("Accept-Language", "zh-CN,zh;q=0.9")
                .addHeader("Cookie", "7=1; JSESSIONID=0001QnriIuE8asa01oF6oLE3P-g:3UG9VEUPJD")
                .post(formBody.build())
                .build();
        RateLimiter limiter = RateLimiter.create(2, 10, TimeUnit.MILLISECONDS);
        for (int i=0;i<10;i++) {
            limiter.acquire();
            client.newCall(request).enqueue(new Callback() {
                @Override
                public void onFailure(Call call, IOException e) {
                    e.printStackTrace();
                }

                @Override
                public void onResponse(Call call, Response response) {
                    try (ResponseBody body = response.body()) {
                        if (body != null) {
                            LOGGER.info("{} ok, result:{}", new Date().toString(), body.string());
                        }
                    } catch (Exception e) {
                    }

                }
            });
        }

        System.in.read();
        }

    }
