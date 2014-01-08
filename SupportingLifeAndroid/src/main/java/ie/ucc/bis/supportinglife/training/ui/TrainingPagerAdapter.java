package ie.ucc.bis.supportinglife.training.ui;

import ie.ucc.bis.supportinglife.R;
import ie.ucc.bis.supportinglife.activity.TrainingActivity;
import ie.ucc.bis.supportinglife.training.Tutorial;

import java.util.ArrayList;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;
import android.support.v4.view.ViewPager;
import android.util.SparseArray;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;

/**
 * TrainingPagerAdapter uses a Fragment to manage each training page. 
 * This class also handles saving and restoring of fragment's state.
 * 
 * @author timothyosullivan
 *
 */
public class TrainingPagerAdapter extends FragmentStatePagerAdapter implements ViewPager.OnPageChangeListener {

	private TrainingActivity trainingActivity;
	private FragmentManager fragmentManager;
	private ArrayList<Tutorial> tutorials;
	private SparseArray<Fragment> registeredFragments = new SparseArray<Fragment>();
	
	/**
	 * Constructor
	 * 
	 * @param trainingActivity
	 * @param tutorials 
	 * @param fm
	 */
	public TrainingPagerAdapter(TrainingActivity trainingActivity, FragmentManager fragmentManager, ArrayList<Tutorial> tutorials) {
		super(fragmentManager);
		setTrainingActivity(trainingActivity);
		setFragmentManager(fragmentManager);
		setTutorials(tutorials);
	}

	@Override
	public Fragment getItem(int position) {
		float scale;
		boolean centerPage;
		
		// make the first pager bigger than others
		if (position == getTrainingActivity().getInitialPageLocation()) {
        	scale = TrainingActivity.BIG_SCALE;
        	centerPage = true;
        }
		else {
        	scale = TrainingActivity.SMALL_SCALE;
        	centerPage = false;
        }
        
        position = position % getTutorials().size();
		return TrainingFragment.create(getTrainingActivity(), position, getTutorials().get(position), scale, centerPage);
	}

	@Override
	public int getCount() {
		return Integer.MAX_VALUE;
	}

	@Override
	public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {
		
		if (positionOffset >= 0f && positionOffset <= 1f) {
			LinearLayout previousPage = getRootView(position - 1);
			LinearLayout currentPage = getRootView(position);
			LinearLayout nextPage = getRootView(position + 1);

			if (previousPage != null) {
				((TrainingCustomLayout) previousPage.getChildAt(0)).adjustSize(TrainingActivity.SMALL_SCALE 
						+ TrainingActivity.DIFF_SCALE * positionOffset);
			}
			
			if (nextPage != null) {
				((TrainingCustomLayout) nextPage.getChildAt(0)).adjustSize(TrainingActivity.SMALL_SCALE 
						+ TrainingActivity.DIFF_SCALE * positionOffset);
			}
			
			if (currentPage != null) {
				((TrainingCustomLayout) currentPage.getChildAt(0)).adjustSize(TrainingActivity.BIG_SCALE 
						- TrainingActivity.DIFF_SCALE * positionOffset);
			}
		}
	}

	@Override
	public void onPageSelected(int position) {
		LinearLayout previousPageLayout = getRootView(position - 1);
		LinearLayout currentPageLayout  = getRootView(position);
		LinearLayout nextPageLayout  = getRootView(position + 1);
		
		if (previousPageLayout  != null) {
			modifyTutorialLayout(previousPageLayout, View.INVISIBLE, TrainingActivity.SMALL_SCALE);
		}
		
		if (nextPageLayout  != null) {
			modifyTutorialLayout(nextPageLayout, View.INVISIBLE, TrainingActivity.SMALL_SCALE);
		}
		
		if (currentPageLayout != null) {
			modifyTutorialLayout(currentPageLayout, View.VISIBLE, TrainingActivity.BIG_SCALE);
		}
		
	}

	@Override
	public void onPageScrollStateChanged(int state) {}

	/**
	 * Method: modifyTutorialLayout
	 * 
	 * Called to manipulate the layout of a page
	 * 		i.e. scale some aspects of the page and show/not show some ui components
	 * 
	 * @param pageLayout LinearLayout
	 * @param visibility int
	 * @param scale float
	 * 
	 */
	private void modifyTutorialLayout(LinearLayout pageLayout, int visibility, float scale) {
		((TrainingCustomLayout) pageLayout.getChildAt(0)).adjustSize(scale);
		pageLayout.getChildAt(1).setVisibility(visibility);
	}	
	
	private LinearLayout getRootView(int position)
	{
		Fragment trainingFragment = getRegisteredFragment(position);
		if (trainingFragment != null) {
			return (LinearLayout) trainingFragment.getView().findViewById(R.id.training_layout_root);
		}
		else {
			return null;
		}
	}

    @Override
    public Object instantiateItem(ViewGroup container, int position) {
        Fragment fragment = (Fragment) super.instantiateItem(container, position);
        registeredFragments.put(position, fragment);
        return fragment;
    }

    @Override
    public void destroyItem(ViewGroup container, int position, Object object) {
        registeredFragments.remove(position);
        super.destroyItem(container, position, object);
    }

    public Fragment getRegisteredFragment(int position) {
        return registeredFragments.get(position);
    }
	
	/**
	 * Getter Method: getTrainingActivity()
	 */
	public TrainingActivity getTrainingActivity() {
		return trainingActivity;
	}

	/**
	 * Setter Method: setTrainingActivity()
	 */
	public void setTrainingActivity(TrainingActivity trainingActivity) {
		this.trainingActivity = trainingActivity;
	}

	/**
	 * Getter Method: getFragmentManager()
	 */
	public FragmentManager getFragmentManager() {
		return fragmentManager;
	}

	/**
	 * Setter Method: setFragmentManager()
	 */
	public void setFragmentManager(FragmentManager fragmentManager) {
		this.fragmentManager = fragmentManager;
	}

	/**
	 * Getter Method: getRegisteredFragments()
	 */
	public SparseArray<Fragment> getRegisteredFragments() {
		return registeredFragments;
	}

	/**
	 * Setter Method: setRegisteredFragments()
	 */
	public void setRegisteredFragments(SparseArray<Fragment> registeredFragments) {
		this.registeredFragments = registeredFragments;
	}

	/**
	 * Getter Method: getTutorials()
	 */
	public ArrayList<Tutorial> getTutorials() {
		return tutorials;
	}

	/**
	 * Setter Method: setTutorials()
	 */
	public void setTutorials(ArrayList<Tutorial> tutorials) {
		this.tutorials = tutorials;
	}
}
