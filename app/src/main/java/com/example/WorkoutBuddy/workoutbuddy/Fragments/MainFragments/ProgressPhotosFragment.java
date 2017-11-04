package com.example.WorkoutBuddy.workoutbuddy.Fragments.MainFragments;

import android.app.ProgressDialog;
import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.Fragment;
import android.support.v4.content.FileProvider;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.WorkoutBuddy.workoutbuddy.Activities.MainActivity;
import com.example.WorkoutBuddy.workoutbuddy.DataBase.Data.ProgressPhoto;
import com.example.WorkoutBuddy.workoutbuddy.DataBase.TableManagers.ProgressPhotosTable;
import com.example.WorkoutBuddy.workoutbuddy.Fragments.FragmentPopupWindows.ProgressPhotoPopupWindows.DeleteProgressPhotoPopup;
import com.example.WorkoutBuddy.workoutbuddy.Fragments.FragmentPopupWindows.ProgressPhotoPopupWindows.ExpandedImagePopup;
import com.example.WorkoutBuddy.workoutbuddy.R;
import com.example.WorkoutBuddy.workoutbuddy.RemoteDatabase.Api.CoreAPI;
import com.example.WorkoutBuddy.workoutbuddy.RemoteDatabase.Api.ProgressPhotoApi;
import com.example.WorkoutBuddy.workoutbuddy.RemoteDatabase.RequestHandlers.ProgressPhotoRequestHandler;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.File;
import java.io.IOException;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import static com.example.WorkoutBuddy.workoutbuddy.Activities.MainActivity.REQUEST_IMAGE_CAPTURE;
// Save photos to file(reork everything)
public class ProgressPhotosFragment extends Fragment {

    private View root;
    private String path;
    private MainActivity mainActivity;
    private RecyclerView recyclerView;
    private List<ProgressPhoto> progressPhotos;
    private ProgressPhotoAdapter progressPhotoAdapter;

    public ProgressPhotosFragment() {
        // Required empty public constructor
    }

    public void setMainActivity(MainActivity mainActivity) {
        this.mainActivity = mainActivity;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        root = inflater.inflate(R.layout.fragment_progress_photos, container, false);
        new LocalAsyncTask().execute(progressPhotos);
        setFloatingActionButton();
        return root;
    }

    @Override
    public void onDetach() {
        super.onDetach();
        resetFloatingActionButton();
    }

    private void resetFloatingActionButton() {
        FloatingActionButton fab = (FloatingActionButton) getActivity().findViewById(R.id.fab);
        if(fab != null) {
            fab.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    //do nothing
                }
            });
        }
    }

    private FloatingActionButton setFloatingActionButton() {
        FloatingActionButton fab = (FloatingActionButton) getActivity().findViewById(R.id.fab);
        if(fab != null) {
            fab.setVisibility(View.VISIBLE);
            fab.setImageResource(R.drawable.ic_menu_camera);
            handleFloatingActionButtonEvents(fab);
        }
        return fab;
    }

    private void handleFloatingActionButtonEvents(FloatingActionButton fab) {
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
               openCamera();
            }
        });
    }

    private void openCamera() {
        try {
            Intent intent = new Intent("android.media.action.IMAGE_CAPTURE");
            Uri photoURI = FileProvider.getUriForFile(getContext(),
                    "com.example.WorkoutBuddy.workoutbuddy.FileProvider",
                    createImageFile());
            intent.putExtra(MediaStore.EXTRA_OUTPUT, photoURI);
            mainActivity.startActivityForResult(intent, REQUEST_IMAGE_CAPTURE);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private File createImageFile() throws IOException {
        String timeStamp = new SimpleDateFormat("yyyyMMdd_HHmmss").format(new Date());
        String imageFileName = "JPEG_"+timeStamp+"_";
        File storageDir = mainActivity.getExternalFilesDir(Environment.DIRECTORY_PICTURES);
        System.out.println(storageDir.getAbsolutePath());
        File image = File.createTempFile(imageFileName,".jpg",storageDir);

        path = image.getAbsolutePath();
        System.out.println(path);
        return image;
    }

    public void saveImageFile() {
        ProgressPhotosTable table = new ProgressPhotosTable(getContext());
        DateFormat dateFormat = new SimpleDateFormat("yyyy/MM/dd  HH:mm:ss");
        String date = dateFormat.format(new Date());
        table.addProgressPhoto(date,path);
    }

    private void showExpandedImagePopup(Bitmap image) {
        ExpandedImagePopup popup = new ExpandedImagePopup(getContext(),root);
        popup.setImage(image);
        popup.showPopupWindow();//expanded
    }

    private void setRecycleView(View root,ProgressPhotoAdapter adapter) {
        recyclerView = (RecyclerView) root.findViewById(R.id.photoRecycleView);
        RecyclerView.LayoutManager manager = new GridLayoutManager(getContext(),2);
        recyclerView.setLayoutManager(manager);
        recyclerView.setItemViewCacheSize(12);
        recyclerView.setHasFixedSize(true);
        recyclerView.setAdapter(adapter);
        if(recyclerView.getAdapter().getItemCount()==0) {
            TextView textView = (TextView) root.findViewById(R.id.noProgressPhotosView);
            textView.setVisibility(View.VISIBLE);
        }
    }

    public void addPhotoToList(ProgressPhoto photo) {
        if(progressPhotoAdapter.getItemCount()==0) {
            progressPhotos.add(photo);
            progressPhotoAdapter.notifyDataSetChanged();
        } else {
            progressPhotos.add(0, photo);
            progressPhotoAdapter.notifyItemInserted(0);
        }
        TextView textView = (TextView) root.findViewById(R.id.noProgressPhotosView);
        if(textView.getVisibility()==View.VISIBLE) {
            textView.setVisibility(View.INVISIBLE);
        }
    }

    private void showDeleteProgressPhotoPopup(int positon) {
        DeleteProgressPhotoPopup popup = new DeleteProgressPhotoPopup(root,getContext());
        popup.setPosition(positon);
        popup.setRecyclerView(recyclerView);
        popup.setProgressPhotoList(progressPhotos);
        popup.showPopupWindow();
    }

    public class ProgressPhotoAdapter extends RecyclerView.Adapter<ProgressPhotoAdapter.CustomViewHolder> {

        List<ProgressPhoto> progressPhotoList;

        public ProgressPhotoAdapter(List<ProgressPhoto> progressPhotoList) {
            this.progressPhotoList = progressPhotoList;
        }

        @Override
        public int getItemCount() {
            return (null != progressPhotoList ? progressPhotoList.size() : 0);
        }

        @Override
        public CustomViewHolder onCreateViewHolder(ViewGroup viewGroup,int i) {
            View view = LayoutInflater.from(viewGroup.getContext())
                    .inflate(R.layout.progress_photo_cardview,null);
            return new CustomViewHolder(view);
        }

        @Override
        public void onBindViewHolder(CustomViewHolder customViewHolder,int i) {
            ProgressPhoto progressPhoto = progressPhotoList.get(i);
            customViewHolder.dateView.setText(progressPhoto.getDate().substring(0,10));
            customViewHolder.imageView.setImageBitmap(progressPhoto.getProgressPhoto());
        }


        class CustomViewHolder extends RecyclerView.ViewHolder {

            protected TextView dateView;
            protected ImageView imageView;

            public CustomViewHolder(View rowView) {
                super(rowView);
                dateView = (TextView) rowView.findViewById(R.id.progressPhotoDateView);
                imageView = (ImageView) rowView.findViewById(R.id.progressPhotoImageView);
                deleteProgressPhoto(imageView);
                expandedImage(imageView);
            }

            private void expandedImage(ImageView imageView) {
                imageView.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        int i = getLayoutPosition();
                        Bitmap image = progressPhotoList.get(i).getProgressPhoto();
                        showExpandedImagePopup(image);
                    }
                });
            }

            private void deleteProgressPhoto(ImageView imageView) {
               imageView.setOnLongClickListener(new View.OnLongClickListener() {
                   @Override
                   public boolean onLongClick(View v) {
                       showDeleteProgressPhotoPopup(getLayoutPosition());
                       return true;
                   }
               });
            }
        }
    }

    class LocalAsyncTask extends AsyncTask<List<ProgressPhoto>,Void,List<ProgressPhoto>> {

        private ProgressPhotosTable table;
        private ProgressDialog progressDialog;

        @Override
        protected void onPreExecute() {
            table = new ProgressPhotosTable(getContext());
            progressDialog = new ProgressDialog(getContext(),ProgressDialog.STYLE_SPINNER);
            progressDialog.setMessage("Loading...");
            progressDialog.show();
        }

        @Override
        protected List<ProgressPhoto> doInBackground(List<ProgressPhoto>[] params) {
            params[0] = table.getProgressPhotos();
            progressPhotos = params[0];
            return params[0];
        }

        @Override
        protected void onPostExecute(List<ProgressPhoto> progressPhotos) {
            progressDialog.dismiss();
            progressPhotoAdapter = new ProgressPhotoAdapter(progressPhotos);
            setRecycleView(root,progressPhotoAdapter);
        }
    }

    private class RemoteAsyncTask extends AsyncTask<Void, Void, String> {

        @Override
        protected String doInBackground(Void... params) {
            ProgressPhotoRequestHandler requestHandler = new ProgressPhotoRequestHandler();
            return requestHandler.sendGetProgressPhotoPathRequest(CoreAPI.getUserId());
        }

        @Override
        protected void onPostExecute(String s) {
            try {
                JSONObject jsonObject = new JSONObject(s);
                if(!jsonObject.getBoolean(CoreAPI.JSON_ERROR)) {
                    //set adapter and shit
                }
            } catch (JSONException e) {
                e.printStackTrace();
            }
        }

        private List<String> getData(JSONObject jsonObject) throws JSONException {
            List<String> list = new ArrayList<>();

            JSONArray array = jsonObject.getJSONArray(CoreAPI.JSON_KEY);
            for(int x=0;x<array.length();x++) {
                JSONObject object = (JSONObject) array.get(x);
                String date = object.getString(ProgressPhotoApi.JSON_DATE_TIME);
                String photoPath = object.getString(ProgressPhotoApi.JSNON_PHOTO_PATH);


            }

            return list;
        }
    }
}
