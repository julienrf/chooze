package fr.irisa.julienrfphd.choze.client;

import com.google.gwt.core.client.EntryPoint;
import com.google.gwt.user.client.Window;
import com.google.gwt.user.client.ui.RootPanel;

/**
 * Entry point classes define <code>onModuleLoad()</code>.
 */
public class Choze implements EntryPoint {



  public void onModuleLoad() {

    String url = Window.Location.getHref();
    //Window.alert(url + " " + url.indexOf("#") + " " +url.indexOf("/result"));
    if ( url.indexOf("#create")>-1 ) {
      Create install = Create.getInstance();
      RootPanel.get().add(install);
    }
    else if ( url.indexOf("#")> -1 && url.indexOf("/result")>-1 ) {
      Result res = Result.getInstance();
      RootPanel.get().add(res);
    } else if ( url.contains("#") ){
      PollPage app = PollPage.getInstance();

      RootPanel.get().add(app);
    }
    else {
        Welcome app = Welcome.getInstance();
        RootPanel.get().add(app);
      }
  }
}
