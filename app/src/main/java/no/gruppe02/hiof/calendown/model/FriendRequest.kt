package no.gruppe02.hiof.calendown.model



data class FriendRequest(
    val recipientUser: User,
    val senderUser: User
)
enum class RequestStatus{

}