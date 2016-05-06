package disaster.master.chef2;

import retrofit2.Call;
import okhttp3.ResponseBody;
import retrofit2.http.GET;

public interface Get {

    @GET("/getAll.php?type=ZdjeciaEtapow&receptureName=muszleZLososiem")
    Call<ResponseBody> getStepsPhotosForSalmoNudle();

    @GET("/getAll.php?type=Wideo&receptureName=muszleZLososiem")
    Call<ResponseBody> getWideoForSalmoNudle();

    @GET("/getAll.php?type=ZdjeciaPotraw&receptureName=muszleZLososiem")
    Call<ResponseBody> getPhotosForSalmoNudle();

    @GET("/getAll.php?type=ZdjeciaEtapow&receptureName=kokosowaPotrawkaZAnanasem")
    Call<ResponseBody> getStepsPhotosCoconutStewWithPineapple();

    @GET("/getAll.php?type=ZdjeciaPotraw&receptureName=kokosowaPotrawkaZAnanasem")
    Call<ResponseBody> getPhotosCoconutStewWithPineapple();

    @GET("/getAll.php?type=ZdjeciaEtapow&receptureName=slodkiePampuchy")
    Call<ResponseBody> getStepsPhotosSweetPampuchy();

    @GET("/getAll.php?type=Wideo&receptureName=slodkiePampuchy")
    Call<ResponseBody> getWideoForSweetPampuchy();

    @GET("/getAll.php?type=ZdjeciaPotraw&receptureName=slodkiePampuchy")
    Call<ResponseBody> getPhotosSweetPampuchy();

    @GET("/getAll.php?type=ZdjeciaEtapow&receptureName=zupaPomidorowa")
    Call<ResponseBody> getStepsPhotosForTomatoSoup();

    @GET("/getAll.php?type=Wideo&receptureName=zupaPomidorowa")
    Call<ResponseBody> getWideoForTomatoSoup();

    @GET("/getAll.php?type=ZdjeciaPotraw&receptureName=zupaPomidorowa")
    Call<ResponseBody> getPhotosTomatoSoup();

    @GET("/getAll.php?type=ZdjeciaEtapow&receptureName=morszczukZapiekany")
    Call<ResponseBody> getStepsPhotosForBakedHake();

    @GET("/getAll.php?type=Wideo&receptureName=morszczukZapiekany")
    Call<ResponseBody> getWideoForBakedHake();

    @GET("/getAll.php?type=ZdjeciaPotraw&receptureName=morszczukZapiekany")
    Call<ResponseBody> getPhotosBakedHake();

    @GET("/getAll.php?type=ZdjeciaEtapow&receptureName=plackiGryczane")
    Call<ResponseBody> getStepsPhotosForCocoaBuckwheatPancakes();

    @GET("/getAll.php?type=ZdjeciaPotraw&receptureName=plackiGryczane")
    Call<ResponseBody> getPhotosCocoaBuckwheatPancakes();

    @GET("/getAll.php?type=ZdjeciaEtapow&receptureName=rurkiZKremem")
    Call<ResponseBody> getStepsPhotosForTubesCream();

    @GET("/getAll.php?type=Wideo&receptureName=rurkiZKremem")
    Call<ResponseBody> getWideoForTubesCream();

    @GET("/getAll.php?type=ZdjeciaPotraw&receptureName=rurkiZKremem")
    Call<ResponseBody> getPhotosTubesCream();

    @GET("/getAll.php?type=ZdjeciaEtapow&receptureName=salatkaZKielkami")
    Call<ResponseBody> getStepsPhotosForSaladWithSprouts();

    @GET("/getAll.php?type=ZdjeciaPotraw&receptureName=salatkaZKielkami")
    Call<ResponseBody> getPhotosSaladWithSprouts();

}
