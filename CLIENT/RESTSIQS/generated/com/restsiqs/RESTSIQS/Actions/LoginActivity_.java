//
// DO NOT EDIT THIS FILE, IT HAS BEEN GENERATED USING AndroidAnnotations 3.0.1.
//


package com.restsiqs.RESTSIQS.Actions;

import android.app.Activity;
import android.app.Fragment;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup.LayoutParams;
import android.widget.Button;
import android.widget.EditText;
import com.restsiqs.RESTSIQS.R.id;
import com.restsiqs.RESTSIQS.R.layout;
import org.androidannotations.api.view.HasViews;
import org.androidannotations.api.view.OnViewChangedListener;
import org.androidannotations.api.view.OnViewChangedNotifier;

public final class LoginActivity_
    extends LoginActivity
    implements HasViews, OnViewChangedListener
{

    private final OnViewChangedNotifier onViewChangedNotifier_ = new OnViewChangedNotifier();

    @Override
    public void onCreate(Bundle savedInstanceState) {
        OnViewChangedNotifier previousNotifier = OnViewChangedNotifier.replaceNotifier(onViewChangedNotifier_);
        init_(savedInstanceState);
        super.onCreate(savedInstanceState);
        OnViewChangedNotifier.replaceNotifier(previousNotifier);
        setContentView(layout.login);
    }

    private void init_(Bundle savedInstanceState) {
        OnViewChangedNotifier.registerOnViewChangedListener(this);
    }

    @Override
    public void setContentView(int layoutResID) {
        super.setContentView(layoutResID);
        onViewChangedNotifier_.notifyViewChanged(this);
    }

    @Override
    public void setContentView(View view, LayoutParams params) {
        super.setContentView(view, params);
        onViewChangedNotifier_.notifyViewChanged(this);
    }

    @Override
    public void setContentView(View view) {
        super.setContentView(view);
        onViewChangedNotifier_.notifyViewChanged(this);
    }

    public static LoginActivity_.IntentBuilder_ intent(Context context) {
        return new LoginActivity_.IntentBuilder_(context);
    }

    public static LoginActivity_.IntentBuilder_ intent(Fragment fragment) {
        return new LoginActivity_.IntentBuilder_(fragment);
    }

    @Override
    public void onViewChanged(HasViews hasViews) {
        etPassword = ((EditText) hasViews.findViewById(id.password));
        etAccount = ((EditText) hasViews.findViewById(id.account));
        btnLogin = ((Button) hasViews.findViewById(id.btnLogin));
        {
            View view = hasViews.findViewById(id.btnLogin);
            if (view!= null) {
                view.setOnClickListener(new OnClickListener() {


                    @Override
                    public void onClick(View view) {
                        LoginActivity_.this.login();
                    }

                }
                );
            }
        }
    }

    public static class IntentBuilder_ {

        private Context context_;
        private final Intent intent_;
        private Fragment fragment_;

        public IntentBuilder_(Context context) {
            context_ = context;
            intent_ = new Intent(context, LoginActivity_.class);
        }

        public IntentBuilder_(Fragment fragment) {
            fragment_ = fragment;
            context_ = fragment.getActivity();
            intent_ = new Intent(context_, LoginActivity_.class);
        }

        public Intent get() {
            return intent_;
        }

        public LoginActivity_.IntentBuilder_ flags(int flags) {
            intent_.setFlags(flags);
            return this;
        }

        public void start() {
            context_.startActivity(intent_);
        }

        public void startForResult(int requestCode) {
            if (fragment_!= null) {
                fragment_.startActivityForResult(intent_, requestCode);
            } else {
                if (context_ instanceof Activity) {
                    ((Activity) context_).startActivityForResult(intent_, requestCode);
                } else {
                    context_.startActivity(intent_);
                }
            }
        }

    }

}
