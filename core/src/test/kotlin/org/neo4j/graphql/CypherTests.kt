package org.neo4j.graphql

import org.junit.jupiter.api.DynamicContainer
import org.junit.jupiter.api.DynamicNode
import org.junit.jupiter.api.TestFactory
import org.neo4j.graphql.utils.CypherTestSuite
import java.nio.file.Files
import java.nio.file.Paths
import java.util.stream.Stream

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
    fun `new cypher tck tests`(): Stream<DynamicNode>? = Files
        .list(Paths.get("src/test/resources/tck-test-files/cypher"))
        .map {
            DynamicContainer.dynamicContainer(
                    it.fileName.toString(),
                    it.toUri(),
                    CypherTestSuite("tck-test-files/cypher/${it.fileName}").generateTests()
            )
        }
}
