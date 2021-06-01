package bobo4.flowgraph.elements;

import java.util.HashMap;
import java.util.Map;

import com.mxgraph.util.mxConstants;

public class CellStyle {
	protected static Map<Object, Object> vertexDefaultStyle = new HashMap<Object, Object>() {

		private static final long serialVersionUID = 1L;

		{
			put(mxConstants.STYLE_FONTSIZE, 20);
			put(mxConstants.STYLE_FILLCOLOR, "D98282");
			put(mxConstants.STYLE_FONTSTYLE, mxConstants.FONT_BOLD);
			put(mxConstants.STYLE_SHAPE, mxConstants.SHAPE_ELLIPSE);
			put(mxConstants.STYLE_FONTCOLOR, "FFFFFF");
		}
	};

	protected static Map<Object, Object> vertexAfterStyle = new HashMap<Object, Object>() {
		/**
		 * 
		 */
		private static final long serialVersionUID = 1L;

		{
			put(mxConstants.STYLE_FILLCOLOR, "BD2BE8");
		}
	};
	protected static Map<Object, Object> vertexCurrentStyle = new HashMap<Object, Object>() {
		/**
		 * 
		 */
		private static final long serialVersionUID = 1L;

		{
			put(mxConstants.STYLE_FILLCOLOR, "65EE35");
		}
	};

	// All attribute of the edge style you can declare in this hash map
	protected static Map<Object, Object> edgeDefaultStyle = new HashMap<Object, Object>() {
		/**
		 * 
		 */
		private static final long serialVersionUID = 1L;

		{
			put(mxConstants.STYLE_STROKECOLOR, "000000");
		}
	};

	// All attribute of the edge style you can declare in this hash map
	protected static Map<Object, Object> edgeAfterStyle = new HashMap<Object, Object>() {
		/**
		 * 
		 */
		private static final long serialVersionUID = 1L;

		{
			put(mxConstants.STYLE_FILLCOLOR, "F9F900");
			put(mxConstants.STYLE_STROKECOLOR, "FD0000");

		}
	};

}
