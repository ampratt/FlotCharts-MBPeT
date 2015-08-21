package com.apratt.flotcharts;

import org.json.JSONArray;

import com.vaadin.shared.communication.ServerRpc;

public interface FlotUpdateRpc extends ServerRpc {
    public void onDataUpdate(int seriesIndex, int dataIndex);
    public void onDataUpdate(JSONArray data);
}
