package fr.irisa.julienrfphd.choze.server;

import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.EntityTransaction;
import javax.servlet.ServletException;

import com.google.gwt.user.server.rpc.RemoteServiceServlet;

import fr.irisa.julienrfphd.choze.client.PollPage;
import fr.irisa.julienrfphd.choze.client.PollService;
import fr.irisa.julienrfphd.choze.shared.model.Alternative;
import fr.irisa.julienrfphd.choze.shared.model.Poll;

/**
 * The server side implementation of the RPC service.
 */
@SuppressWarnings("serial")
public class PollServiceImpl extends RemoteServiceServlet implements
		PollService {
	EntityManager manager ;
	
	public void init() throws ServletException {
		super.init();
		manager = EMF.get().createEntityManager();
		
	}
	
	@Override
	public void createPoll(Poll p) {
		EntityTransaction t = manager.getTransaction();
		t.begin();
		
		manager.persist(p);
		
		t.commit();
		
	}


	@Override
	public Poll getPollByName(String name) {
		return null;
	}

	@Override
	public List<Alternative> getAlternativeByPollName(String name) {
		return null;
	}

}
