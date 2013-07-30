package ie.ucc.bis.wizard.ui;

import ie.ucc.bis.R;
import android.app.Activity;
import android.os.Bundle;
import android.support.v4.app.ListFragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;
import android.widget.Toast;

/**
 * Class: AssessmentClassificationsFragment
 * 
 * Responsible for displaying the classification assessment 
 * results list  
 * 
 * @author TOSullivan
 *
 */
public class AssessmentClassificationsFragment extends ListFragment {
    
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
    	  View myFragmentView = inflater.inflate(R.layout.fragment_assessment_classifications_tab, container, false);
    	     	 
    	  return myFragmentView;
    }

    @Override
    public void onListItemClick(ListView l, View v, int position, long id) {
        Activity activity = getActivity();
        
        if (activity != null) {   
    //        ListAdapter listAdapter = getListAdapter();
    //        LocationModel locationModel = (LocationModel) listAdapter.getItem(position);
            
            // Display a simple Toast to demonstrate that the click event is working. Notice that Fragments have a
            // getString() method just like an Activity, so that you can quickly access your localized Strings.
            Toast.makeText(activity, "TEST ITEM CLICK", Toast.LENGTH_SHORT).show();
        }
    }
	
}
