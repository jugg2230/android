package NetWork;

import Beans.ReturnMsg;
import Utils.MySubscriber;
import retrofit2.Retrofit;
import retrofit2.adapter.rxjava.RxJavaCallAdapterFactory;
import retrofit2.converter.gson.GsonConverterFactory;
import retrofit2.http.GET;
import rx.Observable;

/**
 * Created by Dream on 2016/11/6.
 */

public class HelpApiManager {
    private static HelpApiManager mInstant;
    public static final String BaseUrl="1";

    private HelpApiManager(){

    }
    public static HelpApiManager getInstant(){
        if(mInstant == null){
            mInstant=new HelpApiManager();
        }
        return mInstant;
    }
    public void sendRequest(String url, MySubscriber ss){
        Retrofit retrofit=new Retrofit.Builder()
                .baseUrl(BaseUrl)
                .addConverterFactory(GsonConverterFactory.create())
                .addCallAdapterFactory(RxJavaCallAdapterFactory.create())
                .build();
    }
    public interface HelpApi{
         String parameter="";
        @GET(parameter)
        Observable<ReturnMsg> getData();
    }
}
