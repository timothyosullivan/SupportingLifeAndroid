package ie.ucc.bis.training.ui;

import ie.ucc.bis.R;
import ie.ucc.bis.activity.TrainingActivity;
import android.app.Activity;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
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
	
	private int position;
	private float scale;
	
	/**
	 * Static Constructor
	 */
	public static Fragment create(TrainingActivity trainingActivity, int position, float scale)
	{
		Bundle args = new Bundle();
		args.putInt(POSITION, position);
		args.putFloat(SCALE, scale);
		return Fragment.instantiate(trainingActivity, TrainingFragment.class.getName(), args);
	}

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        
        Bundle args = getArguments();
        setPosition(args.getInt(POSITION));
        setScale(args.getFloat(SCALE));
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
            Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_training, container, false);

        TextView tv = (TextView) rootView.findViewById(R.id.training_video_title);
        
		tv.setText("Position = " + getPosition());
        
		TrainingCustomLayout trainingCustomLayout = (TrainingCustomLayout) rootView.findViewById(R.id.training_custom_layout_root);
		float scale = this.getArguments().getFloat("scale");
		trainingCustomLayout.setPageScale(scale);
		
        return rootView;
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
}
