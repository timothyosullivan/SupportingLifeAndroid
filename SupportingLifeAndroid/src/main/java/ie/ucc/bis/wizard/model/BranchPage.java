package ie.ucc.bis.wizard.model;

import android.support.v4.app.Fragment;
import android.text.TextUtils;

import java.util.ArrayList;
import java.util.List;

/**
 * A page representing a branching point in the wizard. Depending on which choice is selected, the
 * next set of steps in the wizard may change.
 */
public class BranchPage extends AbstractPage {
    private List<Branch> mBranches = new ArrayList<Branch>();

    public BranchPage(ModelCallbacks callbacks, String title) {
        super(callbacks, title);
    }

    @Override
    public AbstractPage findPageByKey(String key) {
        if (getKey().equals(key)) {
            return this;
        }

        for (Branch branch : mBranches) {
            AbstractPage found = branch.childPageList.findPageByKey(key);
            if (found != null) {
                return found;
            }
        }

        return null;
    }

    @Override
    public void flattenCurrentPageSequence(ArrayList<AbstractPage> destination) {
        super.flattenCurrentPageSequence(destination);
        for (Branch branch : mBranches) {
            if (branch.choice.equals(mData.getString(AbstractPage.SIMPLE_DATA_KEY))) {
                branch.childPageList.flattenCurrentPageSequence(destination);
                break;
            }
        }
    }

    public BranchPage addBranch(String choice, AbstractPage... childPages) {
        PageList childPageList = new PageList(childPages);
        for (AbstractPage page : childPageList) {
            page.setParentKey(choice);
        }
        mBranches.add(new Branch(choice, childPageList));
        return this;
    }

//    @Override
//    public Fragment createFragment() {
//        return SingleChoiceFragment.create(getKey());
//    }
    
  @Override
  public Fragment createFragment() {
      return null;
  }


    public String getOptionAt(int position) {
        return mBranches.get(position).choice;
    }

    public int getOptionCount() {
        return mBranches.size();
    }

	/**
	 * Method: getReviewItems
	 * 
	 * Define the review items associated with the branch page.
	 * 
	 * @param reviewItems : ArrayList<ReviewItem>
	 */
    @Override
    public void getReviewItems(ArrayList<ReviewItem> reviewItems) {
    	reviewItems.add(new ReviewItem(getTitle(), mData.getString(SIMPLE_DATA_KEY), getKey()));
    }

    @Override
    public boolean isCompleted() {
        return !TextUtils.isEmpty(mData.getString(SIMPLE_DATA_KEY));
    }

    @Override
    public void notifyDataChanged() {
        getModelCallbacks().onPageTreeChanged();
        super.notifyDataChanged();
    }

    public BranchPage setValue(String value) {
        mData.putString(SIMPLE_DATA_KEY, value);
        return this;
    }

    private static class Branch {
        public String choice;
        public PageList childPageList;

        private Branch(String choice, PageList childPageList) {
            this.choice = choice;
            this.childPageList = childPageList;
        }
    }
}
