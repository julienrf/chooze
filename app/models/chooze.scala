package models

case class Poll(id: Option[Long], name: String, slug: String, description: String, alternatives: Seq[Alternative], votes: Seq[Vote]) {
  /**
   * Compute the result of a poll, according to all its votes
   * @return The alternatives and their score, sorted by score
   */
  def results: Seq[(Alternative, Int)] = {
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
  
  def alternative(id: Long) = alternatives.find(_.id == Some(id))
}

case class Alternative(id: Option[Long], name: String)

case class Vote(id: Option[Long], user: String, notes: Seq[Note])

case class Note(id: Option[Long], alternative: Alternative, value: Int)
