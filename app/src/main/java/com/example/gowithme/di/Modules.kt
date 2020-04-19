package com.example.gowithme.di

import com.example.gowithme.MainViewModel
import com.example.gowithme.data.network.ApiRepository
import com.example.gowithme.data.network.auth.AuthRepository
import com.example.gowithme.data.network.auth.AuthService
import com.example.gowithme.data.network.main.MainRepository
import com.example.gowithme.ui.auth.viewmodel.AuthViewModel
import com.example.gowithme.ui.home.HomeViewModel
import com.example.gowithme.util.PreferencesConst
import org.koin.androidx.experimental.dsl.viewModel
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

}

val viewModelModule = module {

    viewModel {
        HomeViewModel(get())
    }

    viewModel {
        AuthViewModel(get<AuthRepository>())
    }

    viewModel {
        MainViewModel(get<MainRepository>())
    }

}
