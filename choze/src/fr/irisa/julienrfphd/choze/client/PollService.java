package fr.irisa.julienrfphd.choze.client;

import java.util.List;

import com.google.gwt.user.client.rpc.RemoteService;
import com.google.gwt.user.client.rpc.RemoteServiceRelativePath;

import fr.irisa.julienrfphd.choze.shared.exception.ValidatonViolation;
import fr.irisa.julienrfphd.choze.shared.model.Alternative;
import fr.irisa.julienrfphd.choze.shared.model.Poll;

/**
 * The client side stub for the RPC service.
 */
@RemoteServiceRelativePath("greet")
public interface PollService extends RemoteService {
	String createPoll(Poll p) throws ValidatonViolation ;
	Poll getPollByName(String name) ;

	List<Alternative> getAlternativeByPollName(String name) ;
	
}
