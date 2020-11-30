//package aa.youtube;
//
//import android.os.AsyncTask;
//
//import com.google.api.client.extensions.android.http.AndroidHttp;
//import com.google.api.client.googleapis.extensions.android.gms.auth.GoogleAccountCredential;
//import com.google.api.client.http.HttpTransport;
//import com.google.api.client.json.JsonFactory;
//import com.google.api.client.json.jackson2.JacksonFactory;
//import com.walhalla.privacycleaner.mvp.view.MainView;
//
//import java.io.IOException;
//import java.lang.ref.WeakReference;
//import java.util.ArrayList;
//import java.util.List;
//
//public class MakeRequestTask extends AsyncTask<Void, Void, List<String>> {
//
//
//    private final WeakReference<MainView> mWeakReference;
//
//    private com.google.api.services.youtube.YouTube mService;
//    private Exception mLastError = null;
//
//    public MakeRequestTask(GoogleAccountCredential credential, MainView activity) {
//        this.mWeakReference = new WeakReference<MainView>(activity);
//
//        HttpTransport transport = AndroidHttp.newCompatibleTransport();
//        JsonFactory jsonFactory = JacksonFactory.getDefaultInstance();
//        mService = new com.google.api.services.youtube.YouTube.Builder(
//                transport, jsonFactory, credential)
//                .setApplicationName("Privacy PrivacyCleaner")
//                .build();
//    }
//
//    /**
//     * Background task to call YouTube Data API.
//     *
//     * @param params no parameters needed for this task.
//     */
//    @Override
//    protected List<String> doInBackground(Void... params) {
//        try {
//            return getDataFromApi();
//        } catch (Exception e) {
//            mLastError = e;
//            cancel(true);
//            return null;
//        }
//    }
//
//    /**
//     * Fetch information about the "GoogleDevelopers" YouTube channel.
//     *
//     * @return List of Strings containing information about the channel.
//     * @throws IOException
//     */
//    private List<String> getDataFromApi() throws IOException {
//
//
////            try {
////                // Call the YouTube Data API's commentThreads.list method to
////                // retrieve video comment threads.
////                CommentThreadListResponse videoCommentsListResponse = mService.commentThreads()
////                        .list("snippet")
////                        .setVideoId("FhD46nvzrac")
////                        .setTextFormat("plainText")
////                        .execute();
////                List<CommentThread> videoComments = videoCommentsListResponse.getItems();
////
////                if (videoComments.isEmpty()) {
////                    mess("Can't get video comments.");
////                } else {
////
////                    String text = "123";
////
////
////                    // Print information from the API response.
////                    System.out
////                            .println("\n================== Returned Video Comments ==================\n");
////                    for (CommentThread videoComment : videoComments) {
////                        CommentSnippet snippet = videoComment.getSnippet().getTopLevelComment()
////                                .getSnippet();
////                        mess("  - Author: " + snippet.getAuthorDisplayName());
////                        mess("  - Comment: " + snippet.getTextDisplay());
////                        System.out
////                                .println("\n-------------------------------------------------------------\n");
////                    }
////                    CommentThread firstComment = videoComments.get(0);
////
////                    // Will use this thread as parent to new reply.
////                    String parentId = firstComment.getId();
////
////                    // Create a comment snippet with text.
////                    CommentSnippet commentSnippet = new CommentSnippet();
////                    commentSnippet.setTextOriginal(text);
////                    commentSnippet.setParentId(parentId);
////
////                    // Create a comment with snippet.
////                    Comment comment = new Comment();
////                    comment.setSnippet(commentSnippet);
////
////                    // Call the YouTube Data API's comments.insert method to reply
////                    // to a comment.
////                    // (If the intention is to create a new top-level comment,
////                    // commentThreads.insert
////                    // method should be used instead.)
////                    Comment commentInsertResponse = mService.comments().insert("snippet", comment)
////                            .execute();
////
////                    // Print information from the API response.
////                    System.out
////                            .println("\n================== Created Comment Reply ==================\n");
////                    CommentSnippet snippet = commentInsertResponse.getSnippet();
////                    mess("  - Author: " + snippet.getAuthorDisplayName());
////                    mess("  - Comment: " + snippet.getTextDisplay());
////                    System.out
////                            .println("\n-------------------------------------------------------------\n");
////
////                    // Call the YouTube Data API's comments.list method to retrieve
////                    // existing comment
////                    // replies.
////                    CommentListResponse commentsListResponse = mService.comments()
////                            .list("snippet")
////                            .setParentId(parentId)
////                            .setTextFormat("plainText")
////                            .execute();
////                    List<Comment> comments = commentsListResponse.getItems();
////
////                    if (comments.isEmpty()) {
////                        mess("Can't get comment replies.");
////                    } else {
////                        // Print information from the API response.
////                        System.out
////                                .println("\n================== Returned Comment Replies ==================\n");
////                        for (Comment commentReply : comments) {
////                            snippet = commentReply.getSnippet();
////                            mess("  - Author: " + snippet.getAuthorDisplayName());
////                            mess("  - Comment: " + snippet.getTextDisplay());
////                            System.out
////                                    .println("\n-------------------------------------------------------------\n");
////                        }
////                        Comment firstCommentReply = comments.get(0);
////                        firstCommentReply.getSnippet().setTextOriginal("updated");
////                        Comment commentUpdateResponse = mService.comments()
////                                .update("snippet", firstCommentReply).execute();
////                        // Print information from the API response.
////                        System.out
////                                .println("\n================== Updated Video Comment ==================\n");
////                        snippet = commentUpdateResponse.getSnippet();
////                        mess("  - Author: " + snippet.getAuthorDisplayName());
////                        mess("  - Comment: " + snippet.getTextDisplay());
////                        System.out
////                                .println("\n-------------------------------------------------------------\n");
////
////                        // Call the YouTube Data API's comments.setModerationStatus
////                        // method to set moderation
////                        // status of an existing comment.
////                        mService.comments().setModerationStatus(firstCommentReply.getId(), "published");
////                        mess("  -  Changed comment status to published: "
////                                + firstCommentReply.getId());
////
////                        // Call the YouTube Data API's comments.markAsSpam method to
////                        // mark an existing comment as spam.
////                        mService.comments().markAsSpam(firstCommentReply.getId());
////                        mess("  -  Marked comment as spam: " + firstCommentReply.getId());
////
////                        // Call the YouTube Data API's comments.delete method to
////                        // delete an existing comment.
////                        mService.comments().delete(firstCommentReply.getId());
////                        System.out
////                                .println("  -  Deleted comment as spam: " + firstCommentReply.getId());
////                    }
////                }
////            } catch (UserRecoverableAuthIOException e) {
////                //Fixed
////                mWeakReference.get().startActivityForResult(e.getIntent(), REQUEST_AUTHORIZATION);
////            } catch (GoogleJsonResponseException e) {
////                mess("GoogleJsonResponseException code: " + e.getDetails().getCode()
////                        + " : " + e.getDetails().getMessage());
////                e.printStackTrace();
////
////            } catch (IOException e) {
////                mess("IOException: " + e.getMessage());
////                e.printStackTrace();
////            } catch (Throwable t) {
////                mess("Throwable: " + t.getMessage());
////                t.printStackTrace();
////            }
////
////
//////            // Get a list of up to 10 files.
////            List<String> channelInfo = new ArrayList<String>();
////
////            try {
////                HashMap<String, String> parameters = new HashMap<>();
////                parameters.put("part", "snippet");
////                parameters.put("parentId", "z13icrq45mzjfvkpv04ce54gbnjgvroojf0");
////
////                YouTube.Comments.List commentsListRequest = mService.comments()
////                        .list(parameters.get("part"));
////                if (parameters.containsKey("parentId") && !parameters.get("parentId").equals("")) {
////                    commentsListRequest.setParentId(parameters.get("parentId"));
////                }
////
////                CommentListResponse response = commentsListRequest.execute();
////                mess(response);
////
////
////                //            // Get a list of up to 10 files.
//        List<String> channelInfo = new ArrayList<String>();
////                ChannelListResponse result = mService.channels().list("snippet,contentDetails,statistics")
////                        .setForUsername("GoogleDevelopers")
////                        .execute();
////                List<Channel> channels = result.getItems();
////                if (channels != null) {
////                    Channel channel = channels.get(0);
////                    channelInfo.add("This channel's ID is " + channel.getId() + ". " +
////                            "Its title is '" + channel.getSnippet().getTitle() + ", " +
////                            "and it has " + channel.getStatistics().getViewCount() + " views.");
////                }
////
////
////            } catch (GoogleJsonResponseException e) {
////                mess("GoogleJsonResponseException code: " + e.getDetails().getCode()
////                        + " : " + e.getDetails().getMessage());
////                e.printStackTrace();
////
////            } catch (IOException e) {
////                mess("IOException: " + e.getMessage());
////                e.printStackTrace();
////            } catch (Throwable t) {
////                mess("Throwable: " + t.getMessage());
////                t.printStackTrace();
////            }
////
////
//        return channelInfo;
//    }
//
//
//    @Override
//    protected void onPreExecute() {
//    //    mWeakReference.get().onPreExecute();
//    }
//
//    @Override
//    protected void onPostExecute(List<String> output) {
//    //    mWeakReference.get().onPostExecute(output);
//    }
//
//    @Override
//    protected void onCancelled() {
//        MainView mainActivity = mWeakReference.get();
//        if (mainActivity != null) {
//            mainActivity.hideProgress();
//            mainActivity.handleError(mLastError);
//        }
//    }
//}