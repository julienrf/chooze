package fr.irisa.julienrfphd.choze.server;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.EntityManager;
import javax.persistence.EntityTransaction;
import javax.persistence.JoinColumn;
import javax.persistence.OneToMany;
import javax.servlet.ServletException;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

import com.google.gwt.user.server.rpc.RemoteServiceServlet;

import fr.irisa.julienrfphd.choze.client.PollPage;
import fr.irisa.julienrfphd.choze.client.PollService;
import fr.irisa.julienrfphd.choze.shared.exception.ValidatonViolation;
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
	public void createPoll(Poll p) throws ValidatonViolation {
		
		validatePoll(p);
		
		System.err.println(p.getDescription().length());
		
		EntityTransaction t = manager.getTransaction();
		if (!t.isActive())
			t.begin();
		
		manager.persist(p);
		
		t.commit();
		
	}

	
	private void validatePoll(Poll p) throws ValidatonViolation{
		
		List<String[]> errors = new ArrayList<String[]>();
		
		if (p.getName()== null || p.getName().length()<1){
			String err[] =  {"name", "Poll must have a name."};
			errors.add(err);
		}
		if (p.getDescription()== null || p.getDescription().length()<1){
			String err[] =  {"description", "Poll must have a description."};
			errors.add(err);
		}
		if (p.getAlternatives()== null || p.getAlternatives().size()<2)
		{
			String err[] =  {"description", "Poll must have at least 2 alternatives."};
			errors.add(err);
		}
		
		List<Alternative> alts = new ArrayList<Alternative>();
		alts.addAll(p.getAlternatives());

		for (Alternative a : p.getAlternatives()){
			if (a.getName()== null || a.getName().length()<1) 
			{
				String err[] =  {a.getHtmlid(), "Alternative must have a name."};
				errors.add(err);
			}
		}
		for (Alternative a : p.getAlternatives()){
			alts.remove(a);
			for (Alternative b : alts){
				if (b.getName().equals(a.getName())){
					{
						String err[] =  {a.getHtmlid(), "Poll must not have at 2 alternative with the same name"};
						errors.add(err);
						String err1[] =  { b.getHtmlid(), "Poll must not have at 2 alternative with the same name"};
						errors.add(err1);
					}
				}
			}
		}
		if (errors.size()>0)
			throw new ValidatonViolation(errors);
	}

	@Override
	public Poll getPollByName(String name) {
		Poll poll = manager.find(Poll.class, name);
		return poll;

	}

	@Override
	public List<Alternative> getAlternativeByPollName(String name) {
		Poll poll = manager.find(Poll.class, name);
		if (poll!= null)
			return poll.getAlternatives();
		else
			return new ArrayList<Alternative>();
	
	}

}
