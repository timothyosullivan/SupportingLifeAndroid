package ie.ucc.bis.imci.ui;

import ie.ucc.bis.R;
import ie.ucc.bis.imci.model.AbstractPage;
import ie.ucc.bis.imci.model.AbstractWizardModel;
import ie.ucc.bis.imci.model.ModelCallbacks;
import ie.ucc.bis.imci.model.review.ReviewAssessmentAdapter;
import android.app.Activity;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;
import android.widget.TextView;

public class ReviewFragment extends ReviewListFragment implements ModelCallbacks {
    private ReviewFragmentCallbacks reviewFragmentCallbacks;
    private AbstractWizardModel wizardModel;
    private ReviewAssessmentAdapter reviewAssessmentAdapter;

    public ReviewFragment() {
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setReviewAssessmentAdapter(new ReviewAssessmentAdapter(this));
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
            Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_page, container, false);

        TextView titleView = (TextView) rootView.findViewById(android.R.id.title);
        titleView.setText(R.string.assessment_wizard_review_title);
        
        titleView.setTextColor(getResources().getColor(R.color.DarkGreen));

        ListView listView = (ListView) rootView.findViewById(android.R.id.list);
        setListAdapter(getReviewAssessmentAdapter());
        listView.setChoiceMode(ListView.CHOICE_MODE_SINGLE);
        return rootView;
    }

    @Override
    public void onAttach(Activity activity) {
        super.onAttach(activity);

        if (!(activity instanceof ReviewFragmentCallbacks)) {
            throw new ClassCastException("Activity must implement fragment's callbacks");
        }

        setReviewFragmentCallbacks((ReviewFragmentCallbacks) activity);

        setWizardModel(getReviewFragmentCallbacks().getWizardModel());
        getWizardModel().registerListener(this);
        onPageTreeChanged();
    }

   
    public void onPageTreeChanged() {
        onPageDataChanged(null);
    }

    @Override
    public void onDetach() {
        super.onDetach();
        setReviewFragmentCallbacks(null);

        getWizardModel().unregisterListener(this);
    }

    public void onPageDataChanged(AbstractPage changedPage) {
       setCurrentReviewItems(getWizardModel().gatherAssessmentReviewItems());

        if (getReviewAssessmentAdapter() != null) {
        	getReviewAssessmentAdapter().notifyDataSetInvalidated();
        }
    }

    @Override
    public void onListItemClick(ListView l, View v, int position, long id) {
        getReviewFragmentCallbacks().onEditScreenAfterReview(getCurrentReviewItems().get(position).getPageKey());
    }

	/**
	 * Getter Method: getReviewFragmentCallbacks()
	 */		 
	public ReviewFragmentCallbacks getReviewFragmentCallbacks() {
		return reviewFragmentCallbacks;
	}

	/**
	 * Setter Method: setReviewFragmentCallbacks()
	 */  	
	public void setReviewFragmentCallbacks(ReviewFragmentCallbacks reviewFragmentCallbacks) {
		this.reviewFragmentCallbacks = reviewFragmentCallbacks;
	}

	/**
	 * Getter Method: getReviewAssessmentAdapter()
	 */	
	private ReviewAssessmentAdapter getReviewAssessmentAdapter() {
		return reviewAssessmentAdapter;
	}

	/**
	 * Setter Method: setReviewAssessmentAdapter()
	 */
	private void setReviewAssessmentAdapter(ReviewAssessmentAdapter reviewAssessmentAdapter) {
		this.reviewAssessmentAdapter = reviewAssessmentAdapter;
	}

	/**
	 * Getter Method: getWizardModel()
	 */	
	private AbstractWizardModel getWizardModel() {
		return wizardModel;
	}

	/**
	 * Setter Method: setWizardModel()
	 */
	private void setWizardModel(AbstractWizardModel wizardModel) {
		this.wizardModel = wizardModel;
	}
}
