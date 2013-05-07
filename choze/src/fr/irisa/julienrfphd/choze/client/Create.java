package fr.irisa.julienrfphd.choze.client;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;

import javax.validation.ConstraintViolation;
import javax.validation.Validation;
import javax.validation.Validator;

import com.google.gwt.core.shared.GWT;
import com.google.gwt.dom.client.DivElement;
import com.google.gwt.dom.client.Element;
import com.google.gwt.dom.client.InputElement;
import com.google.gwt.dom.client.NodeList;
import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.event.dom.client.ClickHandler;
import com.google.gwt.user.client.DOM;
import com.google.gwt.user.client.rpc.AsyncCallback;
import com.google.gwt.user.client.ui.Button;
import com.google.gwt.user.client.ui.FlowPanel;
import com.google.gwt.user.client.ui.RootPanel;
import com.google.gwt.user.client.ui.TextArea;
import com.google.gwt.user.client.ui.TextBox;

import fr.irisa.julienrfphd.choze.client.template.CreateAlternative;
import fr.irisa.julienrfphd.choze.client.template.CreatePage;
import fr.irisa.julienrfphd.choze.client.template.Footer;
import fr.irisa.julienrfphd.choze.client.template.NotificationsError;
import fr.irisa.julienrfphd.choze.shared.exception.ValidatonViolation;
import fr.irisa.julienrfphd.choze.shared.model.Alternative;
import fr.irisa.julienrfphd.choze.shared.model.Poll;

public class Create extends FlowPanel {

	PollServiceAsync serv = GWT.create(PollService.class);

	/** Singleton stuff - to access Main from all subclasses! */
	private static Create singelton;

	private final PollServiceAsync crudService = GWT.create(PollService.class);

	public static Create getInstance() {
		if (singelton == null) {
			singelton = new Create();
		}
		return singelton;
	}

	int i = 2;

	List<Element> toRemove = new ArrayList<Element>();

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
				for (Element e : toRemove) {
					e.removeFromParent();
				}
				Poll poll = new Poll();
				poll.setName(name.getText());
				poll.setDescription(desc.getText());
				Poll p = new Poll();
				p.setDescription(desc.getText());
				p.setName(name.getText());
				List<Alternative> alts = new ArrayList<Alternative>();

				NodeList<Element> list = RootPanel.get("alternatives")
						.getElement().getElementsByTagName("input");

				for (int i = 0; i < RootPanel.get("alternatives").getElement()
						.getChildCount() - 1; i++) {
					Alternative alt = new Alternative();
					InputElement e = (InputElement) list.getItem(i);
					alt.setName(e.getValue());

					alt.setHtmlid(e.getId());
					alts.add(alt);
				}
				p.setAlternatives(alts);
				try {
					serv.createPoll(p, new AsyncCallback<Void>() {

						@Override
						public void onFailure(Throwable caught) {

							if (caught instanceof ValidatonViolation) {
								Element e1 = new NotificationsError()
								.getElement();
								toRemove.add(e1);
								DOM.getElementById("header")
										.appendChild(
												e1);
								ValidatonViolation er = (ValidatonViolation) caught;
								for (String[] s : er.getErrors()) {
									if (s[0].startsWith("alternatives")) {
										DivElement div = DivElement
												.as(new fr.irisa.julienrfphd.choze.client.template.Error(
														s[1]).getElement());
										toRemove.add(div);
										DOM.getElementById(s[0])
												.getParentElement().getParentElement()
												.appendChild(div);
									} else {
										Element e = new fr.irisa.julienrfphd.choze.client.template.Error(
												s[1]).getElement();
										toRemove.add(e);
										DOM.getElementById(s[0])
												.getParentElement()
												.appendChild(e);
									}
								}

							}

						}

						@Override
						public void onSuccess(Void result) {

						}
					});
				} catch (ValidatonViolation e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}

			}
		});

	}

	public Set<ConstraintViolation<Poll>> checkInputPoll(Poll poll) {
		Validator validator = Validation.buildDefaultValidatorFactory()
				.getValidator();
		Set<ConstraintViolation<Poll>> violations = validator.validate(poll);
		return violations;
	}

	public Set<ConstraintViolation<Alternative>> checkInputAlternative(
			Alternative alt) {
		Validator validator = Validation.buildDefaultValidatorFactory()
				.getValidator();
		Set<ConstraintViolation<Alternative>> violations = validator
				.validate(alt);
		return violations;
	}

}