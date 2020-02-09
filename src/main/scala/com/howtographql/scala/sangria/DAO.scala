package com.howtographql.scala.sangria

import sangria.schema.Action
import slick.jdbc.H2Profile.api._
import DBSchema._

import scala.concurrent.Future

class DAO(db: Database) {
  def allLinks: Future[Seq[models.Link]] = db.run(LinksQuery.result)
}
