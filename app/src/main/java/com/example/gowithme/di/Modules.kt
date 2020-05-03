package com.example.gowithme.di

import com.example.gowithme.MainViewModel
import com.example.gowithme.data.network.ApiRepository
import com.example.gowithme.data.network.auth.AuthRepository
import com.example.gowithme.data.network.event.EventRepository
import com.example.gowithme.data.network.main.MainRepository
import com.example.gowithme.data.network.profile.ProfileRepository
import com.example.gowithme.ui.auth.viewmodel.AuthViewModel
import com.example.gowithme.ui.create_new_event.viewmodel.CreateNewEventViewModel
import com.example.gowithme.ui.home.HomeViewModel
import com.example.gowithme.ui.profile.viewmodel.ProfileViewModel
import com.example.gowithme.util.PreferencesConst
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.core.qualifier.named
import org.koin.dsl.module


val repoModule = module {

    single {
        ApiRepository(get())
    }

    single {
        AuthRepository(get(), get(named(PreferencesConst.TOKEN_PREFERENCES)))
    }

    single {
        MainRepository(get(named(PreferencesConst.TOKEN_PREFERENCES)))
    }

    single {
        EventRepository(get())
    }

    single {
        ProfileRepository(get())
    }

}

val viewModelModule = module {

    viewModel {
        HomeViewModel(get<EventRepository>())
    }

    viewModel {
        AuthViewModel(get<AuthRepository>())
    }

    viewModel {
        MainViewModel(get<MainRepository>())
    }

    viewModel {
        ProfileViewModel(get<ProfileRepository>())
    }

    viewModel {
        CreateNewEventViewModel(get<EventRepository>())
    }

}
