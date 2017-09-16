package data.model.remote;

/**
 * Created by Pabi Moloi on 9/12/2017.
 */

public class RetrofitUtility
{
    public static final String BASE_URL="http://api.openweathermap.org/data/2.5/";
    public static RetrofitService getRetrofitService()
    {
        return RetrofitClient.getClient(BASE_URL).create(RetrofitService.class);
    }
}
