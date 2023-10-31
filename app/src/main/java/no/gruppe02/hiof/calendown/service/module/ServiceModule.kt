package no.gruppe02.hiof.calendown.service.module

import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import no.gruppe02.hiof.calendown.service.AccountService
import no.gruppe02.hiof.calendown.service.StorageService
import no.gruppe02.hiof.calendown.service.impl.AccountServiceImpl
import no.gruppe02.hiof.calendown.service.impl.StorageServiceImpl


@Module
@InstallIn(SingletonComponent::class)
abstract class ServiceModule {
    @Binds
    abstract fun provideStorageService(impl: StorageServiceImpl): StorageService


    @Binds
    abstract fun provideAccountService(impl: AccountServiceImpl): AccountService
}