package activities.ziffytech.code.activities;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.amulyakhare.textdrawable.TextDrawable;
import com.amulyakhare.textdrawable.util.ColorGenerator;
import activities.ziffytech.code.config.ApiParams;
import activities.ziffytech.code.R;


public class Settings extends CommonActivity implements View.OnClickListener {

    TextView btnpersonalDetails;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_settings);
        allowBack();
        setHeaderTitle("Settings");

            String letter;
            letter =common.getSession(ApiParams.USER_FULLNAME).substring(0, 1);
            ColorGenerator mColorGenerator = ColorGenerator.DEFAULT;
            int color = mColorGenerator.getRandomColor();
            TextDrawable mDrawableBuilder = TextDrawable.builder().buildRound(letter, color);
            ImageView thumbnail_image1=(ImageView)findViewById(R.id.thumbnail_image1);
            thumbnail_image1.setImageDrawable(mDrawableBuilder);
    }

    @Override
    protected void onStart()
    {
        super.onStart();
        setUpViews();
    }

    private void setUpViews()
    {
        TextView tvName=(TextView)findViewById(R.id.tvName);
        tvName.setText(common.getSession(ApiParams.USER_FULLNAME));
        TextView tvEmail=(TextView)findViewById(R.id.tvEmail);
        tvEmail.setText(common.getSession(ApiParams.USER_EMAIL));

        btnpersonalDetails = (TextView)findViewById(R.id.btnpersonalDetails);
        btnpersonalDetails.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        if(v.getId()==R.id.btnpersonalDetails)
        {
            Intent i = new Intent(Settings.this,PersonalDetail.class);
            startActivity(i);
        }
    }
}
