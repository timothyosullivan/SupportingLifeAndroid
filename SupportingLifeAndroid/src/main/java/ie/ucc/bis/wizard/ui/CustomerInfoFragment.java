package ie.ucc.bis.wizard.ui;

import ie.ucc.bis.R;
import ie.ucc.bis.wizard.model.CustomerInfoPage;
import android.app.Activity;
import android.content.Context;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.InputMethodManager;
import android.widget.TextView;

public class CustomerInfoFragment extends Fragment {
	
    private static final String ARG_PAGE_KEY = "PAGE_KEY";

    private CustomerInfoPage customerInfoPage;    
    private PageFragmentCallbacks pageFragmentCallbacks;
    private String pageKey;
    private TextView nameTextView;
    private TextView emailTextView;

    
    public static CustomerInfoFragment create(String pageKey) {
        Bundle args = new Bundle();
        args.putString(ARG_PAGE_KEY, pageKey);

        CustomerInfoFragment fragment = new CustomerInfoFragment();
        fragment.setArguments(args);
        return fragment;
    }

	/**
	 * Constructor
	 *
	 */        
    public CustomerInfoFragment() {}

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        Bundle args = getArguments();
        setPageKey(args.getString(ARG_PAGE_KEY));
        setCustomerInfoPage((CustomerInfoPage) getPageFragmentCallbacks().getPage(getPageKey()));
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
            Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_page_customer_info, container, false);
        ((TextView) rootView.findViewById(android.R.id.title)).setText(getCustomerInfoPage().getTitle());

        setNameTextView(((TextView) rootView.findViewById(R.id.your_name)));
        getNameTextView().setText(getCustomerInfoPage().getPageData().getString(CustomerInfoPage.NAME_DATA_KEY));

        setEmailTextView(((TextView) rootView.findViewById(R.id.your_email)));
        getEmailTextView().setText(getCustomerInfoPage().getPageData().getString(CustomerInfoPage.EMAIL_DATA_KEY));
        return rootView;
    }

    @Override
    public void onAttach(Activity activity) {
        super.onAttach(activity);

        if (!(activity instanceof PageFragmentCallbacks)) {
            throw new ClassCastException("Activity must implement PageFragmentCallbacks");
        }

        setPageFragmentCallbacks((PageFragmentCallbacks) activity);
    }

    @Override
    public void onDetach() {
        super.onDetach();
        setPageFragmentCallbacks(null);
    }

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        getNameTextView().addTextChangedListener(new TextWatcher() {
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {
            }

            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
            }

            public void afterTextChanged(Editable editable) {
            	getCustomerInfoPage().getPageData().putString(CustomerInfoPage.NAME_DATA_KEY,
                        (editable != null) ? editable.toString() : null);
            	getCustomerInfoPage().notifyDataChanged();
            }
        });

        getEmailTextView().addTextChangedListener(new TextWatcher() {
            
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {
            }

            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
            }

            public void afterTextChanged(Editable editable) {
            	getCustomerInfoPage().getPageData().putString(CustomerInfoPage.EMAIL_DATA_KEY,
                        (editable != null) ? editable.toString() : null);
            	getCustomerInfoPage().notifyDataChanged();
            }
        });
    }

    @Override
    public void setMenuVisibility(boolean menuVisible) {
        super.setMenuVisibility(menuVisible);

        // In a future update to the support library, this should override setUserVisibleHint
        // instead of setMenuVisibility.
        if (getNameTextView() != null) {
            InputMethodManager imm = (InputMethodManager) getActivity().getSystemService(
                    Context.INPUT_METHOD_SERVICE);
            if (!menuVisible) {
                imm.hideSoftInputFromWindow(getView().getWindowToken(), 0);
            }
        }
    }

	/**
	 * Getter Method: getCustomerInfoPage()
	 * 
	 */
	public CustomerInfoPage getCustomerInfoPage() {
		return customerInfoPage;
	}

	/**
	 * Setter Method: setCustomerInfoPage()
	 * 
	 */   	
	public void setCustomerInfoPage(CustomerInfoPage customerInfoPage) {
		this.customerInfoPage = customerInfoPage;
	}

	/**
	 * Getter Method: getPageFragmentCallbacks()
	 * 
	 */
	public PageFragmentCallbacks getPageFragmentCallbacks() {
		return pageFragmentCallbacks;
	}

	/**
	 * Setter Method: setPageFragmentCallbacks()
	 * 
	 */
	public void setPageFragmentCallbacks(PageFragmentCallbacks pageFragmentCallbacks) {
		this.pageFragmentCallbacks = pageFragmentCallbacks;
	}
	
	/**
	 * Getter Method: getPageKey()
	 * 
	 */	
	public String getPageKey() {
		return pageKey;
	}

	/**
	 * Setter Method: setPageKey()
	 * 
	 */	
	public void setPageKey(String pageKey) {
		this.pageKey = pageKey;
	}

	/**
	 * Getter Method: getNameTextView()
	 * 
	 */		
	public TextView getNameTextView() {
		return nameTextView;
	}

	/**
	 * Setter Method: setNameTextView()
	 * 
	 */		
	public void setNameTextView(TextView nameTextView) {
		this.nameTextView = nameTextView;
	}

	/**
	 * Getter Method: getEmailTextView()
	 * 
	 */		
	public TextView getEmailTextView() {
		return emailTextView;
	}

	/**
	 * Setter Method: setEmailTextView()
	 * 
	 */		
	public void setEmailTextView(TextView emailTextView) {
		this.emailTextView = emailTextView;
	}
}
