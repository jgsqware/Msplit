package com.example.zsplit;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Timer;

import com.example.zsplit.edit.EditSplitActivity;
import com.example.zsplit.urnmodel.Urn;
import com.example.zsplit.urnmodel.UrnUtil;

import android.os.Bundle;
import android.app.Activity;
import android.app.AlertDialog;
import android.app.Dialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TableLayout;
import android.widget.TextView;
import android.widget.Toast;

public class MainActivity extends Activity {

    public int counter = 0;
    public TextView mainTimer;
    public Timer stopwatch;
	public LinearLayout splitTable;
	public UrnUtil splitListUtil;
	public Run run;
	public Urn urn;
	@Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        
        mainTimer = (TextView)findViewById(R.id.maintimer);
        splitListUtil = new UrnUtil(this);
        
        urn = splitListUtil.sampleUrn();
        changeToRun(urn);
    }

	private void changeToRun(Urn newUrn) {
		this.urn = newUrn;
		splitTable = (LinearLayout)findViewById(R.id.splittable);
        splitTable.removeAllViews();
        
        ArrayList<SplitRow> splitRows = SplitViewGenerator.generateSplitViews(this, newUrn);
        for(SplitRow row : splitRows){
        	splitTable.addView(row);
        }

        run = new Run(this, urn, splitRows);
        run.reset();
	}
	
	private void changeToFreeRun() {
		splitTable = (LinearLayout)findViewById(R.id.splittable);
        splitTable.removeAllViews();

        run = new FreeRun(this);
	}

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.activity_main, menu);
        return true;
    }
    
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle item selection
        switch (item.getItemId()) {
        case R.id.save_split:
        	createSaveDialog().show();
            return true;
        case R.id.load_split:
        	createLoadDialog().show();
        	return true;
        case R.id.free_run:
        	changeToFreeRun();
        	return true;
        case R.id.edit_urn:
        	Intent i = new Intent(this, EditSplitActivity.class);
        	startActivity(i);
        	overridePendingTransition(R.anim.anim_in_left, R.anim.anim_out_left);
        	return true;
        default:
            return super.onOptionsItemSelected(item);
        }
    }

	public void startButtonClicked(View view) {
        run.start();
        if(run.isRunning){
        	((TextView)view).setText("Pause");
        } else {
        	((TextView)view).setText("Start");
        }
        
    }
    public void resetButtonClicked(View view) {
        run.reset();
        ((TextView)findViewById(R.id.start)).setText("Start");
    }
    public void splitButtonClicked(View view) {
        run.split();
    }
    
    public AlertDialog createSaveDialog(){
    	AlertDialog.Builder builder = new AlertDialog.Builder(this);
    	builder.setTitle("Save as...");
    	View v = this.getLayoutInflater().inflate(R.layout.savedialog, null);
    	final EditText filename = ((EditText)v.findViewById(R.id.save_filename));
    	if(urn.filename!=null){
    		filename.setText(urn.filename);
    	}
    	builder.setView(v);
    	builder.setPositiveButton("Save", new DialogInterface.OnClickListener() {
			@Override
			public void onClick(DialogInterface dialog, int which) {
				String newName = filename.getText().toString();
				try{
					Urn newUrn = run.createUrnFromRun();
					newUrn.filename = newName;
					splitListUtil.save(newUrn);
					changeToRun(newUrn);
				} catch(IOException e){
					Toast.makeText(getApplicationContext(), "Could not save the file", Toast.LENGTH_SHORT).show();
				}
			}
		});
    	builder.setNegativeButton("Cancel", null);
    	return builder.create();
    }
    
    private AlertDialog createLoadDialog() {
    	AlertDialog.Builder builder = new AlertDialog.Builder(this);
    	builder.setTitle("Load");
    	final String[] list = splitListUtil.listUrns();
    	builder.setItems(list, new DialogInterface.OnClickListener() {
			@Override
			public void onClick(DialogInterface dialog, int which) {
				try{
					Urn loadedList = splitListUtil.load(list[which]);
					changeToRun(loadedList);
					Toast.makeText(getApplicationContext(), "Loaded "+list[which], Toast.LENGTH_SHORT).show();
				} catch(Exception e){
					Toast.makeText(getApplicationContext(), "Could not load the Split", Toast.LENGTH_SHORT).show();
				} finally {
					dialog.dismiss();
				}
			}
		});
		return builder.create();
	}
}
