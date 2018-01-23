# Movie Magic

This Android application requests data from the [themoviedb.org](https://www.themoviedb.org/) API and returns it to the user. 
It displays either a list of the most popular or else the highest user-rated movies. 
It uses the [Picasso](https://square.github.io/picasso/) library to load the images. 
Users can get detailed information about a movie by tapping on that movie's poster.

<img src="https://user-images.githubusercontent.com/8617261/35288258-16a63ea0-005c-11e8-94ef-01ea835880d6.png" alt="Main Grid" width="200" height="300" />
<img src="https://user-images.githubusercontent.com/8617261/35288260-17eb3b1c-005c-11e8-9356-c141d348b2cb.png" alt="Detail" width="200" height="300" />

## API Key Required

To use this application, you will need to request an API key from [themoviedb.org.](https://www.themoviedb.org/)
The steps to do so are detailed [on their site.](https://www.themoviedb.org/faq/api)
Insert the key where specified in the TODO in [NetworkUtils.java](app/src/main/java/com/example/android/moviemagic/utilities/NetworkUtils.java) and
you're all set.

## License

The contents of this repository are covered under the [MIT License.](LICENSE)