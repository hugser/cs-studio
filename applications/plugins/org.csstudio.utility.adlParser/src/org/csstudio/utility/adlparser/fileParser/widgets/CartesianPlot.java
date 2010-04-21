package org.csstudio.utility.adlparser.fileParser.widgets;

import java.util.ArrayList;

import org.csstudio.utility.adlparser.fileParser.ADLWidget;
import org.csstudio.utility.adlparser.fileParser.FileLine;
import org.csstudio.utility.adlparser.fileParser.WrongADLFormatException;
import org.csstudio.utility.adlparser.fileParser.widgetParts.ADLObject;
import org.csstudio.utility.adlparser.fileParser.widgetParts.ADLPlotData;
import org.csstudio.utility.adlparser.fileParser.widgetParts.ADLPlotTrace;
import org.csstudio.utility.adlparser.fileParser.widgetParts.ADLPlotcom;
import org.csstudio.utility.adlparser.internationalization.Messages;

public class CartesianPlot extends ADLAbstractWidget {
	private String count = new String();
	private String erase = new String();
	private String trigger = new String();
	private String eraseMode = new String("if not zero");
	private String plotStyle = new String("point");
	private String plotMode = new String("plot n pts & stop");
	private ArrayList<ADLPlotTrace> traces = new ArrayList<ADLPlotTrace>();
	private ADLPlotData xAxisData = null;
	private ADLPlotData y1AxisData = null;
	private ADLPlotData y2AxisData = null;
	
	public CartesianPlot(ADLWidget adlWidget) {
		super(adlWidget);
		try {
			for (ADLWidget childWidget : adlWidget.getObjects()) {
	        	if (childWidget.getType().equals("object")){
	        		_adlObject = new ADLObject(childWidget);
	        		if (_adlObject != null){
	        			_hasObject = true;
	        		}
	        	}
	        	if (childWidget.getType().equals("plotcom")){
	        		_adlPlotcom = new ADLPlotcom(childWidget);
	        		if (_adlPlotcom != null){
	        			_hasPlotcom = true;
	        		}
	        	}
	        	if (childWidget.getType().startsWith("trace[")){
	        		traces.add(new ADLPlotTrace(childWidget));
	        	}
	        	if (childWidget.getType().equals("x_axis")){
	        		xAxisData = new ADLPlotData(childWidget);
	        	}
	        	if (childWidget.getType().equals("y1_axis")){
	        		y1AxisData = new ADLPlotData(childWidget);
	        	}
	        	if (childWidget.getType().equals("y2_axis")){
	        		y2AxisData = new ADLPlotData(childWidget);
	        	}

			}
			for (FileLine fileLine : adlWidget.getBody()){
				String bodyPart = fileLine.getLine();
				String[] row = bodyPart.trim().split("=");
				if (row.length < 2){
					throw new WrongADLFormatException(Messages.Label_WrongADLFormatException_Parameter_Begin + bodyPart + Messages.Label_WrongADLFormatException_Parameter_End);
				}
				if (FileLine.argEquals(row[0], "countpvname")){
					setCount(FileLine.getTrimmedValue(row[1]));
				}
				if (FileLine.argEquals(row[0], "erase")){
					setErase(FileLine.getTrimmedValue(row[1]));
				}
				if (FileLine.argEquals(row[0], "trigger")){
					setTrigger(FileLine.getTrimmedValue(row[1]));
				}
				if (FileLine.argEquals(row[0], "erasemode")){
					setEraseMode(FileLine.getTrimmedValue(row[1]));
				}
				if (FileLine.argEquals(row[0], "style")){
					setPlotStyle(FileLine.getTrimmedValue(row[1]));
				}
				if (FileLine.argEquals(row[0], "erase_oldest")){
					setPlotMode(FileLine.getTrimmedValue(row[1]));
				}
			}
		}
		catch (WrongADLFormatException ex) {
			ex.printStackTrace();
		}
	}

	/**
	 * @param count the count to set
	 */
	private void setCount(String count) {
		this.count = count;
	}

	/**
	 * @return the count
	 */
	public String getCount() {
		return count;
	}

	/**
	 * @param erase the erase to set
	 */
	private void setErase(String erase) {
		this.erase = erase;
	}

	/**
	 * @return the erase
	 */
	public String getErase() {
		return erase;
	}

	/**
	 * @param trigger the trigger to set
	 */
	private void setTrigger(String trigger) {
		this.trigger = trigger;
	}

	/**
	 * @return the trigger
	 */
	public String getTrigger() {
		return trigger;
	}

	/**
	 * @param eraseMode the eraseMode to set
	 */
	private void setEraseMode(String eraseMode) {
		this.eraseMode = eraseMode;
	}

	/**
	 * @return the eraseMode
	 */
	public String getEraseMode() {
		return eraseMode;
	}

	/**
	 * @param plotStyle the plotStyle to set
	 */
	private void setPlotStyle(String plotStyle) {
		this.plotStyle = plotStyle;
	}

	/**
	 * @return the plotStyle
	 */
	public String getPlotStyle() {
		return plotStyle;
	}

	/**
	 * @param plotMode the plotMode to set
	 */
	private void setPlotMode(String plotMode) {
		this.plotMode = plotMode;
	}

	/**
	 * @return the plotMode
	 */
	public String getPlotMode() {
		return plotMode;
	}

	/**
	 * @return the traces
	 */
	public ArrayList<ADLPlotTrace> getTraces() {
		return traces;
	}

	/**
	 * @return the xAxisData
	 */
	public ADLPlotData getxAxisData() {
		return xAxisData;
	}

	/**
	 * @return the y1AxisData
	 */
	public ADLPlotData getY1AxisData() {
		return y1AxisData;
	}

	/**
	 * @return the y2AxisData
	 */
	public ADLPlotData getY2AxisData() {
		return y2AxisData;
	}

}
