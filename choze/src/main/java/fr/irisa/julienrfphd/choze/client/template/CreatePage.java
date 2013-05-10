package fr.irisa.julienrfphd.choze.client.template;

import com.google.gwt.core.client.GWT;
import com.google.gwt.uibinder.client.UiBinder;
import com.google.gwt.user.client.ui.Composite;
import com.google.gwt.user.client.ui.Widget;

public class CreatePage extends Composite {

	private static CreatePageUiBinder uiBinder = GWT
			.create(CreatePageUiBinder.class);

	interface CreatePageUiBinder extends UiBinder<Widget, CreatePage> {
	}

	public CreatePage() {
		initWidget(uiBinder.createAndBindUi(this));
	

		
	}

}
