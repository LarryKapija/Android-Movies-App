
import com.larrykapija.moviesapp.network.api.TmdbApiService
import com.larrykapija.moviesapp.BuildConfig
import junit.framework.TestCase.assertEquals
import junit.framework.TestCase.assertNotNull
import okhttp3.mockwebserver.MockResponse
import okhttp3.mockwebserver.MockWebServer
import org.junit.After
import org.junit.Before
import org.junit.Test
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

class TmdbApiServiceTest {

    private lateinit var mockWebServer: MockWebServer
    private lateinit var service: TmdbApiService
    private lateinit var apiAccessKey: String

    @Before
    fun setUp() {
        mockWebServer = MockWebServer()
        mockWebServer.start()
        service = Retrofit.Builder()
            .baseUrl(mockWebServer.url("/"))
            .addConverterFactory(GsonConverterFactory.create())
            .build()
            .create(TmdbApiService::class.java)

        apiAccessKey = BuildConfig.TMDB_API_ACCESS_KEY
    }

    @After
    fun tearDown() {
        mockWebServer.shutdown()
    }

    @Test
    fun `getPopularMovies returns data successfully`() {
        val mockResponseJson = """{
          "page": 1,
          "results": [
            {
              "adult": false,
              "backdrop_path": "/1XDDXPXGiI8id7MrUxK36ke7gkX.jpg",
              "genre_ids": [28, 12, 16, 35, 10751],
              "id": 1011985,
              "original_language": "en",
              "original_title": "Kung Fu Panda 4",
              "overview": "Po is gearing up to become the spiritual leader of his Valley of Peace...",
              "popularity": 5263.595,
              "poster_path": "/wkfG7DaExmcVsGLR4kLouMwxeT5.jpg",
              "release_date": "2024-03-02",
              "title": "Kung Fu Panda 4",
              "video": false,
              "vote_average": 6.916,
              "vote_count": 286
            }
          ],
          "total_pages": 43188,
          "total_results": 863758
        }""".trimIndent()

        mockWebServer.enqueue(MockResponse().setBody(mockResponseJson).setResponseCode(200))

        val response = service.getPopularMovies(apiAccessKey).execute()

        assertNotNull(response.body())
        assertEquals(1, response.body()?.page)
        assertEquals(1011985, response.body()?.results?.get(0)?.id)
        assertEquals("Kung Fu Panda 4", response.body()?.results?.get(0)?.title)
    }
}
