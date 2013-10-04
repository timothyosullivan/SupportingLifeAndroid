package ie.ucc.bis.training.ui;

import ie.ucc.bis.R;
import ie.ucc.bis.activity.TrainingActivity;
import ie.ucc.bis.activity.VideoViewerActivity;
import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

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
	private static final String CENTER_PAGE = "CENTER_PAGE";
	
	private int position;
	private float scale;
	private boolean centerPage;
	
	/**
	 * Static Constructor
	 */
	public static Fragment create(TrainingActivity trainingActivity, int position, float scale, boolean centerPage)
	{
		Bundle args = new Bundle();
		args.putInt(POSITION, position);
		args.putFloat(SCALE, scale);
		args.putBoolean(CENTER_PAGE, centerPage);
		return Fragment.instantiate(trainingActivity, TrainingFragment.class.getName(), args);
	}

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        
        Bundle args = getArguments();
        setPosition(args.getInt(POSITION));
        setScale(args.getFloat(SCALE));
        setCenterPage(args.getBoolean(CENTER_PAGE));
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
            Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_training, container, false);
        
		// configure image view
		configureImageView(rootView);
		TrainingCustomLayout trainingCustomLayout = (TrainingCustomLayout) rootView.findViewById(R.id.training_custom_layout);
		float scale = this.getArguments().getFloat("scale");
		trainingCustomLayout.adjustSize(scale);

		// configure title of training
        TextView tv = (TextView) rootView.findViewById(R.id.training_title);
		tv.setText("Position = " + getPosition());
		
		// configure visibility of the descriptive text associated with the training tutorial
		View videoDescDetails = (View) rootView.findViewById(R.id.training_tutorial_description);		
		if (isCenterPage()) {
			videoDescDetails.setVisibility(View.VISIBLE);
		}
		else {
			videoDescDetails.setVisibility(View.INVISIBLE);
		}		
		
        return rootView;
    }

	private void configureImageView(View rootView) {
		final ImageView imageView = (ImageView) rootView.findViewById(R.id.training_image);
		
		imageView.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View v) {
			//	Toast.makeText(getActivity(), "Clicked", Toast.LENGTH_SHORT).show();
				startActivity(new Intent(getActivity().getApplicationContext(), VideoViewerActivity.class));
				
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
	 * Getter Method: isCenterPage()
	 */
	public boolean isCenterPage() {
		return centerPage;
	}

	/**
	 * Setter Method: setCenterPage()
	 */
	public void setCenterPage(boolean centerPage) {
		this.centerPage = centerPage;
	}
}
