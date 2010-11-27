package org.agilesource.android.ilovetravelling.main;

import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.InetSocketAddress;
import java.net.MalformedURLException;
import java.net.Proxy;
import java.net.URL;
import java.net.URLConnection;
import java.net.URLEncoder;
import java.util.List;
import java.util.Timer;
import java.util.TimerTask;

import javax.xml.parsers.SAXParser;
import javax.xml.parsers.SAXParserFactory;

import org.agilesource.android.ilovetravelling.bean.Weather;
import org.agilesource.android.ilovetravelling.util.CityUtil;
import org.agilesource.android.ilovetravelling.util.XmlHandler;
import org.agilesource.android.main.R;
import org.xml.sax.InputSource;
import org.xml.sax.XMLReader;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.Dialog;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.util.Log;
import android.view.Gravity;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup.LayoutParams;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.TableLayout;
import android.widget.TableRow;
import android.widget.TextView;

public class WeatherActivity extends Activity {

	private Spinner txCity;
    
    private Button btnSearch;
    
    private Handler weatherHandler;
    
    private Dialog progressDialog;
    
    private Timer timer;
    
    private Button cancel;
    
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.weather);
        
        timer = new Timer();
        txCity = (Spinner)findViewById(R.id.txCity);
        cancel = (Button)findViewById(R.id.cancel);
        ArrayAdapter<CharSequence> adapter1 = ArrayAdapter.createFromResource(
				this, R.array.countries, android.R.layout.simple_spinner_item);
		adapter1
				.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        txCity.setAdapter(adapter1);
        txCity.setFocusable(true);
        txCity.requestFocus();
        txCity.setFocusableInTouchMode(true);
        btnSearch = (Button)findViewById(R.id.btnSearch);
        
        
        progressDialog = new AlertDialog.Builder(this)
        .setTitle("读取数据中")
        .setMessage("正在加载数据，请稍等...")        
        .create();
        
        weatherHandler = new Handler() {
            public void handleMessage(Message msg) {
                final String cityName = (String)CityUtil.getTranslateMap().get(txCity.getSelectedItem().toString());
                searchWeather(cityName);
                progressDialog.hide();
            }
        };
        
        cancel.setOnClickListener(new OnClickListener() {
            public void onClick(View v) {
            	System.out.print("this is imageButton................");
            	Log.d("aaaaaaaaaaaaaaaaaaaaaaaaaa","aaaaaaaaa");
            }
        });
        
        btnSearch.setOnClickListener(new OnClickListener() {
            public void onClick(View v) {
                progressDialog.show();
                timer.schedule(new TimerTask() {
                    public void run() {
                        Message msg = new Message();
                        msg.setTarget(weatherHandler);
                        msg.sendToTarget();
                    }
                },100);
            }
        });
    }
    
    private void searchWeather(String city) {
    	SAXParserFactory spf = SAXParserFactory.newInstance();
    	try{
    		 SAXParser sp = spf.newSAXParser();
             XMLReader reader = sp.getXMLReader();
             
             XmlHandler  handler = new XmlHandler();
             reader.setContentHandler(handler);        
             URL url = new URL("http://www.google.com/ig/api?hl=zh-cn&weather=" + URLEncoder.encode(city));
             Proxy proxy = new Proxy(Proxy.Type.HTTP, new InetSocketAddress("64.233.183.104", 80));   
 			 URLConnection ucon = url.openConnection(proxy);
 			 ucon.setDoInput(true);   
 			 ucon.connect(); 
             InputStream is = ucon.getInputStream();
             InputStreamReader isr = new InputStreamReader(is,"GBK");
             InputSource source = new InputSource(isr);
             reader.parse(source);
             List<Weather> weatherList = handler.getWeatherList();
             TableLayout table = (TableLayout)findViewById(R.id.table);
             table.removeAllViews();
             if(weatherList.size()==0){
            	 Log.d("weatherList is zero", "weatherList is zero.............");
            	 new AlertDialog.Builder(this)
         		.setTitle("解析错误")
         		.setMessage("获取天气数据失败，请稍候再试。")
         		.setNegativeButton("确定", null)
         		.show();
             }
             for(Weather weather : weatherList) {
                 TableRow row = new TableRow(this); 
                 row.setLayoutParams(new LayoutParams(LayoutParams.FILL_PARENT, LayoutParams.WRAP_CONTENT));
                 row.setGravity(Gravity.CENTER_VERTICAL);
                 ImageView img = new ImageView(this);
                 img.setImageDrawable(loadImage(weather.getImageUrl()));
                 img.setMinimumHeight(60);
                 row.addView(img);
                 TextView day = new TextView(this);
                 day.setText(weather.getDay());
                 day.setGravity(Gravity.CENTER_HORIZONTAL);
                 row.addView(day);
                 TextView temp = new TextView(this);
                 temp.setText(weather.getLowTemp() + "℃- " + weather.getHighTemp() + "℃");
                 temp.setGravity(Gravity.CENTER_HORIZONTAL);
                 row.addView(temp);
                 TextView condition = new TextView(this);
                 condition.setText(weather.getCondition());
                 condition.setGravity(Gravity.CENTER_HORIZONTAL);
                 row.addView(condition);
                 table.addView(row);
             }
    	}catch(Exception e){
    		Log.d("weatherList is error", e.getMessage());
    		new AlertDialog.Builder(this)
    		.setTitle("解析错误")
    		.setMessage("获取天气数据失败，请稍候再试。")
    		.setNegativeButton("确定", null)
    		.show();
    	}
    }
    
    private Drawable loadImage(String url) {
        try {
            return Drawable.createFromStream((InputStream) new URL("http://www.google.com/" + url).getContent(), "test");
        } catch (MalformedURLException e) {
            
            Log.e("exception",e.getMessage());
        } catch (IOException e) {
            
            Log.e("exception",e.getMessage());
        }
        return null;
    }
    
}
