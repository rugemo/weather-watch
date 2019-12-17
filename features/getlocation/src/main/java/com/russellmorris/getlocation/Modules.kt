package com.russellmorris.getlocation

import com.russellmorris.getlocation.data.repository.CityRepositoryImpl
import com.russellmorris.getlocation.data.source.CityDataSource
import com.russellmorris.getlocation.domain.repository.CityRepository
import com.russellmorris.getlocation.domain.usecase.CityUseCase
import com.russellmorris.getlocation.service.api.CityApi
import com.russellmorris.getlocation.service.api.CityApiService
import com.russellmorris.getlocation.ui.viewmodel.GetLocationViewModel
import com.russellmorris.location.LocationProvider
import com.russellmorris.location.LocationProviderImpl
import com.russellmorris.network.createNetworkClient
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.core.context.loadKoinModules
import org.koin.core.module.Module
import org.koin.dsl.module
import retrofit2.Retrofit

fun injectFeature() = loadFeature

private val loadFeature by lazy {
    loadKoinModules(
        viewModelModule,
        locationProviderModule,
        useCaseModule,
        repositoryModule,
        dataSourceModule,
        networkModule
    )
}

val viewModelModule: Module = module {
    viewModel { GetLocationViewModel(cityUseCase = get()) }
}

val locationProviderModule: Module = module {
    single { LocationProviderImpl(context = get(), locationResultListener = get()) as LocationProvider }
}

val useCaseModule: Module = module {
    factory { CityUseCase(cityRepository = get()) }
}

val repositoryModule: Module = module {
    single {CityRepositoryImpl(cityDataSource = get()) as CityRepository}
}

val dataSourceModule: Module = module {
    single { CityApiService(api = cityApi) as CityDataSource }
}

val networkModule: Module = module {
    single { cityApi }
}

private const val BASE_URL = BuildConfig.WEATHER_ENDPOINT

private val retrofit: Retrofit = createNetworkClient(BASE_URL, BuildConfig.DEBUG)

private val cityApi: CityApi = retrofit.create(CityApi::class.java)