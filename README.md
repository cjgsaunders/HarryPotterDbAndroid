My latest project. A Jetpack Compose Android app that uses the Harry Potter Character API (https://hp-api.onrender.com/api/characters) <br />
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
