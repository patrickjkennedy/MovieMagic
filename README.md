# Movie Magic

This Android application requests data from the [themoviedb.org](https://www.themoviedb.org/) API and returns it to the user. 
It displays either a list of the most popular or else the highest user-rated movies. 
It uses the [Picasso](https://square.github.io/picasso/) library to load the images. 
Users can get detailed information about a movie by tapping on that movie's poster.

![Main Grid](app/src/main/res/drawable-xxxhdpi/grid.png
 "Main Grid") ![Detail](app/src/main/res/drawable-xxxhdpi/detail.png
 "Detail")

## API Key Required

To use this application, you will need to request an API key from [themoviedb.org.](https://www.themoviedb.org/)
The steps to do so are detailed [on their site.](https://www.themoviedb.org/faq/api)
Insert the key where specified in the TODO in [NetworkUtils.java](app/src/main/java/com/example/android/moviemagic/utilities/NetworkUtils.java) and
you're all set.

## License

The contents of this repository are covered under the [MIT License.](LICENSE)