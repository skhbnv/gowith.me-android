package com.iitu.gowithme.di

import com.iitu.gowithme.MainViewModel
import com.iitu.gowithme.data.network.ApiRepository
import com.iitu.gowithme.data.network.auth.AuthRepository
import com.iitu.gowithme.data.network.event.EventRepository
import com.iitu.gowithme.data.network.event_list.EventListRepository
import com.iitu.gowithme.data.network.main.MainRepository
import com.iitu.gowithme.data.network.profile.ProfileRepository
import com.iitu.gowithme.data.network.user.UserProfileRepository
import com.iitu.gowithme.ui.auth.viewmodel.AuthViewModel
import com.iitu.gowithme.ui.create_new_event.viewmodel.CreateNewEventViewModel
import com.iitu.gowithme.ui.event_list.viewmodel.EventListViewMode
import com.iitu.gowithme.ui.event_page.EventPageViewModel
import com.iitu.gowithme.ui.home.HomeViewModel
import com.iitu.gowithme.ui.profile.viewmodel.ProfileViewModel
import com.iitu.gowithme.ui.user_profile.viewmodel.UserProfileViewModel
import com.iitu.gowithme.util.PreferencesConst
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
        MainRepository(get(named(PreferencesConst.TOKEN_PREFERENCES)), get())
    }

    single {
        EventRepository(get())
    }

    single {
        ProfileRepository(get())
    }

    single {
        EventListRepository(get())
    }

    single {
        UserProfileRepository(get())
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

    viewModel {
        EventListViewMode(get<EventListRepository>())
    }

    viewModel {
        EventPageViewModel(get<EventRepository>())
    }

    viewModel {
        UserProfileViewModel(get<UserProfileRepository>())
    }

}
