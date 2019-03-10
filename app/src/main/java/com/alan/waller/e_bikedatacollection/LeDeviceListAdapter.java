package com.alan.waller.e_bikedatacollection;

import android.bluetooth.BluetoothDevice;
import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import java.util.ArrayList;

// Adapter for holding devices found through scanning.
public class LeDeviceListAdapter extends BaseAdapter {
    private ArrayList<BluetoothDevice> mLeDevices;
    private LayoutInflater mInflator;

    public LeDeviceListAdapter(Context context) {
        super();
        mLeDevices = new ArrayList<BluetoothDevice>();
        mInflator = LayoutInflater.from(context);
    }

    public void addDevice(BluetoothDevice device) {
        if(!mLeDevices.contains(device)) {
            mLeDevices.add(device);
        }
    }

    public BluetoothDevice getDevice(int position) {
        return mLeDevices.get(position);
    }

    public void clear() {
        mLeDevices.clear();
    }

    @Override
    public int getCount() {
        return mLeDevices.size();
    }

    @Override
    public Object getItem(int i) {
        return mLeDevices.get(i);
    }

    @Override
    public long getItemId(int i) {
        return i;
    }

    @Override
    public View getView(int i, View view, ViewGroup viewGroup) {
        RecyclerView.ViewHolder viewHolder;
        TextView deviceAddr;
        TextView deviceN;
        // General ListView optimization code.

        view = mInflator.inflate(R.layout.recycler_view_item, null);
        viewHolder = (RecyclerView.ViewHolder) view.getTag();
        deviceAddr = (TextView) view.findViewById(R.id.startView);
        deviceN = (TextView) view.findViewById(R.id.nameView);



        BluetoothDevice device = mLeDevices.get(i);
        final String deviceName = device.getName();
        if (deviceName != null && deviceName.length() > 0)
            deviceN.setText(deviceName);
        else
            deviceN.setText("Unknown Device");
        deviceAddr.setText(device.getAddress());

        return view;
    }
}