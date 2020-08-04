package com.angga.telkomselapprenticeprogam

import android.app.Application
import com.angga.telkomselapprenticeprogam.activities.detail_program.DetailProgramViewModel
import com.angga.telkomselapprenticeprogam.activities.edit_password.EditPasswordViewModel
import com.angga.telkomselapprenticeprogam.activities.edit_profil.ActivityEditProfileViewModel
import com.angga.telkomselapprenticeprogam.activities.login.LoginViewModel
import com.angga.telkomselapprenticeprogam.fragments.home_fragment.HomeViewModel
import com.angga.telkomselapprenticeprogam.fragments.profile_fragment.ProfileViewModel
import com.angga.telkomselapprenticeprogam.fragments.program_fragment.ProgramViewModel
import com.angga.telkomselapprenticeprogam.repositories.*
import com.angga.telkomselapprenticeprogam.webservices.ApiClient
import org.koin.android.ext.koin.androidContext
import org.koin.android.ext.koin.androidLogger
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.core.context.startKoin
import org.koin.core.logger.Level
import org.koin.dsl.module

class BaseApp : Application(){
    override fun onCreate() {
        super.onCreate()
        startKoin {
            androidLogger(Level.DEBUG)
            androidContext(this@BaseApp)
            modules(listOf(repositoryModules, viewModelModules, retrofitModule))
        }
    }
}

val retrofitModule = module { single { ApiClient.instance() } }

val repositoryModules = module {
    factory { InfoRepository(get()) }
    factory { ProgramRepository(get()) }
    factory { RewardRepository(get()) }
    factory { UserRepository(get()) }
    factory { ChallengeRepository(get()) }
}
val viewModelModules = module {
    viewModel { HomeViewModel(get(), get(), get()) }
    viewModel { ProgramViewModel(get()) }
    viewModel { ProfileViewModel(get()) }
    viewModel { LoginViewModel(get()) }
    viewModel { DetailProgramViewModel(get()) }
    viewModel { ActivityEditProfileViewModel(get()) }
    viewModel { EditPasswordViewModel(get()) }
}