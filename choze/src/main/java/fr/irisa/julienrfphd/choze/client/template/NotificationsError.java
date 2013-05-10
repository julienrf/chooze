package fr.irisa.julienrfphd.choze.client.template;

import com.google.gwt.core.client.GWT;
import com.google.gwt.dom.client.Element;
import com.google.gwt.uibinder.client.UiBinder;
import com.google.gwt.user.client.DOM;
import com.google.gwt.user.client.Event;
import com.google.gwt.user.client.EventListener;
import com.google.gwt.user.client.ui.RootPanel;
import com.google.gwt.user.client.ui.UIObject;

public class NotificationsError extends UIObject {

	private static NotificationsUiBinder uiBinder = GWT
			.create(NotificationsUiBinder.class);

	interface NotificationsUiBinder extends UiBinder<Element, NotificationsError> {
	}

	public NotificationsError() {
		setElement(uiBinder.createAndBindUi(this));
		com.google.gwt.user.client.Element e1 =(com.google.gwt.user.client.Element) this.getElement().getElementsByTagName("span").getItem(0);
		DOM.sinkEvents(e1, Event.ONCLICK);
		DOM.setEventListener(e1, new NotDeleteEventListener(this));
		
	}


	class NotDeleteEventListener implements EventListener{
		
		NotificationsError alt;
		
		public NotDeleteEventListener(NotificationsError alt) {
			super();
			this.alt = alt;
		}

		public void onBrowserEvent(Event event) {
			RootPanel.getBodyElement().getElementsByTagName("header").getItem(0).removeChild(alt.getElement());
		}
		
	}

}
