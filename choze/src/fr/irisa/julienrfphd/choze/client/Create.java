package fr.irisa.julienrfphd.choze.client;

import java.util.Set;

import javax.validation.ConstraintViolation;
import javax.validation.Validation;
import javax.validation.Validator;

import com.google.gwt.core.shared.GWT;
import com.google.gwt.dom.client.Element;
import com.google.gwt.dom.client.NodeList;
import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.event.dom.client.ClickHandler;
import com.google.gwt.user.client.Window;
import com.google.gwt.user.client.rpc.AsyncCallback;
import com.google.gwt.user.client.ui.Button;
import com.google.gwt.user.client.ui.FlowPanel;
import com.google.gwt.user.client.ui.RootPanel;
import com.google.gwt.user.client.ui.TextArea;
import com.google.gwt.user.client.ui.TextBox;

import fr.irisa.julienrfphd.choze.client.template.CreateAlternative;
import fr.irisa.julienrfphd.choze.client.template.CreatePage;
import fr.irisa.julienrfphd.choze.client.template.Footer;
import fr.irisa.julienrfphd.choze.shared.model.Alternative;
import fr.irisa.julienrfphd.choze.shared.model.Poll;

public class Create extends FlowPanel {
	
	PollServiceAsync serv = GWT.create(PollService.class);
	

	/** Singleton stuff - to access Main from all subclasses! */
	private static Create singelton;

	public static Create getInstance() {
		if (singelton == null) {
			singelton = new Create();
		}
		return singelton;
	}

	int i = 2;

	/** Constructor - called by Main.onModuleLoad() */
	private Create() {
		Element create = new CreatePage().getElement();
		create.setId("create");
		RootPanel.getBodyElement().appendChild(create);
		Element footer = new Footer().getElement();
		footer.setId("footer");
		RootPanel.getBodyElement().appendChild(footer);

		final TextBox name = TextBox.wrap(RootPanel.get("name").getElement());
		final TextArea desc = TextArea.wrap(RootPanel.get("description")
				.getElement());

		RootPanel.get("alternatives").getElement()
				.appendChild(new CreateAlternative(0).getElement());
		RootPanel.get("alternatives").getElement()
				.appendChild(new CreateAlternative(1).getElement());

		Button b = Button.wrap(RootPanel.get("addAlt").getElement());
		b.addClickHandler(new ClickHandler() {

			@Override
			public void onClick(ClickEvent event) {
				RootPanel.get("alternatives").getElement()
						.appendChild(new CreateAlternative(i).getElement());
				i++;

			}
		});

		Button submit = Button.wrap(RootPanel.get("submit").getElement());
		submit.addClickHandler(new ClickHandler() {

			@Override
			public void onClick(ClickEvent event) {
				Poll poll = new Poll();
				poll.setName(name.getText());
				poll.setDescription(desc.getText());

				NodeList<Element> nodes = RootPanel.get("alternatives")
						.getElement().getElementsByTagName("input");
				for (int i = 0; i < nodes.getLength(); i++) {
					Alternative alt = new Alternative();
					alt.setName(nodes.getItem(i).getPropertyString("value"));
					poll.getAlternatives().add(alt);
					/*Set<ConstraintViolation<Alternative>>  errors = checkInputAlternative(alt);
					java.util.Iterator<ConstraintViolation<Alternative>> it = errors.iterator();
					while (it.hasNext()){
						ConstraintViolation<Alternative> p = it.next();
						Window.alert(p.getMessage());				
					}*/

					
				}
				/*Set<ConstraintViolation<Poll>>  errors = checkInputPoll(poll);
				java.util.Iterator<ConstraintViolation<Poll>> it = errors.iterator();
				while (it.hasNext()){
					ConstraintViolation<Poll> p = it.next();
					Window.alert(p.getMessage());				
				}*/
				
				serv.createPoll(poll,new AsyncCallback<Void>() {
					
					@Override
					public void onSuccess(Void result) {
						
					}
					
					@Override
					public void onFailure(Throwable caught) {
						
					}
				});
			}
		});

	}

	public Set<ConstraintViolation<Poll>>  checkInputPoll(Poll poll) {
		Validator validator = Validation.buildDefaultValidatorFactory().getValidator();
		Set<ConstraintViolation<Poll>> violations = validator.validate(poll);
		return violations;
	}
	public Set<ConstraintViolation<Alternative>>  checkInputAlternative(Alternative alt) {
		Validator validator = Validation.buildDefaultValidatorFactory().getValidator();
		Set<ConstraintViolation<Alternative>> violations = validator.validate(alt);
		return violations;
	}

}