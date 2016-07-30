package com.mealshare.android.flow;

import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentTransaction;
import android.view.KeyEvent;

import com.mealshare.android.event.Event;

/**
 * Fragment flow controller
 * Created by Alan on 5/31/2016.
 */
public class FlowController implements Presenter.OnPresentationChangeListener {
    private FragmentActivity activity;
    private int resourceID;
    private Presenter presenter;

    public FlowController(FragmentActivity activity, int resourceID) {
        this.activity = activity;
        this.resourceID = resourceID;
    }

    public boolean handleEvent(Event event) {
        return presenter.handleEvent(event);
    }

    public boolean handleEvent(KeyEvent event) {
        return presenter.handleEvent(event);
    }

    public FragmentActivity getActivity() {
        return activity;
    }

    public void setActivity(FragmentActivity activity) {
        this.activity = activity;
    }

    public int getResourceID() {
        return resourceID;
    }

    public void setResourceID(int resourceID) {
        this.resourceID = resourceID;
    }

    public Presenter getPresenter() {
        return presenter;
    }

    public void setPresenter(Presenter presenter) {
        this.presenter = presenter;
        presenter.setListener(this);

        FragmentTransaction ft = activity.getSupportFragmentManager().beginTransaction();
        ft.replace(resourceID, this.presenter.getViewFragment());
        ft.commit();
    }

    @Override
    public void onPresentationChange(Presenter presenter) {
        setPresenter(presenter);
    }
}
