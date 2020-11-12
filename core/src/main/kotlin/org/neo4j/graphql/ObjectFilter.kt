package org.neo4j.graphql

import graphql.schema.DataFetchingEnvironment
import graphql.schema.GraphQLFieldsContainer

@FunctionalInterface
interface ObjectFilter {
    fun getFilterQuery(variable: String, type: GraphQLFieldsContainer, env: DataFetchingEnvironment): Cypher?
}