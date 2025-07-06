A Jetpack Compose Android app that fetches data from a Harry Potter Character API (https://hp-api.onrender.com/api/characters) . The app features a responsive UI that adapts to different screen sizes, with full support for dark and light modes.  <br />
 
Users can search characters by name or actor, with real-time filtering.  <br />

App has an offline mode showing data cached to the device using room if it cannot reach the API. <br />
 <br />


https://github.com/user-attachments/assets/c6194b2f-0f2c-4872-83a2-e2e302a155dd





Tech Used:<br />
<br />
Hilt for DI<br />
MockK for testing<br />
Compose for the views<br />
Coil to asyncronously load images from the web into compose<br />
Retrofit to make API calls<br />
Room to persist data<br />
Googles snapshot testing library<br />
Flows to send data between the layers<br />
<br />

Designed with clean architecture keeping :<br />
<br />
api calls and their mappers in the data layer, <br />
objects created from those apis and the api interface in the domain layer <br />
and views and viewmodels in the presentation layer<br />
<br />
<br />

Created by Chris Saunders 20 Oct 2024
