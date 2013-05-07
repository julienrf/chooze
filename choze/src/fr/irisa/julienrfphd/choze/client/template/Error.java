package fr.irisa.julienrfphd.choze.client.template;

import com.google.gwt.core.client.GWT;
import com.google.gwt.dom.client.DivElement;
import com.google.gwt.dom.client.Element;
import com.google.gwt.uibinder.client.UiBinder;
import com.google.gwt.uibinder.client.UiField;
import com.google.gwt.user.client.ui.UIObject;

public class Error extends UIObject {

	private static ErrorUiBinder uiBinder = GWT.create(ErrorUiBinder.class);

	interface ErrorUiBinder extends UiBinder<Element, Error> {
	}

	
	
	@UiField
	DivElement div;
	

	public DivElement getDiv() {
		return div;
	}


	public void setDiv(DivElement div) {
		this.div = div;
	}


	public Error(String text) {
		setElement(uiBinder.createAndBindUi(this));
		div.setInnerHTML(text);
	}

}
