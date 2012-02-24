package models

case class Poll(name: String, description: String, alternatives: Seq[Alternative], votes: Seq[Vote]) {
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
      (alternative, notes.sum / votes.size)
    }).sortBy(_._2).reverse
  }
}

case class Alternative(id: Option[Long], name: String)

case class Vote(id: Option[Long], user: String, choices: Seq[Note])

case class Note(id: Option[Long], alternative: Alternative, value: Int)

object Mock {
  val greece = Alternative("Greece")
  val montenegro = Alternative("Monte Negro")
  val berlin = Alternative("Berlin")
  val poll = Poll(
      "Zen Day 2012",
      "Where do you want to go?",
      List(greece, montenegro, berlin),
      List(
          Vote("jrf", List(Note(greece, 100), Note(montenegro, 40), Note(berlin, 10))),
          Vote("sre", List(Note(greece, 80), Note(montenegro, 50), Note(berlin, 100))),
          Vote("ybr", List(Note(greece, 50), Note(montenegro, 50), Note(berlin, 100)))
      )
   )
}
