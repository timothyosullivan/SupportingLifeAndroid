package ie.ucc.bis.assessment.model;

import ie.ucc.bis.activity.AssessmentActivity;
import ie.ucc.bis.imci.ui.ReviewFragment;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;
import android.view.ViewGroup;

/**
 * AssessmentPagerAdapter uses a Fragment to manage each page. 
 * This class also handles saving and restoring of fragment's state.
 * 
 * @author timothyosullivan
 *
 */
public class AssessmentPagerAdapter extends FragmentStatePagerAdapter {
    private int mCutOffPage;
    private Fragment mPrimaryItem;
    private AssessmentActivity assessmentActivity;

    public AssessmentPagerAdapter(AssessmentActivity assessmentActivity, FragmentManager fm) {
        super(fm);
        setAssessmentActivity(assessmentActivity);
    }

    @Override
    public Fragment getItem(int i) {
        if (i >= getAssessmentActivity().getPageSequence().size()) {
            return new ReviewFragment();
        }

        return getAssessmentActivity().getPageSequence().get(i).createFragment();
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
        		getAssessmentActivity().getPageSequence() != null ? getAssessmentActivity().getPageSequence().size() + 1
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
	 * Getter Method: getAssessmentActivity()
	 */
	public AssessmentActivity getAssessmentActivity() {
		return assessmentActivity;
	}

	/**
	 * Setter Method: setAssessmentActivity()
	 */
	public void setAssessmentActivity(AssessmentActivity assessmentActivity) {
		this.assessmentActivity = assessmentActivity;
	}
}