package com.ireport.fragment;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Handler;
import android.provider.MediaStore;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.Spinner;
import android.widget.Toast;

import com.github.aurae.retrofit2.LoganSquareConverterFactory;
import com.ireport.R;
import com.ireport.apiService.AddReportService;
import com.ireport.constants.ApiConstants;
import com.ireport.servicesAndGeneralInterface.IntentAndFragmentService;
import com.ireport.utils.CheckInternet;
import com.ireport.utils.GPSTracker;
import com.ireport.utils.ProgressLoader;
import com.ireport.utils.SharedPreferencesUtil;

import org.apache.http.HttpResponse;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.mime.HttpMultipartMode;
import org.apache.http.entity.mime.MultipartEntity;
import org.apache.http.entity.mime.content.FileBody;
import org.apache.http.entity.mime.content.StringBody;
import org.apache.http.impl.client.DefaultHttpClient;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.UnsupportedEncodingException;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import okhttp3.MediaType;
import okhttp3.MultipartBody;
import okhttp3.RequestBody;
import retrofit2.Retrofit;

import static com.ireport.constants.CommonConstants.USER_ADDRESS;
import static com.ireport.constants.CommonConstants.USER_AREA;
import static com.ireport.constants.CommonConstants.USER_LATITUDE;
import static com.ireport.constants.CommonConstants.USER_LONGITUDE;

/**
 * Created by Manoj on 11/23/2016.
 */

public class FileReport extends Fragment implements View.OnClickListener{

    View view;
    LinearLayout addReportImage;
    RecyclerView reportImages;
    EditText description,location,latitude_longitude;
    Spinner litteringSize,litteringLevel;
    Button submitButton;
    ImageView imageOne,imageTwo,imageThree,imageFour,imageFive;

    String litteringSizeText,litteringLevelSize;
    private static final int CAMERA_REQUEST = 1888;

    List<String> imagePathList = new ArrayList<String>();

    public static final String MULTIPART_FORM_DATA = "multipart/form-data";
    Uri fileUri;

    String path1,path2,path3,path4,path5;
    File file1,file2,file3,file4,file5;

    RequestBody userId,userArea,userLongitude,userLatitude,userAddress,userDescription,userReportDate,userReportTime
            ,userReportSize,userReportSeverity,userIPAddress;
    AddReportService addReportService;
    ProgressLoader progressLoader;
    CheckInternet checkInternet;

    GPSTracker gpsTracker;
    Handler handler;
    double latitude = 0,longitude = 0;
    String upLoadServerUri = null;
    /*http entity*/
    MultipartEntity multipartEntity = new MultipartEntity(HttpMultipartMode.BROWSER_COMPATIBLE);

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        /**
         * retrofit initialization and also the service
         * interface of retrofit
         * */
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(ApiConstants.BASE_URL)
                .addConverterFactory(LoganSquareConverterFactory.create())
                .build();
        addReportService = retrofit.create(AddReportService.class);

    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.file_report_fragment,container,false);

        progressLoader = new ProgressLoader();
        checkInternet = new CheckInternet(getActivity());

        submitButton = (Button) view.findViewById(R.id.submit_button);
        submitButton.setOnClickListener(this);

        addReportImage = (LinearLayout) view.findViewById(R.id.add_image);
        addReportImage.setOnClickListener(this);

        reportImages = (RecyclerView) view.findViewById(R.id.image_list);
        reportImages.setLayoutManager(new LinearLayoutManager(getActivity(),LinearLayoutManager.HORIZONTAL,false));

        description = (EditText) view.findViewById(R.id.description);
        location = (EditText) view.findViewById(R.id.location);
        latitude_longitude = (EditText) view.findViewById(R.id.latitude_longitude);

        litteringSize = (Spinner) view.findViewById(R.id.size);
        litteringLevel = (Spinner) view.findViewById(R.id.severity_level);

        imageOne = (ImageView) view.findViewById(R.id.image_one);
        imageTwo = (ImageView) view.findViewById(R.id.image_two);
        imageThree = (ImageView) view.findViewById(R.id.image_three);
        imageFour = (ImageView) view.findViewById(R.id.image_four);
        imageFive = (ImageView) view.findViewById(R.id.image_five);

        gpsTracker = new GPSTracker(getActivity());

        if(gpsTracker.canGetLocation()){
            latitude = gpsTracker.getLatitude();
            longitude = gpsTracker.getLongitude();
        }else {
            gpsTracker.showSettingsAlert();
        }

        handler = new Handler();
        handler.postDelayed(new Runnable() {
            @Override
            public void run() {
                if(latitude == 0){
                    handler.postDelayed(this,500);
                    gpsTracker = new GPSTracker(getActivity());

                    latitude = gpsTracker.getLatitude();
                    longitude = gpsTracker.getLongitude();

                }else {
                    gpsTracker = new GPSTracker(getActivity());

                    latitude = gpsTracker.getLatitude();
                    longitude = gpsTracker.getLongitude();

                    handler.removeCallbacks(this);

                    /**
                     * get Address From GOOGLE useing latitude and longitude
                     * */
                    getLocationDetails();
                }
            }
        },500);

        return view;
    }

    private void getLocationDetails() {
        progressLoader.showProgress(getActivity());

        gpsTracker.getLocationAddress(latitude,longitude);

        latitude_longitude.setText(USER_LATITUDE+","+USER_LONGITUDE);
        location.setText(USER_ADDRESS);

        progressLoader.DismissProgress();

    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (requestCode == CAMERA_REQUEST && resultCode == Activity.RESULT_OK) {
            if (data != null) {
                String[] projection = { MediaStore.Images.Media.DATA };
                Cursor cursor = getActivity().managedQuery(
                        MediaStore.Images.Media.EXTERNAL_CONTENT_URI,
                        projection, null, null, null);
                int column_index_data = cursor
                        .getColumnIndexOrThrow(MediaStore.Images.Media.DATA);
                cursor.moveToLast();

                String imagePath = cursor.getString(column_index_data);

                fileUri = data.getData();
                Bitmap photo = (Bitmap) data.getExtras().get("data");
                Uri tempUri = getImageUri(getActivity(), photo);
                Bitmap bitmapImage = BitmapFactory.decodeFile(getRealPathFromURI(tempUri));

                if(imagePathList.size() == 0){
                    path1 = getRealPathFromURI(tempUri);
                    imageOne.setImageBitmap(bitmapImage);
                }else if(imagePathList.size() == 1){
                    path2 = getRealPathFromURI(tempUri);
                    imageTwo.setImageBitmap(bitmapImage);
                }else if(imagePathList.size() == 2){
                    path3 = getRealPathFromURI(tempUri);
                    imageThree.setImageBitmap(bitmapImage);
                }else if(imagePathList.size() == 3){
                    path4 = getRealPathFromURI(tempUri);
                    imageFour.setImageBitmap(bitmapImage);
                }else if(imagePathList.size() == 4){
                    path5 = getRealPathFromURI(tempUri);
                    imageFive.setImageBitmap(bitmapImage);
                }

                imagePathList.add(imagePath);

            }
        }
    }

    public Uri getImageUri(Context inContext, Bitmap inImage) {
        ByteArrayOutputStream bytes = new ByteArrayOutputStream();
        inImage.compress(Bitmap.CompressFormat.JPEG, 100, bytes);
        String path = MediaStore.Images.Media.insertImage(inContext.getContentResolver(), inImage, "Title", null);
        return Uri.parse(path);
    }

    public String getRealPathFromURI(Uri uri) {
        Cursor cursor = getActivity().getContentResolver().query(uri, null, null, null, null);
        cursor.moveToFirst();
        int idx = cursor.getColumnIndex(MediaStore.Images.ImageColumns.DATA);
        return cursor.getString(idx);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.submit_button:
                litteringSizeText = litteringSize.getSelectedItem().toString();
                litteringLevelSize = litteringLevel.getSelectedItem().toString();

                /**
                 * validate the edit gtetxt value
                 * */
                ValidateEditText();

                break;

            case R.id.add_image:
                if(imagePathList.size() > 4){
                    Toast.makeText(getActivity(), "You can add maximum of five images", Toast.LENGTH_SHORT).show();
                }else {
                    Intent cameraIntent = new Intent(android.provider.MediaStore.ACTION_IMAGE_CAPTURE);
                    startActivityForResult(cameraIntent, CAMERA_REQUEST);
                }
                break;
        }
    }

    private void ValidateEditText() {
        if(description.getText().toString() != null && !description.getText().toString().isEmpty()
                && location.getText().toString() != null && !location.getText().toString().isEmpty()
                && latitude_longitude.getText().toString() != null
                && !latitude_longitude.getText().toString().isEmpty()){
            if(checkInternet.isConnectingToInternet()){
                StartUploadingData();
            }else {
                Toast.makeText(getActivity(), "Please turn on the internet connection to proceed", Toast.LENGTH_SHORT).show();
            }
        }else {
            Toast.makeText(getActivity(), "Please make sure the above edit fields are not empty", Toast.LENGTH_SHORT).show();
        }
    }

    private void StartUploadingData() {
        progressLoader.showProgress(getActivity());

        String litteringSizeValue = null,litteringLevelValue = null;

        if(litteringSize.getSelectedItem().toString().equals("Small (< 12 inches)")){
            litteringSizeValue = "1";
        }else if(litteringSize.getSelectedItem().toString().equals("Median ( between 1-3 feet)")){
            litteringSizeValue = "2";
        }else if(litteringSize.getSelectedItem().toString().equals("Large (between 3-6 feet)")){
            litteringSizeValue = "3";
        }else if(litteringSize.getSelectedItem().toString().equals("Extra Large (6 or more feet)")){
            litteringSizeValue = "4";
        }

        if(litteringLevel.getSelectedItem().toString().equals("Minor")){
            litteringLevelValue = "1";
        }else if(litteringLevel.getSelectedItem().toString().equals("Medium")){
            litteringLevelValue = "2";
        }else if(litteringLevel.getSelectedItem().toString().equals("Urgent")){
            litteringLevelValue = "3";
        }

        Date date = new Date(System.currentTimeMillis());
        DateFormat df = new SimpleDateFormat("yyyy-MM-dd");
        final String formedDate = df.format(date);

        DateFormat tf = new SimpleDateFormat("HH : mm ");
        final String timeFormat = tf.format(date);

        upLoadServerUri = ApiConstants.BASE_URL+ApiConstants.ADD_REPORT;
        final String finalLitteringSizeValue = litteringSizeValue;
        final String finalLitteringLevelValue = litteringLevelValue;

       /* new Thread(new Runnable() {
            @Override
            public void run() {
                uploadFiles(path1,path2,path3,path4,path5,formedDate,timeFormat, finalLitteringSizeValue, finalLitteringLevelValue);
            }
        }).start();*/

        new UploadFileData(path1,path2,path3,path4,path5,formedDate,timeFormat, finalLitteringSizeValue, finalLitteringLevelValue).execute();
    }

    private void MoveToList() {
        IntentAndFragmentService.fragmentdisplay(getActivity(),R.id.main_frame,new MyReportList(),null,false,false);
    }


    private class UploadFileData extends AsyncTask<String,String,String>{
        String path1,path2,path3, path4, path5;
        String formedDate, timeFormat, finalLitteringSizeValue,finalLitteringLevelValue;

        FileBody fileBody1 = null,fileBody2 = null,fileBody3 = null,fileBody4 = null,fileBody5 = null;

        String descriptionValue = description.getText().toString();

        public UploadFileData(String path1, String path2, String path3, String path4, String path5,
                              String formedDate, String timeFormat, String finalLitteringSizeValue, String finalLitteringLevelValue) {
            this.path1 = path1;
            this.path2 = path2;
            this.path3 = path3;
            this.path4 = path4;
            this.path5 = path5;
            this.formedDate = formedDate;
            this.timeFormat = timeFormat;
            this.finalLitteringSizeValue = finalLitteringSizeValue;
            this.finalLitteringLevelValue = finalLitteringLevelValue;
        }

        @Override
        protected String doInBackground(String... params) {
            HttpClient httpClient = new DefaultHttpClient();
            HttpPost httpPost = new HttpPost(upLoadServerUri);

            try {

                if(path1 != null){
                    file1 = new File(path1);
                    fileBody1 = new FileBody(file1);
                }
                if(path2 != null){
                    file2 = new File(path2);
                    fileBody2 = new FileBody(file2);
                }
                if(path3 != null){
                    file3 = new File(path3);
                    fileBody3 = new FileBody(file3);
                }
                if(path4 != null){
                    file4 = new File(path4);
                    fileBody4 = new FileBody(file4);
                }
                if(path5 != null){
                    file5 = new File(path5);
                    fileBody5 = new FileBody(file5);
                }

                multipartEntity.addPart("user_id",new StringBody(SharedPreferencesUtil.getInstance(getActivity()).getUserId()));
                multipartEntity.addPart("area",new StringBody(USER_AREA));
                multipartEntity.addPart("longitude",new StringBody(USER_LONGITUDE));
                multipartEntity.addPart("latitude",new StringBody(USER_LATITUDE));
                multipartEntity.addPart("address",new StringBody(USER_ADDRESS));
                multipartEntity.addPart("description",new StringBody(descriptionValue));
                multipartEntity.addPart("report_date",new StringBody(formedDate));
                multipartEntity.addPart("report_time",new StringBody(timeFormat));
                multipartEntity.addPart("report_size",new StringBody(finalLitteringSizeValue));
                multipartEntity.addPart("report_severity_level",new StringBody(finalLitteringLevelValue));
                multipartEntity.addPart("email",new StringBody(SharedPreferencesUtil.getInstance(getActivity()).getemailId()));
                multipartEntity.addPart("screen_name",new StringBody(SharedPreferencesUtil.getInstance(getActivity()).getscreenName()));
                if(fileBody1 != null){
                    multipartEntity.addPart("report_image1", fileBody1);
                }
                if(fileBody2 != null){
                    multipartEntity.addPart("report_image2", fileBody2);
                }
                if(fileBody3 != null){
                    multipartEntity.addPart("report_image3", fileBody3);
                }
                if(fileBody4 != null){
                    multipartEntity.addPart("report_image4", fileBody4);
                }
                if(fileBody5 != null){
                    multipartEntity.addPart("report_image5", fileBody5);
                }

            } catch (UnsupportedEncodingException e) {
                e.printStackTrace();
            }catch (Exception e){
                e.printStackTrace();
            }

            try {
                httpPost.setEntity(multipartEntity);
                HttpResponse httpResponse = httpClient.execute(httpPost);
                InputStream inputStream = httpResponse.getEntity()
                        .getContent();
                InputStreamReader inputStreamReader = new InputStreamReader(
                        inputStream);
                BufferedReader bufferedReader = new BufferedReader(
                        inputStreamReader);
                StringBuilder stringBuilder = new StringBuilder();

                String bufferedStrChunk = null;

                while ((bufferedStrChunk = bufferedReader.readLine()) != null) {
                    stringBuilder.append(bufferedStrChunk);
                }

                return stringBuilder.toString();
            }catch (ClientProtocolException cpe) {
                cpe.printStackTrace();
            }catch (IOException ioe) {
                ioe.printStackTrace();
            }catch (IndexOutOfBoundsException e) {
                e.printStackTrace();
            }catch (Exception e){
                e.printStackTrace();
            }

            return null;
        }

        @Override
        protected void onPostExecute(String s) {
            super.onPostExecute(s);
            progressLoader.DismissProgress();

            try {
                JSONObject jsonObject = new JSONObject(s);
                if(jsonObject.has("status")){
                    if(jsonObject.getString("status") != null){
                        if(jsonObject.getString("status").equals("success")){
                            MoveToList();
                        }
                    }
                }
            }catch (Exception e){
                e.printStackTrace();
            }
        }
    }
}
