package models

case class Poll(name: String, description: String, alternatives: Seq[Alternative], votes: Seq[Vote])

case class Alternative(name: String)

case class Vote(user: String, choices: Seq[Note])

case class Note(alternative: Alternative, value: Int)