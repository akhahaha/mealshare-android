package com.munch.android.flow;

import android.content.Context;
import android.support.v4.app.Fragment;
import android.view.KeyEvent;

import com.munch.android.event.Event;

/**
 * Presentation controller base class
 * Created by Alan on 5/31/2016.
 */
public abstract class Presenter {
    private Context context;
    private Presenter prevPresenter;
    private OnPresentationChangeListener listener;

    public Presenter(Context context) {
        this.context = context;
    }

    public Context getContext() {
        return context;
    }

    public void setContext(Context context) {
        this.context = context;
    }

    public abstract boolean handleEvent(Event event);

    public abstract boolean handleEvent(KeyEvent event);

    public abstract Fragment getViewFragment();

    public Presenter getPrevPresenter() {
        return prevPresenter;
    }

    public void setPrevPresenter(Presenter prevPresenter) {
        this.prevPresenter = prevPresenter;
    }

    public void pop() {
        if (getPrevPresenter() != null) {
            notifyListeners(getPrevPresenter());
        }
    }

    public OnPresentationChangeListener getListener() {
        return listener;
    }

    public void setListener(OnPresentationChangeListener listener) {
        this.listener = listener;
    }

    public void notifyListeners(Presenter presenter) {
        if (getListener() != null) {
            getListener().onPresentationChange(presenter);
        }
    }

    public interface OnPresentationChangeListener {
        void onPresentationChange(Presenter presenter);
    }
}
