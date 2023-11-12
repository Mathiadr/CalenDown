package no.gruppe02.hiof.calendown.service

import kotlinx.coroutines.flow.Flow
import no.gruppe02.hiof.calendown.model.Invitation

interface InvitationService {
    val invitations: Flow<List<Invitation>>
    suspend fun delete(invitation: Invitation): Unit
}