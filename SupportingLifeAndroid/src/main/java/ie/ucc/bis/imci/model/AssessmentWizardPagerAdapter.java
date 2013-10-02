package ie.ucc.bis.imci.model;

import ie.ucc.bis.activity.AssessmentWizardActivity;
import ie.ucc.bis.imci.ui.ReviewFragment;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;
import android.view.ViewGroup;

/**
 * AssessmentWizardPagerAdapter uses a Fragment to manage each page. 
 * This class also handles saving and restoring of fragment's state.
 * 
 * @author timothyosullivan
 *
 */
public class AssessmentWizardPagerAdapter extends FragmentStatePagerAdapter {
    private int mCutOffPage;
    private Fragment mPrimaryItem;
    private AssessmentWizardActivity assessmentWizardActivity;

    public AssessmentWizardPagerAdapter(AssessmentWizardActivity assessmentWizardActivity, FragmentManager fm) {
        super(fm);
        setAssessmentWizardActivity(assessmentWizardActivity);
    }

    @Override
    public Fragment getItem(int i) {
        if (i >= getAssessmentWizardActivity().getCurrentPageSequence().size()) {
            return new ReviewFragment();
        }

        return getAssessmentWizardActivity().getCurrentPageSequence().get(i).createFragment();
    }

    @Override
    public int getItemPosition(Object object) {
        // TODO: be smarter about this
        if (object == mPrimaryItem) {
            // Re-use the current fragment (its position never changes)
            return POSITION_UNCHANGED;
        }

        return POSITION_NONE;
    }

    @Override
    public void setPrimaryItem(ViewGroup container, int position, Object object) {
        super.setPrimaryItem(container, position, object);
        mPrimaryItem = (Fragment) object;
    }

    @Override
    public int getCount() {
        return Math.min(mCutOffPage + 1, 
        		getAssessmentWizardActivity().getCurrentPageSequence() != null ? getAssessmentWizardActivity().getCurrentPageSequence().size() + 1
        				: mCutOffPage + 1);
    }

    public void setCutOffPage(int cutOffPage) {
        if (cutOffPage < 0) {
            cutOffPage = Integer.MAX_VALUE;
        }
        mCutOffPage = cutOffPage;
    }

    public int getCutOffPage() {
        return mCutOffPage;
    }

	/**
	 * Getter Method: getAssessmentWizardActivity()
	 * 
	 */	    
	public AssessmentWizardActivity getAssessmentWizardActivity() {
		return assessmentWizardActivity;
	}

	/**
	 * Setter Method: setAssessmentWizardActivity()
	 * 
	 */  	
	public void setAssessmentWizardActivity(AssessmentWizardActivity assessmentWizardActivity) {
		this.assessmentWizardActivity = assessmentWizardActivity;
	}
}