package com.example.freeweatherproject

import android.content.Context
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.TypedValue
import android.view.View
import android.view.inputmethod.InputMethodManager
import android.widget.LinearLayout
import android.widget.LinearLayout.LayoutParams
import android.widget.TextView

import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory
import retrofit2.Retrofit

import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.Disposable
import io.reactivex.schedulers.Schedulers
import kotlinx.android.synthetic.main.activity_main.*

/**
 * A simple Kotlin + RXJava2 Android app example on top of FWP's existing serverless API,
 * using Retrofit & gson for network access and serialization, have fun!
 *
 * @author Chase Logan - https://github.com/cslogan-red
 */
class MainActivity : AppCompatActivity() {
    private var currentLocation: String = ""
    private var disposables = ArrayList<Disposable>(0)

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        // init search click listener
        weatherButton.setOnClickListener { loadData() }
    }

    /**
     * Initially loads location data based on entered location
     */
    private fun loadData() {
        val locationText = searchInput.text?.toString()
        if (locationText!!.isNotEmpty()) {
            currentlyText.visibility = View.INVISIBLE
            outlookText.visibility = View.INVISIBLE
            // Build the Retrofit instance using GetService data classes returned as Observables
            // Push Observable to io thread, handle results on main thread via onLocationResponse
            disposables.add(
                Retrofit.Builder()
                    .baseUrl(Constants().BASE_API_URL)
                    .addConverterFactory(GsonConverterFactory.create())
                    .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
                    .build()
                    .create(GetService::class.java)
                    .getLocation(locationText)
                    .observeOn(AndroidSchedulers.mainThread())
                    .subscribeOn(Schedulers.io())
                    .doOnError { println("Error in onLocationResponse: " + it.printStackTrace()) }
                    .subscribe(this::onLocationResponse)
            )
        }
    }

    /**
     * Loads weather data for a given location
     */
    private fun onLocationResponse(location: LocationData) {
        // Build the Retrofit instance using GetService data classes returned as Observables
        // Push Observable to io thread, handle results on main thread via onWeatherResponse
        currentLocation = location.formattedAddr
        disposables.add(
            Retrofit.Builder()
                .baseUrl(Constants().BASE_API_URL)
                .addConverterFactory(GsonConverterFactory.create())
                .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
                .build()
                .create(GetService::class.java)
                .getWeather(location.lat, location.lng)
                .observeOn(AndroidSchedulers.mainThread())
                .subscribeOn(Schedulers.io())
                .doOnError { println("Error in onWeatherResponse: " + it.printStackTrace()) }
                .subscribe(this::onWeatherResponse)
        )
    }

    /**
     * Returns weather response data, cleans up resources, terminates disposables
     */
    private fun onWeatherResponse(weather: WeatherData) {
        searchInput.text.clear()
        currentlyText.visibility = View.VISIBLE
        outlookText.visibility = View.VISIBLE
        // right now
        val currentTemp = weather.currently?.feelsLikeTemp
        val summary = weather.currently?.summary
        rightNowText.text = "$currentLocation\n$currentTemp\n$summary"
        // outlook
        buildOutlookViews(weather)
        // clear soft keyboard via InputMethodManager
        val view = this.currentFocus
        view?.let {
            val iManager = getSystemService(Context.INPUT_METHOD_SERVICE) as? InputMethodManager
            iManager?.hideSoftInputFromWindow(it.windowToken, 0)
        }
        // clean up RXJava2 disposables
        disposables.forEach { it.dispose() }
    }

    /**
     * Fill extended outlook scroll view, constructs LinearLayout view as only child object,
     * fills layout with simple TextView's
     */
    private fun buildOutlookViews(weather: WeatherData) {
        val context: Context = applicationContext
        val linearLayout = LinearLayout(context)
        val layout = LayoutParams(LayoutParams.WRAP_CONTENT, LayoutParams.WRAP_CONTENT)
        val id = 100
        val defaultMargin = 6
        linearLayout.id = id
        layout.marginStart = defaultMargin
        layout.topMargin = defaultMargin
        linearLayout.layoutParams = layout
        linearLayout.orientation = LinearLayout.VERTICAL
        var textViewLayout = LayoutParams(LayoutParams.WRAP_CONTENT, LayoutParams.WRAP_CONTENT)
        textViewLayout.topMargin = defaultMargin
        weather.daily?.forEach{
            val localDate = toLocalDate(it.date?.toLong())
            var tV = TextView(context)
            tV.layoutParams = textViewLayout
            tV.text =
                localDate + "\nHigh: " + it.tempHigh + ", Low: " + it.tempLow + "\n" + it.summary
            tV.setTextSize(TypedValue.COMPLEX_UNIT_PX, resources.getDimension(R.dimen.outlook_font))
            tV.setTextColor(resources.getColor(R.color.colorPrimaryText))
            linearLayout.addView(tV)
        }
        outlookScrollView.addView(linearLayout)
    }

    /**
     * Convert unix time to local date
     */
    private fun toLocalDate(unixTime: Long): String  {
        val date = java.util.Date(unixTime * 1000)
        val simpleFormat = java.text.SimpleDateFormat("E, MMM d")
        return simpleFormat.format(date)
    }
}
