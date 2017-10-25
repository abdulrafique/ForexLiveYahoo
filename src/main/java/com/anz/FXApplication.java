package com.anz;

import com.google.gson.Gson;
import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.HttpClientBuilder;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import yahoofinance.YahooFinance;
import yahoofinance.quotes.fx.FxQuote;
import yahoofinance.quotes.fx.FxSymbols;

import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * Created by ar on 25/10/2017.
 */
@SpringBootApplication
public class FXApplication {
    public static void main(String[] args) throws InterruptedException, IOException {
        SpringApplication.run(FXApplication.class);
        while (true) {
            HttpClient client = HttpClientBuilder.create().build();
            FxQuote eurusd = YahooFinance.getFx(FxSymbols.EURUSD);
            FxQuote usdjpy = YahooFinance.getFx(FxSymbols.USDJPY);
            String timeStamp = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss.SSS'Z'").format(new Date());
            System.out.println(eurusd);
            System.out.println(usdjpy);
            FxLive fxLive[] = new FxLive[1];
            fxLive[0]=new FxLive();
            fxLive[0].setEurusd(eurusd.getPrice().doubleValue());
            fxLive[0].setUsdjpy(usdjpy.getPrice().doubleValue());
            fxLive[0].setDate(timeStamp);
            String json = new Gson().toJson(fxLive);
            String url = "https://api.powerbi.com/beta/633ebcf5-3cd0-4146-84a4-b51368d2e40c/datasets/9e114d4a-b77f-4a1e-a24a-4fcdf067a3a3/rows?key=4dBN%2Fw%2F9kfzg2QSv9vQ0kAV2h6s3wwuDF7KkrdZRbj%2FKPfo2C7nrfGh1LoL77fwmQrppvn9GjdGxXFC9Xl1S8w%3D%3D";
            HttpPost httpPost = new HttpPost(url);
            httpPost.setEntity(new StringEntity(json));
            httpPost.setHeader("Content-Type", "application/json");
            HttpResponse response = client.execute(httpPost);
            System.out.println("Response Code : "
                    + response.getStatusLine().getStatusCode());
            Thread.sleep(1000);
        }
    }
}
