package ie.ucc.bis.ui.utilities;

import android.util.Log;

/**
 * 
 * @author timothyosullivan
 */
public class LoggerUtils {

private static final int LOGGER_ENTRY_MAX_LEN = 4000;
	
	public static void i(String logTag,	StringBuilder debugOutput) {	
		if (debugOutput.length() > LOGGER_ENTRY_MAX_LEN) {
		    Log.v(logTag, "debugOutput.length = " + debugOutput.length());
		    
		    int totalChunks = debugOutput.length() / LOGGER_ENTRY_MAX_LEN; // INTEGER DIVIDE
		    
		    for (int chunkCounter = 0; chunkCounter <= totalChunks; chunkCounter++) {
		        int max = 4000 * (chunkCounter + 1);
		        if (max >= debugOutput.length()) {
		            Log.v(logTag, "chunk " + chunkCounter + " of " + totalChunks + "\n"); 
		            Log.i(logTag, debugOutput.substring(LOGGER_ENTRY_MAX_LEN * chunkCounter));
		        } else {
		        	Log.v(logTag, "chunk " + chunkCounter + " of " + totalChunks + "\n");
		        	Log.i(logTag, debugOutput.substring(LOGGER_ENTRY_MAX_LEN * chunkCounter, max));
		        }
		    }
		}
		else {
			LoggerUtils.i(logTag, debugOutput.toString());
		}
	}

	public static void i(String logTag, String debugOutput) {
		Log.i(logTag, debugOutput);
	}
}
