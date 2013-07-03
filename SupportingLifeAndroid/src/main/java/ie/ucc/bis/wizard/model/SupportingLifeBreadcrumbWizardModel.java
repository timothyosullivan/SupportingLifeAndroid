package ie.ucc.bis.wizard.model;

import android.content.Context;


public class SupportingLifeBreadcrumbWizardModel extends AbstractWizardModel {

	public SupportingLifeBreadcrumbWizardModel(Context context) {
		super(context);
	}

	@Override
	protected PageList onNewRootPageList() {
		return new PageList(new CustomerInfoPage(this, "Your info").setRequired(true),
							new CustomerInfoPage(this, "Second Info").setRequired(true));
	}
}
