package fr.irisa.julienrfphd.choze.client.template;

import com.google.gwt.core.client.GWT;
import com.google.gwt.dom.client.Element;
import com.google.gwt.dom.client.InputElement;
import com.google.gwt.dom.client.SpanElement;
import com.google.gwt.uibinder.client.UiBinder;
import com.google.gwt.uibinder.client.UiField;
import com.google.gwt.user.client.ui.UIObject;

public class PollChoice extends UIObject {

	private static PollChoiceUiBinder uiBinder = GWT
			.create(PollChoiceUiBinder.class);

	interface PollChoiceUiBinder extends UiBinder<Element, PollChoice> {
	}
	
	
	
	
	@UiField
	SpanElement valuemin;
	
	@UiField
	SpanElement valuemax;
	
	@UiField
	InputElement value;
	
	@UiField
	SpanElement name;
	
	public PollChoice(String _name, String _id, String _valuemax, String _valuemin) {
		valuemin.setInnerHTML(_valuemin);
		valuemax.setInnerHTML(_valuemax);
		value.setId(_id);
		name.setInnerHTML(_name);
		setElement(uiBinder.createAndBindUi(this));
	}

}
