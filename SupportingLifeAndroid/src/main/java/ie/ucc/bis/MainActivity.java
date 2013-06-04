package ie.ucc.bis;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.View;
import android.widget.EditText;

public class MainActivity extends Activity {

	public final static String EXTRA_MESSAGE = "ie.ucc.bis.supportinglife.MESSAGE";
	
    /**
     * Called when the activity is first created.
     * @param savedInstanceState If the activity is being re-initialized after 
     * previously being shut down then this Bundle contains the data it most 
     * recently supplied in onSaveInstanceState(Bundle). <b>Note: Otherwise it is null.</b>
     */
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
	// Inflate the menu; this adds items to the action bar if it is present.
	getMenuInflater().inflate(ie.ucc.bis.R.menu.main, menu);
	return true;
    }
    
    /**
     * Method invoked when the user clicks the Submit button
     * 
     * @param view
     */
    public void sendMessage(View view) {
        // start DisplayMessageActivity
    	Intent intent = new Intent(this, DisplayMessageActivity.class);
    	
    	// attach contents of Message textfield in data bundle to DisplayMessageActivity    	
    	EditText editText = (EditText) findViewById(R.id.edit_message);
    	String message = editText.getText().toString();
    	intent.putExtra(EXTRA_MESSAGE, message);
    	startActivity(intent);    	
    }

    
    
}

