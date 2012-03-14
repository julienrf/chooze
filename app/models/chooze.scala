package models

import java.util.Date

case class Poll(id: Long, name: String, slug: String, description: String, alternatives: Seq[Alternative], votes: Seq[Vote]) {
  /**
   * Compute the result of a poll, according to all its votes
   * @return The alternatives and their score, sorted by score
   */
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

case class Alternative(id: Long, name: String)

case class Vote(id: Long, user: String, notes: Seq[Note])

case class Note(id: Long, alternative: Alternative, value: Int)
