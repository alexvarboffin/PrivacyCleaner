//package com.walhalla.privacycleaner.fragment;
//
//import android.content.Context;
//import android.os.Bundle;
//import android.util.Log;
//import android.view.LayoutInflater;
//import android.view.View;
//import android.view.ViewGroup;
//
//import androidx.annotation.NonNull;
//import androidx.annotation.Nullable;
//import androidx.fragment.app.Fragment;
//import androidx.recyclerview.widget.DefaultItemAnimator;
//import androidx.recyclerview.widget.DividerItemDecoration;
//import androidx.recyclerview.widget.LinearLayoutManager;
//import androidx.recyclerview.widget.RecyclerView;
//
//import com.google.api.client.googleapis.extensions.android.gms.auth.GoogleAccountCredential;
//
//import com.google.api.services.gmail.model.Message;
//import com.walhalla.boilerplate.domain.executor.impl.BackgroundExecutor;
//import com.walhalla.boilerplate.threading.MainThreadImpl;
//import aa.model.GmailMessageViewModel;
//import aa.model.ViewModel;
//import com.walhalla.privacycleaner.repository.PrivacyCleaner;
//import com.walhalla.privacycleaner.GmailInteractorImpl;
//import com.walhalla.privacycleaner.R;
//
//import com.walhalla.privacycleaner.ui.ComplexRecyclerViewAdapter;
//
//import java.util.ArrayList;
//import java.util.List;
//
//public class FragmentOne extends Fragment {
//
//    public interface Callback {
//        void getResultsFromApi(Context context, FragmentOne one);
//    }
//
////    public interface RadarFragmentPresenter extends GalleryAdapter.RadarFragmentPresenter{
////        void replaceFragment(Fragment fragment);
////        //void setActionBarTitle(String title);
////        void onClickGetLessonRequest(int position, Lesson lesson);
////
////        void replaceGalleryFragment(Lesson lesson);
////    }
//
//
//    private Callback mPresenter;
//    private View button;
//

//
////    @Override
////    public void onPreExecute() {
//////        mPresenter.showProgress();
////    }
////
////    @Override
////    public void onPostExecute(String output) {
//////        hideProgress();
//////        if (output == null || output.length() == 0) {
//////            this.showMessage(null, "No results returned.");
//////        } else {
//////            this.showMessage(null, output);
//////        }
////        Log.d(TAG, "@@@@@@@@@@@@@@@@@@@@@@: "+output);
////    }
////
////    @Override
////    public void onCancelled(Exception mLastError) {
//////        handleError(mLastError);
////        Log.d(TAG, "===== task canceled =====" + mLastError);
////    }
//
//
//    public static final String TAG = "@@@";
//    private final String KEY_ADAPTER_STATE = "com.walhalla.ui.adapter.KEY_ADAPTER_STATE";
//    //MessageListPresenter presenter;
//    private ArrayList<GmailMessageViewModel> adapterSavedState;
//    private RecyclerView recyclerView;
//
//    //    @BindView(R.id.swiperefresh)
////    SwipeRefreshLayout swipeRefreshLayout;
//    private boolean onSaveInstanceCalled;
//
//
////    @BindView(R.id.coordinator_layout)
////    RelativeLayout coordinatorLayout;
//
//    private ComplexRecyclerViewAdapter rcAdapter;
//
//    //==============================================================================================
//    //Lifecycle
////    @Override
////    public void onInflate(Context context, AttributeSet attrs, Bundle savedInstanceState) {
////        super.onInflate(context, attrs, savedInstanceState);
////        d(TAG, "onInflate: ");
////    }
//
////    @Override
////    public void onAttachFragment(Fragment childFragment) {
////        super.onAttachFragment(childFragment);
////        d(TAG, "onAttachFragment: ");
////    }
//
//    @Override
//    public void onCreate(@Nullable Bundle savedInstanceState) {
//        super.onCreate(savedInstanceState);
//        d(TAG, "onCreate: " + savedInstanceState);
//
//        //setRetainInstance(true);//saved fragment state
//        // Check whether we're recreating a previously destroyed instance
//        //Retrieve saved state in onCreate. This method is called even when this fragment is on the back stack
//
//        if (savedInstanceState != null && savedInstanceState.containsKey(KEY_ADAPTER_STATE)) {
//            adapterSavedState = savedInstanceState.getParcelableArrayList(KEY_ADAPTER_STATE);
//            d(TAG, "Restore data: " + adapterSavedState);
//            //Сохраненные данные
////            this.uri = savedInstanceState.getString(KEY_RSS_LINK);
////            this.title = savedInstanceState.getString(KEY_TITLE);
//
//        } else if (getArguments() != null) {
//            // Probably initialize members with default values for a new instance
//            //setupViews(getArgs());
//            //if(MyApplication.getInstance(getContext()).isInternetAvailable()){
//
//            //Данные пришли с предыдущего фрагмента...
////            this.uri = getArguments().getString(KEY_RSS_LINK);
////            this.title = getArguments().getString(KEY_TITLE);
//            //}
//            //else Toast.makeText(mContext, "not connected...", Toast.LENGTH_SHORT).show();
//
//            //
//
//        } else {
//            //throw new InvalidArgumentException("args");
//        }
//        //Log.e(TAG, "#SI: " + savedInstanceState);
//        //Log.e(TAG, "#ARG: " + getArguments());
//        //Log.e(TAG, "#VARS: " + mRSSTitle + mAdapterSavedState);
//    }
//
//    //App proc killed
//    //backstack
//    @Nullable
//    @Override
//    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
//        d(TAG, "onCreateView: " + savedInstanceState + " ");
//        //===============================================================================================
//        //When we go to next fragment and return back here, the adapter is already present and populated.
//        //Don't create it again in such cases. Hence the null check.
//        if (rcAdapter == null) {
//            rcAdapter = new ComplexRecyclerViewAdapter(new ArrayList<>());
//            rcAdapter.setChildItemClickListener(childItemClickListener);
//        }
//
////        presenter = Presenters.getPresenter(getViewId());
////@@@@@@@2        if (presenter == null) {
////            presenter = new MessageListPresenter();
////            //@@@@@ presenter.bind(this);
////        }
//
//
//        //Use the state retrieved in onCreate and set it on your views etc in onCreateView
//        //This method is not called if the device is rotated when your fragment is on the back stack.
//        //That's OK since the next time the device is rotated, we save the state we had retrieved in onCreate
//        //instead of saving current state. See onSaveInstanceState for more details.
//
//        if (adapterSavedState != null) {
//            onSaveInstanceCalled = true;
//
//            d(TAG, "Блокируем повторный запрос в initinstances: ");
//        }
//        return inflater.inflate(R.layout.screen_category_list, container, false);
//    }
//
//
//    @Override
//    public void onAttach(@NonNull Context context) {
//        super.onAttach(context);
//        if (context instanceof Callback) {
//            mPresenter = (Callback) context;
//        } else {
//            throw new RuntimeException(context.toString()
//                    + " must implement RadarFragmentPresenter");
//        }
//    }
//
//
//    //public Object onRetainNonConfigurationInstance() {
//    //    return mAdapterSavedState; //saved...
//    //}
//
//
//    //4
//    //Fragment 4
//    @Override
//    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
//        super.onViewCreated(view, savedInstanceState);
//        //getLastNonConfigurationInstance()
//
//        button = view.findViewById(R.id.button);
//        button.setOnClickListener(view1 -> mPresenter.getResultsFromApi(getContext(), FragmentOne.this));
//
//        recyclerView = view.findViewById(R.id.recycler_view);
//        // use this if you want the RecyclerView to look like a vertical list view
//        RecyclerView.LayoutManager mLayoutManager = new LinearLayoutManager(getContext());
//        recyclerView.setLayoutManager(mLayoutManager);
//        // use this if you want the RecyclerView to look like a grid view
//        //recyclerView.setLayoutManager(new GridLayoutManager(this, 2));
//
//        recyclerView.setItemAnimator(new DefaultItemAnimator());
//        recyclerView.addItemDecoration(new DividerItemDecoration(getContext(), LinearLayoutManager.VERTICAL));
//
//
//        // set the adapter
//        recyclerView.setAdapter(rcAdapter);
//
//
//        d(TAG, "DO_STUFF_" + adapterSavedState);
//
////        swipeRefreshLayout.setOnRefreshListener(
////                () -> //@@@@@ presenter.listAll()
////        );
//
//
//        if (/*!onSaveInstanceCalled*/ adapterSavedState == null) {
//            d(TAG, "LOAD NEW DATA");
//            //@@@@@ presenter.listAll();
//        } else {
//            updateData(adapterSavedState);
//            //@@@@@ presenter.setData(adapterSavedState);
//        }
//
//        //swipeRefreshLayout.setRefreshing(false);
//    }
//
//    @Override
//    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
//        d(TAG, "onActivityCreated: " + savedInstanceState);
//        super.onActivityCreated(savedInstanceState);
//    }
//    //on view state restore
//
//
//    @Override
//    public void onStart() {
//        super.onStart();
//        d(TAG, "onStart: ");
//    }
//
//    @Override
//    public void onResume() {
//        super.onResume();
//        d(TAG, "onResume: ");
//    }
//
//
//    @Override
//    public void onSaveInstanceState(@NonNull Bundle savedInstanceState) {
//        d(TAG, "onSaveInstanceState: ");
//        // Save the current state
////        savedInstanceState.putString(KEY_RSS_LINK, uri);
////        savedInstanceState.putString(KEY_TITLE, title);
//
//        // Always call the superclass so it can save the view hierarchy state
//        super.onSaveInstanceState(savedInstanceState);
//
//        if (rcAdapter != null) {
//            //This case is for when the fragment is at the top of the stack. onCreateView was called and hence there is state to save
//            //rssItems = rcAdapter.onSaveInstanceState();
//        }
//
//        //However, remember that this method is called when the device is rotated even if your fragment is on the back stack.
//        //In such cases, the onCreateView was not called, hence there is nothing to save.
//        //Hence, we just re-save the state that we had retrieved in onCreate. We sort of relay the state from onCreate to onSaveInstanceState.
//        savedInstanceState.putParcelableArrayList(KEY_ADAPTER_STATE, adapterSavedState);
//    }
//
//    @Override
//    public void onPause() {
//        super.onPause();
//        d(TAG, "onPause: ");
//    }
//
//    @Override
//    public void onStop() {
//        super.onStop();
//        d(TAG, "onStop: ");
//    }
//
//    @Override
//    public void onLowMemory() {
//        super.onLowMemory();
//        d(TAG, "onLowMemory: ");
//    }
//
//    @Override
//    public void onDestroyView() {
//        super.onDestroyView();
//        d(TAG, "onDestroyView: ");
//    }
//
//    @Override
//    public void onDestroy() {
//        d(TAG, "DESTROY: " + adapterSavedState);
//        //@@@@@ presenter.unbind();
////        if (onSaveInstanceCalled) {
////@@@@@2            Presenters.savePresenter(getViewId(), presenter);
////        } else {
////            Presenters.removePresenter(getViewId());
////        }
//        super.onDestroy();
//    }
//
//    @Override
//    public void onDetach() {
//        super.onDetach();
//        d(TAG, "onDetach: ");
//    }
//
//    //==============================================================================================
////    @Override
////    public boolean onOptionsItemSelected(MenuItem item) {
////        switch (item.getItemId()) {
////
////            // Check if user triggered a refresh:
////            case R.id.menu_refresh:
////                d(TAG, "Refresh menu item selected");
////                // Start the refresh background task.
////                // This method calls setRefreshing(false) when it's finished.
////                //@@@@@ presenter.listAll();
////                return true;
////        }
////
////        // User didn't trigger a refresh, let the superclass handle this action
////        return super.onOptionsItemSelected(item);
////
////    }
//
//    private void d(String tag, String s) {
//        Log.d(tag, s
////                + "|rv:" + isNull(recyclerView)
////                + "|sic?:" + isNull(onSaveInstanceCalled)
////                + "|presenter:" + isNull(presenter)
////                +"|adapter_data:"+isNull(mAdapterSavedState)
//                + "|");
//    }
//
//    private String isNull(Object o) {
//        return (o == null) ? "-" : "оk " + o.hashCode();
//    }
//
//    //@Override
//    private void updateData(ArrayList<GmailMessageViewModel> data) {
//        d(TAG, "updateData: " + data);
//        List<ViewModel> obj = new ArrayList<>();
//        obj.addAll(data);
//        adapterSavedState = data;
//        rcAdapter.swap(obj);
//        //hideLoading();
//    }
//
////    @Override
////    public void showError(String error) {
////        hideLoading();
////    }
////
////    @Override
////    public void showSuccess(String success) {
////        //Snackbar snackbar = Snackbar.make(coordinatorLayout, success, Snackbar.LENGTH_LONG);
////        //snackbar.show();
////        hideLoading();
////    }
//
//
//    private ComplexRecyclerViewAdapter.OnItemClickListener childItemClickListener = (v, object) -> {
////        if (FragmentOne.this.presenter != null) {
////            switch (v.getId()) {
////                case R.id.ib_edit:
////                    //@@@@@ presenter.editSelectedModel((GmailMessageViewModel) object);
////                    break;
////                default:
////                    //@@@@@ presenter.onItemClicked((GmailMessageViewModel) object);
////                    break;
////            }
////        }
//    };
//
//
////    //Navigation
////    @Override
////    public void viewMessage(GmailMessageViewModel category) {
////        if (notNull(listener)) {
////            Bundle bundle = new Bundle();
////            bundle.putParcelable(KEY_MODELS, category);
////            RetrieveMessageScreen retrieveScreen = new RetrieveMessageScreen();
////            retrieveScreen.setArguments(bundle);
////            listener.replaceFragment(retrieveScreen);
////        }
////    }
//
////    @Override
////    public void viewEditMessage(GmailMessageViewModel category) {
////        Bundle bundle = new Bundle();
////        bundle.putParcelable(KEY_MODELS, category);
////        EditMessageScreen fragment = new EditMessageScreen();
////        fragment.setArguments(bundle);
////        listener.replaceFragment(fragment);
////    }
////
////    @Override
////    public void showLoading() {
////        // Signal SwipeRefreshLayout to start the onProgress indicator
////        //swipeRefreshLayout.setRefreshing(true);
////        pg.setVisibility(View.VISIBLE);
////    }
////
////    @Override
////    public void hideLoading() {
////        // Signal SwipeRefreshLayout to start the onProgress indicator
////        //swipeRefreshLayout.setRefreshing(false);
////        pg.setVisibility(View.INVISIBLE);
////    }
//
//}