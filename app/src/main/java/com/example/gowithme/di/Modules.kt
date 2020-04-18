package com.example.gowithme.di

import com.example.gowithme.data.network.ApiRepository
import com.example.gowithme.data.network.auth.AuthRepository
import com.example.gowithme.ui.auth.viewmodel.AuthViewModel
import com.example.gowithme.ui.home.HomeViewModel
import org.koin.androidx.experimental.dsl.viewModel
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.dsl.module


val repoModule = module {

    single {
        ApiRepository(get())
    }

    single {
        AuthRepository(get(), get())
    }

}

val viewModelModule = module {

    viewModel {

        HomeViewModel(get())

    }

    viewModel {
        AuthViewModel(get())
    }

}
