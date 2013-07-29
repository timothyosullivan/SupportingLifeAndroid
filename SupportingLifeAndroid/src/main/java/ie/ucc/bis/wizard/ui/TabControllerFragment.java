package ie.ucc.bis.wizard.ui;

import ie.ucc.bis.R;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.TextView;

public class TabControllerFragment extends Fragment {

	private static final int ASSESSMENT_DETAILS_TAB_STATE = 0x0;
	private static final int CLASSIFICATIONS_TAB_STATE = 0x1;
	private static final int TREATMENTS_TAB_STATE = 0x2;
	private static final int IMMUNIZATIONS_TAB_STATE = 0x3;
	
	private int tabState;
	
	/* 
	 * Method: onCreateView() 
	 * 
	 * This method represents a lifecycle event that is unique to a Fragment. This is called when Android
	 * needs the layout for this Fragment. 
	 * 
	 * The call to LayoutInflater::inflate() simply takes the layout ID for the layout file, 
	 * the parent view that will hold the layout, and an option to add the inflated
	 * view to the parent.
	 * 
	 * This should always be false or an exception will be thrown. Android will add
	 * the view to the parent when necessary.
	 * 
	 * (non-Javadoc)
	 * @see android.support.v4.app.Fragment#onCreateView(android.view.LayoutInflater, android.view.ViewGroup, android.os.Bundle)
	 */
	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
	    View view = inflater.inflate(R.layout.assessment_results_fragment_tabs, container, false);
	    
	    // Grab the tab buttons from the layout and attach event handlers. The code just uses standard
	    // buttons for the tab widgets. These are bad tab widgets, design something better, this is just
	    // to keep the code simple.
	    TextView classificationsListViewTab = (TextView) view.findViewById(R.id.assessment_classifications_list_view_tab);

	    classificationsListViewTab.setOnClickListener(new OnClickListener() {
	        @Override
	        public void onClick(View v) {
	            // Switch the tab content to display the list view.
	        	displayAssessmentClassificationsView();
	        }
	    });
	    	    
	    return view;
	}
	
	/**
	 * Method: gotoClassificationsView()
	 * 
	 * Displays the assessment classifications tab view.
	 * 
	 */
	public void displayAssessmentClassificationsView() {
	    // tabState keeps track of which tab is currently displaying its contents.
	    // Perform a check to make sure the classifications 
		// list tab content isn't already displaying.
	    
	    if (getTabState() != CLASSIFICATIONS_TAB_STATE) {
	        // Update the tabState 
	       setTabState(CLASSIFICATIONS_TAB_STATE);
	        
	        // Fragments have access to their parent Activity's FragmentManager. You can
	        // obtain the FragmentManager like this.
	        FragmentManager fragmentManager = getFragmentManager();
	        
	        if (fragmentManager != null) {
	            // Perform the FragmentTransaction to load in the list tab content.
	            // Using FragmentTransaction#replace will destroy any Fragments
	            // currently inside R.id.fragment_content and add the new Fragment
	            // in its place.
	            FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
	            fragmentTransaction.replace(R.id.fragment_content, new AssessmentClassificationsListFragment());
	            fragmentTransaction.commit();
	        }
	    }
	}
	
	/**
	 * Getter Method: getTabState()
	 */		
	public int getTabState() {
		return tabState;
	}
	
	/**
	 * Setter Method: setTabState()
	 */  
	public void setTabState(int tabState) {
		this.tabState = tabState;
	}	
}

