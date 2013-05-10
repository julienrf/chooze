package fr.irisa.julienrfphd.choze.client;

import com.google.gwt.dom.client.Element;
import com.google.gwt.user.client.ui.FlowPanel;
import com.google.gwt.user.client.ui.RootPanel;

import fr.irisa.julienrfphd.choze.client.template.Footer;
import fr.irisa.julienrfphd.choze.client.template.ParticipatePage;

public class PollPage extends FlowPanel {

  /** Singleton stuff - to access Main from all subclasses! */  
  private static PollPage singelton;
private ParticipatePage part;
  public ParticipatePage getPart() {
	return part;
}

public static PollPage getInstance() {
    if (singelton == null) {singelton = new PollPage();}
    return singelton;
  }

  /** Constructor - called by Main.onModuleLoad() */
  private PollPage() {
	  part = new ParticipatePage();
	  Element participate = new ParticipatePage().getElement();
	  participate.setId("participate");
	 RootPanel.getBodyElement().appendChild(participate);
	 Element footer = new Footer().getElement();
	 footer.setId("footer");
	 RootPanel.getBodyElement().appendChild(footer);
  }
}