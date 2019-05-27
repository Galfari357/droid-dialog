package com.dialog;

import android.app.Dialog;
import android.content.Context;
import android.graphics.Typeface;
import android.support.v4.content.ContextCompat;
import android.text.TextUtils;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;


public class DroidDialog {

    public Dialog dialog = null;

    public DroidDialog(final Builder builder) {

        dialog = new Dialog(builder.context);
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        View view = LayoutInflater.from(builder.context).inflate(R.layout.layout_dialog, null);
        dialog.setContentView(view);

        WindowManager.LayoutParams lp = new WindowManager.LayoutParams();
        lp.copyFrom(dialog.getWindow().getAttributes());
        lp.width = WindowManager.LayoutParams.MATCH_PARENT;
        lp.height = WindowManager.LayoutParams.WRAP_CONTENT;
        lp.gravity = Gravity.CENTER;
        dialog.getWindow().setAttributes(lp);

         if (builder.animType == AnimUtils.AnimFadeInOut) {
            dialog.getWindow().getAttributes().windowAnimations = R.style.AnimFadeInOut;
        }

        ImageView imgIcon = (ImageView) view.findViewById(R.id.imgIcon);
        imgIcon.setColorFilter(builder.iconTintColor);

        RelativeLayout iconLayout = (RelativeLayout) view.findViewById(R.id.iconLayout);
        iconLayout.setBackgroundColor(builder.titleBgColor);

        TextView txtTitle = (TextView) view.findViewById(R.id.txtTitle);
        TextView txtContent = (TextView) view.findViewById(R.id.txtContent);

        Button btnPositive = (Button) view.findViewById(R.id.btnPositive);
        View viewStrip = view.findViewById(R.id.viewStrip);

        btnPositive.setTextColor(builder.buttonTextColor);

        if (builder.isDivider) {
            viewStrip.setVisibility(View.VISIBLE);
            viewStrip.setBackgroundColor(builder.dividerColor);
        } else {
            viewStrip.setVisibility(View.GONE);
        }

        if (builder.icon != 0) {
            imgIcon.setImageResource(builder.icon);
            iconLayout.setVisibility(View.VISIBLE);
        } else {
            iconLayout.setVisibility(View.GONE);
        }

        if (TextUtils.isEmpty(builder.title)) {
            txtTitle.setVisibility(View.GONE);
        } else {
            txtTitle.setVisibility(View.VISIBLE);
            txtTitle.setText(builder.title);
        }

        if (TextUtils.isEmpty(builder.content)) {
            txtContent.setVisibility(View.GONE);
        } else {
            txtContent.setVisibility(View.VISIBLE);
            txtContent.setText(builder.content);
        }

        if (TextUtils.isEmpty(builder.positiveText)) {
            btnPositive.setVisibility(View.GONE);
        } else {
            btnPositive.setVisibility(View.VISIBLE);
            btnPositive.setText(builder.positiveText);
            btnPositive.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    builder.onPositiveListener.onPositive(dialog);
                }
            });
        }


        if (!TextUtils.isEmpty(builder.typeface)) {
            Typeface ttf = Typeface.createFromAsset(builder.context.getAssets(), "fonts/" + builder.typeface);
            txtTitle.setTypeface(ttf);
            txtContent.setTypeface(ttf);
            btnPositive.setTypeface(ttf, Typeface.BOLD);
        }

        dialog.setCancelable(builder.isCancelable);
        dialog.setCanceledOnTouchOutside(builder.isCancelableTouchOutside);

        dialog.show();
    }

    public void dismiss() {
        if (dialog != null)
            dialog.dismiss();
    }


    public static class Builder {

        // default values
        private Context context;
        private int icon = 0;
        private String title = "Title";
        private String content = "Content Description";

        private boolean isCancelable = true;
        private boolean isCancelableTouchOutside = false;

        private String positiveText = "";
        private onPositiveListener onPositiveListener = new onPositiveListener() {
            @Override
            public void onPositive(Dialog droidDialog) {

            }
        };

        private String typeface = "";

        private int animType = 0;

        private int titleBgColor = 0;
        private int iconTintColor = 0;
        private int buttonTextColor = 0;

        private boolean isDivider = false;
        private int dividerColor = 0;

        public Builder(Context context) {
            this.context = context;
            titleBgColor = ContextCompat.getColor(context, R.color.indigo);
            iconTintColor = ContextCompat.getColor(context, R.color.white);
            buttonTextColor = ContextCompat.getColor(context, R.color.dark_indigo);
            dividerColor = ContextCompat.getColor(context, R.color.orange);
        }

        public DroidDialog show() {
            return new DroidDialog(this);
        }

        /*
        dialog topbar icon
        */
        public Builder icon(int icon) {
            this.icon = icon;
            return this;
        }

        /*
        dialog title
        */
        public Builder title(String title) {
            this.title = title;
            return this;
        }

        /*
        dialog content message
        */
        public Builder content(String content) {
            this.content = content;
            return this;
        }

        /*
        dialog cancelable flag
        */
        public Builder cancelable(boolean isCancelable, boolean isCancelableTouchOutside) {
            this.isCancelable = isCancelable;
            this.isCancelableTouchOutside = isCancelableTouchOutside;
            return this;
        }

        /*
        dialog positive button and click event handler
        */
        public Builder positiveButton(String positiveText, onPositiveListener onPositiveListener) {
            this.positiveText = positiveText;
            this.onPositiveListener = onPositiveListener;
            return this;
        }

        public Builder typeface(String typeface) {
            this.typeface = typeface;
            return this;
        }

        public Builder animation(int animType) {
            this.animType = animType;
            return this;
        }

        public Builder color(int titleBgColor, int iconTintColor, int buttonTextColor) {
            this.titleBgColor = titleBgColor;
            this.iconTintColor = iconTintColor;
            this.buttonTextColor = buttonTextColor;
            return this;
        }

        public Builder divider(boolean isDivider, int dividerColor) {
            this.isDivider = isDivider;
            this.dividerColor = dividerColor;
            return this;
        }
    }

    public interface onPositiveListener {
        void onPositive(Dialog droidDialog);
    }

}
