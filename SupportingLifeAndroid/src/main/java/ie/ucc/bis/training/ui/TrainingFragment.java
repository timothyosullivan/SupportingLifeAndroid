package ie.ucc.bis.training.ui;

import ie.ucc.bis.R;
import ie.ucc.bis.activity.TrainingActivity;
import android.app.Activity;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.DisplayMetrics;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.MediaController;
import android.widget.TextView;
import android.widget.VideoView;

/**
 * Responsible for UI fragment to display a 
 * training item
 * 
 * @author timothyosullivan
 * 
 */
public class TrainingFragment extends Fragment {
	
	private static final String POSITION = "POSITION";
	private static final String SCALE = "SCALE";
	private static final String VIDEO_PLAYING = "VIDEO_PLAYING";
	
	private int position;
	private float scale;
	private boolean videoPlaying;
	
	/**
	 * Static Constructor
	 */
	public static Fragment create(TrainingActivity trainingActivity, int position, float scale)
	{
		Bundle args = new Bundle();
		args.putInt(POSITION, position);
		args.putFloat(SCALE, scale);
		args.putBoolean(VIDEO_PLAYING, false);
		return Fragment.instantiate(trainingActivity, TrainingFragment.class.getName(), args);
	}

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        
        Bundle args = getArguments();
        setPosition(args.getInt(POSITION));
        setScale(args.getFloat(SCALE));
        setVideoPlaying(args.getBoolean(VIDEO_PLAYING));
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
            Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_training, container, false);

        TextView tv = (TextView) rootView.findViewById(R.id.training_video_title);
		tv.setText("Position = " + getPosition());
		
		// configure video view
		configureVideoView(rootView);
				
		TrainingCustomLayout trainingCustomLayout = (TrainingCustomLayout) rootView.findViewById(R.id.training_custom_layout_root);
		float scale = this.getArguments().getFloat("scale");
		trainingCustomLayout.setPageScale(scale);
		
        return rootView;
    }

	private void configureVideoView(View rootView) {
        // Execute StreamVideo AsyncTask
		final VideoView videoView = (VideoView) rootView.findViewById(R.id.VideoView);
		
		// Start the MediaController
		MediaController mediaController = new MediaController(getActivity());
		mediaController.setAnchorView(videoView);

		// Get the URL from String VideoURL
	//	Uri.parse("android.resource://com.testvideo/" + R.raw.video);
		Uri videoURL = Uri.parse("android.resource://" + getActivity().getPackageName() + "/" + R.raw.android_sample_content);
		videoView.setMediaController(mediaController);
		videoView.setVideoURI(videoURL);
		
		videoView.setOnTouchListener(new View.OnTouchListener() {
			@Override
			public boolean onTouch(View v, MotionEvent event) {
					if (isVideoPlaying()) {
						// stop the video
						videoView.stopPlayback();
						
//						DisplayMetrics metrics = new DisplayMetrics(); 
//						getActivity().getWindowManager().getDefaultDisplay().getMetrics(metrics);
//						android.widget.LinearLayout.LayoutParams params = (android.widget.LinearLayout.LayoutParams) videoView.getLayoutParams();
//						params.width =  (int) (220*metrics.density);
//						params.height = (int) (120*metrics.density);
//						params.gravity = Gravity.CENTER_VERTICAL;
//						videoView.setLayoutParams(params);
						
		//				setVideoPlaying(false);
					}
					else {
			//			setVideoPlaying(true);
//						DisplayMetrics metrics = new DisplayMetrics(); 
//						getActivity().getWindowManager().getDefaultDisplay().getMetrics(metrics);
//						android.widget.LinearLayout.LayoutParams params = (android.widget.LinearLayout.LayoutParams) videoView.getLayoutParams();
//						params.width =  metrics.widthPixels;
//						params.height = metrics.heightPixels;
//						videoView.setLayoutParams(params);
						
						// start the video
						videoView.start();
					}
				return true;
			}
		});
	}


	@Override
    public void onAttach(Activity activity) {
        super.onAttach(activity);
    }

    @Override
    public void onDetach() {
        super.onDetach();
    }

	/**
	 * Getter Method: getPosition()
	 */
	public int getPosition() {
		return position;
	}

	/**
	 * Setter Method: setPosition()
	 */
	public void setPosition(int position) {
		this.position = position;
	}

	/**
	 * Getter Method: getScale()
	 */
	public float getScale() {
		return scale;
	}

	/**
	 * Setter Method: setScale()
	 */
	public void setScale(float scale) {
		this.scale = scale;
	}

	/**
	 * Getter Method: isVideoPlaying()
	 */
	public boolean isVideoPlaying() {
		return videoPlaying;
	}

	/**
	 * Setter Method: setVideoPlaying()
	 */
	public void setVideoPlaying(boolean videoPlaying) {
		this.videoPlaying = videoPlaying;
	}
}
