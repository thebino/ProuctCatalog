# Android application written in Kotlin

[![CC-BY license](https://img.shields.io/badge/License-CC--BY-blue.svg)](https://creativecommons.org/licenses/by-nd/4.0)

This project is an android application to showcase the [Jetpack libraries](https://d.android.com/jetpack) for 

 * [Data Binding](https://developer.android.com/topic/libraries/data-binding/)
 * [Lifecycles](https://developer.android.com/topic/libraries/architecture/lifecycle)
 * [LiveData](https://developer.android.com/topic/libraries/architecture/livedata)
 * [Paging](https://developer.android.com/topic/libraries/architecture/paging/)
 * [Room](https://developer.android.com/topic/libraries/architecture/room)
 * [ViewModel](https://developer.android.com/topic/libraries/architecture/viewmodel)
 * [WorkManager](https://developer.android.com/topic/libraries/architecture/workmanager)


## Library usage
#### Data Binding

Products are binded into the recyclerview via one-way binding inside the `ProductViewHolder`


#### Paging

Paging is used to load more items when you scroll to the bottom of the list. Its limited to 8 items to force the reload.

#### WorkManager

The `ProductCatalogPrePopulateDataWorker` is used to populate sample data into the database on the first creation.

#### Room

All Data is persisted in a local Room database


## Installation

To build and run the app on and android device or emulator, use the included gradle wrapper:

```sh
./gradlew installDebug
```


## Release History

* 0.0.1
    * First draft

## Meta

Benjamin Stürmer – [@benjaminstrmer](https://twitter.com/benjaminstrmer) – webmaster@stuermer-benjamin.de

Distributed under the Attribution 4.0 International (CC BY 4.0) license. See ``LICENSE`` for more information.

[https://github.com/thebino/ProuctCatalog](https://github.com/thebino/ProuctCatalog)

## Contributing

1. Fork it (https://github.com/thebino/ProuctCatalog/fork>)
2. Create your feature branch (`git checkout -b feature/fooBar`)
3. Commit your changes (`git commit -am 'Add some fooBar'`)
4. Push to the branch (`git push origin feature/fooBar`)
5. Create a new Pull Request
