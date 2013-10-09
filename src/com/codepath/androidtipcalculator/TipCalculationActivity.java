package com.codepath.androidtipcalculator;

import java.math.BigDecimal;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.AlertDialog.Builder;
import android.app.Dialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.util.Log;
import android.view.Gravity;
import android.view.Menu;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RelativeLayout.LayoutParams;
import android.widget.TextView;
import android.widget.Toast;

public class TipCalculationActivity extends Activity {
//	Float amnt = null;
	TextView tvTipValue;
	EditText etAmount;
	int percent = 0;
	EditText et_other_percent = null;
	private static final String TAG = "TIP_CALCULATION_ACTIVITY";
	private static final int DIALOG_ALERT = 10;
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_tip_calculation);
        Button bt_ten_percent = (Button) findViewById(R.id.bt_10_percent);
        Button bt_fifteen_percent = (Button) findViewById(R.id.bt_15_percent);
        Button bt_twenty_percent = (Button) findViewById(R.id.bt_20_percent);
        Button bt_other_percent = (Button) findViewById(R.id.bt_other_percent);
        
        etAmount = (EditText) findViewById(R.id.et_amount);
        tvTipValue= (TextView) findViewById(R.id.tv_tip_value);
        TipPercentOnClickListener onClickListener = new TipPercentOnClickListener();
        bt_ten_percent.setOnClickListener(onClickListener);
        bt_fifteen_percent.setOnClickListener(onClickListener);
        bt_twenty_percent.setOnClickListener(onClickListener);
        bt_other_percent.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				if(etAmount.getText().toString() == ""){
					Toast.makeText(getApplicationContext(), R.string.enter_amount_prompt, Toast.LENGTH_LONG).show();
					
				}else{
					try{
					Float amnt =  Float.valueOf(etAmount.getText().toString());
					showDialog(DIALOG_ALERT);
					}catch(Exception e){
						Toast.makeText(getApplicationContext(), R.string.enter_amount_prompt, Toast.LENGTH_LONG).show();
						Log.e(TAG, "amount: "+etAmount.getText().toString());
						Log.e(TAG, e.toString());
					}
				}
			}
		});
    }
    
    private final class TipPercentOnClickListener implements OnClickListener {
		@Override
		public void onClick(View v) {
			v.setSelected(true);
			switch(v.getId()){
			case R.id.bt_10_percent:
				percent = 10;
				break;
			case R.id.bt_15_percent:
				percent = 15;
				break;
			case R.id.bt_20_percent:
				percent = 20;
				break;
			}
			
			Toast.makeText(getApplicationContext(), Integer.valueOf(percent).toString(),Toast.LENGTH_LONG);
			if(etAmount.getText().toString().trim() == ""){
				Toast.makeText(getApplicationContext(), R.string.enter_amount_prompt, Toast.LENGTH_LONG).show();
				
			}else{
				try{
					Float amnt =  Float.valueOf(etAmount.getText().toString().trim());
					BigDecimal tip = new BigDecimal(amnt*percent/100);
					tip = tip.setScale(2, BigDecimal.ROUND_HALF_UP);
					tvTipValue.setText(tip.toString());
					
				}catch(Exception e){
					Toast.makeText(getApplicationContext(), R.string.enter_amount_prompt, Toast.LENGTH_LONG).show();
					Log.e(TAG, "amount: "+etAmount.getText().toString());
					Log.e(TAG, e.toString());
				}
			}
		}
    }
    
    protected Dialog onCreateDialog(int id) {
	    switch (id) {
	    case DIALOG_ALERT:
	      Builder builder = new AlertDialog.Builder(this);
	      builder.setMessage(R.string.enter_custom_tip_percent);
	      et_other_percent = new EditText(this);
	      et_other_percent.setGravity(Gravity.CENTER);
	      LayoutParams params = new LayoutParams(LayoutParams.WRAP_CONTENT,
	    	        LayoutParams.WRAP_CONTENT);
	      et_other_percent.setLayoutParams(params);
	      et_other_percent.setVisibility(EditText.VISIBLE);
	      builder.setCancelable(true);
	      builder.setPositiveButton("OK", new OkOnClickListener());
	      
	      builder.setNegativeButton("Cancel", new CancelOnClickListener());
	      
	      AlertDialog dialog = builder.create();
//	      TextView tv_message = (TextView)dialog.findViewById(android.R.id.message);
//	      tv_message.setGravity(Gravity.CENTER);
	      dialog.setView(et_other_percent);
	      dialog.show();
	    }
	    return super.onCreateDialog(id);
	  }
    
    private final class CancelOnClickListener implements DialogInterface.OnClickListener {
    	public void onClick(DialogInterface dialog, int which) {
    		Toast.makeText(getApplicationContext(), "Won't apply tip",
    				Toast.LENGTH_LONG).show();
    	}
    }

    private final class OkOnClickListener implements DialogInterface.OnClickListener {
    	public void onClick(DialogInterface dialog, int which) {
    		try{
    			Float amnt =  Float.valueOf(etAmount.getText().toString());
    			percent = Integer.valueOf(et_other_percent.getText().toString().trim());
				BigDecimal tip = new BigDecimal(amnt*percent/100);
				tip = tip.setScale(2, BigDecimal.ROUND_HALF_UP);
				tvTipValue.setText(tip.toString());
				
			}catch(Exception e){
				Toast.makeText(getApplicationContext(), R.string.enter_amount_prompt, Toast.LENGTH_LONG).show();
				Log.e(TAG,e.toString());
			}
    	}
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.tip_calculation, menu);
        return true;
    }
    
}
