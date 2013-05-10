package fr.irisa.julienrfphd.choze.client.template;

import java.util.ArrayList;
import java.util.List;

import com.google.gwt.core.client.GWT;
import com.google.gwt.dom.client.AnchorElement;
import com.google.gwt.dom.client.Element;
import com.google.gwt.dom.client.HRElement;
import com.google.gwt.uibinder.client.UiBinder;
import com.google.gwt.uibinder.client.UiField;
import com.google.gwt.user.client.DOM;
import com.google.gwt.user.client.Window.Location;
import com.google.gwt.user.client.rpc.AsyncCallback;
import com.google.gwt.user.client.ui.Composite;
import com.google.gwt.user.client.ui.Widget;

import fr.irisa.julienrfphd.choze.client.PollService;
import fr.irisa.julienrfphd.choze.client.PollServiceAsync;
import fr.irisa.julienrfphd.choze.shared.model.Alternative;
import fr.irisa.julienrfphd.choze.shared.model.Poll;

public class ParticipatePage extends Composite {

	private static ParticipatePageUiBinder uiBinder = GWT
			.create(ParticipatePageUiBinder.class);

	interface ParticipatePageUiBinder extends UiBinder<Widget, ParticipatePage> {
	}

	private final PollServiceAsync crudService = GWT.create(PollService.class);

	@UiField
	Element alternatives;

	@UiField
	AnchorElement link;

	@UiField
	AnchorElement linkresult;

	List<String> toRemove = new ArrayList<String>();

	public ParticipatePage() {

		initWidget(uiBinder.createAndBindUi(this));

	}

	public void setContent(String name) {
		for (String s : toRemove) {
			DOM.getElementById(s).removeFromParent();
		}
		toRemove.clear();
		crudService.getPollByName(name, new AsyncCallback<Poll>() {

			public void onSuccess(Poll result) {
				link.setAttribute("href", Location.getHref());
				link.setHref(Location.getHref());
				linkresult.setHref(Location.getHref() + "/result");
				int i = 0;
				if (result != null) {
					for (Alternative a : result.getAlternatives()) {
						alternatives.appendChild(new PollChoice(a.getName(),
								"alt" + i, "no", "yes").getElement());
						toRemove.add("alt" + i);
						i++;
					}	
				}
			}

			public void onFailure(Throwable caught) {

			}
		});

	}

}
