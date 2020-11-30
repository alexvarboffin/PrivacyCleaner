package aa.model;

public class RecyclerMenuViewModel implements ViewModel{


    public String threadId;
    public String id;
    public int icon;


    public RecyclerMenuViewModel(String id, String threadId, int icon) {
        this.threadId = threadId;
        this.id = id;
        this.icon = icon;
    }

}
