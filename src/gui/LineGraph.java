package gui;

import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.Font;
import java.awt.FontMetrics;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Point;
import java.awt.event.MouseEvent;
import java.awt.event.MouseMotionAdapter;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.LinkedList;
import java.util.List;

import javax.swing.JPanel;

import aj210328.Reader;

public class LineGraph extends JPanel {

	private List<Integer> data;
	private String graphName;
	private List<Point> points;
	private long timeSinceStart;

	private static final int pointsToShow = 20;

	private Point hoveredPoint;

	public LineGraph(List<Integer> data, String name, long timeSinceStart) {
		int fromIndex = Math.max(0, data.size() - pointsToShow);
		this.data = new ArrayList<Integer>();
		for (int i : data.subList(fromIndex, data.size())) {
			this.data.add(i);
		}
		this.graphName = name;
		this.timeSinceStart = timeSinceStart;

		addMouseMotionListener(new MouseMotionAdapter() {
			@Override
			public void mouseMoved(MouseEvent e) {
				// Check if the mouse is over a point
				int mouseX = e.getX();
				int mouseY = e.getY();

				for (Point p : points) {
					if (isMouseOverPoint(mouseX, mouseY, p)) {
						hoveredPoint = p;
						repaint(); // Trigger repaint to update the tooltip
						return;
					}
				}

				// If the mouse is not over any point, set hoveredPoint to null
				hoveredPoint = null;
				repaint();
			}
		});
	}

	public LineGraph(List<Integer> data) {
		this(data, "Test", Reader.getTimesRead() * Reader.getPeriod());
	}

	@Override
	protected void paintComponent(Graphics g) {
		super.paintComponent(g);

		// Need Graphics2D to set line thickness
		Graphics2D g2d = (Graphics2D) g;

		int width = getWidth();
		int height = getHeight();

		// Define usable area
		int xLeft = 80;
		int xRight = width - 20;
		int yTop = 50;
		int yBottom = height - 40;

		int usableHeight = yBottom - yTop - 10;

		int hGap = (xRight - xLeft) / pointsToShow;

		// Draw graph name
		g2d.setColor(Color.black);
		g2d.setFont(new Font("Arial", Font.BOLD, 25));
		drawCenteredString(g2d, graphName, new Point((xRight - xLeft) / 2, 15));

		// Draw x-axis
		g2d.setStroke(new BasicStroke(3));
		g2d.drawLine(xLeft, yBottom, xRight, yBottom);

		// Draw y-axis
		g2d.drawLine(xLeft, yBottom, xLeft, yTop);

		int minLargestValue = 5;

		// Calculate scaled values on graph size
		ArrayList<Integer> scaledValues = scaleValues(data, usableHeight, minLargestValue);

		// Calculate points from data
		points = new LinkedList<Point>();
		for (int i = 0; i < data.size(); i++) {
			int pointY = scaledValues.get(i);
			points.add(new Point(xLeft + i * hGap, yBottom - pointY));
		}

		int readerPeriod = Reader.getPeriod();
		long startTime = Math.max(0, timeSinceStart - pointsToShow * readerPeriod);

		// Draw x-axis marks
		g2d.setFont(new Font("Arial", Font.PLAIN, 18));
		for (int i = 0; i < data.size(); i++) {
			g2d.drawLine(xLeft + i * hGap, yBottom, xLeft + i * hGap, yBottom + 3);
			drawCenteredString(g2d, (startTime + i * readerPeriod) + "", new Point(xLeft + i * hGap, yBottom + 10));
		}

		// Draw y-axis marks
		// int differentYValues = (new HashSet<Integer>(data)).size();
		// int numOfMarks = Math.min(minLargestValue, differentYValues);
		int numOfMarks = minLargestValue;
		numOfMarks = Math.max(numOfMarks, 1);
		int maxElement = Math.max(maxElementInList(data), minLargestValue);
		for (int i = 0; i < numOfMarks + 1; i++) {
			g2d.setStroke(new BasicStroke(3));
			g2d.setColor(Color.black);
			int markY = yBottom - i * (usableHeight / numOfMarks);
			g2d.drawLine(xLeft - 3, markY, xLeft, markY);
			drawCenteredString(g2d, String.format("%.1f", i * (maxElement * 1.0 / numOfMarks)),
					new Point(xLeft - 25, markY));

			// Draw support lines
			g2d.setStroke(new BasicStroke(1));
			g2d.setColor(Color.LIGHT_GRAY);
			g2d.drawLine(xLeft, markY, xRight, markY);
		}

		// Connect points
		g2d.setStroke(new BasicStroke(3));
		g2d.setColor(Color.blue);
		Point leftPoint = null;
		for (Point p : points) {
			if (leftPoint != null) {
				g2d.drawLine(leftPoint.x, leftPoint.y, p.x, p.y);
			}
			leftPoint = p;
		}

		// Draw points
		for (Point p : points) {
			int index = points.indexOf(p);
			if (hoveredPoint != null && hoveredPoint.equals(p)) {
				// Draw a larger dot and display value when hovered
				g2d.setColor(Color.BLACK);
				drawDot(g2d, p.x, p.y, 15);
				drawCenteredString(g2d, "Value: " + data.get(index), new Point(p.x, p.y - 15));
			} else {
				g2d.setColor(Color.red);
				drawDot(g2d, p.x, p.y);
			}
		}
	}

	private static void drawCenteredString(Graphics g, String text, Point center) {
		FontMetrics fontMetrics = g.getFontMetrics();
		int x = center.x - fontMetrics.stringWidth(text) / 2;
		int y = center.y + fontMetrics.getAscent() / 2;
		g.drawString(text, x, y);
	}

	private void drawDot(Graphics g, int x, int y) {
		drawDot(g, x, y, 10);
	}

	private void drawDot(Graphics g, int x, int y, int size) {
		int dotSize = size;
		g.fillOval(x - dotSize / 2, y - dotSize / 2, dotSize, dotSize);
	}

	private static ArrayList<Integer> scaleValues(List<Integer> original, int maxHeight, int minLargestValue) {
		ArrayList<Integer> scaledValues = new ArrayList<>();

		List<Integer> originalValues = original;

		// int minValue = minElementInList(original);
		int minValue = 0;
		int maxValue = maxElementInList(original);

		if (maxValue < minLargestValue) {
			maxValue = minLargestValue;
		}

		// Calculate the scaling factor
		double scaleFactor = (double) maxHeight / (maxValue - minValue);

		// Scale each value and add it to the scaled list
		for (int value : originalValues) {
			int scaledValue = (int) ((value - minValue) * scaleFactor);
			scaledValues.add(scaledValue);
		}
		return scaledValues;
	}

	private static int maxElementInList(List<Integer> list) {
		if (list.isEmpty())
			return 0;
		int max = Integer.MIN_VALUE;
		for (int num : list) {
			if (num > max) {
				max = num;
			}
		}
		return max;
	}

	private static int minElementInList(List<Integer> list) {
		if (list.isEmpty())
			return 0;
		int min = Integer.MAX_VALUE;
		for (int num : list) {
			if (num < min) {
				min = num;
			}
		}
		return min;
	}

	private boolean isMouseOverPoint(int mouseX, int mouseY, Point point) {
		int dotSize = 5;
		return mouseX >= point.x - dotSize / 2 && mouseX <= point.x + dotSize / 2 && mouseY >= point.y - dotSize / 2
				&& mouseY <= point.y + dotSize / 2;
	}

}
