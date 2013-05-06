package fr.irisa.julienrfphd.choze.client;

import com.google.gwt.dom.client.Element;
import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.event.dom.client.ClickHandler;
import com.google.gwt.user.client.Window;
import com.google.gwt.user.client.ui.FlowPanel;
import com.google.gwt.user.client.ui.RootPanel;

import fr.irisa.julienrfphd.choze.client.template.Footer;
import fr.irisa.julienrfphd.choze.client.template.WelcomePage;

public class Welcome extends FlowPanel {

  /** Singleton stuff - to access Main from all subclasses! */  
  private static Welcome singelton;
  public static Welcome getInstance() { 
    if (singelton == null) {singelton = new Welcome();}
    return singelton;
  }

  /** Constructor - called by Main.onModuleLoad() */
  private Welcome() {
	  Element welcome = new WelcomePage().getElement();
	  welcome.setId("welcome");
	  
	  
	 RootPanel.getBodyElement().appendChild(welcome);
	 
	 Element footer = new Footer().getElement();
	 footer.setId("footer");
	  
	  
	 RootPanel.getBodyElement().appendChild(footer);
	 
	 RootPanel.get("create-poll").addHandler(new ClickHandler() {
		
		public void onClick(ClickEvent event) {
			Window.alert("toto");
			
		}
	},  ClickEvent.getType());
	 
	 
  }
}