import java.util.Date

data class CustomerEntity(
    var name: String?,
    var uniqueId: String?,
    var phoneNumber: String?,
    var bio: String?,
    var birthDate: Date?,
    var address: String?,
    var personalData: String?
)
