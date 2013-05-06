package fr.irisa.julienrfphd.choze.client.template;

import com.google.gwt.core.client.GWT;
import com.google.gwt.uibinder.client.UiBinder;
import com.google.gwt.user.client.ui.Composite;
import com.google.gwt.user.client.ui.Widget;

public class ParticipatePage extends Composite {

	private static ParticipatePageUiBinder uiBinder = GWT
			.create(ParticipatePageUiBinder.class);

	interface ParticipatePageUiBinder extends UiBinder<Widget, ParticipatePage> {
	}

	public ParticipatePage() {
		initWidget(uiBinder.createAndBindUi(this));
	}

}
