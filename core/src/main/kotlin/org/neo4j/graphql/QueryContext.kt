package org.neo4j.graphql

data class QueryContext @JvmOverloads constructor(
        var objectFilter: ObjectFilter? = null,

        var contextParams: Map<String, Any?>? = emptyMap(),

        /**
         * if true the <code>__typename</code> will be always returned for interfaces, no matter if it was queried or not
         */
        var queryTypeOfInterfaces: Boolean = false,

        /**
         * If set alternative approaches for query translation will be used
         */
        var optimizedQuery: Set<OptimizationStrategy>? = null

) {
    enum class OptimizationStrategy {
        /**
         * If used, filter queries will be converted to cypher matches
         */
        FILTER_AS_MATCH
    }
}
