package com.apratt.flotcharts;

import javax.servlet.annotation.WebServlet;

import com.vaadin.annotations.Theme;
import com.vaadin.annotations.Title;
import com.vaadin.annotations.VaadinServletConfiguration;
import com.vaadin.server.VaadinRequest;
import com.vaadin.server.VaadinServlet;
import com.vaadin.ui.Button;
import com.vaadin.ui.Button.ClickEvent;
import com.vaadin.ui.HorizontalLayout;
import com.vaadin.ui.Label;
import com.vaadin.ui.Notification;
import com.vaadin.ui.Notification.Type;
import com.vaadin.ui.TextField;
import com.vaadin.ui.UI;
import com.vaadin.ui.VerticalLayout;

@SuppressWarnings("serial")
@Theme("flotcharts")
@Title("FlotCharts") 
public class DemoUI extends UI {  

	final VerticalLayout right = new VerticalLayout();
	final VerticalLayout left = new VerticalLayout();
//	private TextField inputField = new TextField();
//	private Button button = new Button("Submit Data to Graph");
	//private String flotData = new String();
	private FlotChart chart;
	private FlotChart updatesChart;
	int x = 0;	//testing updates
	int prevY = 0;
	
	@WebServlet(value = "/*", asyncSupported = true)
	@VaadinServletConfiguration(productionMode = false, ui = DemoUI.class, widgetset = "com.apratt.flotcharts.widgetset.FlotchartsWidgetset")
	public static class Servlet extends VaadinServlet {
	}

	@Override
	protected void init(VaadinRequest request) {
		HorizontalLayout main = new HorizontalLayout();
		main.setWidth("100%");
//		main.setMargin(true);
//		main.setSpacing(true);
		setContent(main);

		main.addComponent(leftLayout());
		main.addComponent(rightLayout());
	
	}
	
	
	public VerticalLayout rightLayout() { 
		right.setMargin(true);
		right.setSpacing(true);
		
		// FLOT CHART

		// set input data
		final TextField inputField = new TextField();
		inputField.setWidth("45%");
		inputField.setCaption("Give graph data in format: '[[0,0], [10,30], [20,50]]'");
		inputField.setValue("[ [0,0] ]");
		right.addComponent(inputField);
		
		// lable to display graph data TESTING PURPOSES
		final Label currentData = new Label();
		right.addComponent(currentData);
		

		// button to draw graph
		Button button = new Button("Submit Data to Graph");
		button.addClickListener(new Button.ClickListener() {
			public void buttonClick(ClickEvent event) {				
				if (updatesChart != null) {
					right.removeComponent(updatesChart); 
	//				try {
	//					Thread.sleep(500);
	//				} catch (InterruptedException e) {
	//					e.printStackTrace();
	//				}				
				}

				buildUpdatesChart(inputField.getValue());
				right.addComponent(updatesChart);

				// update label
				currentData.setValue("Data from chart State:\n" + updatesChart.getData().toString());	//current.setValue("Graph data is: " + flot.getData());				
			}
		}); 
		right.addComponent(button);

		
		// button to get graph data
		Button dataButton = new Button("get current data from chart");
		dataButton.addClickListener(new Button.ClickListener() {
			public void buttonClick(ClickEvent event) {			
				updatesChart.getCurrentData();

				// update label
				currentData.setValue("Data from chart State:\n" + updatesChart.getData());	//current.setValue("Graph data is: " + flot.getData());
			}
		});
//		right.addComponent(dataButton);

		
		// update Button
		Button updateButton = new Button("get Data Updates");
		updateButton.addClickListener(new Button.ClickListener() {
			public void buttonClick(ClickEvent event) {		
				
				Notification.show("you asked for updates...", Type.HUMANIZED_MESSAGE);

				fetchNewData(20);
//				for (int i=0; i<25; i++) { 
//					updatesChart.update();
//				}
				updatesChart.getCurrentData();
				
				// update label
				currentData.setValue("Data from chart State:\n" + updatesChart.getData());	//current.setValue("Graph data is: " + flot.getData());
			}
		});
		right.addComponent(updateButton);
		
		
		return right;
	}
	

	public void fetchNewData(int rounds) {
		int y;
		for (int i=0; i<rounds; i++) {
			if (prevY <= 150) {
				y = prevY + 10; 
			} else {
				y = prevY - 75; 
			}
			x += 1; 	

			updatesChart.addNewData(x, y);	// update the server side data
			updatesChart.update(x, y);			// update the js code to effect the chart 
	//		data.push([x, y]);
			prevY = y;
//			prevX = x;
		}
		
//		return data;
	}

	
	
	public VerticalLayout leftLayout() {
		left.setMargin(true);
		left.setSpacing(true);
		
		// FLOT CHART

		// set input data
		final TextField inputField = new TextField();
		inputField.setWidth("45%");
		inputField.setCaption("Give graph data in format: '[[0,0], [10,30], [20,50]]'");
		inputField.setValue("[[0,0],[19.33,19.93],[33.16,24.39],[49.66,102.45],[76.33,13.23]]");		//("[[0,0], [10,30], [20,50],[50,70],[60,0]]");
		//flotInput.setInputPrompt("[[0,0], [10,30], [20,50],[50,70],[60,0]]");
		left.addComponent(inputField);
		
		// lable to display graph data TESTING PURPOSES
		final Label currentData = new Label();
		left.addComponent(currentData);
		

		// button to draw graph
		Button button = new Button("Submit Data to Graph");
		button.addClickListener(new Button.ClickListener() {
			public void buttonClick(ClickEvent event) {
				//flotData = flotInput.getValue();
				
				if (chart != null) {
					left.removeComponent(chart); 
	//				try {
	//					Thread.sleep(500);
	//				} catch (InterruptedException e) {
	//					e.printStackTrace();
	//				}				
				}

				buildFlotChart(inputField.getValue());
				left.addComponent(chart);
//						layout.addComponent(new Label("this is the chart options JSON: " + chart.getOptions().toString()));
				// update label
				currentData.setValue("Data from chart State:\n" + chart.getData().toString());	//current.setValue("Graph data is: " + flot.getData());				
			}
		});
		left.addComponent(button);

		
		// button to get graph data
		Button dataButton = new Button("get current data from chart");
		dataButton.addClickListener(new Button.ClickListener() {
			public void buttonClick(ClickEvent event) {			
//						layout.addComponent(new Label("this is the chart options JSON: " + chart.getOptions().toString()));
				// update label
//						currentData.setValue(formatDataFromGraph("Data from chart State: " + chart.getData().toString()));	//current.setValue("Graph data is: " + flot.getData());
				currentData.setValue("Data from chart State:\n" + chart.getData().toString());	//current.setValue("Graph data is: " + flot.getData());
//						Notification.show(formatDataFromGraph("this is the data now in the chart: " + chart.getData().toString()));
			}
		});
		left.addComponent(dataButton);
		
		return left;
				
//				buildFlotChart("[[0,0], [10,30], [20,50]]");
//				layout.addComponent(chart);
//				FlotChart flot2 = new FlotChart();
//				flot2.setWidth("600px");
//				flot2.setHeight("300px");
//				flot2.addSeries(1, 2, 4, 8, 16);
//				layout.addComponent(flot2);
	}
	
	
	public void buildUpdatesChart(String data) {
		updatesChart = new FlotChart();
		updatesChart.setWidth("95%");
		updatesChart.setHeight("300px");
		
//		String data1 = formatDataForGraph(data);		//"[{ data:" + data + "\", lines:{show:true}\", points:{show:true} }]";
//		String d = "[[0,0],[5,5],[10,10],[20,20],[25,25],[30,40],[32,44],[37,50],[40,100],[45,110],[50,110],[55,100],[60,50],[70,20],[80,10]]";
		String data1 = data;	//lines:{show:true, fill:true}, points:{show:true}, 	//formatDataForGraph("[[0,0], [10,30], [20,50]]");
//		String data2 = data + ", label: ramp function, lines:{show:true, fill:false}, points:{show:true}, clickable:false, hoverable:true, editable:true";
		
		updatesChart.setData("[{ data: " + data1 + " }]");	
//		updatesChart.setData("[{ data: " + data1 + "}, { data: " + data2 + " }]");	

		// options
		String options =
				"{" + 
					"series: { label: server data, lines: {show:true, fill:true}, points: {show: true} }," +
//					"crosshair: {mode: x}, " +
					"legend: { position: nw }, " +
					"xaxes: [{ axisLabel: x label, }], " +
					"yaxes: [{ position: left, axisLabel: y label, tickFormatter: 'ms'}], " +
					"grid: { " +
						"clickable: true," +
						"hoverable: true," +
					"}" +
				"}";
		updatesChart.setOptions(options);

	}
	
	
	
	public void buildFlotChart(String data) {
		chart = new FlotChart();
		chart.setWidth("95%");
		chart.setHeight("300px");
		
//		String data1 = formatDataForGraph(data);		//"[{ data:" + data + "\", lines:{show:true}\", points:{show:true} }]";
		String d = "[[0,0],[5,5],[10,10],[20,20],[25,25],[30,40],[32,44],[37,50],[40,100],[45,110],[50,110],[55,100],[60,50],[70,20],[80,10]]";
		String data1 = d + ", label: server data, lines: {show:true, fill:true}, points:{show:true}, clickable:true, hoverable:true, editable:false";	//lines:{show:true, fill:true}, points:{show:true}, 	//formatDataForGraph("[[0,0], [10,30], [20,50]]");
		String data2 = data + ", label: ramp function, lines:{show:true, fill:false}, points:{show:true}, clickable:false, hoverable:true, editable:true";
		
//		chart.setData("[{ data: " + data1 + " }]");	//(formatDataForGraph(data));
		chart.setData("[{ data: " + data1 + "}, { data: " + data2 + " }]");	//("[{ data:[[0,0], [10,30], [20,50]], lines:{show:true}, points:{show:true}, hoverable:true, clickable:true }]");	//(formatDataForGraph(data));
		// options
		String options =
				"{" + 
					//"series: { lines: {show: true}, points: {show: true} }" +
//					"crosshair: {mode: x}, " +
					"legend: { position: nw }, " +
					"xaxes: [{ axisLabel: x label, }], " +
					"yaxes: [{ position: left, axisLabel: y label, tickFormatter: 'ms'}], " +
					"grid: { " +
						"aboveData: false," +
						"clickable: true," +
						"hoverable: true," +
						"editable:true," +
//						"backgroundColor:{" +
//							"colors:[ \"#fef\", \"#eee\" ]" +
//						"}" +
					"}" +
				"}";
		chart.setOptions(options);
		/*String data = "[" + //"[" +
					"[0, 5]," +
					"[2, 7]," +
					"[4, 8]," +
					"[10, 5]" +
					"]";// + "]";
		*/
	}
	
	
	public String formatDataForGraph(String input) {
		String formatted = new StringBuilder().append("[").append(input).append("]").toString();
		System.out.println("formatted: " + formatted);
		return formatted;
		
	}

	public String formatDataFromGraph(String graphData) {
		//String formatted = new StringBuilder().append("[").append(input).append("]").toString();	
		//String formatted = StringUtils.substringBetween(graphData, "[", "]");
		String formatted = graphData.substring(1, graphData.length() - 1);
		System.out.println("de-formatted: " + formatted);
		return formatted;	
	}
	
}