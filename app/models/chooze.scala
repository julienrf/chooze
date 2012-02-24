package models

case class Poll(id: Option[Long], name: String, description: String, alternatives: Seq[Alternative], votes: Seq[Vote]) {
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

object Mock {
  val greece = Alternative(None, "Greece")
  val montenegro = Alternative(None, "Monte Negro")
  val berlin = Alternative(None, "Berlin")
  val poll = Poll(
      None,
      "Zen Day 2012",
      "Where do you want to go?",
      List(greece, montenegro, berlin),
      List(
          Vote(None, "jrf", List(Note(None, greece, 100), Note(None, montenegro, 40), Note(None, berlin, 10))),
          Vote(None, "sre", List(Note(None, greece, 80), Note(None, montenegro, 50), Note(None, berlin, 100))),
          Vote(None, "ybr", List(Note(None, greece, 50), Note(None, montenegro, 50), Note(None, berlin, 100)))
      )
   )
}
