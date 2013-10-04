package ie.ucc.bis.activity;

import ie.ucc.bis.R;
import android.net.Uri;
import android.os.Bundle;
import android.widget.MediaController;
import android.widget.VideoView;

/**
 * 
 * This is the VideoViewer activity in the Supporting LIFE application.
 * 
 * The purpose of the activity is to facilitate the playing of video
 * content in the Supporting LIFE application.
 * 
 * @author timothyosullivan
 *
 */
public class VideoViewerActivity  extends SupportingLifeBaseActivity {

	/**
	 * onCreate method
	 * 
	 * Called when the activity is first created.
	 * Method is always followed by onStart() method.
	 * 
	 * @param savedInstanceState Bundle
	 */
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);

		setContentView(R.layout.activity_video_viewer);	
		setTitleFromActivityLabel(R.id.action_bar_title_text);
		
		VideoView videoView = (VideoView) findViewById(R.id.VideoView);
		
		// Start the MediaController
		MediaController mediaController = new MediaController(this);
		mediaController.setAnchorView(videoView);

		// Get the URL from String VideoURL
		Uri videoURL = Uri.parse("android.resource://" + getPackageName() + "/" + R.raw.android_sample_content);
		videoView.setMediaController(mediaController);
		videoView.setVideoURI(videoURL);
		
		videoView.start();
	}
}