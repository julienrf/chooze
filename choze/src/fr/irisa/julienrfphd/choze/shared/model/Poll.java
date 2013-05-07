package fr.irisa.julienrfphd.choze.shared.model;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.OneToMany;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

import org.datanucleus.api.jpa.annotations.Extension;


/**
 * Compute the result of a poll, according to all its votes
 * @return The alternatives and their score, sorted by score
 */


@Entity
public class Poll implements Serializable{
	
	
	/**
	 * 
	 */
	private static final long serialVersionUID = -3559784661945436560L;

//	@GeneratedValue(strategy = GenerationType.IDENTITY)
//	@Extension(vendorName = "datanucleus", key = "gae.encoded-pk", value = "true")  
//	protected String key;

	@Id
	@NotNull
	@Size(min = 4, message = "Name must be at least 4 characters long.")
	String name;

	
	String slug;

	@NotNull
	@Size(min = 1,message = "Poll must have a description")
	String description;
	
	
	@OneToMany(cascade=CascadeType.ALL)
	@JoinColumn(name="pollId")
	@Size( min = 2, message = "Poll must have at least 2 alternatives.")
	List<Alternative> alternatives = new ArrayList<Alternative>();
	
	@OneToMany(cascade=CascadeType.ALL)
	@JoinColumn(name="pollId")
	List<Vote> votes;//

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getSlug() {
		return slug;
	}

	public void setSlug(String slug) {
		this.slug = slug;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public List<Alternative> getAlternatives() {
		return alternatives;
	}

	public void setAlternatives(List<Alternative> alternatives) {
		this.alternatives = alternatives;
	}

	public List<Vote> getVotes() {
		return votes;
	}

	public void setVotes(List<Vote> votes) {
		this.votes = votes;
	}
	
	public Alternative getAlternative(String name){
		for (Alternative a : alternatives){
			if (a.getName().equals(name))
				return a;
		}
		return null;
	}

	@Override
	public String toString() {
		return "Poll [name=" + name + ", slug=" + slug + ", description="
				+ description + ", alternatives=" + alternatives + ", votes="
				+ votes + "]";
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result
				+ ((alternatives == null) ? 0 : alternatives.hashCode());
		result = prime * result
				+ ((description == null) ? 0 : description.hashCode());
		result = prime * result + ((name == null) ? 0 : name.hashCode());
		result = prime * result + ((slug == null) ? 0 : slug.hashCode());
		result = prime * result + ((votes == null) ? 0 : votes.hashCode());
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Poll other = (Poll) obj;
		if (alternatives == null) {
			if (other.alternatives != null)
				return false;
		} else if (!alternatives.equals(other.alternatives))
			return false;
		if (description == null) {
			if (other.description != null)
				return false;
		} else if (!description.equals(other.description))
			return false;
		if (name == null) {
			if (other.name != null)
				return false;
		} else if (!name.equals(other.name))
			return false;
		if (slug == null) {
			if (other.slug != null)
				return false;
		} else if (!slug.equals(other.slug))
			return false;
		if (votes == null) {
			if (other.votes != null)
				return false;
		} else if (!votes.equals(other.votes))
			return false;
		return true;
	}

	
	
}


/*case class Poll() {
	  lazy val results: Seq[(Alternative, Int)] = {
	    // Current implementation: range voting. In the future we may add more voting strategies
	    (for {
	      alternative <- alternatives
	    } yield {
	      val notes = for {
	        vote <- votes
	        note <- vote.notes
	        if note.alternative == alternative
	      } yield note.value
	      (alternative, if (votes.nonEmpty) notes.sum / votes.size else 50)
	    }).sortBy(_._2).reverse
	  }
	  
	  def alternative(id: Long) = alternatives.find(_.id == id)
	}
*/



