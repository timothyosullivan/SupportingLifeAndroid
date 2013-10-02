package ie.ucc.bis.training.ui;

import ie.ucc.bis.R;
import ie.ucc.bis.activity.TrainingActivity;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;
import android.support.v4.view.ViewPager;
import android.util.SparseArray;
import android.view.ViewGroup;

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
	private SparseArray<Fragment> registeredFragments = new SparseArray<Fragment>();
	
	/**
	 * Constructor
	 * 
	 * @param trainingActivity
	 * @param fm
	 */
	public TrainingPagerAdapter(TrainingActivity trainingActivity, FragmentManager fragmentManager) {
		super(fragmentManager);
		setTrainingActivity(trainingActivity);
		setFragmentManager(fragmentManager);
	}

	@Override
	public Fragment getItem(int position) {
		
		float scale;
		
		// make the first pager bigger than others
		if (position == TrainingActivity.FIRST_PAGE) {
        	scale = TrainingActivity.BIG_SCALE;
        }
		else {
        	scale = TrainingActivity.SMALL_SCALE;
        }
        
        position = position % TrainingActivity.PAGES;

		return TrainingFragment.create(getTrainingActivity(), position, scale);
	}

	@Override
	public int getCount() {
		// return TrainingActivity.PAGES;
		return TrainingActivity.PAGES * TrainingActivity.LOOPS;
		// return Integer.MAX_VALUE;
	}

	@Override
	public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {
		
		if (positionOffset >= 0f && positionOffset <= 1f) {
			TrainingCustomLayout previousPage = getRootView(position - 1);
			TrainingCustomLayout currentPage = getRootView(position);
			TrainingCustomLayout nextPage = getRootView(position + 1);

			if (previousPage != null) {
				previousPage.setPageScale(TrainingActivity.SMALL_SCALE 
						+ TrainingActivity.DIFF_SCALE * positionOffset);
			}
			
			if (nextPage != null) {
				nextPage.setPageScale(TrainingActivity.SMALL_SCALE 
						+ TrainingActivity.DIFF_SCALE * positionOffset);
			}
			
			if (currentPage != null) {
				currentPage.setPageScale(TrainingActivity.BIG_SCALE 
						- TrainingActivity.DIFF_SCALE * positionOffset);
			}
		}
	}

	@Override
	public void onPageSelected(int position) {
		TrainingCustomLayout nextPage = getRootView(position + 1);
		
		if (nextPage != null) {
			nextPage.setPageScale(TrainingActivity.SMALL_SCALE);
		}
	}

	@Override
	public void onPageScrollStateChanged(int state) {}
	
	private TrainingCustomLayout getRootView(int position)
	{
		Fragment trainingFragment = getRegisteredFragment(position);
		if (trainingFragment != null) {
			return (TrainingCustomLayout) trainingFragment.getView().findViewById(R.id.training_custom_layout_root);
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
}
