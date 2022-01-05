package in.linus.busmate.Utility;

import android.app.Dialog;
import android.content.Context;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import androidx.cardview.widget.CardView;
import in.linus.busmate.R;

public class CustomProgressDialog extends Dialog {
    CardView cardView = ((CardView) findViewById(R.id.card_view));
    Context mContext;
    CardView transparentCard = ((CardView) findViewById(R.id.app));
    Animation zoom_in = AnimationUtils.loadAnimation(this.mContext, R.anim.zoomin);
    Animation zoom_out = AnimationUtils.loadAnimation(this.mContext, R.anim.zoomout);
    Animation zoomin = AnimationUtils.loadAnimation(this.mContext, R.anim.zoom_in);
    Animation zoomout = AnimationUtils.loadAnimation(this.mContext, R.anim.zoom_out);

    public CustomProgressDialog(Context context) {
        super(context, R.style.TransparentProgressDialog);
        this.mContext = context;
        setContentView(R.layout.progress_dialog);
    }

    public void card1_out() {
        this.cardView.startAnimation(this.zoomout);
        this.zoomout.setAnimationListener(new Animation.AnimationListener() {
            public void onAnimationRepeat(Animation animation) {
            }

            public void onAnimationStart(Animation animation) {
            }

            public void onAnimationEnd(Animation animation) {
                CustomProgressDialog.this.card1_in();
            }
        });
    }

    public void card1_in() {
        this.cardView.startAnimation(this.zoomin);
        this.zoomin.setAnimationListener(new Animation.AnimationListener() {
            public void onAnimationRepeat(Animation animation) {
            }

            public void onAnimationStart(Animation animation) {
            }

            public void onAnimationEnd(Animation animation) {
                CustomProgressDialog.this.card1_out();
            }
        });
    }

    public void card2_in() {
        this.transparentCard.startAnimation(this.zoom_in);
        this.zoom_in.setAnimationListener(new Animation.AnimationListener() {
            public void onAnimationRepeat(Animation animation) {
            }

            public void onAnimationStart(Animation animation) {
            }

            public void onAnimationEnd(Animation animation) {
                CustomProgressDialog.this.card2_out();
            }
        });
    }

    public void card2_out() {
        this.transparentCard.startAnimation(this.zoom_out);
        this.zoom_out.setAnimationListener(new Animation.AnimationListener() {
            public void onAnimationRepeat(Animation animation) {
            }

            public void onAnimationStart(Animation animation) {
            }

            public void onAnimationEnd(Animation animation) {
                CustomProgressDialog.this.card2_in();
            }
        });
    }

    public void show() {
        super.show();
        card1_out();
        card2_in();
    }

    public void dismiss() {
        super.dismiss();
        this.zoom_in.cancel();
        this.zoom_out.cancel();
        this.zoomin.cancel();
        this.zoomout.cancel();
    }
}
