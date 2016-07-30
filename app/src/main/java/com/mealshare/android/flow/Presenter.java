package com.mealshare.android.flow;

import android.support.v4.app.Fragment;
import android.view.KeyEvent;

import com.mealshare.android.event.Event;

/**
 * Presentation controller base class
 * Created by Alan on 5/31/2016.
 */
public abstract class Presenter {
    private Presenter prevPresenter;
    private OnPresentationChangeListener listener;

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
