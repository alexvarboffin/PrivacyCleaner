//package com.walhalla.privacycleaner;
//
//import android.content.Context;
//import android.content.Intent;
//import android.content.SharedPreferences;
//import android.os.Bundle;
//
//import androidx.annotation.NonNull;
//import androidx.annotation.Nullable;
//import androidx.fragment.app.Fragment;
//import android.view.LayoutInflater;
//import android.view.View;
//import android.view.ViewGroup;
//import android.widget.ImageView;
//import android.widget.TextView;
//import android.widget.Toast;
//
//import java.util.Random;
//
//
//public class JunkCleanerFragment extends Fragment {
//
//
//    private LayoutInflater inflater;
//    private ViewGroup container;
//    private View mView;
//    ImageView mainIcon, cache_memory_icon, temporary_files_icon, residual_icon, system_junk_icon, mainButton;
//    TextView cache_memory_title, temp_files_title, residual_title, system_junk, mainText;
//    private SharedPreferences preferences;
//    private boolean toClean = true;
//
//    @Override
//    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
//        this.inflater = inflater;
//        this.container = container;
//        init();
//        return mView;
//    }
//
//    private void init() {
//        initViews();
//        initPrefs();
//        if (toClean) {
//            //changeCleanState();
//        }
//        initListeners();
//    }
//
//    private void initListeners() {
//        mainButton.setOnClickListener(v -> {
//            if (!cache_memory_title.getText().equals("0 MB")) {
////                Intent i = new Intent(getActivity(), JunkCleaningActivity.class);
////                i.putExtra("mb", mainText.getText().toString().split(" ")[0]);
////                startActivityForResult(i, 1);
//            } else {
//                Toast.makeText(getActivity(), "No Junk Files Already Cleaned.", Toast.LENGTH_LONG).show();
//            }
//        });
//    }
//
//    private void finishCleaning() {
////        AlarmManager mgr = (AlarmManager) getActivity().getSystemService(Context.ALARM_SERVICE);
////        Intent i = new Intent(getActivity(), CleanAlarmReceiver.class);
////        PendingIntent pi = PendingIntent.getBroadcast(getActivity(), 0, i, 0);
////        mgr.setRepeating(AlarmManager.ELAPSED_REALTIME_WAKEUP, SystemClock.elapsedRealtime(), 10 * 60 * 1000, pi);
////        SharedPreferences.Editor editor = preferences.edit();
////        editor.putBoolean("toClean", false);
////        editor.apply();
//    }
//
//
//
//
//
//    private void generateAndSetRandomNums() {
//        String cache = (new Random().nextInt(25) + 5) + " MB";
//        String temp = (new Random().nextInt(15) + 7) + " MB";
//        String res = (new Random().nextInt(30) + 9) + " MB";
//        String sys = (new Random().nextInt(25) + 15) + " MB";
//        String sum = (Integer.parseInt(cache.split(" ")[0]) + Integer.parseInt(temp.split(" ")[0]) + Integer.parseInt(res.split(" ")[0]) + Integer.parseInt(sys.split(" ")[0])) + " MB";
//        cache_memory_title.setText(cache);
//        temp_files_title.setText(temp);
//        mainText.setText(sum);
//        residual_title.setText(res);
//        system_junk.setText(sys);
//    }
//
//    private void initPrefs() {
//        preferences = getActivity().getSharedPreferences("junk", Context.MODE_PRIVATE);
//        if (!preferences.contains("toClean")) {
//            SharedPreferences.Editor editor = preferences.edit();
//            editor.putBoolean("toClean", true);
//            editor.apply();
//            toClean = true;
//        } else {
//            toClean = preferences.getBoolean("toClean", true);
//        }
//    }
//
//    private void initViews() {
//        mView = inflater.inflate(R.layout.junk_cleaner_fragment, container, false);
//        mainIcon = mView.findViewById(R.id.mainbrush);
//        cache_memory_icon = mView.findViewById(R.id.cache);
//        temporary_files_icon = mView.findViewById(R.id.temp);
//        residual_icon = mView.findViewById(R.id.residue);
//        system_junk_icon = mView.findViewById(R.id.system);
//        cache_memory_title = mView.findViewById(R.id.cachetext);
//        temp_files_title = mView.findViewById(R.id.temptext);
//        residual_title = mView.findViewById(R.id.residuetext);
//        system_junk = mView.findViewById(R.id.systemtext);
//        mainText = mView.findViewById(R.id.maintext);
//        mainButton = mView.findViewById(R.id.mainbutton);
//    }
//
//    @Override
//    public void onActivityResult(int requestCode, int resultCode, Intent data) {
//        if (requestCode == 1) {
//            finishCleaning();
//            showRateUs(this);
//        }
//        super.onActivityResult(requestCode, resultCode, data);
//    }
//
//    private void showRateUs(JunkCleanerFragment fragment) {
//        if(fragment.getFragmentManager() != null) {
//            SharedPreferences sharedPreferences = fragment.getContext().getSharedPreferences("show_rate_us", Context.MODE_PRIVATE);
//            if(sharedPreferences.getBoolean("show", true)) {
//                RateUsDialogFragment newFragment = RateUsDialogFragment.newInstance();
//                newFragment.show(fragment.getFragmentManager(), "dialog");
//            }
//        }
//    }
//
//    @Override
//    public void onResume() {
//        if (preferences.getBoolean("toClean", true) != toClean) {
//            toClean = preferences.getBoolean("toClean", true);
//            //changeCleanState();
//        }
//        super.onResume();
//    }
//}
