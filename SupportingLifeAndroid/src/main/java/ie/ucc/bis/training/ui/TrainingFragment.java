package ie.ucc.bis.training.ui;

import ie.ucc.bis.R;
import ie.ucc.bis.activity.SupportingLifeBaseActivity;
import ie.ucc.bis.activity.TrainingActivity;
import ie.ucc.bis.activity.VideoViewerActivity;
import ie.ucc.bis.training.Tutorial;
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
	public static final String TUTORIAL = "TUTORIAL";
	private static final String SCALE = "SCALE";
	private static final String CENTER_PAGE = "CENTER_PAGE";
	
	private int position;
	private Tutorial tutorial;
	private float scale;
	private boolean centerPage;
	
	/**
	 * Static Constructor
	 * 
	 * @param trainingActivity
	 * @param position
	 * @param tutorial
	 * @param scale
	 * @param centerPage
	 * 
	 */
	public static Fragment create(TrainingActivity trainingActivity, int position, Tutorial tutorial, float scale, boolean centerPage)
	{
		Bundle args = new Bundle();
		args.putInt(POSITION, position);
		args.putSerializable(TUTORIAL, tutorial);
		args.putFloat(SCALE, scale);
		args.putBoolean(CENTER_PAGE, centerPage);
		return Fragment.instantiate(trainingActivity, TrainingFragment.class.getName(), args);
	}

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        
        Bundle args = getArguments();
        setPosition(args.getInt(POSITION));
        setTutorial((Tutorial) args.getSerializable(TUTORIAL));
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

		// configure title of tutorial
        TextView title = (TextView) rootView.findViewById(R.id.training_tutorial_title);
        title.setText(getTutorial().getTitle());
		
		// configure type of tutorial
        TextView type = (TextView) rootView.findViewById(R.id.training_tutorial_type);
        type.setText(getTutorial().getType());
        
		// configure availability of tutorial
        TextView availability = (TextView) rootView.findViewById(R.id.training_tutorial_availability);
        availability.setText(getTutorial().getAvailability());

		// configure description of tutorial
        TextView description = (TextView) rootView.findViewById(R.id.training_tutorial_description);
        description.setText(getTutorial().getDescription());
        
		// configure 'created date' of tutorial
        TextView createdDate = (TextView) rootView.findViewById(R.id.training_tutorial_created_date);
        createdDate.setText(getTutorial().getCreatedDate());

		// configure 'created by' of tutorial
        TextView createdBy = (TextView) rootView.findViewById(R.id.training_tutorial_created_by);
        createdBy.setText(getTutorial().getCreatedBy());
        
		// configure visibility of tutorial headings and content
		configureTutorialContentVisibility(rootView);
		
		// add soft keyboard handler - essentially hiding soft
		// keyboard when an EditText is not in focus
		((SupportingLifeBaseActivity) getActivity()).addSoftKeyboardHandling(rootView);
		
        return rootView;
    }

	/**
	 * 
	 * Responsible for configuring the visibility of the tutorial
	 * headings and textual content
	 * 
	 * @param rootView 
	 * 
	 */
	private void configureTutorialContentVisibility(View rootView) {
		View tutorialDetails = (View) rootView.findViewById(R.id.training_tutorial_description_layout);		
		if (isCenterPage()) {
			tutorialDetails.setVisibility(View.VISIBLE);
		}
		else {
			tutorialDetails.setVisibility(View.INVISIBLE);
		}
	}

	/**
	 * 
	 * Responsible for configuring the display of the title image associated with the 
	 * tutorial. Additionally, include a click handler to manage the display of the
	 * actual tutorial.
	 * 
	 * @param rootView 
	 * 
	 */
	private void configureImageView(View rootView) {
		final ImageView imageView = (ImageView) rootView.findViewById(R.id.training_image);
		
		// configure the tutorial button image
		String packageName = getActivity().getApplicationContext().getPackageName();
		int imageResource = getResources().getIdentifier(getTutorial().getTitleImageName(), null, packageName);

		imageView.setImageDrawable(getResources().getDrawable(imageResource));
		
		imageView.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View v) {
				Intent trainingIntent = new Intent(getActivity().getApplicationContext(), VideoViewerActivity.class);
				Bundle bundle = new Bundle();
				// take note of the video tutorial selected
				bundle.putString(TrainingFragment.TUTORIAL, getTutorial().getVideoName());
				trainingIntent.putExtras(bundle);
				startActivity(trainingIntent);		
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
	 * Getter Method: getTutorial()
	 */
	public Tutorial getTutorial() {
		return tutorial;
	}

	/**
	 * Setter Method: setTutorial()
	 */
	public void setTutorial(Tutorial tutorial) {
		this.tutorial = tutorial;
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
