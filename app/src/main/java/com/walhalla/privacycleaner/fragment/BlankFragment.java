package com.walhalla.privacycleaner.fragment;

import android.animation.Animator;
import android.animation.AnimatorInflater;
import android.animation.ObjectAnimator;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.os.Build;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Handler;
import android.text.Spannable;
import android.text.SpannableStringBuilder;
import android.text.style.BackgroundColorSpan;
import android.text.style.ForegroundColorSpan;
import android.text.style.StyleSpan;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.LinearInterpolator;
import android.view.animation.OvershootInterpolator;
import android.view.animation.RotateAnimation;
import android.widget.CheckBox;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.github.ybq.android.spinkit.style.ThreeBounce;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.snackbar.Snackbar;
import com.google.api.services.gmail.model.Message;
import com.skyfishjy.library.RippleBackground;
import com.walhalla.ui.DLog;
import com.wang.avi.AVLoadingIndicatorView;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;
import java.util.TimerTask;

import aa.model.GmailMessageViewModel;
import aa.model.RecyclerMenuViewModel;
import aa.model.ViewModel;
import com.walhalla.privacycleaner.CoolTimer;
import com.walhalla.privacycleaner.R;
import com.walhalla.privacycleaner.State;
import com.walhalla.privacycleaner.config.Config;
import com.walhalla.privacycleaner.mvp.view.MainView;
import com.walhalla.privacycleaner.repository.PrivacyCleaner;
import com.walhalla.privacycleaner.ui.ComplexRecyclerViewAdapter;
import com.walhalla.privacycleaner.ui.SimpleDividerItemDecoration;
import jp.wasabeef.recyclerview.animators.SlideInLeftAnimator;
import jp.wasabeef.recyclerview.animators.SlideInUpAnimator;

import static android.content.Context.MODE_PRIVATE;
import static android.text.Spanned.SPAN_EXCLUSIVE_EXCLUSIVE;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link BlankFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class BlankFragment extends Fragment implements PrivacyCleaner.Callback<Set<Message>> {

    private MainView ___mView; //Callback to Activity
    private static final String KEY_NAG_SCREEN_DISABLED = "warning";
    private AVLoadingIndicatorView avi1, avi2, avi3, avi4, avi5, avi6;
    //Radar
    private ImageView front, back;
    private int check = 0;
    private TextView files;
    private int applicationIndex = 0;

    private CoolTimer T2;
    private ComplexRecyclerViewAdapter mAdapter;
    private RecyclerView recyclerView;
    private List<ViewModel> messages = new ArrayList<>();

    protected TextView scanning;
    private int total_count_in_box = -1;

    private FloatingActionButton submit;
    //EditText edtToAddress, edtSubject, edtMessage, edtAttachmentData;

    public ProgressDialog mProgress;

    public String fileName = "";
    private View fab;
    private RippleBackground rippleBackground;
    private ProgressBar progressBar;

    @Override
    public void successResult(Set<Message> data) {
        //data = null;
        if (data == null || data.isEmpty()) {
            DLog.d("@@@@@@@@@@@ Почтовый ящик пуст");
            List<ViewModel> data2 = new ArrayList<>();
            ViewModel m = header();
            data2.add(m);
            initAppsAnimation(data2);
            return;
        }

//   List<Object> lst = new ArrayList<>();
//   lst.addAll(data);
//   initAppsAnimation(lst);


        //Delete all messages
        List<GmailMessageViewModel> delete_all_data = new ArrayList<>(data.size());
        for (Message message : data) {
            delete_all_data.add(new GmailMessageViewModel(message.getId(), message.getThreadId(), R.drawable.ic_email_black_24dp));
        }

        if (!Config.DEMO_MODE) {
            //"Too many message ids in request; max 1000",
            if (___mView != null) {
                ___mView.deleteGmailMessagesRequest(delete_all_data);
            }
        }

        List<ViewModel> lst = new ArrayList<>();
        lst.addAll(delete_all_data);
        initAppsAnimation(lst);


//         for (Message datum : data) {
//             try {
//                 //DLog.d( "successResult: " + datum.toPrettyString());
//                 //GmailMessageViewModel message = readMessages(datum.getId());
//                 //Log.i(TAG, "successResult: " + message.toPrettyString());
//             } catch (Exception e) {
//                 e.printStackTrace();
//             }
//         }
    }

    /**
     * Handle Exception from repository
     */
    @Override
    public void onFailure(Exception e) {
        ___mView.handleError(e, this);
    }

    /**
     * Return message array in one token
     */
    @Override
    public void onProgress(String finI) {
        if (finI != null && !finI.isEmpty()) {
            setLabel(finI.replace(",", "\t")
                    .replace("\\d", "*"));
        }
//                        List<Object> data2 = new ArrayList<>();
//                        GmailMessageViewModel m = new GmailMessageViewModel("000", "0000");
//                        data2.add(m);
//                        initAppsAnimation(data2);
    }

    @Override
    public void mailboxEmpty(String response) {
        setLabel(getString(R.string.mailbox_empty, response));
        initAppsAnimation(new ArrayList<>());
    }

    public interface Callback {
        void getResultsFromApi(@Nullable PrivacyCleaner.Callback<Set<Message>> mListener);
    }

    @Override
    public void onCreateOptionsMenu(@NonNull Menu menu, MenuInflater inflater) {
        inflater.inflate(R.menu.menu_main, menu);
        super.onCreateOptionsMenu(menu, inflater);
    }

    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    private String mParam1;
    private String mParam2;

    public BlankFragment() {
        // Required empty public constructor
    }


    public static BlankFragment newInstance(String param1, String param2) {
        BlankFragment fragment = new BlankFragment();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        setHasOptionsMenu(true);
        super.onCreate(savedInstanceState);

        if (getArguments() != null) {
            mParam1 = getArguments().getString(ARG_PARAM1);
            mParam2 = getArguments().getString(ARG_PARAM2);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        return inflater.inflate(R.layout.content_main, container, false);
    }

    //back stack....
    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        rippleBackground = view.findViewById(R.id.contentMain);
        avi1 = view.findViewById(R.id.scan1);
        avi2 = view.findViewById(R.id.scan2);
        avi3 = view.findViewById(R.id.scan3);
        avi4 = view.findViewById(R.id.scan4);
        avi5 = view.findViewById(R.id.scan5);
        avi6 = view.findViewById(R.id.scan6);
        files = view.findViewById(R.id.files);
        back = view.findViewById(R.id.back);
        scanning = view.findViewById(R.id.scanning);
        front = view.findViewById(R.id.front);
        recyclerView = view.findViewById(R.id.recycler_view);
        recyclerView.setItemAnimator(new SlideInLeftAnimator());
        recyclerView.addItemDecoration(new SimpleDividerItemDecoration(getContext()));
        progressBar = view.findViewById(R.id.spin_kit);
        mAdapter = new ComplexRecyclerViewAdapter(messages);
        RecyclerView.LayoutManager mLayoutManager = new LinearLayoutManager(
                getContext().getApplicationContext(), LinearLayoutManager.VERTICAL, false);
        recyclerView.setLayoutManager(mLayoutManager);
        recyclerView.setItemAnimator(new SlideInUpAnimator(new OvershootInterpolator(1f)));
        recyclerView.computeHorizontalScrollExtent();
        fab = view.findViewById(R.id.fab);

        setState(State.IDLE);   //Preload new data

        recyclerView.setAdapter(mAdapter);
        mAdapter.notifyDataSetChanged();
        recyclerView.addItemDecoration(new SimpleDividerItemDecoration(getContext()));
        init(view);

//        findViewById(R.id.attachment).setOnClickListener(v -> {
//            if (Utils.checkPermission(getApplicationContext(), Manifest.permission.WRITE_EXTERNAL_STORAGE)) {
//                Intent photoPickerIntent = new Intent(Intent.ACTION_PICK);
//                photoPickerIntent.setType("image/*");
//                startActivityForResult(photoPickerIntent, SELECT_PHOTO);
//            } else {
//                ActivityCompat.requestPermissions(Main.this,
//                        new String[]{Manifest.permission.WRITE_EXTERNAL_STORAGE}, SELECT_PHOTO);
//            }
//        });
//
//        findViewById(R.id.changeAccount).setOnClickListener(v -> {
//            if (Utils.checkPermission(getApplicationContext(), Manifest.permission.WRITE_EXTERNAL_STORAGE)) {
//                startActivityForResult(mCredential.newChooseAccountIntent(), Utils.REQUEST_ACCOUNT_PICKER);
//            } else {
//                ActivityCompat.requestPermissions(Main.this,
//                        new String[]{Manifest.permission.WRITE_EXTERNAL_STORAGE}, SELECT_PHOTO);
//            }
//        });

        submit.setOnClickListener(this::startCleaner);
    }

    private void startCleaner(View view) {
        SharedPreferences preferences = getActivity().getPreferences(MODE_PRIVATE);
        boolean disabled = preferences.getBoolean(KEY_NAG_SCREEN_DISABLED, false);

        if (!disabled) {
            String warn = "ATTENTION, THE APPLICATION WILL GET ACCESS TO THE GMAIL MAILBOX, FOR ACCOUNT OF THIS DEVICE ... " +
                    "SURE THAT YOU WANT IT?";
            SpannableStringBuilder value = new SpannableStringBuilder();
            Spannable none = new SpannableStringBuilder(warn);
            none.setSpan(new ForegroundColorSpan(Color.RED), 0, none.length(), SPAN_EXCLUSIVE_EXCLUSIVE);
            none.setSpan(new BackgroundColorSpan(Color.YELLOW), 0, none.length(), SPAN_EXCLUSIVE_EXCLUSIVE);
            none.setSpan(new StyleSpan(android.graphics.Typeface.BOLD), 0, none.length(), SPAN_EXCLUSIVE_EXCLUSIVE);

            value.append(none);
            value.append("\n");
            value.append(getString(R.string.warning_message_one));

            AlertDialog.Builder builder = new AlertDialog.Builder(getContext());
            builder.setTitle(R.string.alert_important_message)
                    .setMessage(value)
                    .setIcon(R.drawable.ic_warning_black_24dp)
                    .setCancelable(false)
                    .setNegativeButton(android.R.string.cancel, (dialog, id) -> dialog.cancel())
                    .setPositiveButton(R.string.alert_one_positive_text,
                            new DialogInterface.OnClickListener() {
                                public void onClick(DialogInterface dialog, int id) {
                                    dialog.cancel();
                                    cD(getActivity(), preferences);
                                }
                            });
            AlertDialog alert = builder.create();
            alert.show();
        } else {
            setState(State.START);
        }
    }

    private void cD(Context context, SharedPreferences preferences) {


        SpannableStringBuilder value = new SpannableStringBuilder();
        Spannable none = new SpannableStringBuilder(getString(R.string.warning_message_seccond));
        none.setSpan(new ForegroundColorSpan(Color.RED), 0, none.length(), SPAN_EXCLUSIVE_EXCLUSIVE);
        none.setSpan(new BackgroundColorSpan(Color.YELLOW), 0, none.length(), SPAN_EXCLUSIVE_EXCLUSIVE);
        none.setSpan(new StyleSpan(android.graphics.Typeface.BOLD), 0, none.length(), Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);

        value.append(none);
        value.append("\n");


        View checkBoxView = View.inflate(context, R.layout.nag_screen_layout, null);
        CheckBox checkBox = checkBoxView.findViewById(R.id.checkbox);
        checkBox.setOnCheckedChangeListener((buttonView, isChecked) -> {
            if (isChecked) {
                preferences.edit().putBoolean(KEY_NAG_SCREEN_DISABLED, true).apply();
            }
        });
        checkBox.setText(R.string.do_not_show_warning);

        AlertDialog.Builder builder = new AlertDialog.Builder(context);
        builder
                .setTitle(R.string.alert_important_message)
                .setMessage(
//                        String.format(
//                                getString(R.string.warning_message_seccond),
//                                getString(R.string.alert_seccond_positive_text)
//                        )
                        value
                )
                .setIcon(R.drawable.ic_warning_black_24dp)
                .setCancelable(false)
                .setView(checkBoxView)
                .setPositiveButton(R.string.alert_seccond_positive_text, (dialog, id) -> {
                    dialog.cancel();
                    setState(State.START);
                })
                .setNegativeButton(android.R.string.cancel, (dialog, id) -> dialog.cancel()).show();
    }

    protected void setState(State state) {
        switch (state) {
            case IDLE:
                //messages.add(new RecyclerMenuViewModel(DLog.getAppVersion(this)));
                messages.clear();
                messages.add(header());
                //messages.add(new GmailMessageViewModel(getString(R.string.action_privacy_policy), null, R.drawable.ic_email_black_24dp));
                //messages.add(new GmailMessageViewModel("Rate application", DLog.getAppVersion(this), R.drawable.ic_rate_review_black_24dp));
                break;

            case START:
                messages.clear();
                mAdapter.notifyDataSetChanged();
                scanning.setText(R.string.action_scanning);

                initCircleAnimation();
                initTimer();
                ___mView.getResultsFromApi(this);
                break;
        }
    }

    private RecyclerMenuViewModel header() {
        return new RecyclerMenuViewModel(
                getString(R.string.app_name), "Current version: "
                + DLog.getAppVersion(getContext()), R.mipmap.ic_launcher);
    }

    private void initTimer() {
        files.setText("");
        initRadarView(true);

        if (T2 != null/* && T2.hasStarted*/) {
            T2.cancel();
            T2.purge();
            files.setText("");
            T2 = null;
        }
        applicationIndex = 0;
        T2 = new CoolTimer();
        T2.scheduleAtFixedRate(new TimerTask() {
            @Override
            public void run() {
                if (getActivity() != null) {
                    getActivity().runOnUiThread(() -> {
                        if (applicationIndex < 8) {//packages.size()
                            //@@@            setLabel("" + "@@@@" + applicationIndex);//packages.get(applicationIndex).sourceDir
                            applicationIndex++;
                        } else {
                            T2.cancel();
                            T2.purge();
                        }
                    });
                }
            }
        }, 80, 80);
    }

    public void initRadarView(boolean started) {
        if (started) {
            front.setImageResource(R.drawable.upper);
            back.setImageResource(R.drawable.back);

            avi1.hide();
            avi2.hide();
            avi3.hide();
            avi4.hide();
            avi5.hide();
            avi6.hide();

        } else {
            front.setImageResource(R.drawable.task_complete);
            back.setImageResource(R.drawable.green_circle);
        }
    }

    private void initCircleAnimation() {
        RotateAnimation rotate = new RotateAnimation(0, 360, Animation.RELATIVE_TO_SELF, 0.5f, Animation.RELATIVE_TO_SELF, 0.5f);
        rotate.setDuration(1500);
        rotate.setRepeatCount(4);
        rotate.setInterpolator(new LinearInterpolator());
        rotate.setAnimationListener(new Animation.AnimationListener() {
            @Override
            public void onAnimationStart(Animation animation) {

            }

            @Override
            public void onAnimationEnd(Animation animation) {
                if (T2 != null) {
                    T2.cancel();
                    T2.purge();
                }
                avi1.hide();
                avi2.hide();
                avi3.hide();
                avi4.hide();
                avi5.hide();
                avi6.hide();
                files.setText("");
            }

            @Override
            public void onAnimationRepeat(Animation animation) {
                check++;
                startAnim(check);
            }
        });
        front.startAnimation(rotate);
    }

    void startAnim(int position) {

        if (position == 1) {
            avi1.show();
            avi3.show();
            avi5.show();

            avi2.hide();
            avi4.hide();
            avi6.hide();
        } else if (position == 2) {
            avi2.show();
            avi4.show();
            avi6.show();

            avi1.hide();
            avi3.hide();
            avi5.hide();
        } else if (position == 3) {
            avi2.show();
            avi4.show();
            avi6.show();

            avi1.show();
            avi3.show();
            avi5.show();
        } else if (position == 4) {
            avi2.show();
            avi3.show();
            avi5.show();

            avi1.show();
            avi2.show();
            avi6.show();
        }
    }

    private void init(View view) {

        // Initializing Progress Dialog
        Context context = view.getContext();
        mProgress = new ProgressDialog(context);
        mProgress.setMessage("Sending...");
        submit = view.findViewById(R.id.fab);

//        edtToAddress = findViewById(R.id.to_address);
//        edtSubject = findViewById(R.id.subject);
//        edtMessage = findViewById(R.id.body);
//        edtAttachmentData = findViewById(R.id.attachmentData);

    }

    public void showMessage(View view, String message) {
        if (view == null) {
            view = submit;
        }
        Snackbar.make(view, message, Snackbar.LENGTH_LONG).show();
        DLog.d("showMessage: " + message);
    }


    public void setLabel(String m) {
        files.setText(m);
        //DLog.d("setLabel: " + m);
    }

    public void add(ViewModel o, int position) {
        //int p = (int) (Math.random() * ((packages.size() - 1) + 1));
//        int p = (int) (Math.random() * 10);
//
//        GmailMessageViewModel message = new GmailMessageViewModel("000","00");
//        try {
//            //message.setImage(getPackageManager().getApplicationIcon(packages.get(p).packageName));
//        } catch (Exception e) {//PackageManager.NameNotFoundException
//            e.printStackTrace();
//        }
//        message.setId(String.valueOf(p));//packages.get(p).dataDir
        try {
            messages.add(o);
            mAdapter.notifyItemInserted(position);
        } catch (Exception e) {
            DLog.d("add: " + e.getLocalizedMessage());
        }
    }


    public void remove(int position) {
        mAdapter.notifyItemRemoved(position);
        if (messages.size() > position) {
            messages.remove(position);
        }
    }

    private void initAppsAnimation(@NonNull List<ViewModel> data) {
        //DLog.d("INIT_ANIMATION [" + data.size() + "] " + data.toString());
        total_count_in_box = data.size();

        final int total_anim_size = 8; //data.size();
        final int half_anim_size = total_anim_size / 2;

        for (int i = 0; i < total_anim_size; i++) {
            final int finalI = i;
            new Handler().postDelayed(() -> {

                try {
                    //Current message
                    //Message message = data.get(finalI);
                    if (finalI < half_anim_size) {
                        int p = (int) (Math.random() * ((data.size() - 1) + 1));
                        add(data.get(p), finalI);
                    } else {
                        remove(0);
                    }
                    if (finalI == total_anim_size - 1) {
                        finishAnimation();
                    }
                } catch (Exception e) {
                    DLog.d("Exception: " + e.getLocalizedMessage() + " " + finalI);
                }
            }, (i + 1) * 600);
        }
    }

    private void finishAnimation() {
        DLog.d("finishAnimation: ");
        rippleBackground.startRippleAnimation();

        initRadarView(false);
        ThreeBounce doubleBounce = new ThreeBounce();
        progressBar.setIndeterminateDrawable(doubleBounce);
        progressBar.setVisibility(View.GONE);

        scanning.setPadding(20, 0, 0, 0);

        if (Build.VERSION.SDK_INT < 23) {
            scanning.setTextAppearance(getContext().getApplicationContext(), android.R.style.TextAppearance_Medium);
            scanning.setText(total_count_in_box + " Messages in the mailbox Successfully Cleared");
            scanning.setTextColor(Color.WHITE);
        } else {
            scanning.setTextAppearance(android.R.style.TextAppearance_Medium);
            scanning.setText(total_count_in_box + " Messages in the mailbox Successfully Cleared");
            scanning.setTextColor(Color.WHITE);
        }

        ObjectAnimator anim = (ObjectAnimator) AnimatorInflater.loadAnimator(
                getContext().getApplicationContext(), R.animator.flipping);
        anim.setTarget(front);
        anim.setDuration(3000);
        anim.start();

        anim.addListener(new Animator.AnimatorListener() {
            @Override
            public void onAnimationStart(Animator animation) {

                scanning.setText(total_count_in_box + " Cleared MB");
                scanning.setTextColor(Color.parseColor("#FFFFFF"));
            }

            @Override
            public void onAnimationEnd(Animator animation) {
                rippleBackground.stopRippleAnimation();
                //@@@            AdManager.getInstance().showAds(JunkCleaningActivity.this, null);
                final Handler handler7 = new Handler();
                handler7.postDelayed(() -> {
                    //finish();
                    //if (BuildConfig.DEBUG) {
                    if (fab != null) {
                        Snackbar.make(fab, "Cleaning Complete - (" + total_count_in_box + ")", Snackbar.LENGTH_SHORT).show();
                    }
                    //}
                }, 1000);
            }

            @Override
            public void onAnimationCancel(Animator animation) {

            }

            @Override
            public void onAnimationRepeat(Animator animation) {

            }
        });

        files.setText("");
        setState(State.IDLE);
    }


    @Override
    public void onAttach(@NonNull Context context) {
        super.onAttach(context);
        if (context instanceof MainView) {
            ___mView = (MainView) context;
        } else {
            throw new RuntimeException(context.toString() + " must implement MainView");
        }
    }


    @Override
    public void onDetach() {
        super.onDetach();
        ___mView = null;
    }

    @Override
    public void onResume() {
        super.onResume();
        AppCompatActivity a = (AppCompatActivity) getActivity();
        if (a != null && a.getSupportActionBar() != null) {
            a.getSupportActionBar().setDisplayHomeAsUpEnabled(false);
            a.getSupportActionBar().setDisplayShowHomeEnabled(false);
            a.getSupportActionBar().setHomeButtonEnabled(false);
            a.getSupportActionBar().setSubtitle(null);
        }
    }
}