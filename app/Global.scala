import play.api.{Application, GlobalSettings}

object Global extends GlobalSettings {
  
  override def beforeStart(app: Application) {
    import play.api.db.evolutions.Evolutions._
    import db.Db
    
    // TODO remove
    updateEvolutionScript(
        ups = Db.ddl.createStatements.mkString(";\n"),
        downs = Db.ddl.createStatements.mkString(";\n")
    )(app)
  }
}