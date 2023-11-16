package no.gruppe02.hiof.calendown.service.module

import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import no.gruppe02.hiof.calendown.service.AuthenticationService
import no.gruppe02.hiof.calendown.service.InvitationService
import no.gruppe02.hiof.calendown.service.StorageService
import no.gruppe02.hiof.calendown.service.UserService
import no.gruppe02.hiof.calendown.service.impl.AuthenticationServiceImpl
import no.gruppe02.hiof.calendown.service.impl.InvitationServiceImpl
import no.gruppe02.hiof.calendown.service.impl.StorageServiceImpl
import no.gruppe02.hiof.calendown.service.impl.UserServiceImpl


@Module
@InstallIn(SingletonComponent::class)
abstract class ServiceModule {
    @Binds
    abstract fun provideStorageService(impl: StorageServiceImpl): StorageService

    @Binds
    abstract fun provideAuthenticationService(impl: AuthenticationServiceImpl): AuthenticationService

    @Binds
    abstract fun provideUserService(impl: UserServiceImpl): UserService

    @Binds
    abstract fun provideInvitationService(impl: InvitationServiceImpl): InvitationService
}