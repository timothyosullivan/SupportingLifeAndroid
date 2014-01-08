package ie.ucc.bis.supportinglife.ui.utilities;

/**
 * 
 * @author timothyosullivan
 */
import ie.ucc.bis.supportinglife.assessment.imci.model.DynamicView;

import java.util.List;

import android.view.ViewGroup;

public class ViewGroupUtilities {

	public static void removeDynamicViews(ViewGroup viewGroup, List<DynamicView> dynamicViews) {	
		for (DynamicView dynamicView : dynamicViews) {
			viewGroup.removeView(dynamicView.getWrappedView());
		}
	}
}
