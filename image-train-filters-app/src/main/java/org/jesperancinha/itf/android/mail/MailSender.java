package org.jesperancinha.itf.android.mail;

import android.content.Intent;
import android.net.Uri;
import android.util.Log;

import androidx.activity.ComponentActivity;

import org.jesperancinha.chartizate.BuildConfig;
import org.jesperancinha.itf.android.EmailFragment;
import org.jesperancinha.itf.android.R;
import org.jesperancinha.itf.android.main.MainFragment;

import java.io.File;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.NoArgsConstructor;

@Builder
@AllArgsConstructor
@NoArgsConstructor
public class MailSender extends ComponentActivity {

    private MainFragment mainFragment;
    private EmailFragment emailFragment;
    private String outputFileName;

    public void sendEmail() {

        final Uri uri = Uri.fromFile(new File(mainFragment.getImageConfiguration().getCurrentSelectedFolder().getFile(), outputFileName));
        final Intent emailIntent = MailIntentCreator
                .builder().to(getTo(emailFragment)).cc(getCc(emailFragment)).bcc(getBcc(emailFragment))
                .subject("Generated by Image train filters " + BuildConfig.VERSION_NAME)
                .text(emailFragment.getTextEmail().getText().toString())
                .uri(uri).build().build();
        try {
            startActivity(Intent.createChooser(emailIntent, "Send mail..."));
            Log.i("Mail sent!", "");
        } catch (android.content.ActivityNotFoundException ex) {
            Log.e("There is no email client installed.", ex.getMessage());
        }
    }


    private String[] getBcc(EmailFragment emailFragment) {
        return emailFragment.getTextBCC().getText().toString().split(getResources().getString(R.string.comma));
    }

    private String[] getCc(EmailFragment emailFragment) {
        return emailFragment.getTextCC().getText().toString().split(getResources().getString(R.string.comma));
    }

    private String[] getTo(EmailFragment emailFragment) {
        return emailFragment.getTextTo().getText().toString().split(getResources().getString(R.string.comma));
    }

}
