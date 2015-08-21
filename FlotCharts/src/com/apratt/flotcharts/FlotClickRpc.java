package com.apratt.flotcharts;

import com.vaadin.shared.communication.ServerRpc;

public interface FlotClickRpc extends ServerRpc {
    public void onPlotClick(int seriesIndex, int dataIndex);
}