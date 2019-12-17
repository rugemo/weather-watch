# Weather Watch

Weather Watch is a modular app built for a coding challenge.

It uses RxJava and Retrofit to make a call to get the open weather map (OWM) api to retrieve current weather conditions for a givern location.

Other third party libraries used are:

- Koin for Dependency Injection

## Application Architecture

The app is feature based and modular, following Clean Architecture guidelines with MVVM Pattern

- app
- buildSrc
- common
- features
- navigtion

### Improvements

- Convert RxJava to Kotlin coroutines and use databinding between viewmodel and view
_ There's some logic in the views, but it's primarily there when using system services such as Location. Theses services require context or activity. Find a way to pull that logic out, to make the view as dumb as possible.
- Redo the city serch function, the search option provided by OWM doesn't seem very good. See known issues. Replace with Google Places for search.
- Increase test coverage

### Known Issues
When searching for a city, if mutiple results are found for a given country, eg multiple New York results for the USA, using the id to get the weather or forecast information could be confusing for the user.

What New Youk are they interseted in? What New York is the detail info for?

Instead of city ID, I used the lat and lon values. This brings back more granular data. However, the place name is different and doesn't state New York. This too is confusing. Ideally we would want the granular and general data on the list page, so the user is aware before clicking what they should expect to see.

I pass the city and country code to the detail fragment, then display it above the detailed place name. That minimises the confusion somewhat.

After some further investigation using (lat and lon) it seems the name New York is returned but the actual coordinates relate to somewhere associatiated with the name New York. For example, the last result on the list (Slater) is actually a place in Kentucky, there is a place near there called New York which has a population of 10 https://en.wikipedia.org/wiki/Slater,_Kentucky

I don't think this api call is a good choice for a city search. As mentioned previously, Google Places would be a better option but the list requirement in the UI would have to be reconsidered.
