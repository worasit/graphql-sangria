package com.howtographql.scala.sangria

import com.howtographql.scala.sangria.models.Link
import slick.jdbc.H2Profile.api._
import slick.lifted.ProvenShape

import scala.concurrent.duration._
import scala.concurrent.Await
import scala.language.postfixOps


object DBSchema {


  /// 1.) Create the link table
  class LinksTable(tag: Tag) extends Table[Link](tag, "LINKS") {
    def id: Rep[Int] = column[Int]("ID", O.PrimaryKey, O.AutoInc)

    def url: Rep[String] = column[String]("URL")

    def description: Rep[String] = column[String]("DESCRIPTION")

    override def * : ProvenShape[Link] = (id, url, description).mapTo[Link]
  }

  //  2
  val LinksQuery = TableQuery[LinksTable]


  //  3
  /**
    * Load schema and populate sample data withing this Sequence od DBActions
    */
  val databaseSetup = DBIO.seq(
    LinksQuery.schema.create,
    LinksQuery.forceInsertAll {
      Seq(
        Link(1, "http://howtographl.com", "Awesome community driven GraphQL tutorial"),
        Link(2, "http://graphql.org", "Official GraphQL web page"),
        Link(3, "https://facebook.github.io/graphql/", "GraphQL specification")
      )
    }
  )


  def createDatabase: DAO = {
    val db = Database.forConfig("h2mem")

    Await.result(db.run(databaseSetup), 10 seconds)

    new DAO(db)

  }

}
