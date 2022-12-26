import org.junit.jupiter.api.Test
import java.util.*
import kotlin.test.assertEquals
import kotlin.test.assertNull

class MappingTest {
    @Test
    fun test_adapt_customerEntity_to_customerDto()
    {
        val customerEntity = CustomerEntity(
            name = "customer name",
            uniqueId = "SAKDJ32INS4Y89",
            phoneNumber = "00000000000",
            bio = "customer bio",
            birthDate = Date(),
            address = "customer address",
            personalData = "customer personal data"
        )

        val customerDto = customerEntity.adapt<CustomerDto>()

        assertEquals(customerDto.name, customerEntity.name)
        assertEquals(customerDto.username, customerEntity.uniqueId)
        assertEquals(customerDto.bio, customerEntity.bio)
        assertEquals(customerDto.birthDate, customerEntity.birthDate)
    }

    @Test
    fun test_adapt_customerDto_to_customerEntity()
    {
        val customerDto = CustomerDto(
            name = "customer name",
            username = "SAKDJ32INS4Y89",
            bio = "customer bio",
            birthDate = Date()
        )

        val customerEntity = customerDto.adapt<CustomerEntity>()

        assertEquals(customerEntity.name, customerDto.name)
        assertEquals(customerEntity.uniqueId, customerDto.username)
        assertEquals(customerEntity.bio, customerDto.bio)
        assertEquals(customerEntity.birthDate, customerDto.birthDate)
        assertNull(customerEntity.phoneNumber)
        assertNull(customerEntity.address)
        assertNull(customerEntity.personalData)
    }
}