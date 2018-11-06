package es.iessaladillo.pedrojoya.pr04.ui.avatar;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.List;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import es.iessaladillo.pedrojoya.pr04.R;
import es.iessaladillo.pedrojoya.pr04.data.local.Database;
import es.iessaladillo.pedrojoya.pr04.data.local.model.Avatar;
import es.iessaladillo.pedrojoya.pr04.utils.ResourcesUtils;

public class AvatarActivity extends AppCompatActivity {

    public static final String EXTRA_AVATAR = "EXTRA_AVATAR";
    private ImageView imgAvatar1;
    private ImageView imgAvatar2;
    private ImageView imgAvatar3;
    private ImageView imgAvatar4;
    private ImageView imgAvatar5;
    private ImageView imgAvatar6;
    private final Database database = Database.getInstance();
    private TextView lblAvatar1;
    private TextView lblAvatar2;
    private TextView lblAvatar3;
    private TextView lblAvatar4;
    private TextView lblAvatar5;
    private TextView lblAvatar6;
    private Avatar avatar;
    private final byte positionAvatar1 = 0;
    private final byte positionAvatar2 = 1;
    private final byte positionAvatar3 = 2;
    private final byte positionAvatar4 = 3;
    private final byte positionAvatar5 = 4;
    private final byte positionAvatar6 = 5;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_avatar);
        getIntentData();
        initViews();
    }

    private void getIntentData() {
        Intent intent = getIntent();
        if (intent != null) {
            if (intent.hasExtra(EXTRA_AVATAR)) {
                avatar = intent.getParcelableExtra(EXTRA_AVATAR);
            }
        }
    }

    private void initViews() {
        imgAvatar1 = ActivityCompat.requireViewById(this, R.id.imgAvatar1);
        imgAvatar2 = ActivityCompat.requireViewById(this, R.id.imgAvatar2);
        imgAvatar3 = ActivityCompat.requireViewById(this, R.id.imgAvatar3);
        imgAvatar4 = ActivityCompat.requireViewById(this, R.id.imgAvatar4);
        imgAvatar5 = ActivityCompat.requireViewById(this, R.id.imgAvatar5);
        imgAvatar6 = ActivityCompat.requireViewById(this, R.id.imgAvatar6);
        lblAvatar1 = ActivityCompat.requireViewById(this, R.id.lblAvatar1);
        lblAvatar2 = ActivityCompat.requireViewById(this, R.id.lblAvatar2);
        lblAvatar3 = ActivityCompat.requireViewById(this, R.id.lblAvatar3);
        lblAvatar4 = ActivityCompat.requireViewById(this, R.id.lblAvatar4);
        lblAvatar5 = ActivityCompat.requireViewById(this, R.id.lblAvatar5);
        lblAvatar6 = ActivityCompat.requireViewById(this, R.id.lblAvatar6);

        startAvatar();
        selectedImage();
        imgAvatar1.setOnClickListener(v -> changeAvatar(positionAvatar1));
        imgAvatar2.setOnClickListener(v -> changeAvatar(positionAvatar2));
        imgAvatar3.setOnClickListener(v -> changeAvatar(positionAvatar3));
        imgAvatar4.setOnClickListener(v -> changeAvatar(positionAvatar4));
        imgAvatar5.setOnClickListener(v -> changeAvatar(positionAvatar5));
        imgAvatar6.setOnClickListener(v -> changeAvatar(positionAvatar6));
        lblAvatar1.setOnClickListener(v -> changeAvatar(positionAvatar1));
        lblAvatar2.setOnClickListener(v -> changeAvatar(positionAvatar2));
        lblAvatar3.setOnClickListener(v -> changeAvatar(positionAvatar3));
        lblAvatar4.setOnClickListener(v -> changeAvatar(positionAvatar4));
        lblAvatar5.setOnClickListener(v -> changeAvatar(positionAvatar5));
        lblAvatar6.setOnClickListener(v -> changeAvatar(positionAvatar6));
    }


    private void startAvatar() {
        List<Avatar> avatars = database.queryAvatars();

        imgAvatar1.setImageResource(avatars.get(positionAvatar1).getImageResId());
        imgAvatar1.setTag(avatars.get(positionAvatar1).getImageResId());
        lblAvatar1.setText(avatars.get(positionAvatar1).getName());

        imgAvatar2.setImageResource(avatars.get(positionAvatar2).getImageResId());
        imgAvatar2.setTag(avatars.get(positionAvatar2).getImageResId());
        lblAvatar2.setText(avatars.get(positionAvatar2).getName());

        imgAvatar3.setImageResource(avatars.get(positionAvatar3).getImageResId());
        imgAvatar3.setTag(avatars.get(positionAvatar3).getImageResId());
        lblAvatar3.setText(avatars.get(positionAvatar3).getName());

        imgAvatar4.setImageResource(avatars.get(positionAvatar4).getImageResId());
        imgAvatar4.setTag(avatars.get(positionAvatar4).getImageResId());
        lblAvatar4.setText(avatars.get(positionAvatar4).getName());

        imgAvatar5.setImageResource(avatars.get(positionAvatar5).getImageResId());
        imgAvatar5.setTag(avatars.get(positionAvatar5).getImageResId());
        lblAvatar5.setText(avatars.get(positionAvatar5).getName());

        imgAvatar6.setImageResource(avatars.get(positionAvatar6).getImageResId());
        imgAvatar6.setTag(avatars.get(positionAvatar6).getImageResId());
        lblAvatar6.setText(avatars.get(positionAvatar6).getName());

    }

    void selectedImage() {
        List<Avatar> avatarToSelect = database.queryAvatars();
        Long avatarId = avatar.getId();

        if (avatarId == avatarToSelect.get(positionAvatar1).getId()) {
            selectAvatar(imgAvatar1, lblAvatar1);
        } else if (avatarId == avatarToSelect.get(positionAvatar2).getId()) {
            selectAvatar(imgAvatar2, lblAvatar2);
        } else if (avatarId == avatarToSelect.get(positionAvatar3).getId()) {
            selectAvatar(imgAvatar3, lblAvatar3);
        } else if (avatarId == avatarToSelect.get(positionAvatar4).getId()) {
            selectAvatar(imgAvatar4, lblAvatar4);
        } else if (avatarId == avatarToSelect.get(positionAvatar5).getId()) {
            selectAvatar(imgAvatar5, lblAvatar5);
        } else if (avatarId == avatarToSelect.get(positionAvatar6).getId()) {
            selectAvatar(imgAvatar6, lblAvatar6);
        }
    }

    private void selectAvatar(ImageView imgAvatar1, TextView lblAvatar1) {
        selectImageView(imgAvatar1);
        // AL DESHABILITAR EL ACTUAL EL USUARIO NO PUEDE VOLVER A SELECCIONAR EL QUE YA
        // HAY. POR ESO HAY DOS TEST QUE NO LOS PASA.
        imgAvatar1.setEnabled(false);
        lblAvatar1.setEnabled(false);
    }

    // DO NO TOUCH
    private void selectImageView(ImageView imageView) {
        imageView.setAlpha(ResourcesUtils.getFloat(this, R.dimen.selected_image_alpha));
    }

    // DO NOT TOUCH
    @Override
    public boolean onSupportNavigateUp() {
        onBackPressed();
        return true;
    }

    public static void startForResult(Activity activity, int requestCode, Avatar avatar) {
        Intent intent = new Intent(activity, AvatarActivity.class);
        intent.putExtra(EXTRA_AVATAR, avatar);
        activity.startActivityForResult(intent, requestCode);
    }

    private void changeAvatar(int positionAvatar) {
        Intent result = new Intent();
        Avatar avatar = database.queryAvatar(database.queryAvatars().get(positionAvatar).getId());

        result.putExtra(EXTRA_AVATAR, avatar);
        setResult(RESULT_OK, result);
        finish();
    }
}
