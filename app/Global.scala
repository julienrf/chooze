import play.api.{Application, GlobalSettings}

object Global extends GlobalSettings {
  override def beforeStart(app: Application) {
    import play.api.db.evolutions.Evolutions._
    import db.Schema
    
    updateEvolutionScript(
        ups = Schema.ddl.createStatements.mkString(";\n"),
        downs = Schema.ddl.dropStatements.mkString(";\n")
    )(app)
  }
}