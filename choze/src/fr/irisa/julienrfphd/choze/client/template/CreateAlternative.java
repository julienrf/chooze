package fr.irisa.julienrfphd.choze.client.template;

import com.google.gwt.core.client.GWT;
import com.google.gwt.dom.client.Element;
import com.google.gwt.uibinder.client.UiBinder;
import com.google.gwt.user.client.DOM;
import com.google.gwt.user.client.Event;
import com.google.gwt.user.client.EventListener;
import com.google.gwt.user.client.ui.Composite;
import com.google.gwt.user.client.ui.RootPanel;
import com.google.gwt.user.client.ui.Widget;

public class CreateAlternative extends Composite  {

	private static CreateAlternativeUiBinder uiBinder = GWT
			.create(CreateAlternativeUiBinder.class);

	interface CreateAlternativeUiBinder extends
			UiBinder<Widget, CreateAlternative> {
	}

	public CreateAlternative(final int id) {
		initWidget(uiBinder.createAndBindUi(this));
		Element e = this.getElement().getElementsByTagName("input").getItem(0);
		e.setId("alternatives["+id+"]");
		e.setPropertyString("name", "alternatives["+id+"]");
		com.google.gwt.user.client.Element e1 =(com.google.gwt.user.client.Element) this.getElement().getElementsByTagName("span").getItem(0);
		
		
		e1.setId("rem"+id);
	
		
		DOM.sinkEvents(e1, Event.ONCLICK);
		DOM.setEventListener(e1, new DeleteEventListener(this));
		
	}


	class DeleteEventListener implements EventListener{
		
		CreateAlternative alt;
		
		public DeleteEventListener(CreateAlternative alt) {
			super();
			this.alt = alt;
		}

		@Override
		public void onBrowserEvent(Event event) {
			RootPanel.get("alternatives").getElement().removeChild(alt.getElement());
		}
		
	}


}

