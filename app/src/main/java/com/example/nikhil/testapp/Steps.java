package com.example.nikhil.testapp;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;
import android.net.Uri;
import android.os.Bundle;
import android.provider.ContactsContract;
import android.support.v4.app.Fragment;
import android.support.v7.app.AppCompatActivity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

public class Steps extends Fragment implements SensorEventListener,StepListener {
    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    private OnFragmentInteractionListener mListener;

    public Steps() {
        // Required empty public constructor
    }

    private TextView tvsteps;
    private StepDetector simpleStepDetector;
    private SensorManager sensorManager;
    private Sensor accel;
    private static final String TEXT_NUM_STEPS = "Number of Steps: ";
    private int numSteps;
    private final SensorEventListener sel = this;
    private int pause = 0;
    private int started = 0;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            // TODO: Rename and change types of parameters
            String mParam1 = getArguments().getString(ARG_PARAM1);
            String mParam2 = getArguments().getString(ARG_PARAM2);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        final View rootView =  inflater.inflate(R.layout.fragment_steps, container, false);
        sensorManager = (SensorManager) getActivity().getSystemService(Context.SENSOR_SERVICE);
        accel = sensorManager.getDefaultSensor(Sensor.TYPE_ACCELEROMETER);
        simpleStepDetector = new StepDetector();
        simpleStepDetector.registerListener(this);

        tvsteps = (TextView) rootView.findViewById(R.id.tvsteps);
        final ImageView imageViewStart = rootView.findViewById(R.id.imageViewStart);
        final ImageView imageViewStartPause = rootView.findViewById(R.id.imageViewStartPause);
        final ImageView imageViewStop = rootView.findViewById(R.id.imageViewStop);

        imageViewStart.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                numSteps = 0;
                started = 1;
                sensorManager.registerListener(sel,accel,SensorManager.SENSOR_DELAY_FASTEST);
                imageViewStart.setVisibility(View.INVISIBLE);
                imageViewStartPause.setVisibility(View.VISIBLE);
                imageViewStartPause.setImageResource(R.drawable.pause);
                imageViewStop.setVisibility(View.VISIBLE);
            }
        });

        imageViewStartPause.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(started == 1){
                    if(pause == 0){
                        sensorManager.unregisterListener(sel);
                        pause = 1;
                        imageViewStartPause.setImageResource(R.drawable.start);
                    }
                    else{
                        pause = 0;
                        imageViewStartPause.setImageResource(R.drawable.pause);
                        sensorManager.registerListener(sel,accel,SensorManager.SENSOR_DELAY_FASTEST);
                    }
                }
                else{
                    Toast.makeText(getActivity(),"Please start the counter first!",Toast.LENGTH_SHORT).show();
                }
            }
        });
        imageViewStop.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                started = 0;
                numSteps = 0;
                sensorManager.unregisterListener(sel);
                imageViewStart.setVisibility(View.VISIBLE);
                imageViewStartPause.setVisibility(View.INVISIBLE);
                imageViewStop.setVisibility(View.INVISIBLE);
                tvsteps.setText(getString(R.string.letsgo));
            }
        });

        return rootView;

    }

    // TODO: Rename method, update argument and hook method into UI event
    public void onButtonPressed(Uri uri) {
        if (mListener != null) {
            mListener.onFragmentInteraction(uri);
        }
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        if (context instanceof OnFragmentInteractionListener) {
            mListener = (OnFragmentInteractionListener) context;
        } else {
            throw new RuntimeException(context.toString()
                    + " must implement OnFragmentInteractionListener");
        }
    }

    @Override
    public void onDetach() {
        super.onDetach();
        mListener = null;
    }

    @Override
    public void onSensorChanged(SensorEvent event) {
        if(event.sensor.getType() == Sensor.TYPE_ACCELEROMETER){
            simpleStepDetector.updateAccel(
                    event.timestamp, event.values[0], event.values[1], event.values[2]);
        }

    }

    @Override
    public void onAccuracyChanged(Sensor sensor, int accuracy) {

    }

    @Override
    public void step(long timeNs) {
        numSteps++;
        String text = "Steps: " + numSteps;
        tvsteps.setText(text);
    }

    /**
     * This interface must be implemented by activities that contain this
     * fragment to allow an interaction in this fragment to be communicated
     * to the activity and potentially other fragments contained in that
     * activity.
     * <p>
     * See the Android Training lesson <a href=
     * "http://developer.android.com/training/basics/fragments/communicating.html"
     * >Communicating with Other Fragments</a> for more information.
     */
    public interface OnFragmentInteractionListener {
        // TODO: Update argument type and name
        void onFragmentInteraction(Uri uri);
    }
}
