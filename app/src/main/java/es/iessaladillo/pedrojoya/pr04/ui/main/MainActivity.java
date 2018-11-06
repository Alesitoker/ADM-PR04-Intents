package es.iessaladillo.pedrojoya.pr04.ui.main;

import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.inputmethod.EditorInfo;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import es.iessaladillo.pedrojoya.pr04.R;
import es.iessaladillo.pedrojoya.pr04.data.local.Database;
import es.iessaladillo.pedrojoya.pr04.data.local.model.Avatar;
import es.iessaladillo.pedrojoya.pr04.ui.avatar.AvatarActivity;
import es.iessaladillo.pedrojoya.pr04.utils.IntentsUtils;
import es.iessaladillo.pedrojoya.pr04.utils.KeyboardUtils;
import es.iessaladillo.pedrojoya.pr04.utils.SnackbarUtils;
import es.iessaladillo.pedrojoya.pr04.utils.TextViewUtils;
import es.iessaladillo.pedrojoya.pr04.utils.ValidationUtils;

@SuppressWarnings("WeakerAccess")
public class MainActivity extends AppCompatActivity {

    private TextView lblAvatar;
    private ImageView imgAvatar;
    private EditText txtName;
    private EditText txtEmail;
    private EditText txtPhonenumber;
    private EditText txtAddress;
    private EditText txtWeb;
    private TextView lblName;
    private TextView lblEmail;
    private TextView lblPhonenumber;
    private TextView lblAddress;
    private TextView lblWeb;
    private ImageView imgEmail;
    private ImageView imgPhonenumber;
    private ImageView imgAddress;
    private ImageView imgWeb;
    private final Database database = Database.getInstance();
    private final int RC_AVATAR = 12;
    private Avatar avatar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        initViews();
    }

    private void initViews() {
        lblAvatar = ActivityCompat.requireViewById(this, R.id.lblAvatar);
        imgAvatar = ActivityCompat.requireViewById(this, R.id.imgAvatar);
        txtName = ActivityCompat.requireViewById(this, R.id.txtName);
        txtEmail = ActivityCompat.requireViewById(this, R.id.txtEmail);
        txtPhonenumber = ActivityCompat.requireViewById(this, R.id.txtPhonenumber);
        txtAddress = ActivityCompat.requireViewById(this, R.id.txtAddress);
        txtWeb = ActivityCompat.requireViewById(this, R.id.txtWeb);
        lblName = ActivityCompat.requireViewById(this, R.id.lblName);
        lblEmail = ActivityCompat.requireViewById(this, R.id.lblEmail);
        lblPhonenumber = ActivityCompat.requireViewById(this, R.id.lblPhonenumber);
        lblAddress = ActivityCompat.requireViewById(this, R.id.lblAddress);
        lblWeb = ActivityCompat.requireViewById(this, R.id.lblWeb);
        imgEmail = ActivityCompat.requireViewById(this, R.id.imgEmail);
        imgPhonenumber = ActivityCompat.requireViewById(this, R.id.imgPhonenumber);
        imgAddress = ActivityCompat.requireViewById(this, R.id.imgAddress);
        imgWeb = ActivityCompat.requireViewById(this, R.id.imgWeb);

        showAvatar(avatar = database.getDefaultAvatar());
        changeFocus();
        editorAction();
        TextViewUtils.afterTextChanged(txtName, lblName, this);
        TextViewUtils.onTextChanged(txtEmail, lblEmail, imgEmail, this);
        TextViewUtils.onTextChanged(txtPhonenumber, lblPhonenumber, imgPhonenumber, this);
        TextViewUtils.afterTextChanged(txtAddress, lblAddress, imgAddress, this);
        TextViewUtils.onTextChanged(txtWeb, lblWeb, imgWeb, this);
        imgAvatar.setOnClickListener(v -> changeImg());
        lblAvatar.setOnClickListener(v -> changeImg());
        imgEmail.setOnClickListener(v -> sendEmail());
        imgPhonenumber.setOnClickListener(v -> dialPhoneNumber());
        imgAddress.setOnClickListener(v -> searchInMap());
        imgWeb.setOnClickListener(v -> webSearch());
    }

    private void webSearch() {
        Intent intent;
        if (validateWeb()) {
            intent = IntentsUtils.newWebSearch(txtWeb.getText().toString());
            sendIntent(intent);
        }
    }

    private void searchInMap() {
        Intent intent;
        if (validateAddress()) {
            intent = IntentsUtils.newSearchInMap(txtAddress.getText().toString());
            sendIntent(intent);
        }
    }

    private void dialPhoneNumber() {
        Intent intent;
        if (validatePhonenumber()) {
            intent = IntentsUtils.newDial(txtPhonenumber.getText().toString());
            sendIntent(intent);
        }
    }

    private void sendEmail() {
        Intent intent;
        if (validateEmail()) {
            intent = IntentsUtils.newEmail(txtEmail.getText().toString());
            sendIntent(intent);
        }
    }

    private void sendIntent(Intent intent) {
        if (IntentsUtils.isAvailable(this, intent)) {
            startActivity(intent);
        } else {
            KeyboardUtils.hideSoftKeyboard(this);
            SnackbarUtils.snackbar(imgWeb, "Can not find an application to perform this action");
        }
    }

    private void editorAction() {
        txtWeb.setOnEditorActionListener((v, actionId, event) -> {
            if (actionId == EditorInfo.IME_ACTION_DONE) {
                save();
                return true;
            }
            return false;
        });
    }

    private void changeFocus() {
        TextViewUtils.changeFocus(txtName, lblName);
        TextViewUtils.changeFocus(txtEmail, lblEmail);
        TextViewUtils.changeFocus(txtPhonenumber, lblPhonenumber);
        TextViewUtils.changeFocus(txtAddress, lblAddress);
        TextViewUtils.changeFocus(txtWeb, lblWeb);
    }

    private void showAvatar(Avatar avatar) {
        this.avatar = avatar;
        imgAvatar.setImageResource(avatar.getImageResId());
        imgAvatar.setTag(avatar.getImageResId());
        lblAvatar.setText(avatar.getName());
    }

    private void changeImg() {
        AvatarActivity.startForResult(this, RC_AVATAR, avatar);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        if (resultCode == RESULT_OK && requestCode == RC_AVATAR) {
            if (data != null && data.hasExtra(AvatarActivity.EXTRA_AVATAR)) {
                avatar = data.getParcelableExtra(AvatarActivity.EXTRA_AVATAR);
                showAvatar(avatar);
            }
        }
    }

    // DO NOT TOUCH
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.activity_main, menu);
        return super.onCreateOptionsMenu(menu);
    }

    // DO NOT TOUCH
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId() == R.id.mnuSave) {
            save();
            return true;
        }
        return super.onOptionsItemSelected(item);
    }

    // ESTOS MÉTODOS validate SON PRÁCTICAMENTE EL MISMO. HAZ UN MÉTODO ÚNICO QUE RECIBA
    // LOS PARÁMETROS NECESARIOS.
    private boolean validateName() {
        if (txtName.getText().toString().isEmpty()) {
            txtName.setError(getString(R.string.main_invalid_data));
            lblName.setTextColor(getResources().getColor(R.color.colorError));
            return false;
        }
        return true;
    }

    private boolean validateEmail() {
        if (!ValidationUtils.isValidEmail(txtEmail.getText().toString())) {
            txtEmail.setError(getString(R.string.main_invalid_data));
            lblEmail.setTextColor(getResources().getColor(R.color.colorError));
            imgEmail.setEnabled(false);
            return false;
        }
        return true;
    }

    private boolean validatePhonenumber() {
        if (!ValidationUtils.isValidPhone(txtPhonenumber.getText().toString())) {
            txtPhonenumber.setError(getString(R.string.main_invalid_data));
            lblPhonenumber.setTextColor(getResources().getColor(R.color.colorError));
            imgPhonenumber.setEnabled(false);
            return false;
        }
        return true;
    }

    private boolean validateAddress() {
        if (txtAddress.getText().toString().isEmpty()) {
            txtAddress.setError(getString(R.string.main_invalid_data));
            lblAddress.setTextColor(getResources().getColor(R.color.colorError));
            imgAddress.setEnabled(false);
            return false;
        }
        return true;
    }

    private boolean validateWeb() {
        if (!ValidationUtils.isValidUrl(txtWeb.getText().toString())) {
            txtWeb.setError(getString(R.string.main_invalid_data));
            lblWeb.setTextColor(getResources().getColor(R.color.colorError));
            imgWeb.setEnabled(false);
            return false;
        }
        return true;
    }
    private boolean validate() {
        boolean validName, validEmail, validPhonenumber, validAddress, validWeb;
        validName = validateName();
        validEmail = validateEmail();
        validPhonenumber = validatePhonenumber();
        validAddress = validateAddress();
        validWeb = validateWeb();

        return validName && validEmail && validPhonenumber && validAddress && validWeb;
    }

    private void save() {
        KeyboardUtils.hideSoftKeyboard(this);
        if (!validate()) {
            SnackbarUtils.snackbar(lblName, getString(R.string.main_error_saving));
        } else {
            SnackbarUtils.snackbar(lblName, getString(R.string.main_saved_succesfully));
        }
    }

}
