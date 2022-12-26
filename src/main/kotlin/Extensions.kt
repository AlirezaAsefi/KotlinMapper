import kotlin.reflect.KClass
import kotlin.reflect.KParameter
import kotlin.reflect.KProperty1
import kotlin.reflect.full.declaredMembers
import kotlin.reflect.full.primaryConstructor

inline fun <reified T : Any> Any.adapt(): T {
    val targetClass = (T::class as KClass<*>)
    val hasDefaultConstructor = targetClass.constructors.any { it.parameters.isEmpty() }

    val mapper = MapperBuilder.init()
    if (hasDefaultConstructor) {
        return mapper.map<Any, T>(this)
    }

    val targetParameters = targetClass.primaryConstructor?.parameters ?: emptyList()
    val converters = emptyArray<Convert<Any, Any>>() //builder.converters.map { it as Convert<Any, Any> }.toTypedArray()

    val argsMap: MutableMap<KParameter, Any?> = targetParameters
        .associateWith { prop -> getPropertyValue(prop.name ?: "")?.let { applyConverter(it, *converters) } }
        .toMutableMap()

    val t = "${this::class.java.name}_to_${targetClass.java.name}"
    MapperBuilder.getMappings(t).forEach { binder ->
        targetParameters.find { it.name == binder.prop.second }?.let { param ->
            getPropertyValue(binder.prop.first)?.let { value ->
                val converter = binder.convert as Convert<Any, Any>?
                argsMap[param] = converter?.let { applyConverter(value, it) } ?: value
            }
        }
    }

    return targetClass.primaryConstructor?.callBy(argsMap) as T
}

fun Any.getPropertyValue(name: String) = this::class
    .declaredMembers
    .filterIsInstance<KProperty1<*, *>>()
    .firstOrNull { it.name == name }
    ?.call(this)

fun applyConverter(input: Any, vararg converters: Convert<Any, Any>): Any {
    for (converter in converters) {
        try {
            return converter.convert.invoke(input)
        } catch (_: ClassCastException) {
        }
    }
    return input
}