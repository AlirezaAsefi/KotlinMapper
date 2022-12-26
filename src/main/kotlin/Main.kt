import java.util.*

fun main(args: Array<String>) {
    // Initialize a CustomerEntity
    val customerEntity = CustomerEntity(
        name = "customer name",
        uniqueId = "SAKDJ32INS4Y89",
        phoneNumber = "00000000000",
        bio = "customer bio",
        birthDate = Date(),
        address = "customer address",
        personalData = "customer personal data"
    )

    // Map the CustomerEntity to a CustomerDto
    val customerDto = customerEntity.adapt<CustomerDto>()
    println("CustomerDto: $customerDto")

    // Map the CustomerDto to a CustomerEntity
    val customerEntityNew = customerDto.adapt<CustomerEntity>()
    println("CustomerEntity: $customerEntityNew")

}