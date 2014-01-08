package ie.ucc.bis.supportinglife.activity;

import ie.ucc.bis.supportinglife.R;
import ie.ucc.bis.supportinglife.training.TrainingTutorialParser;
import ie.ucc.bis.supportinglife.training.ui.TrainingPagerAdapter;
import android.os.Bundle;
import android.support.v4.view.ViewPager;

/**
 * 
 * @author timothyosullivan
 */

public class TrainingActivity extends SupportingLifeBaseActivity {
	
//	public final static int PAGES = 6;
	public final static int LOOPS = 1000; 
//	public final static int FIRST_PAGE = PAGES * LOOPS / 2;
	public final static float BIG_SCALE = 1.0f;
	public final static float SMALL_SCALE = 0.7f;
	public final static float DIFF_SCALE = BIG_SCALE - SMALL_SCALE;

	private static final int PAGE_MARGIN = -400;
	private static final int OFF_SCREEN_PAGE_LIMIT = 2;
	
	private ViewPager trainingViewPager;
    private TrainingPagerAdapter trainingPagerAdapter;
    private TrainingTutorialParser trainingTutorialParser;
    private int initialPageLocation;

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

		setContentView(R.layout.activity_training);	
		setTitleFromActivityLabel(R.id.action_bar_title_text);
		
		// parse training tutorial details
		setTrainingTutorialParser(new TrainingTutorialParser());
		getTrainingTutorialParser().parseTrainingTutorials(this);
		
		// determine the initial page to be shown
		setInitialPageLocation(getTrainingTutorialParser().getTutorials().size() * LOOPS / 2);
		
		// configure PagerAdapter for Training Screen
		setTrainingPagerAdapter(new TrainingPagerAdapter(this, getSupportFragmentManager(), getTrainingTutorialParser().getTutorials()));
		
        // configure ViewPager for Training Screen
        setTrainingViewPager((ViewPager) findViewById(R.id.training_view_pager));
        getTrainingViewPager().setAdapter(getTrainingPagerAdapter());
        getTrainingViewPager().setOnPageChangeListener(getTrainingPagerAdapter());
        
        // Set current item to the middle page so we can fling to both
        // directions left and right
        getTrainingViewPager().setCurrentItem(getInitialPageLocation());
        
        // Necessary or the pager will only have one extra page to show
        // make this at least however many pages you can see
        getTrainingViewPager().setOffscreenPageLimit(OFF_SCREEN_PAGE_LIMIT);
        
        // Set margin for pages as a negative number, so a part of next and 
        // previous pages will be showed
        getTrainingViewPager().setPageMargin(PAGE_MARGIN);
        
		// add soft keyboard handler - essentially hiding soft
		// keyboard when an EditText is not in focus
		 addSoftKeyboardHandling(findViewById(R.id.training_tutorials));
	}
	
	/**
	 * Getter Method: getTrainingViewPager()
	 */
	public ViewPager getTrainingViewPager() {
		return trainingViewPager;
	}

	/**
	 * Setter Method: setTrainingViewPager()
	 */
	public void setTrainingViewPager(ViewPager trainingViewPager) {
		this.trainingViewPager = trainingViewPager;
	}

	/**
	 * Getter Method: getTrainingPagerAdapter()
	 */
	public TrainingPagerAdapter getTrainingPagerAdapter() {
		return trainingPagerAdapter;
	}

	/**
	 * Setter Method: setTrainingPagerAdapter()
	 */
	public void setTrainingPagerAdapter(TrainingPagerAdapter trainingPagerAdapter) {
		this.trainingPagerAdapter = trainingPagerAdapter;
	}

	/**
	 * Getter Method: getTrainingTutorialParser()
	 */
	public TrainingTutorialParser getTrainingTutorialParser() {
		return trainingTutorialParser;
	}

	/**
	 * Setter Method: setTrainingTutorialParser()
	 */
	public void setTrainingTutorialParser(TrainingTutorialParser trainingTutorialParser) {
		this.trainingTutorialParser = trainingTutorialParser;
	}

	/**
	 * Getter Method: getInitialPageLocation()
	 */
	public int getInitialPageLocation() {
		return initialPageLocation;
	}

	/**
	 * Setter Method: setInitialPageLocation()
	 */
	private void setInitialPageLocation(int initialPageLocation) {
		this.initialPageLocation = initialPageLocation;
	}
}
