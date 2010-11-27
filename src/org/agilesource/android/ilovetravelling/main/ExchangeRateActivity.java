package org.agilesource.android.ilovetravelling.main;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.InetSocketAddress;
import java.net.Proxy;
import java.net.URL;
import java.net.URLConnection;
import java.util.Timer;
import java.util.TimerTask;

import org.agilesource.android.ilovetravelling.util.CityUtil;
import org.agilesource.android.main.R;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.Dialog;
import android.graphics.Color;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.view.Gravity;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup.LayoutParams;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.DigitalClock;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TableLayout;
import android.widget.TableRow;
import android.widget.TextView;

public class ExchangeRateActivity extends Activity {

	private int flag = 0;
	
	private Spinner txCity;
	
	private Spinner xtCity;
	
	private Handler exchangeHandler;
    
    private Dialog progressDialog;
    
    private Timer timer;
    
    private Button search;
    
    private EditText editText;
    
    private String txValue;
    
    private String xtValue;
	
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.exchange);
		//String currentDateTimeString = DateFormat.getDateInstance().format(new Date());
		//Log.d("currentDateTimeString=",currentDateTimeString);
		DigitalClock clock = (DigitalClock)findViewById(R.id.digitalClock);
		timer = new Timer();
		txCity = (Spinner)findViewById(R.id.txCity);
		xtCity = (Spinner)findViewById(R.id.xtCity);
		search = (Button)findViewById(R.id.btnSearch);
		editText = (EditText)findViewById(R.id.edit_text);
		
		ArrayAdapter<CharSequence> adapter1 = ArrayAdapter.createFromResource(
				this, R.array.countries2, android.R.layout.simple_spinner_item);
		adapter1
				.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        txCity.setAdapter(adapter1);
        
        ArrayAdapter<CharSequence> adapter2 = ArrayAdapter.createFromResource(
				this, R.array.countries2, android.R.layout.simple_spinner_item);
		adapter2
				.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
		xtCity.setAdapter(adapter2);
		/* Will be filled and displayed later. */
		progressDialog = new AlertDialog.Builder(this)
        .setTitle("汇率转换中")
        .setMessage("正在加载实时汇率数据，请稍等...")        
        .create();
		

		exchangeHandler = new Handler() {
            public void handleMessage(Message msg) {
            	txValue=txCity.getSelectedItem().toString();
            	xtValue=xtCity.getSelectedItem().toString();
            	String jine=editText.getText().toString();
            	searchExchangeRate((String)CityUtil.getExchangeMap().get(txValue),
            			(String)CityUtil.getExchangeMap().get(xtValue),jine);
            	progressDialog.hide();
            }
            
        };
        
        search.setOnClickListener(new OnClickListener() {
            public void onClick(View v) {
                progressDialog.show();
                timer.schedule(new TimerTask() {
                    public void run() {
                        Message msg = new Message();
                        msg.setTarget(exchangeHandler);
                        msg.sendToTarget();
                    }
                },100);
            }
        });
		
	}
	
	public void searchExchangeRate(String tx,String xt,String jine ){
		
		try {
			/* Define the URL we want to load data from. */
			URL myURL = new URL(
					"http://www.123cha.com/hl/?q="+jine+"&from="+tx+"&to="+xt+"&s="+tx+xt+"#symbol="+tx+xt+"=X;range=3m;");
			/* Open a connection to that URL. */
			Proxy proxy = new Proxy(Proxy.Type.HTTP, new InetSocketAddress("222.76.217.154", 80));   
			URLConnection ucon = myURL.openConnection(proxy);
			ucon.setDoInput(true);   
			ucon.connect(); 
			/*
			 * Define InputStreams to read from the URLConnection.
			 */
			BufferedReader in = new BufferedReader(new InputStreamReader(ucon.getInputStream()));
			/*
			 * Read bytes to the Buffer until there is nothing more to read(-1).
			 */
			String inputLine;
            while (
                    (inputLine = in.readLine()) != null) {
                    convertion(inputLine);
            }
			/* Convert the Bytes read to a String. */
			
		} catch (Exception e) {
			/* On any Error we want to display it. */
			new AlertDialog.Builder(this)
    		.setTitle("解析错误")
    		.setMessage("获取实时汇率数据失败，请稍候再试。")
    		.setNegativeButton("确定", null)
    		.show();
			
		}
		/* Show the String on the GUI. */
	}
	
	public void convertion(String values){
		String arr[]=null;
		if(flag <=0){
			if(values.indexOf("<tr bgcolor=#ffffff align=center") != -1){
				flag++;
				int b = values.indexOf("<tr bgcolor=#ffffff align=center");
				String d = values.substring(b+33,values.indexOf("</tr>"));
				arr=d.split("</td>");
				TableLayout table = (TableLayout)findViewById(R.id.table);
				TableRow row = new TableRow(this); 
                row.setLayoutParams(new LayoutParams(LayoutParams.FILL_PARENT, LayoutParams.WRAP_CONTENT));
                TextView tx = new TextView(this);
                tx.setText(txValue);
                tx.setGravity(Gravity.RIGHT);
                tx.setPadding(5, 0, 0, 0);
                tx.setTextColor(Color.BLUE);
                row.addView(tx);
                TextView tx1 = new TextView(this);
                tx1.setText("当前汇率");
                tx1.setGravity(Gravity.RIGHT);
                tx1.setTextColor(Color.BLUE);
                row.addView(tx1);
                TextView tx2 = new TextView(this);
                tx2.setText(xtValue);
                tx2.setGravity(Gravity.RIGHT);
                tx2.setTextColor(Color.BLUE);
                row.addView(tx2);
                table.addView(row);
                
                TableRow row1 = new TableRow(this); 
                row1.setLayoutParams(new LayoutParams(LayoutParams.FILL_PARENT, LayoutParams.WRAP_CONTENT));
                TextView xt = new TextView(this);
                xt.setText(arr[0].substring(4, arr[0].length()));
                xt.setGravity(Gravity.RIGHT);
                row1.addView(xt);
                TextView xt1 = new TextView(this);
                xt1.setText(arr[1].substring(4, arr[1].length()));
                xt1.setGravity(Gravity.RIGHT);
                row1.addView(xt1);
                TextView xt2 = new TextView(this);
                xt2.setText(arr[2].substring(4, arr[2].length()));
                xt2.setGravity(Gravity.RIGHT);
                row1.addView(xt2);
                table.addView(row1);
			}
		}
	}
}
