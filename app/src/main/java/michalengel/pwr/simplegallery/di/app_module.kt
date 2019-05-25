package michalengel.pwr.simplegallery.di

import michalengel.pwr.simplegallery.model.ImageDetailsProvider
import michalengel.pwr.simplegallery.view_model.ImagesUrisViewModel
import org.koin.android.ext.koin.androidApplication
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.core.module.Module
import org.koin.dsl.module


val groupModule: Module = module {
    viewModel {ImagesUrisViewModel()}
    single {ImageDetailsProvider(androidApplication().contentResolver)}
}
