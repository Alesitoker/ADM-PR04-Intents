package es.iessaladillo.pedrojoya.pr04.utils;

import android.app.SearchManager;
import android.content.Intent;
import android.net.Uri;

public class IntentsUtils {
    private IntentsUtils() {}

    public static Intent newEmail(String email) {
        return new Intent(Intent.ACTION_SENDTO, Uri.parse("mailto:"+ email));
    }

    public static Intent newDial(String phoneNumber) {
        return new Intent(Intent.ACTION_DIAL, Uri.parse("tel:" + phoneNumber.trim()));
    }

    public static Intent newSearchInMap(String address) {
        return new Intent(Intent.ACTION_VIEW, Uri.parse("geo:0,0?q=" + address));
    }

    public static Intent newWebSearch(String url) {
        Intent intentWebSearch = new Intent(Intent.ACTION_VIEW);
        String head = "";
        if (!url.startsWith("http://") && !url.startsWith("https://")) {
            head = "https://";
        }
        intentWebSearch.setData(Uri.parse(head + url));
//        intentWebSearch.putExtra(SearchManager.QUERY, url);
        return intentWebSearch;
    }
}
