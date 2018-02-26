# AndroidWeatherApp
A simple android application that fetches and displays current weather for the current location that a user is in. Made use of the Retrofit2 library and GSON. The application reads weather data from the Openweathermap api.

# Screenshots


# Libraries
- Retrofit 2.3.0
- Android Support Library 26.1.0
- Constraint Layout 1.0.2
# How the app works
- When the user opens the application, the application will prompt the user to turn on their gps and ensure that they have an internet connection.
- Once available, the application will collect the user's current location, and make an api call to retrive the weather details.
- The application will then the user's street address, weather, and date.
