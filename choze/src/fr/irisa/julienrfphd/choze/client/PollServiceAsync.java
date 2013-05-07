package fr.irisa.julienrfphd.choze.client;

import java.util.List;

import com.google.gwt.user.client.rpc.AsyncCallback;

import fr.irisa.julienrfphd.choze.shared.exception.ValidatonViolation;
import fr.irisa.julienrfphd.choze.shared.model.Alternative;
import fr.irisa.julienrfphd.choze.shared.model.Poll;

/**
 * The async counterpart of <code>GreetingService</code>.
 */
public interface PollServiceAsync {
	
	void createPoll(Poll p, AsyncCallback<Void> callback)throws ValidatonViolation;

	void getPollByName(String name, AsyncCallback<Poll> callback);
	
	void getAlternativeByPollName(String name,
			AsyncCallback<List<Alternative>> callback);
	
	
}
