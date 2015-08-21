package com.apratt.flotcharts;

import com.vaadin.shared.communication.ClientRpc;

public interface FlotHighlightRpc extends ClientRpc {
    public void highlight(int seriesIndex, int dataIndex);
}