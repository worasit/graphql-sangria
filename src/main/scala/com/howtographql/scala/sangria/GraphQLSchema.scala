package com.howtographql.scala.sangria

import com.howtographql.scala.sangria.models.Link
import sangria.schema.{Field, IntType, ListType, ObjectType, Schema, StringType, fields}
import sangria.macros.derive._

object GraphQLSchema {

  // 1.) GraphQL object type for Link
  //  val LinkType: ObjectType[Unit, Link] = ObjectType[Unit, Link](
  //    name = "Link",
  //    fields = fields[Unit, Link](
  //      Field("id", IntType, resolve = _.value.id),
  //      Field("url", StringType, resolve = _.value.url),
  //      Field("description", StringType, resolve = _.value.description)
  //    )
  //  )

  // 1.) Marcro way
  implicit val linkType: ObjectType[Unit, Link] = deriveObjectType[Unit, Link]()

  // 2
  val QueryType = ObjectType(
    "Query",
    fields[MyContext, Unit](
      Field("allLinks", ListType(linkType), resolve = c => c.ctx.dao.allLinks)
    )
  )

  // 3
  val SchemaDefinition = Schema(QueryType)
}
