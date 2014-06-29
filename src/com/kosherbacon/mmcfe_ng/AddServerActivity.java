package com.kosherbacon.mmcfe_ng;

import android.app.ActionBar;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.graphics.Color;
import android.graphics.Point;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.Display;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;
import com.actionbarsherlock.app.SherlockActivity;
import com.dm.zbar.android.scanner.ZBarConstants;
import com.dm.zbar.android.scanner.ZBarScannerActivity;
import com.google.zxing.BarcodeFormat;
import com.google.zxing.WriterException;
import com.google.zxing.common.BitMatrix;
import com.google.zxing.qrcode.QRCodeWriter;
import com.throrinstudio.android.common.libs.validator.Form;
import com.throrinstudio.android.common.libs.validator.Validate;
import com.throrinstudio.android.common.libs.validator.validator.AlnumValidator;
import com.throrinstudio.android.common.libs.validator.validator.NotEmptyValidator;
import com.throrinstudio.android.common.libs.validator.validator.UrlValidator;
import net.sourceforge.zbar.Symbol;

import java.io.IOException;

public class AddServerActivity extends SherlockActivity {

    private static final int ZBAR_SCANNER_REQUEST = 0;
    private static final int ZBAR_QR_SCANNER_REQUEST = 1;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.add_server);
        findViewById(R.id.qrCode).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                launchQRScanner();
            }
        });
        findViewById(R.id.resetAddServer).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                clearForms();
            }
        });
        LayoutInflater inflater = (LayoutInflater) getActionBar().getThemedContext()
                .getSystemService(LAYOUT_INFLATER_SERVICE);
        final View customActionBarView = inflater.inflate(
                R.layout.actionbar_custom_view_done_discard, null);
        customActionBarView.findViewById(R.id.actionbar_done).setOnClickListener(
                new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        Form mForm = new Form();

                        Validate url = new Validate((EditText) findViewById(R.id.serverAddress));
                        url.addValidator(new NotEmptyValidator(getApplicationContext()));
                        url.addValidator(new UrlValidator(getApplicationContext()));

                        Validate api = new Validate((EditText) findViewById(R.id.apiKey));
                        api.addValidator(new NotEmptyValidator(getApplicationContext()));
                        api.addValidator(new AlnumValidator(getApplicationContext()));

                        Validate id = new Validate((EditText) findViewById(R.id.userID));
                        id.addValidator(new NotEmptyValidator(getApplicationContext()));
                        id.addValidator(new AlnumValidator(getApplicationContext()));

                        mForm.addValidates(url);
                        mForm.addValidates(api);
                        mForm.addValidates(id);

                        if (mForm.validate()) {
                            Preferences.servers.add(new ServerEntry(((EditText) findViewById(R.id.serverAddress)).getText().toString(), ((EditText) findViewById(R.id.apiKey)).getText().toString(), ((EditText) findViewById(R.id.userID)).getText().toString(), null));
                            Log.w("12345", Preferences.servers.size() + "");
                            savePreferences();
                            loadPreferences();
                            finish();
                            overridePendingTransition(android.R.anim.fade_in, android.R.anim.fade_out);
                        }
                    }
                });
        customActionBarView.findViewById(R.id.actionbar_discard).setOnClickListener(
                new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        finish();
                        overridePendingTransition(android.R.anim.fade_in, android.R.anim.fade_out);
                    }
                });
        // Show the custom action bar view and hide the normal Home icon and title.
        final com.actionbarsherlock.app.ActionBar actionBar = getSupportActionBar();
        actionBar.setDisplayOptions(
                ActionBar.DISPLAY_SHOW_CUSTOM,
                ActionBar.DISPLAY_SHOW_CUSTOM | ActionBar.DISPLAY_SHOW_HOME
                        | ActionBar.DISPLAY_SHOW_TITLE);
        actionBar.setCustomView(customActionBarView,
                new com.actionbarsherlock.app.ActionBar.LayoutParams(
                    ViewGroup.LayoutParams.MATCH_PARENT,
                    ViewGroup.LayoutParams.MATCH_PARENT
                )
        );
        System.gc();
    }

    public void loadPreferences() {
        try {
            Preferences.servers = Preferences.fromString(Preferences.loadPreferences(Preferences.toString(Preferences.servers), getApplicationContext()));
            Log.w("12345", "Loaded Preferences");
            Log.w("12345", "" + Preferences.servers.size());
        } catch (IOException e) {
            e.printStackTrace();
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
    }

    public void savePreferences() {
        try {
            Preferences.savePreferences(Preferences.toString(Preferences.servers), getApplicationContext());
            Log.w("12345", "Saved Preferences");
        } catch (IOException e) {
            e.printStackTrace();
        }
    }


    public void launchQRScanner() {
        if (isCameraAvailable()) {
            Intent intent = new Intent(this, ZBarScannerActivity.class);
            intent.putExtra(ZBarConstants.SCAN_MODES, new int[]{Symbol.QRCODE});
            startActivityForResult(intent, ZBAR_QR_SCANNER_REQUEST);
        } else {
            Toast.makeText(this, "Rear Facing Camera Unavailable", Toast.LENGTH_SHORT).show();
        }
    }

    public boolean isCameraAvailable() {
        PackageManager pm = getPackageManager();
        return pm.hasSystemFeature(PackageManager.FEATURE_CAMERA);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        switch (requestCode) {
            case ZBAR_SCANNER_REQUEST:
            case ZBAR_QR_SCANNER_REQUEST:
                if (resultCode == RESULT_OK) {
                    String response = data.getStringExtra(ZBarConstants.SCAN_RESULT);
                    if (response.length() > 0) {
                        response = response.substring(1, response.length() - 1);
                        Toast.makeText(this, "Scan Result = " + response, Toast.LENGTH_SHORT).show();
                        generateQRCode((ImageView) findViewById(R.id.qrCodeImage), data.getStringExtra(ZBarConstants.SCAN_RESULT));
                        String[] pieces = response.split("\\|");
                        if (pieces.length == 3) {
                            ((EditText) findViewById(R.id.serverAddress)).setText(pieces[0]);
                            ((EditText) findViewById(R.id.apiKey)).setText(pieces[1]);
                            ((EditText) findViewById(R.id.userID)).setText(pieces[2]);
                        }
                        else {
                            ((EditText) findViewById(R.id.apiKey)).setText(response);
                        }
                    }

                } else if(resultCode == RESULT_CANCELED && data != null) {
                    String error = data.getStringExtra(ZBarConstants.ERROR_INFO);
                    if(!TextUtils.isEmpty(error)) {
                        Toast.makeText(this, error, Toast.LENGTH_SHORT).show();
                    }
                }
                break;
        }
    }

    public void clearForms() {
        ((EditText) findViewById(R.id.serverAddress)).setText("");
        ((EditText) findViewById(R.id.apiKey)).setText("");
        ((EditText) findViewById(R.id.userID)).setText("");
        findViewById(R.id.qrCodeImage).setVisibility(View.INVISIBLE);
    }

    public void generateQRCode(ImageView imageView, String qrData) {
        Display display = getWindowManager().getDefaultDisplay();
        Point size = new Point();
        display.getSize(size);
        int width = size.x;

        Bitmap mBitmap = null;
        int dimensions = 500;

        int first = 0;
        boolean firstMarked = false;

        try {
            BitMatrix bitMatrix = new QRCodeWriter().encode(qrData, BarcodeFormat.QR_CODE, 250, 250);
            mBitmap = Bitmap.createBitmap(dimensions, dimensions, Bitmap.Config.ARGB_8888);
            for (int i = 0; i < dimensions / 2; i++) {
                for (int j = 0; j < dimensions / 2; j++) {
                    if (!firstMarked && bitMatrix.get(i, j)) {
                        firstMarked = true;
                        first = i;
                    }
                    mBitmap.setPixel(i, j, bitMatrix.get(i, j) ? Color.BLACK : Color.rgb(226, 226, 226));
                }
            }
        } catch (WriterException e) {
            e.printStackTrace();
        }
        if (mBitmap != null) {
            imageView.setVisibility(View.VISIBLE);
            mBitmap = Bitmap.createBitmap(mBitmap, first, first, (dimensions / 2) - (2 * first), (dimensions / 2) - (2 * first));
            int dimens = (int) (.5 * width);
            mBitmap = Bitmap.createScaledBitmap(mBitmap, dimens, dimens, false);
            imageView.setImageBitmap(mBitmap);
        }
    }
}
