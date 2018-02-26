# AndroidWeatherApp
A simple android application that fetches and displays current weather for the current location that a user is in. Made use of the Retrofit2 library and GSON. The application reads weather data from the Openweathermap api.

# Screenshots
![Screenshot](https://github.com/PabiMoloi/AndroidWeatherApp/blob/master/art/weatherApp.png)
![Screenshot](https://github.com/PabiMoloi/AndroidWeatherApp/blob/master/art/splashScreen.png)

# Libraries
- Retrofit 2.3.0
- Android Support Library 26.1.0
- Constraint Layout 1.0.2
- GSON 2.8.1

# How the app works
- When the user opens the application, the application will prompt the user to turn on their gps and ensure that they have an internet connection.
- Once available, the application will collect the user's current location, and make an api call to retrive the weather details.
- The application will then the user's street address, weather, and date.

# Attributions
Thank you to the following for use of images off the Noun Project:
Atif Arshad 
- clouds by Atif Arshad from the Noun Project
- sun by Atif Arshad from the Noun Project
- cloud snow by Atif Arshad from the Noun Project
https://thenounproject.com/atifarshad/collection/weather-5