package ie.ucc.bis.ui.custom;

import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseExpandableListAdapter;

public class RadioGroupExpandableListAdapter extends BaseExpandableListAdapter {

	@Override
	public int getGroupCount() {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public int getChildrenCount(int groupPosition) {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public Object getGroup(int groupPosition) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Object getChild(int groupPosition, int childPosition) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public long getGroupId(int groupPosition) {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public long getChildId(int groupPosition, int childPosition) {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public boolean hasStableIds() {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public View getGroupView(int groupPosition, boolean isExpanded,
			View convertView, ViewGroup parent) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public View getChildView(int groupPosition, int childPosition,
			boolean isLastChild, View convertView, ViewGroup parent) {
		if (convertView == null) {
			LayoutInflater inflater = (LayoutInflater)get.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
	    10
	            v = inflater.inflate(R.layout.item_layout, parent, false);
	    11
	        }
	    12
	     
	    13
	        TextView itemName = (TextView) v.findViewById(R.id.itemName);
	    14
	        TextView itemDescr = (TextView) v.findViewById(R.id.itemDescr);
	    15
	     
	    16
	        ItemDetail det = catList.get(groupPosition).getItemList().get(childPosition);
	    17
	     
	    18
	        itemName.setText(det.getName());
	    19
	        itemDescr.setText(det.getDescr());
	    20
	     
	    21
	        return v;
	    22
	     
	    23
	    }

	}

	@Override
	public boolean isChildSelectable(int groupPosition, int childPosition) {
		// TODO Auto-generated method stub
		return false;
	}

}
