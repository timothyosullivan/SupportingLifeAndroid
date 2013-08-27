package ie.ucc.bis.ui.utilities;

import ie.ucc.bis.wizard.model.DynamicView;

import java.util.List;

import android.view.ViewGroup;

public class ViewGroupUtilities {

	public static void removeDynamicViews(ViewGroup viewGroup, List<DynamicView> dynamicViews) {	
		for (DynamicView dynamicView : dynamicViews) {
			viewGroup.removeView(dynamicView.getWrappedView());
		}
	}
}
