import java.net.URI;

import org.bson.Document;
import org.htmlparser.beans.FilterBean;

import websocks.WSClient;

import com.mongodb.MongoClient;

import javax.websocket.*;


public class Main {


    public static void main(String[] args) {
        // CoinBase
        String startYear = args[0];
        String endYear = args[1];
        String granularity;
        String url;


        System.out.println("----");

        for (int y = 0; y < (Integer.parseInt(endYear) - Integer.parseInt(startYear)); y++) {
            for (int m = 0; m < 12; m++) {

                for (int d = 0; d < 31; d++) {

                    for (int h = 0; h < 24; h++) {
                        System.out.println(lp("", "_", 24));
                        url = "https://api.exchange.coinbase.com/products/BTC-USD/candles?start=";

                        url = url + (Integer.parseInt(startYear) + y) + "-" + lp("" + (m + 1), "0", 2) + "-" + lp("" + (d + 1), "0", 2) + "T" + lp("" + (h), "0", 2) + ":00:00&end=";
                        url = url + (Integer.parseInt(startYear) + y) + "-" + lp("" + (m + 1), "0", 2) + "-" + lp("" + (d + 1), "0", 2) + "T" + lp("" + (h + 1), "0", 2) + ":00:00&granularity=60";
                        System.out.println(url);
                        FilterBean bean = new FilterBean();


                        bean.setURL(url);


                        String result = bean.getText();

                        if(result.length()>3) {
                            result = result.substring(1, result.length() - 2);

                            String data[] = result.split(",");

                            System.out.println(" Url elements count : " + data.length);

                            for (int j = 0; j < data.length; j++) {

                                String dataElem = data[j].substring(1, data[j].length() - 2);
                                String[] elemPart = dataElem.split(",");

                                System.out.println(" Inserting " + elemPart[0]);

                                MongoClient mongoClient = new MongoClient();

                                MongoClient db = new MongoClient("localhost", 27017);

                                db.getDatabase("bitcoin").getCollection("archive").insertOne(
                                        new Document().append("Time", elemPart[0])
                                                .append("low", elemPart[1])
                                                .append("high", elemPart[2])
                                                .append("open", elemPart[3])
                                                .append("close", elemPart[4])
                                                .append("volume", elemPart[5])
                                );
                            }
                        }
                        System.out.println(lp("", "_", 24));

                    }
                }
                //

                // WebSocketContainer wsc;

                //wsc.connectToServer(WSClient.class, URI.create("wss://api2.bitfinex.com:3000/ws"));
		
		 /*HttpClient client = new DefaultHttpClient();
		 
		   HttpPost post = new HttpPost("http://restUrl");
		 
		   StringEntity input = new StringEntity("product");
		 
		   post.setEntity(input);
		 
		   HttpResponse response = client.execute(post);
		 
		   BufferedReader rd = new BufferedReader(new InputStreamReader(response.getEntity().getContent()));
		 
		   String line = "";
		 
		   while ((line = rd.readLine()) != null) {
		 
		    System.out.println(line);*/

            }

        }
    }

    public static String lp(String entry, String theChar , int size )
    {
        for(int i =0 ; i < size -entry.length() ; i++ )
        {
            entry=theChar+entry ;
        }
        return entry ;
    }
}