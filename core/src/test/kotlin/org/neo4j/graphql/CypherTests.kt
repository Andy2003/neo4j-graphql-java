package org.neo4j.graphql

import graphql.schema.DataFetchingEnvironment
import graphql.schema.GraphQLDirectiveContainer
import graphql.schema.GraphQLFieldsContainer
import org.junit.jupiter.api.TestFactory
import org.neo4j.graphql.utils.CypherTestSuite

class CypherTests {

    @TestFactory
    fun `cypher-directive-tests`() = CypherTestSuite("cypher-directive-tests.adoc").generateTests()

    @TestFactory
    fun `dynamic-property-tests`() = CypherTestSuite("dynamic-property-tests.adoc").generateTests()

    @TestFactory
    fun `filter-tests`() = CypherTestSuite("filter-tests.adoc").generateTests()

    @TestFactory
    fun `relationship-tests`() = CypherTestSuite("relationship-tests.adoc").generateTests()

    @TestFactory
    fun `movie-tests`() = CypherTestSuite("movie-tests.adoc").generateTests()

    @TestFactory
    fun `property-tests`() = CypherTestSuite("property-tests.adoc").generateTests()

    @TestFactory
    fun `translator-tests1`() = CypherTestSuite("translator-tests1.adoc").generateTests()

    @TestFactory
    fun `translator-tests2`() = CypherTestSuite("translator-tests2.adoc").generateTests()

    @TestFactory
    fun `translator-tests3`() = CypherTestSuite("translator-tests3.adoc").generateTests()

    @TestFactory
    fun `translator-tests-custom-scalars`() = CypherTestSuite("translator-tests-custom-scalars.adoc").generateTests()

    @TestFactory
    fun `optimized-query-for-filter`() = CypherTestSuite("optimized-query-for-filter.adoc").generateTests()

    @TestFactory
    fun `custom-fields`() = CypherTestSuite("custom-fields.adoc").generateTests()

    @TestFactory
    fun `object-filter-tests`() = object : CypherTestSuite("object-filter-tests.adoc") {
        override fun enhanceQueryContext(config: QueryContext?, requestParams: Map<String, Any?>): QueryContext {
            return (config
                    ?: QueryContext()).copy(objectFilter = TestObjectFilter(requestParams), contextParams = requestParams)
        }
    }.generateTests()


    // tag::example[]
    class TestObjectFilter(val params: Map<String, Any?> = emptyMap()) : ObjectFilter {

        override fun getFilterQuery(variable: String, type: GraphQLFieldsContainer, env: DataFetchingEnvironment): Cypher? =
                ((type as? GraphQLDirectiveContainer)
                    ?.getDirective("filter")
                    ?.getArgument("statement")
                    ?.value?.toJavaValue() as? String)
                    ?.replace("this", variable)
                    ?.let { query ->
                        // extract all variables $...
                        val params = "\\\$(\\w+)".toRegex().findAll(query).map { it.groups.get(1)?.value }
                            .filterNotNull()
                            .map { ctxVar -> ctxVar to (env.getLocalContext() as? QueryContext)?.contextParams?.get(ctxVar) }
                            .toMap()
                        Cypher(query, params)
                    }
    }
    // end::example[]
}
