# Movie Magic

This Android application requests data from the [themoviedb.org](https://www.themoviedb.org/) API and returns it to the user. 
It displays either a list of the most popular or else the highest user-rated movies. 
It uses the [Picasso](https://square.github.io/picasso/) library to load the images. 
Users can get detailed information about a movie by tapping on that movie's poster. 

Users can watch trailers, read reviews, and mark films as favourites and display these within the app. 

<img src="https://user-images.githubusercontent.com/8617261/36937472-9c0560da-1f0b-11e8-8cf2-df4a7c37006b.png" alt="Main Grid"/>
<img src="https://user-images.githubusercontent.com/8617261/36937474-aa6d4692-1f0b-11e8-8e88-ea4304b2bdb3.png" alt="Detail"/>

## API Key Required

To use this application, you will need to request an API key from [themoviedb.org.](https://www.themoviedb.org/)
The steps to do so are detailed [on their site.](https://www.themoviedb.org/faq/api)

Add the following line to [USER_HOME]/.gradle/gradle.properties:

```MyTheMovieDBApiToken="XXXXX"```

Resync your project and you're all set!

## License

The contents of this repository are covered under the [MIT License.](LICENSE)

<div>Clapper icon made by <a href="http://www.freepik.com" title="Freepik">Freepik</a> from <a href="https://www.flaticon.com/" title="Flaticon">www.flaticon.com</a> is licensed by <a href="http://creativecommons.org/licenses/by/3.0/" title="Creative Commons BY 3.0" target="_blank">CC 3.0 BY</a></div>