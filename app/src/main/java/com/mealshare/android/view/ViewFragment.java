package com.mealshare.android.view;

import android.content.Context;
import android.support.v4.app.Fragment;

import com.mealshare.android.event.Event;

/**
 * A simple {@link Fragment} subclass.
 * Activities that contain this fragment must implement the
 * {@link OnFragmentInteractionListener} interface
 * to handle interaction events.
 */
public abstract class ViewFragment extends Fragment {
    private OnFragmentInteractionListener fragmentInteractionListener;

    public OnFragmentInteractionListener getFragmentInteractionListener() {
        return fragmentInteractionListener;
    }

    public void setFragmentInteractionListener(
            OnFragmentInteractionListener fragmentInteractionListener) {
        this.fragmentInteractionListener = fragmentInteractionListener;
    }

    public void notifyListener(Event event) {
        if (getFragmentInteractionListener() != null) {
            getFragmentInteractionListener().onFragmentInteraction(event);
        }
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        if (context instanceof OnFragmentInteractionListener) {
            fragmentInteractionListener = (OnFragmentInteractionListener) context;
        } else {
            throw new RuntimeException(context.toString()
                    + " must implement OnFragmentInteractionListener");
        }
    }

    @Override
    public void onDetach() {
        super.onDetach();
        fragmentInteractionListener = null;
    }

    /**
     * This interface must be implemented by activities that contain this
     * fragment to allow an interaction in this fragment to be communicated
     * to the activity and potentially other fragments contained in that
     * activity.
     * <p/>
     * See the Android Training lesson <a href=
     * "http://developer.android.com/training/basics/fragments/communicating.html"
     * >Communicating with Other Fragments</a> for more information.
     */
    public interface OnFragmentInteractionListener {
        void onFragmentInteraction(Event event);
    }
}
