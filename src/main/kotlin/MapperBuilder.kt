import dev.krud.shapeshift.ShapeShift
import dev.krud.shapeshift.ShapeShiftBuilder
import dev.krud.shapeshift.dsl.mapper
import dev.krud.shapeshift.enums.AutoMappingStrategy
import dev.krud.shapeshift.resolver.MappingDefinition

class MapperBuilder {
    companion object {
        private val bindMaps: HashMap<String, List<Bind<*, *>>> = hashMapOf()

        fun init(): ShapeShift {
            mappers.forEach {
                val t = "${it.fromClazz.name}_to_${it.toClazz.name}"
                val g = it.resolvedMappedFields.map {
                    Bind<Any, Any>(it.mapFromCoordinates.first().name to it.mapToCoordinates.first().name)
                }

                bindMaps[t] = g
            }

            return ShapeShiftBuilder()
                .withMapping(*mappers.toTypedArray())
                .build()
        }

        private val CustomerEntity_to_CustomerEntityDisplay: MappingDefinition = mapper<CustomerEntity, CustomerDto> {
            autoMap(AutoMappingStrategy.BY_NAME_AND_TYPE)
            CustomerEntity::name mappedTo CustomerDto::name
            CustomerEntity::uniqueId mappedTo CustomerDto::username
            CustomerEntity::bio mappedTo CustomerDto::bio
            CustomerEntity::birthDate mappedTo CustomerDto::birthDate
        }.mappingDefinition

        private val CustomerDto_to_CustomerEntity: MappingDefinition = mapper<CustomerDto, CustomerEntity> {
            CustomerDto::name mappedTo CustomerEntity::name
            CustomerDto::username mappedTo CustomerEntity::uniqueId
            CustomerDto::bio mappedTo CustomerEntity::bio
            CustomerDto::birthDate mappedTo CustomerEntity::birthDate
        }.mappingDefinition

        private val mappers: List<MappingDefinition> = listOf(
            CustomerEntity_to_CustomerEntityDisplay,
            CustomerDto_to_CustomerEntity
        )

        fun getMappings(title: String): List<Bind<*, *>> {
            return bindMaps[title] ?: listOf()
        }
    }
}