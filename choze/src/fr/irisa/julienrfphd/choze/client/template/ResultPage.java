package fr.irisa.julienrfphd.choze.client.template;

import com.google.gwt.core.client.GWT;
import com.google.gwt.uibinder.client.UiBinder;
import com.google.gwt.user.client.ui.Composite;
import com.google.gwt.user.client.ui.Widget;

public class ResultPage extends Composite {

	private static ResultPageUiBinder uiBinder = GWT
			.create(ResultPageUiBinder.class);

	interface ResultPageUiBinder extends UiBinder<Widget, ResultPage> {
	}

	public ResultPage() {
		initWidget(uiBinder.createAndBindUi(this));
	}

}
