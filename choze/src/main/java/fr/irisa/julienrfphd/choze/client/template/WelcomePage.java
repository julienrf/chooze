package fr.irisa.julienrfphd.choze.client.template;

import com.google.gwt.core.client.GWT;
import com.google.gwt.dom.client.Element;
import com.google.gwt.uibinder.client.UiBinder;
import com.google.gwt.user.client.ui.UIObject;

public class WelcomePage extends UIObject {

	private static WelcomePageUiBinder uiBinder = GWT
			.create(WelcomePageUiBinder.class);

	public static WelcomePageUiBinder getUiBinder() {
		return uiBinder;
	}


	interface WelcomePageUiBinder extends UiBinder<Element, WelcomePage> {
	}


	public WelcomePage() {
		setElement(uiBinder.createAndBindUi(this));
	}

}
