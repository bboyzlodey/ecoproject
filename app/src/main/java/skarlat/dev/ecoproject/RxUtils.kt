package skarlat.dev.ecoproject

import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers
import io.reactivex.rxjava3.core.Completable
import io.reactivex.rxjava3.core.Maybe
import io.reactivex.rxjava3.schedulers.Schedulers

val <T> Maybe<T>.IOSchedulers: Maybe<T>
    get() = compose { it.subscribeOn(Schedulers.io()).observeOn(AndroidSchedulers.mainThread()) }

val Completable.IOSchedulers: Completable
    get() = compose { it.subscribeOn(Schedulers.io()).observeOn(AndroidSchedulers.mainThread()) }