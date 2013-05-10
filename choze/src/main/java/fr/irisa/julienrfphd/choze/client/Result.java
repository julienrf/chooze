package fr.irisa.julienrfphd.choze.client;

import com.google.gwt.dom.client.Element;
import com.google.gwt.user.client.ui.FlowPanel;
import com.google.gwt.user.client.ui.RootPanel;

import fr.irisa.julienrfphd.choze.client.template.Footer;
import fr.irisa.julienrfphd.choze.client.template.ResultPage;

public class Result extends FlowPanel {

  /** Singleton stuff - to access Main from all subclasses! */  
  private static Result singelton;
  public static Result getInstance() {
    if (singelton == null) {singelton = new Result();}
    return singelton;
  }

  /** Constructor - called by Main.onModuleLoad() */
  private Result() {
   
	  Element result = new ResultPage().getElement();
	  result.setId("resut");
	  
	  
	 RootPanel.getBodyElement().appendChild(result);
	 
	 Element footer = new Footer().getElement();
	 footer.setId("footer");
	  
	  
	 RootPanel.getBodyElement().appendChild(footer);
	  
  }
}